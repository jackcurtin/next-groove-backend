package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.*;
import com.example.nextgroovebackend.repositories.*;
import com.example.nextgroovebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlbumService {
    private AlbumRepository albumRepository;
    private GenreRepository genreRepository;
    private UserProfileRepository userProfileRepository;
    private ToneService toneService;
    private MoodService moodService;
    private MoodRepository moodRepository;
    private ToneRepository toneRepository;

    @Autowired
    public void setRecordRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Autowired
    public void setToneService(ToneService toneService) {
        this.toneService = toneService;
    }

    @Autowired
    public void setMoodService(MoodService moodService) {
        this.moodService = moodService;
    }

    @Autowired
    public void setMoodRepository(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Autowired
    public void setToneRepository(ToneRepository toneRepository) {
        this.toneRepository = toneRepository;
    }

    public Album createRecord(Map<String, String> recordObject) {
        System.out.println("Album service is calling createRecord");
        String recordTitle = recordObject.get("title").toUpperCase();
        String recordArtist = recordObject.get("artist").toUpperCase();
        Optional<Album> recordOptional = albumRepository.findByTitleAndArtist(recordTitle, recordArtist);
        if (recordOptional.isPresent()) {
            System.out.println("Updating --- " + recordOptional.get().getTitle());
            return assembleOrUpdateRecord(recordOptional.get(), recordObject);
//            throw new InformationExistsException("The following record already appears in our database: \n" +
//                    recordTitle + " -- " + recordArtist);
        } else {
            System.out.println("Creating new entry for " + recordObject.get("title"));
            Album newAlbum = new Album();
            return assembleOrUpdateRecord(newAlbum, recordObject);
        }
    }

    private Album assembleOrUpdateRecord(Album album, Map<String, String> recordObject) {
        System.out.println("Album service calling Assemble or Update Album");
        Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(recordObject.get("genre").toUpperCase());
        if (genreOptional.isEmpty()) {
            throw new InformationNotFoundException("Genre not found in our database.");
        } else {
            album.setTitle(recordObject.get("title").toUpperCase());
            album.setArtist(recordObject.get("artist").toUpperCase());
            album.setGenre(genreOptional.get());
            album.setCoverArtURL(recordObject.get("coverArtURL"));

//            Optional<Mood> moodOptional = moodRepository.findByFastSlowValueAndAndUpbeatDepressingValue();
            return albumRepository.save(album);
        }
    }

    public List<Album> getAllRecords() {
        System.out.println("Album service calling getAllRecords");
        List<Album> allAlbums = albumRepository.findAll();
        if (allAlbums.isEmpty()) {
            throw new InformationNotFoundException("No records here, my friend.");
        }
        return allAlbums;
    }

    public Album getRecord(Long recordId) {
        System.out.println("Album service calling getRecord");
        Optional<Album> recordOptional = albumRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            return recordOptional.get();
        }
    }

    public String addToCollection(Long recordId) {
        System.out.println("Album service calling addToCollection");
        Optional<Album> recordOptional = albumRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        } else {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            recordOptional.get().getAlbumOwners().add(userProfile);
            userProfile.getRecordCollection().add(recordOptional.get());
            userProfileRepository.save(userProfile);
            return recordOptional.get().getTitle() + " - " + recordOptional.get().getArtist() + " added to your collection";
        }
    }

    public Album updateRecordAvgRatings(Long recordId) {
        System.out.println("Album service calling updateRecord");
        Optional<Album> recordOptional = albumRepository.findById(recordId);
        if (recordOptional.isEmpty())
            throw new InformationNotFoundException("No record in database with id:" + recordId);
        else {
            recordOptional.get().calcAvgHLVal();
            recordOptional.get().calcAvgMDVal();
            recordOptional.get().calcAvgFSVal();
            recordOptional.get().calcAvgUDVal();
            return albumRepository.save(recordOptional.get());
        }
    }

    public Album createRecordFromJSON(Map<String, String> recordObject) {
        System.out.println("Album service is calling createRecord");
        String recordTitle = recordObject.get("title").toUpperCase();
        String recordArtist = recordObject.get("artist").toUpperCase();
        Optional<Album> recordOptional = albumRepository.findByTitleAndArtist(recordTitle, recordArtist);
        if (recordOptional.isPresent()) {
            System.out.println("Updating --- " + recordOptional.get().getTitle());
            return assembleOrUpdateRecord(recordOptional.get(), recordObject);
//            throw new InformationExistsException("The following record already appears in our database: \n" +
//                    recordTitle + " -- " + recordArtist);
        } else {
            System.out.println("Creating new entry for " + recordObject.get("title"));
            Album album = new Album();
            Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(recordObject.get("genre").toUpperCase());
            if (genreOptional.isEmpty()) {
                throw new InformationNotFoundException("Genre not found in our database.");
            } else {
                album.setTitle(recordObject.get("title").toUpperCase());
                album.setArtist(recordObject.get("artist").toUpperCase());
                album.setGenre(genreOptional.get());
                album.setCoverArtURL(recordObject.get("coverArtURL"));
                album.setAvgFSVal(Integer.parseInt(recordObject.get("fsValue")));
                album.setAvgUDVal(Integer.parseInt(recordObject.get("udValue")));
                album.setAvgHLVal(Integer.parseInt(recordObject.get("hiLoValue")));
                album.setAvgMDVal(Integer.parseInt(recordObject.get("mdValue")));
                return albumRepository.save(album);
            }
        }
    }

}
