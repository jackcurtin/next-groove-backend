package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository <Album, Long> {
    Optional<Album> findByTitleAndArtist(String recordTitle, String recordArtist);
}
