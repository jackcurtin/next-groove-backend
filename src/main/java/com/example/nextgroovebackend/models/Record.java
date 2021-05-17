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
    @Column
    private String coverArtURL;
    @Column
    private int avgHLVal;
    @Column
    private int avgMDVal;
    @Column
    private int avgFSVal;
    @Column
    private int avgUDVal;

    @ManyToOne
    private Genre genre;
    @ManyToMany(mappedBy = "recordCollection")
    @JsonIgnore
    private List<UserProfile> recordOwners;

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    @JsonIgnore
    private List<Mood> moodRatings;

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    @JsonIgnore
    private List<Tone> toneRatings;

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

    public List<UserProfile> getRecordOwners() {
        return recordOwners;
    }

    public void setRecordOwners(List<UserProfile> recordOwners) {
        this.recordOwners = recordOwners;
    }

    public String getCoverArtURL() {
        return coverArtURL;
    }

    public void setCoverArtURL(String coverArtURL) {
        this.coverArtURL = coverArtURL;
    }

    public int getAvgHLVal() {
        return avgHLVal;
    }

    public void setAvgHLVal(int avgHLVal) {
        this.avgHLVal = avgHLVal;
    }

    public int getAvgMDVal() {
        return avgMDVal;
    }

    public void setAvgMDVal(int avgMDVal) {
        this.avgMDVal = avgMDVal;
    }

    public int getAvgFSVal() {
        return avgFSVal;
    }

    public void setAvgFSVal(int avgFSVal) {
        this.avgFSVal = avgFSVal;
    }

    public int getAvgUDVal() {
        return avgUDVal;
    }

    public void setAvgUDVal(int avgUDVal) {
        this.avgUDVal = avgUDVal;
    }

    public List<Mood> getMoodRatings() {
        return moodRatings;
    }

    public void setMoodRatings(List<Mood> moodRatings) {
        this.moodRatings = moodRatings;
    }

    public List<Tone> getToneRatings() {
        return toneRatings;
    }

    public void setToneRatings(List<Tone> toneRatings) {
        this.toneRatings = toneRatings;
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
