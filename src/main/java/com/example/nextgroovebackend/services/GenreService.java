package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre createGenre(Genre genreObject){
        System.out.println("Genre service calling createGenre");
        Optional<Genre> genreOptional = genreRepository.findByName(genreObject.getName());
        if(genreOptional.isPresent()){
            throw new InformationExistsException("The genre of " + genreObject.getName() + " is already in our database");
        } else {
            if(genreObject.getName().length()<1){
                throw new CannotBeNullException("You must provide a name for the genre.");
            } else {
                return genreRepository.save(genreObject);
            }
        }
    }
}
