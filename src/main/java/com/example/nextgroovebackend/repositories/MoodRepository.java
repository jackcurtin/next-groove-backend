package com.example.nextgroovebackend.repositories;

import com.example.nextgroovebackend.models.Mood;
import com.example.nextgroovebackend.models.Album;
import com.example.nextgroovebackend.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoodRepository extends JpaRepository <Mood, Long> {
    Optional<Mood> findByUserProfileAndAlbum(UserProfile userProfile, Album album);
}
