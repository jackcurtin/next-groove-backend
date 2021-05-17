package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Album;
import com.example.nextgroovebackend.models.Mood;
import com.example.nextgroovebackend.models.Tone;
import com.example.nextgroovebackend.models.UserProfile;
import com.example.nextgroovebackend.repositories.MoodRepository;
import com.example.nextgroovebackend.repositories.AlbumRepository;
import com.example.nextgroovebackend.repositories.ToneRepository;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private AlbumRepository albumRepository;
    private ToneRepository toneRepository;
    private MoodRepository moodRepository;
    private UserService userService;
    private AlbumService albumService;

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {this.userProfileRepository = userProfileRepository;}
    @Autowired
    public void setRecordRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    @Autowired
    public void setToneRepository(ToneRepository toneRepository) { this.toneRepository = toneRepository; }
    @Autowired
    public void setMoodRepository(MoodRepository moodRepository) { this.moodRepository = moodRepository; }
    @Autowired
    public void setRecordService(AlbumService albumService) { this.albumService = albumService; }

    public List<Album> getEntireCollection(){
        UserProfile userProfile = getUserProfile();
        if (userProfile.getAlbumCollection().isEmpty()){
            throw new InformationNotFoundException("Your collection is empty.");
        }else {
            return userProfile.getAlbumCollection();
        }
    }

    public Album selectRecord(Long albumId){
        System.out.println("UserProfile service calling select record");
        UserProfile userProfile = getUserProfile();
        Optional<Album> recordOptional = albumRepository.findById(albumId);
        if (recordOptional.isEmpty())
            throw new InformationNotFoundException("No record in database with id:" + albumId);
        else{
            List<Album> myCollection = userProfile.getAlbumCollection();
            Optional<Album> myCopy = myCollection.stream().filter(record -> record.equals(recordOptional.get())).findFirst();
            if(myCopy.isPresent())
                return myCopy.get();
            else
                throw new InformationNotFoundException(recordOptional.get().getTitle() + " is not in your collection.");
        }
    }

    public void removeFromCollection(Long albumId){
        System.out.println("UserProfile service calling removeFromCollection");
        UserProfile userProfile = getUserProfile();
        Optional<Album> recordOptional = albumRepository.findById(albumId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + albumId);
        } else {
            List<Album> myCollection = userProfile.getAlbumCollection();
            Optional<Album> myCopy = myCollection.stream().filter(record -> record.equals(recordOptional.get())).findFirst();
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

    public String rateRecord(Long albumId, Map <String, String> ratingObject) {
        System.out.println("Album service calling rateRecord");
        Optional<Album> recordOptional = albumRepository.findById(albumId);
        if (recordOptional.isEmpty()){
            throw new InformationNotFoundException("No record in database with id:" + albumId);
        } else {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            if (!userProfile.getAlbumCollection().contains(recordOptional.get())){
                throw new InformationNotFoundException("You can only rate records from your collection.");
            }
            else{
                Optional<Tone> toneOptional = toneRepository.findByUserProfileAndAlbum(userProfile, recordOptional.get());
                Optional<Mood> moodOptional = moodRepository.findByUserProfileAndAlbum(userProfile, recordOptional.get());
                if (toneOptional.isPresent() && moodOptional.isPresent())
                    throw new InformationExistsException("You have already rated this record!");
                else{
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
                    albumService.updateRecordAvgRatings(albumId);
                    return recordOptional.get().getTitle() + " have been given the following rating: " + newTone + "\n" + newMood;
                }
            }
        }
    }
}
