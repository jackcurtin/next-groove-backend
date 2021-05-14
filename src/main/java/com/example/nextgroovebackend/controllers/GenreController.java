package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService){this.genreService = genreService;}

    @PostMapping("/add")
    public Genre createGenre(@RequestBody Genre genreObject){
        System.out.println("Genre controller calling createGenre");
        return genreService.createGenre(genreObject);
    }
}
