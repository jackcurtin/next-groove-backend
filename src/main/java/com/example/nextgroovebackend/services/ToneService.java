package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.models.Tone;
import com.example.nextgroovebackend.repositories.ToneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ToneService {
    private ToneRepository toneRepository;

    @Autowired
    public void setToneRepository(ToneRepository toneRepository) { this.toneRepository = toneRepository; }

    public Tone createTone(Map<String, String> toneObject){
        if(toneObject.get("mdValue").isEmpty() || toneObject.get("hiLoValue").isEmpty()){
            throw new CannotBeNullException("Tone values are required for record creation");
        } else{
            int md = Integer.parseInt(toneObject.get("mdValue"));
            int hiLo = Integer.parseInt(toneObject.get("hiLoValue"));
            Tone newTone = new Tone(hiLo, md);
            return toneRepository.save(newTone);
        }
    }
}
