package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository <Mood, Long> {
}
