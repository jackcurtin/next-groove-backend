package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.CannotBeNullException;
import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.exceptions.InformationNotFoundException;
import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Genre> genreOptional = genreRepository.findByNameIgnoreCase(genreObject.getName());
        if(genreOptional.isPresent()){
            System.out.println("genre already exists");
            return genreRepository.save(genreOptional.get());
//            throw new InformationExistsException("The genre of " + genreObject.getName() + " is already in our database");
        } else {
            if(genreObject.getName().length()<1){
                throw new CannotBeNullException("You must provide a name for the genre.");
            } else {
                genreObject.setName(genreObject.getName().toUpperCase());
                return genreRepository.save(genreObject);
            }
        }
    }

    public List<Genre> getAllGenres() {
        System.out.println("Genre service calling getAllGenres");
        List<Genre> allGenres = genreRepository.findAll();
        if(allGenres.isEmpty()){
            throw new InformationNotFoundException("there are no genres");
        } else{
            return allGenres;
        }
    }

    public Genre getGenre(Long genreId){
        System.out.println("Genre service calling getGenre");
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if (genreOptional.isEmpty()){
            throw new InformationNotFoundException("No genre with id: " + genreId);
        } else{
            return genreOptional.get();
        }
    }
}
