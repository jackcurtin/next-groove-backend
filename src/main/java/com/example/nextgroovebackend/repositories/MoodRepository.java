package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoodRepository extends JpaRepository <Mood, Long> {
    Optional<Mood> findByFastSlowValueAndAndUpbeatDepressingValue(int fsVal, int udVal);
}
