package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.repositories.GenreRepository;
import com.example.nextgroovebackend.repositories.MoodRepository;
import com.example.nextgroovebackend.repositories.RecordRepository;
import com.example.nextgroovebackend.repositories.ToneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RecordService {
    private RecordRepository recordRepository;
    private GenreRepository genreRepository;
    private ToneRepository toneRepository;
    private MoodRepository moodRepository;

    @Autowired
    public void setRecordRepository (RecordRepository recordRepository) {this.recordRepository = recordRepository;}
    @Autowired
    public void setGenreRepository (GenreRepository genreRepository) {this.genreRepository = genreRepository;}
    @Autowired
    public void setToneRepository (ToneRepository toneRepository) {this.toneRepository = toneRepository;}
    @Autowired
    public void setMoodRepository (MoodRepository moodRepository) {this.moodRepository = moodRepository;}

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

    private Record assembleOrUpdateRecord(Record newRecord, Map <String, String> recordObject){
        Optional<Genre> genreOptional =
    }
}
