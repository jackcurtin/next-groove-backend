package com.example.nextgroovebackend;

import com.example.nextgroovebackend.models.Genre;
import com.example.nextgroovebackend.services.GenreService;
import com.example.nextgroovebackend.services.AlbumService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class NextGrooveBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextGrooveBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner genreRunner(GenreService genreService){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Genre>> typeReference = new TypeReference<List<Genre>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/genres.json");
            try{
                List<Genre> genres = mapper.readValue(inputStream, typeReference);
                genres.forEach(genre -> {
                    genreService.createGenre(genre);
                    System.out.println(genre.getName() + " created as genre.");
                });
            } catch (IOException e){
                System.out.println("Unable to save genres: " + e.getMessage());
            }
        };
    }

    @Bean
    CommandLineRunner recordRunner(AlbumService albumService){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String, String>>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/records.json");
            try{
                List<Map<String, String>> records = mapper.readValue(inputStream,typeReference);
                records.forEach(record -> {
                    albumService.createRecordFromJSON(record);
                    System.out.println(record.get("title") + " --- " + record.get("artist") + " ");
                });
            } catch(IOException e){
                System.out.println("Unable to save records: " + e.getMessage());
            }
        };
    }


}
