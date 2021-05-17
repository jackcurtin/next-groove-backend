package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Mood;
import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.Tone;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.repositories.MoodRepository;
import com.example.nextgroovebackend.repositories.RecordRepository;
import com.example.nextgroovebackend.repositories.ToneRepository;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private RecordRepository recordRepository;
    private ToneRepository toneRepository;
    private MoodRepository moodRepository;
    private UserService userService;

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {this.userProfileRepository = userProfileRepository;}
    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }
    @Autowired
    public void setToneRepository(ToneRepository toneRepository) { this.toneRepository = toneRepository; }
    @Autowired
    public void setMoodRepository(MoodRepository moodRepository) { this.moodRepository = moodRepository; }


    public List<Record> getEntireCollection(){
        UserProfile userProfile = getUserProfile();
        if (userProfile.getRecordCollection().isEmpty()){
            throw new InformationNotFoundException("Your collection is empty.");
        }else {
            return userProfile.getRecordCollection();
        }
    }

    public Record selectRecord(Long recordId){
        System.out.println("UserProfile service calling select record");
        UserProfile userProfile = getUserProfile();
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty())
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        else{
            List<Record> myCollection = userProfile.getRecordCollection();
            Optional<Record> myCopy = myCollection.stream().filter(record -> record.equals(recordOptional.get())).findFirst();
            if(myCopy.isPresent())
                return myCopy.get();
            else
                throw new InformationNotFoundException(recordOptional.get().getTitle() + " is not in your collection.");
        }
    }

    public void removeFromCollection(Long recordId){
        System.out.println("UserProfile service calling removeFromCollection");
        UserProfile userProfile = getUserProfile();
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            List<Record> myCollection = userProfile.getRecordCollection();
            Optional<Record> myCopy = myCollection.stream().filter(record -> record.equals(recordOptional.get())).findFirst();
            if(myCopy.isPresent()){
                myCollection.remove(myCopy.get());
                userProfileRepository.save(userProfile);
            } else {
                throw new InformationNotFoundException(myCopy + " is not in your collection");
            }
        }
    }

    public UserProfile getUserProfile(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getUserProfile();
    }

    public String rateRecord(Long recordId, Map <String, String> ratingObject) {
        System.out.println("Record service calling rateRecord");
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            int userMDValue = Integer.parseInt(ratingObject.get("mdValue"));
            int userHiLoValue = Integer.parseInt(ratingObject.get("hiLoValue"));
            int userFSValue = Integer.parseInt(ratingObject.get("fsValue"));
            int userUDValue = Integer.parseInt(ratingObject.get("udValue"));
            Tone newTone = new Tone(userHiLoValue, userMDValue);
            newTone.setRecord(recordOptional.get());
            newTone.setUserProfile(userProfile);
            Mood newMood = new Mood(userFSValue, userUDValue);
            newMood.setRecord(recordOptional.get());
            newMood.setUserProfile(userProfile);
            toneRepository.save(newTone);
            moodRepository.save(newMood);
            return recordOptional.get().getTitle() + " have been given the following rating: " + newTone + "\n" + newMood;
        }
    }
}
