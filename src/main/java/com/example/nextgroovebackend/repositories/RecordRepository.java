package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository <Record, Long> {
}
