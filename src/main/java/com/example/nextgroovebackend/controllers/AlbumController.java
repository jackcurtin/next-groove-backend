package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Album;
import com.example.nextgroovebackend.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/albums")
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public void setAlbumService(AlbumService albumService) {this.albumService = albumService; }

    @PostMapping("/add")
    public Album createAlbum(@RequestBody Map<String, String> newAlbumObject){
        System.out.println("Album controller calling createRecord");
        return albumService.createAlbum(newAlbumObject);
    }

    @GetMapping("/browse/")
    public List<Album> getAllAlbums(){
        System.out.println("Album controller calling getAllRecords");
        return albumService.getAllAlbums();
    }

    @GetMapping("/browse/{albumId}")
    public Album getRecord(@PathVariable Long albumId){
        System.out.println("Album controller calling getRecord");
        return albumService.getAlbum(albumId);
    }

    @PostMapping("/addToCollection/{albumId}")
    public String addToCollection(@PathVariable Long albumId){
        System.out.println("Album controller calling addToCollection");
        return albumService.addToCollection(albumId);
    }

    @DeleteMapping("/browse/{albumId}")
    public void deleteRecord(@PathVariable Long albumId){
        System.out.println("Album controller calling deleteRecord");
        albumService.deleteAlbum(albumId);
    }

//    @PutMapping("/browse/{recordId}")
//    public Album updateRecord(@PathVariable Long recordId){
//        System.out.println("Album controller calling updateRecord");
//        return recordService.updateRecord(recordId);
//    }
}
