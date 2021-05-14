package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}
