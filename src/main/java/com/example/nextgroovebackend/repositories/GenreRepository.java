package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository <Genre, Long> {
}
