package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;
    @Column
    private String artist;

    @ManyToOne
    private Genre genre;
    @OneToOne
    private Tone tone;
    @OneToOne
    private Mood mood;
    @ManyToMany(mappedBy = "recordCollection")
    @JsonIgnore
    private List<UserProfile> recordOwners;

    private UserRatings userRatings = [];

    public Record() {
    }

    public Record(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Tone getTone() {
        return tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public List<UserProfile> getRecordOwners() {
        return recordOwners;
    }

    public void setRecordOwners(List<UserProfile> recordOwners) {
        this.recordOwners = recordOwners;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        System.out.println("override equals method running with ::: " + obj);
        if(!(obj instanceof Record))
            return false;
        Record objectToCompare = (Record) obj;
        return objectToCompare.title.equals(this.title) && objectToCompare.artist.equals(this.artist) &&
                (objectToCompare.id == this.id);
    }
}
