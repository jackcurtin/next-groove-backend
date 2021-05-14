package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Tone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToneRepository extends JpaRepository <Tone, Long> {
}
