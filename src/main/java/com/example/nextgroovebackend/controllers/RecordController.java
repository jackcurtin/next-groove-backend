package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/records")
public class RecordController {
    private RecordService recordService;

    @Autowired
    public void setRecordService(RecordService recordService) {this.recordService = recordService; }

    @PostMapping("/add")
    public Record createRecord(@RequestBody Map<String, String> newRecordObject){
        System.out.println("Record controller calling createRecord");
        return recordService.createRecord(newRecordObject);
    }

    @GetMapping("/browse/")
    public List<Record> getAllRecords(){
        System.out.println("Record controller calling getAllRecords");
        return recordService.getAllRecords();
    }

    @GetMapping("/browse/{recordId}")
    public Record getRecord(@PathVariable Long recordId){
        System.out.println("Record controller calling getRecord");
        return recordService.getRecord(recordId);
    }
}
