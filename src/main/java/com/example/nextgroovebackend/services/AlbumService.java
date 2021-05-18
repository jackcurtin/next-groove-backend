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
    public void setalbumRepository(AlbumRepository albumRepository) {
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

    public Album createAlbum(Map<String, String> albumObject) {
        System.out.println("Album service is calling createAlbum");
        String albumTitle = albumObject.get("title").toUpperCase();
        String albumArtist = albumObject.get("artist").toUpperCase();
        Optional<Album> albumOptional = albumRepository.findByTitleAndArtist(albumTitle, albumArtist);
        if (albumOptional.isPresent()) {
            System.out.println("Updating --- " + albumOptional.get().getTitle());
            return assembleOrUpdateAlbum(albumOptional.get(), albumObject);
//            throw new InformationExistsException("The following album already appears in our database: \n" +
//                    albumTitle + " -- " + albumArtist);
        } else {
            System.out.println("Creating new entry for " + albumObject.get("title"));
            Album newAlbum = new Album();
            return assembleOrUpdateAlbum(newAlbum, albumObject);
        }
    }

    private Album assembleOrUpdateAlbum(Album album, Map<String, String> albumObject) {
        System.out.println("Album service calling Assemble or Update Album");
        Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(albumObject.get("genre").toUpperCase());
        if (genreOptional.isEmpty()) {
            throw new InformationNotFoundException("Genre not found in our database.");
        } else {
            album.setTitle(albumObject.get("title").toUpperCase());
            album.setArtist(albumObject.get("artist").toUpperCase());
            album.setGenre(genreOptional.get());
            album.setCoverArtURL(albumObject.get("coverArtURL"));
            return albumRepository.save(album);
        }
    }

    public List<Album> getAllAlbums() {
        System.out.println("Album service calling getAllAlbums");
        List<Album> allAlbums = albumRepository.findAll();
        if (allAlbums.isEmpty()) {
            throw new InformationNotFoundException("No albums here, my friend.");
        }
        return allAlbums;
    }

    public Album getAlbum(Long albumId) {
        System.out.println("Album service calling getalbum");
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            throw new InformationNotFoundException("No album in database with id:" + albumId);
        } else {
            return albumOptional.get();
        }
    }

    public String addToCollection(Long albumId) {
        System.out.println("Album service calling addToCollection");
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            throw new InformationNotFoundException("No album in database with id:" + albumId);
        } else {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserProfile userProfile = userDetails.getUser().getUserProfile();
            albumOptional.get().getAlbumOwners().add(userProfile);
            userProfile.getAlbumCollection().add(albumOptional.get());
            userProfileRepository.save(userProfile);
            return albumOptional.get().getTitle() + " - " + albumOptional.get().getArtist() + " added to your collection";
        }
    }

    public Album updateAlbumAvgRatings(Long albumId) {
        System.out.println("Album service calling updatealbum");
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty())
            throw new InformationNotFoundException("No album in database with id:" + albumId);
        else {
            albumOptional.get().calcAvgHLVal();
            albumOptional.get().calcAvgMDVal();
            albumOptional.get().calcAvgFSVal();
            albumOptional.get().calcAvgUDVal();
            return albumRepository.save(albumOptional.get());
        }
    }

    public Album createAlbumFromJSON(Map<String, String> albumObject) {
        System.out.println("Album service is calling createalbum");
        String albumTitle = albumObject.get("title").toUpperCase();
        String albumArtist = albumObject.get("artist").toUpperCase();
        Optional<Album> albumOptional = albumRepository.findByTitleAndArtist(albumTitle, albumArtist);
        if (albumOptional.isPresent()) {
            System.out.println("Updating --- " + albumOptional.get().getTitle());
            return assembleOrUpdateAlbum(albumOptional.get(), albumObject);
//            throw new InformationExistsException("The following album already appears in our database: \n" +
//                    albumTitle + " -- " + albumArtist);
        } else {
            System.out.println("Creating new entry for " + albumObject.get("title"));
            Album album = new Album();
            Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(albumObject.get("genre").toUpperCase());
            if (genreOptional.isEmpty()) {
                throw new InformationNotFoundException("Genre not found in our database.");
            } else {
                album.setTitle(albumObject.get("title").toUpperCase());
                album.setArtist(albumObject.get("artist").toUpperCase());
                album.setGenre(genreOptional.get());
                album.setCoverArtURL(albumObject.get("coverArtURL"));
                album.setAvgFSVal(Integer.parseInt(albumObject.get("fsValue")));
                album.setAvgUDVal(Integer.parseInt(albumObject.get("udValue")));
                album.setAvgHLVal(Integer.parseInt(albumObject.get("hiLoValue")));
                album.setAvgMDVal(Integer.parseInt(albumObject.get("mdValue")));
                return albumRepository.save(album);
            }
        }
    }

    public String deleteAlbum(Long albumId) {
        System.out.println("Album service calling deleteAlbum");
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()){
            throw new InformationNotFoundException("No album in our database with id: " + albumId);
        } else{
            albumRepository.deleteById(albumId);
            return albumId + " -- " + albumOptional.get().getTitle() + "deleted";
        }
    }
}
