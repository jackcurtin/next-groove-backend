package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.models.Mood;
import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.Tone;
import com.example.nextgroovebackend.repositories.GenreRepository;
import com.example.nextgroovebackend.repositories.MoodRepository;
import com.example.nextgroovebackend.repositories.RecordRepository;
import com.example.nextgroovebackend.repositories.ToneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecordService {
    private RecordRepository recordRepository;
    private GenreRepository genreRepository;
    private ToneService toneService;
    private MoodService moodService;

    @Autowired
    public void setRecordRepository (RecordRepository recordRepository) {this.recordRepository = recordRepository;}
    @Autowired
    public void setGenreRepository (GenreRepository genreRepository) {this.genreRepository = genreRepository;}
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
}
