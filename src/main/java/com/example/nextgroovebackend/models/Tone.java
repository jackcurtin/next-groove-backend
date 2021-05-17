package com.example.nextgroovebackend.models;

import javax.persistence.*;

@Entity
@Table(name = "Tones")
public class Tone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private int hifiLofiValue;
    @Column
    private int minimalDenseValue;

    @ManyToOne
    private UserProfile userProfile;

    @ManyToOne
    private Album album;


    public Tone() {
    }

    public Tone(int hifiLofiValue, int minimalDenseValue) {
        this.hifiLofiValue = hifiLofiValue;
        this.minimalDenseValue = minimalDenseValue;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getHifiLofiValue() {
        return hifiLofiValue;
    }

    public void setHifiLofiValue(int hifiLofiValue) {
        this.hifiLofiValue = hifiLofiValue;
    }

    public int getMinimalDenseValue() {
        return minimalDenseValue;
    }

    public void setMinimalDenseValue(int minimalDenseValue) {
        this.minimalDenseValue = minimalDenseValue;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Album getRecord() {
        return album;
    }

    public void setRecord(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Tone{" +
                "Id=" + Id +
                ", hifiLofiValue=" + hifiLofiValue +
                ", minimalDenseValue=" + minimalDenseValue +
                ", userProfile=" + userProfile +
                ", album=" + album +
                '}';
    }
}
