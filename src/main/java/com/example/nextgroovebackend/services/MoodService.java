package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.models.Mood;
import com.example.nextgroovebackend.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MoodService {
    private MoodRepository moodRepository;

    @Autowired
    public void setMoodRepository(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public Mood createMood(Map<String, String> moodObject){
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
