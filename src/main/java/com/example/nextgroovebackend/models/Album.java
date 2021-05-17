package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {

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
    @ManyToMany(mappedBy = "albumCollection")
    @JsonIgnore
    private List<UserProfile> recordOwners;

    @OneToMany(mappedBy = "album", orphanRemoval = true)
    @JsonIgnore
    private List<Mood> moodRatings;

    @OneToMany(mappedBy = "album", orphanRemoval = true)
    @JsonIgnore
    private List<Tone> toneRatings;

    public Album() {
    }

    public Album(String title, String artist) {
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

    public List<UserProfile> getAlbumOwners() {
        return recordOwners;
    }

    public void setAlbumOwners(List<UserProfile> recordOwners) {
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

    public void calcAvgHLVal() {
        System.out.println("setting HL val");
        ArrayList<Integer> hifiRatings = new ArrayList<Integer>();
        this.toneRatings.forEach(rating -> {
            hifiRatings.add(rating.getHifiLofiValue());
        });
        int sum = 0;
        for(int i = 0; i < hifiRatings.size(); i++){
            sum = sum + hifiRatings.get(i);
        }
        this.avgHLVal = (sum / hifiRatings.size());
    }

    public int getAvgMDVal() {
        return avgMDVal;
    }

    public void calcAvgMDVal() {
        System.out.println("setting MD val");
        ArrayList<Integer> mdRatings = new ArrayList<Integer>();
        this.toneRatings.forEach(rating -> {
            mdRatings.add(rating.getMinimalDenseValue());
        });
        int sum = 0;
        for(int i = 0; i < mdRatings.size(); i++){
            sum = sum + mdRatings.get(i);
        }
        this.avgMDVal = (sum / mdRatings.size());
    }

    public int getAvgFSVal() {
        return avgFSVal;
    }

    public void calcAvgFSVal() {
        System.out.println("setting FS val");
        ArrayList<Integer> fsRatings = new ArrayList<Integer>();
        this.moodRatings.forEach(rating -> {
            fsRatings.add(rating.getFastSlowValue());
        });
        int sum = 0;
        for(int i = 0; i < fsRatings.size(); i++){
            sum = sum + fsRatings.get(i);
        }
        this.avgFSVal = (sum / fsRatings.size());
    }

    public int getAvgUDVal() {
        return avgUDVal;
    }

    public void calcAvgUDVal() {
        System.out.println("setting UD val");
        ArrayList<Integer> udRatings = new ArrayList<Integer>();
        this.moodRatings.forEach(rating -> {
            udRatings.add(rating.getUpbeatDepressingValue());
        });
        int sum = 0;
        for(int i = 0; i < udRatings.size(); i++){
            sum = sum + udRatings.get(i);
        }
        this.avgUDVal = (sum / udRatings.size());
    }

    public void setAvgHLVal(int avgHLVal) {
        this.avgHLVal = avgHLVal;
    }

    public void setAvgMDVal(int avgMDVal) {
        this.avgMDVal = avgMDVal;
    }

    public void setAvgFSVal(int avgFSVal) {
        this.avgFSVal = avgFSVal;
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
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        System.out.println("override equals method running with ::: " + obj);
        if(!(obj instanceof Album))
            return false;
        Album objectToCompare = (Album) obj;
        return objectToCompare.title.equals(this.title) && objectToCompare.artist.equals(this.artist) &&
                (objectToCompare.id == this.id);
    }
}
