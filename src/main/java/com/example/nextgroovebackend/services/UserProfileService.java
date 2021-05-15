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
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
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

    public void removeFromCollection(Long recordId){
        System.out.println("UserProfile service calling removeFromCollection");
        UserProfile userProfile = getUserProfile();
        Optional<Record> recordOptional = recordRepository.findById(recordId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            List<Record> myCollection = userProfile.getRecordCollection();
            Record targetRecord = recordOptional.get();

            if(myCollection.contains(targetRecord))
                System.out.println("Contains the record");
            else
                System.out.println("Does not contain " + targetRecord);

            Optional<Record> myCopy = myCollection.stream().filter(record -> record.equals(targetRecord)).findFirst();

            if(myCopy.isPresent()){
                myCollection.remove(myCopy);
                userProfileRepository.save(userProfile);
            }
            else
                System.out.println("Record not found.");

            System.out.println(myCollection);

//            System.out.println("current record to delete: " + record);
//            System.out.println("current collection: " + myCollection);
//            System.out.println(record.getRecordOwners());
//            userProfile.getRecordCollection().remove(recordRepository.findById(recordId));
//            record.getRecordOwners().remove(userProfile);
//            System.out.println("after delete collection: " + userProfile.getRecordCollection());
//            System.out.println(record.getRecordOwners());
//            if(userProfile.getRecordCollection().contains(recordRepository.findById(recordId))){
//                myCollection.remove(record);
//            }else{
//                throw new InformationNotFoundException(record.getTitle() + " is not in your collection.");
//            }
        }

    }


    public UserProfile getUserProfile(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getUserProfile();
    }
}
