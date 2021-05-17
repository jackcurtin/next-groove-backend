package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository <Record, Long> {
    Optional<Record> findByTitleIgnoreCaseAndArtistIgnoreCase(String recordTitle, String recordArtist);
}
