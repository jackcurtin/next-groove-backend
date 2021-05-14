package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    private RecordRepository recordRepository;

    @Autowired
    public void setRecordRepository (RecordRepository recordRepository) {this.recordRepository = recordRepository;}
}
