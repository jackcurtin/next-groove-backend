package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository <Genre, Long> {
    Optional<Genre> findByName(String name);
}
