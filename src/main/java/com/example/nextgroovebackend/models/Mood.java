package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.action.internal.OrphanRemovalAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private int fastSlowValue;
    @Column
    private int upbeatDepressingValue;

    @OneToOne
    @JsonIgnore
    private Record record;

    @ManyToMany(mappedBy = "userMoodRatings")
    @JsonIgnore
    private List<UserProfile> userMoodRatings;

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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public List<UserProfile> getUserMoodRatings() {
        return userMoodRatings;
    }

    public void setUserMoodRatings(List<UserProfile> userMoodRatings) {
        this.userMoodRatings = userMoodRatings;
    }

    @Override
    public String toString() {
        return "Mood{" +
                "Id=" + Id +
                ", fastSlowValue=" + fastSlowValue +
                ", upbeatDepressingValue=" + upbeatDepressingValue +
                '}';
    }
}
