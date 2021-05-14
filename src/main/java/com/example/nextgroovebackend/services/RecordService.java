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

    private Record assembleOrUpdateRecord(Record record, Map <String, String> recordObject){
        System.out.println("Record service calling Assemble or Update Record");
        Optional<Genre> genreOptional = genreRepository.findByName(recordObject.get("genre"));
        if(genreOptional.isEmpty()){
            throw new InformationNotFoundException("Genre not found in our database.");
        } else{
            record.setTitle(recordObject.get("title"));
            record.setArtist(recordObject.get("artist"));
            record.setGenre(genreOptional.get());
            record.setTone(createTone(recordObject));
            record.setMood(createMood(recordObject));
            return recordRepository.save(record);
        }
    }

    private Tone createTone(Map <String, String> toneObject){
        if(toneObject.get("mdValue").isEmpty() || toneObject.get("hiLoValue").isEmpty()){
            throw new CannotBeNullException("Tone values are required for record creation");
        } else{
            int md = Integer.parseInt(toneObject.get("mdValue"));
            int hiLo = Integer.parseInt(toneObject.get("hiLoValue"));
            Tone newTone = new Tone(hiLo, md);
            return toneRepository.save(newTone);
        }
    }

    private Mood createMood(Map <String, String> moodObject){
        if(moodObject.get("fsValue").isEmpty() || moodObject.get("udValue").isEmpty()){
            throw new CannotBeNullException("Mood values are required for record creation");
        } else{
            int fs = Integer.parseInt(moodObject.get("fsValue"));
            int ud = Integer.parseInt(moodObject.get("udValue"));
            Mood newMood = new Mood(fs, ud);
            return moodRepository.save(newMood);
        }
    }
}
