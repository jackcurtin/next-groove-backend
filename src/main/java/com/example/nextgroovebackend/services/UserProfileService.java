package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.repositories.RecordRepository;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private RecordRepository recordRepository;
    private UserService userService;

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

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
        System.out.println("UserProfile service calling removeFromCollection.");
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
                throw new InformationNotFoundException(recordOptional.get().getTitle() + " is not in your collection");
            }
        }
    }

    public UserProfile getUserProfile(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getUserProfile();
    }
}
