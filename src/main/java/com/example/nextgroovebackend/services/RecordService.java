package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.*;
import com.example.nextgroovebackend.repositories.*;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecordService {
    private RecordRepository recordRepository;
    private GenreRepository genreRepository;
    private UserProfileRepository userProfileRepository;
    private ToneService toneService;
    private MoodService moodService;

    @Autowired
    public void setRecordRepository (RecordRepository recordRepository) {this.recordRepository = recordRepository;}
    @Autowired
    public void setGenreRepository (GenreRepository genreRepository) {this.genreRepository = genreRepository;}
    @Autowired
    public void setUserProfileRepository (UserProfileRepository userProfileRepository) {this.userProfileRepository = userProfileRepository;}
    @Autowired
    public void setToneService (ToneService toneService) {this.toneService = toneService;}
    @Autowired
    public void setMoodService (MoodService moodService) {this.moodService = moodService;}

    public Record createRecord(Map<String, String> recordObject){
        System.out.println("Record service is calling createRecord");
        String recordTitle = recordObject.get("title").toUpperCase();
        String recordArtist = recordObject.get("artist").toUpperCase();
        Optional<Record> recordOptional = recordRepository.findByTitleAndArtist(recordTitle, recordArtist);
        if(recordOptional.isPresent()){
            System.out.println("Updating --- " + recordOptional.get().getTitle());
            return assembleOrUpdateRecord(recordOptional.get(), recordObject);
//            throw new InformationExistsException("The following record already appears in our database: \n" +
//                    recordTitle + " -- " + recordArtist);
        }
        else{
            System.out.println("Creating new entry for " + recordObject.get("title"));
            Record newRecord = new Record();
            return assembleOrUpdateRecord(newRecord, recordObject);
        }
    }

    private Record assembleOrUpdateRecord(Record record, Map <String, String> recordObject){
        System.out.println("Record service calling Assemble or Update Record");
        Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(recordObject.get("genre").toUpperCase());
        if(genreOptional.isEmpty()){
            throw new InformationNotFoundException("Genre not found in our database.");
        } else{
            record.setTitle(recordObject.get("title").toUpperCase());
            record.setArtist(recordObject.get("artist").toUpperCase());
            record.setGenre(genreOptional.get());
            record.setCoverArtURL(recordObject.get("coverArtURL"));

//            Optional<Mood> moodOptional = moodRepository.findByFastSlowValueAndAndUpbeatDepressingValue();

            record.setMood(moodService.createMood(recordObject));
            record.setTone(toneService.createTone(recordObject));
            return recordRepository.save(record);
        }
    }

    public List<Record> getAllRecords(){
        System.out.println("Record service calling getAllRecords");
        List<Record> allRecords = recordRepository.findAll();
        if(allRecords.isEmpty()){
            throw new InformationNotFoundException("No records here, my friend.");
        }
        return allRecords;
    }

    public Record getRecord(Long recordId){
        System.out.println("Record service calling getRecord");
        Optional <Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        }else{
            return recordOptional.get();
        }
    }

    public String addToCollection(Long recordId){
        System.out.println("Record service calling addToCollection");
        Optional <Record> recordOptional = recordRepository.findById(recordId);
        if(recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        }else{
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            recordOptional.get().getRecordOwners().add(userProfile);
            userProfile.getRecordCollection().add(recordOptional.get());
            userProfileRepository.save(userProfile);
            return recordOptional.get().getTitle() + " - " + recordOptional.get().getArtist() + " added to your collection";
        }
    }

//    public Record rateRecord(Long recordId, Map <String, String> ratingObject) {
//        System.out.println("Record service calling rateRecord");
//        Optional<Record> recordOptional = recordRepository.findById(recordId);
//        if (recordOptional.isEmpty()){
//            throw new InformationNotFoundException("No record in database with id:" + recordId);
//        } else {
//            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            UserProfile userProfile = userDetails.getUser().getUserProfile();
//            int userMDValue = Integer.parseInt(ratingObject.get("mdValue"));
//            int userHiLoValue = Integer.parseInt(ratingObject.get("hiLoValue"));
//            int userFSValue = Integer.parseInt(ratingObject.get("fsValue"));
//            int userUDValue = Integer.parseInt(ratingObject.get("udValue"));
//            Tone userToneRating = new Tone(userHiLoValue, userMDValue);
//            Mood userMoodRating = new Mood(userFSValue, userUDValue);
//
//        }
//    }
}
