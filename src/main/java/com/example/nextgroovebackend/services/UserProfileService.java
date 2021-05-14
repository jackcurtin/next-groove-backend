package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private UserService userService;

    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<Record> getEntireCollection(){
        UserProfile userProfile = getProfile();
        return userProfile.getRecordCollection();
    }

    public UserProfile getProfile(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getUserProfile();
    }
}
