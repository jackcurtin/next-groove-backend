package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.*;
import com.example.nextgroovebackend.repositories.*;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


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
    private MoodRepository moodRepository;
    private ToneRepository toneRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Autowired
    public void setToneService(ToneService toneService) {
        this.toneService = toneService;
    }

    @Autowired
    public void setMoodService(MoodService moodService) {
        this.moodService = moodService;
    }

    @Autowired
    public void setMoodRepository(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Autowired
    public void setToneRepository(ToneRepository toneRepository) {
        this.toneRepository = toneRepository;
    }

    public Record createRecord(Map<String, String> recordObject) {
        System.out.println("Record service is calling createRecord");
        String recordTitle = recordObject.get("title").toUpperCase();
        String recordArtist = recordObject.get("artist").toUpperCase();
        Optional<Record> recordOptional = recordRepository.findByTitleAndArtist(recordTitle, recordArtist);
        if (recordOptional.isPresent()) {
            System.out.println("Updating --- " + recordOptional.get().getTitle());
            return assembleOrUpdateRecord(recordOptional.get(), recordObject);
//            throw new InformationExistsException("The following record already appears in our database: \n" +
//                    recordTitle + " -- " + recordArtist);
        } else {
            System.out.println("Creating new entry for " + recordObject.get("title"));
            Record newRecord = new Record();
            return assembleOrUpdateRecord(newRecord, recordObject);
        }
    }

    private Record assembleOrUpdateRecord(Record record, Map<String, String> recordObject) {
        System.out.println("Record service calling Assemble or Update Record");
        Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(recordObject.get("genre").toUpperCase());
        if (genreOptional.isEmpty()) {
            throw new InformationNotFoundException("Genre not found in our database.");
        } else {
            record.setTitle(recordObject.get("title").toUpperCase());
            record.setArtist(recordObject.get("artist").toUpperCase());
            record.setGenre(genreOptional.get());
            record.setCoverArtURL(recordObject.get("coverArtURL"));
            Optional <List<Tone>> toneRatings = toneRepository.findAllByRecordTitle(record.getTitle());
            if (toneRatings.isEmpty()){
                int fs = Integer.parseInt(recordObject.get("fsValue"));
                int ud = Integer.parseInt(recordObject.get("udValue"));
                int hl = Integer.parseInt(recordObject.get("hiLoValue"));
                int md = Integer.parseInt(recordObject.get("md" + "Value"));
                record.setAvgFSVal(fs);
                record.setAvgUDVal(ud);
                record.setAvgHLVal(hl);
                record.setAvgMDVal(md);
            }
            System.out.println(record);
            Record savedRecord = recordRepository.save(record);
            return updateRecordAvgRatings(savedRecord.getId());
        }
    }

    public List<Record> getAllRecords() {
        System.out.println("Record service calling getAllRecords");
        List<Record> allRecords = recordRepository.findAll();
        if (allRecords.isEmpty()) {
            throw new InformationNotFoundException("No records here, my friend.");
        }
        return allRecords;
    }

    public Record getRecord(Long recordId) {
        System.out.println("Record service calling getRecord");
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            return recordOptional.get();
        }
    }

    public String addToCollection(Long recordId) {
        System.out.println("Record service calling addToCollection");
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            recordOptional.get().getRecordOwners().add(userProfile);
            userProfile.getRecordCollection().add(recordOptional.get());
            userProfileRepository.save(userProfile);
            return recordOptional.get().getTitle() + " - " + recordOptional.get().getArtist() + " added to your collection";
        }
    }

    public Record updateRecordAvgRatings(Long recordId) {
        System.out.println("Record service calling updateRecord");
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty())
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        else {
            recordOptional.get().calcAvgHLVal();
            recordOptional.get().calcAvgMDVal();
            recordOptional.get().calcAvgFSVal();
            recordOptional.get().calcAvgUDVal();
            return recordRepository.save(recordOptional.get());
        }
    }
}
