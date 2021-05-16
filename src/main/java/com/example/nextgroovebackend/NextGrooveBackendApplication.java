package com.example.nextgroovebackend;

import com.example.nextgroovebackend.models.Record;
import com.example.nextgroovebackend.services.RecordService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
    CommandLineRunner runner(RecordService recordService){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String, String>>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/records.json");
            try{
                List<Map<String, String>> records = mapper.readValue(inputStream,typeReference);
                records.forEach(record -> {
                    recordService.createRecord(record);
                    System.out.println(record.get("title") + " --- " + record.get("artist") + " created");
                });
            } catch(IOException e){
                System.out.println("Unable to save users: " + e.getMessage());
            }
        };
    }

}
