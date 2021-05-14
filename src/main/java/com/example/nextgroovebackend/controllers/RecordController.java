package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/records")
public class RecordController {
    private RecordService recordService;

    @Autowired
    public void setRecordService(RecordService recordService) {this.recordService = recordService; }
}
