package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Album;
import com.example.nextgroovebackend.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/records")
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public void setRecordService(AlbumService albumService) {this.albumService = albumService; }

    @PostMapping("/add")
    public Album createRecord(@RequestBody Map<String, String> newRecordObject){
        System.out.println("Album controller calling createRecord");
        return albumService.createRecord(newRecordObject);
    }

    @GetMapping("/browse/")
    public List<Album> getAllRecords(){
        System.out.println("Album controller calling getAllRecords");
        return albumService.getAllRecords();
    }

    @GetMapping("/browse/{recordId}")
    public Album getRecord(@PathVariable Long recordId){
        System.out.println("Album controller calling getRecord");
        return albumService.getRecord(recordId);
    }

    @PostMapping("/addToCollection/{recordId}")
    public String addToCollection(@PathVariable Long recordId){
        System.out.println("Album controller calling addToCollection");
        return albumService.addToCollection(recordId);
    }

//    @PutMapping("/browse/{recordId}")
//    public Album updateRecord(@PathVariable Long recordId){
//        System.out.println("Album controller calling updateRecord");
//        return recordService.updateRecord(recordId);
//    }
}
