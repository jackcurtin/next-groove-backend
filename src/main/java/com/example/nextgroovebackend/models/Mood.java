package com.example.nextgroovebackend.models;

import javax.persistence.*;

@Entity
@Table (name = "moods")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private int fastSlowValue;
    @Column
    private int upbeatDepressingValue;

    @ManyToOne
    private UserProfile userProfile;

    @ManyToOne
    private Album album;

    public Mood() {
    }

    public Mood(int fastSlowValue, int upbeatDepressingValue) {
        this.fastSlowValue = fastSlowValue;
        this.upbeatDepressingValue = upbeatDepressingValue;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getFastSlowValue() {
        return fastSlowValue;
    }

    public void setFastSlowValue(int fastSlowValue) {
        this.fastSlowValue = fastSlowValue;
    }

    public int getUpbeatDepressingValue() {
        return upbeatDepressingValue;
    }

    public void setUpbeatDepressingValue(int upbeatDepressingValue) {
        this.upbeatDepressingValue = upbeatDepressingValue;
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
        return "Mood{" +
                "Id=" + Id +
                ", fastSlowValue=" + fastSlowValue +
                ", upbeatDepressingValue=" + upbeatDepressingValue +
                ", userProfile=" + userProfile +
                ", album=" + album +
                '}';
    }
}
