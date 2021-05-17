package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne
    @JsonIgnore
    private Record record;

    @ManyToMany(mappedBy = "userToneRatings")
    @JsonIgnore
    private List<UserProfile> userToneRatings;


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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public List<UserProfile> getUserToneRatings() {
        return userToneRatings;
    }

    public void setUserToneRatings(List<UserProfile> userToneRatings) {
        this.userToneRatings = userToneRatings;
    }

    @Override
    public String toString() {
        return "Tone{" +
                "Id=" + Id +
                ", hifiLofiValue=" + hifiLofiValue +
                ", minimalDenseValue=" + minimalDenseValue +
                '}';
    }
}
