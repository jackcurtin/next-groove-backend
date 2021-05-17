package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    private UserProfileService userProfileService;

    @Autowired
    public void setUserProfileService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public UserProfile getUserProfile(){
        System.out.println("User controller calling getUserProfile");
        return userProfileService.getUserProfile();
    }

    @GetMapping("/collection/{recordId}")
    public Record selectRecord(@PathVariable Long recordId){
        System.out.println("UserProfile controller calling selectRecord");
        return userProfileService.selectRecord(recordId);
    }


    @GetMapping("/collection")
    public List<Record> getEntireCollection(){
        System.out.println("UserProfile controller calling getEntireCollection");
        return userProfileService.getEntireCollection();
    }

    @DeleteMapping("/collection/{recordId}")
    public void removeFromCollection(@PathVariable Long recordId){
        System.out.println("UserProfile controller calling removeFromCollection");
        userProfileService.removeFromCollection(recordId);
    }

    @PutMapping("/collection/{recordId}/rate")
    public String rateRecord(@PathVariable Long recordId, @RequestBody Map<String, String> ratingObject) {
        System.out.println("Record controller calling rateRecord");
        return userProfileService.rateRecord(recordId, ratingObject);
    }
}
