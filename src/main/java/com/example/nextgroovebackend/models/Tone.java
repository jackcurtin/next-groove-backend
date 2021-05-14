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

    @OneToMany
    @JsonIgnore
    private List<Record> recordList;

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

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
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
