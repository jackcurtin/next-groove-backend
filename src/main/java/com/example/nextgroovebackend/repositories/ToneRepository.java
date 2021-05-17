package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.models.Tone;
import com.example.nextgroovebackend.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToneRepository extends JpaRepository <Tone, Long> {
    Optional<Tone> findByUserProfileAndRecord(UserProfile userProfile, Record record);
}
