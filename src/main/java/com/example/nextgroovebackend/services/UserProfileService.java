package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.repositories.RecordRepository;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private RecordRepository recordRepository;
    private UserService userService;

    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

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
            if(myCollection.contains(recordOptional.get())){
                myCollection.remove(recordOptional.get());
            }else{
                throw new InformationNotFoundException(recordOptional.get().getTitle() + " is not in your collection.");
            }
        }

    }


    public UserProfile getUserProfile(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getUserProfile();
    }
}
