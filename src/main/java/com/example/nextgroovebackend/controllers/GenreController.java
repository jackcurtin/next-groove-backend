package com.example.nextgroovebackend.controllers;

import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {
    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService){this.genreService = genreService;}

    @GetMapping
    public List<Genre> getAllGenres(){
        System.out.println("Genre controller calling getAllGenres");
        return genreService.getAllGenres();
    }

    @GetMapping("/{genreId}")
    public Genre getGenre(@PathVariable Long genreId){
        System.out.println("Genre controller calling getGenre");
        return genreService.getGenre(genreId);
    }

    @PostMapping("/add")
    public Genre createGenre(@RequestBody Genre genreObject){
        System.out.println("Genre controller calling createGenre");
        return genreService.createGenre(genreObject);
    }
}
