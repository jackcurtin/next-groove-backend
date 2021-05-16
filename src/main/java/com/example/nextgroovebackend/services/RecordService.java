package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.*;
import com.example.nextgroovebackend.repositories.*;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
        String recordTitle = recordObject.get("title");
        String recordArtist = recordObject.get("artist");
        Optional<Record> recordOptional = recordRepository.findByTitleAndArtist(recordTitle, recordArtist);
        if(recordOptional.isPresent() &&
                recordOptional.get().getTitle().equalsIgnoreCase(recordTitle) &&
                recordOptional.get().getArtist().equalsIgnoreCase(recordArtist)){
            throw new InformationExistsException("The following record already appears in our database: \n" +
                    recordTitle + " -- " + recordArtist);
        }
        else{
            Record newRecord = new Record();
            return assembleOrUpdateRecord(newRecord, recordObject);
        }
    }

    public Record createRecordFromJson(Record recordObject){
        System.out.println("Record service is calling createRecord");
        Optional <Record> recordOptional = recordRepository.findByTitleAndArtist(recordObject.getTitle(), recordObject.getArtist());
        if(recordOptional.isPresent()){
            throw new InformationExistsException("The following record already appears in our database: \n" +
                    recordOptional.get().getTitle() + " -- " + recordOptional.get().getArtist());
        }else{
            Optional<Genre> genreOptional = genreRepository.findByName(recordObject.getGenre().getName());
            if(genreOptional.isEmpty()){
                throw new InformationNotFoundException("Genre not found in database");
            } else{
                return recordRepository.save(recordObject);
            }
        }
    }

    private Record assembleOrUpdateRecord(Record record, Map <String, String> recordObject){
        System.out.println("Record service calling Assemble or Update Record");
        Optional<Genre> genreOptional = genreRepository.findByName(recordObject.get("genre"));
        if(genreOptional.isEmpty()){
            throw new InformationNotFoundException("Genre not found in our database.");
        } else{
            record.setTitle(recordObject.get("title"));
            record.setArtist(recordObject.get("artist"));
            record.setGenre(genreOptional.get());
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
//            userProfile.
//            userProfile.getUserToneRatings().add(userToneRating);
//            userProfile.getUserMoodRatings().add(userMoodRating);
//        }
//    }
}
