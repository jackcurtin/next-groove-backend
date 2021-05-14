package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "mood", orphanRemoval = true)
    @JsonIgnore
    private List<Record> recordList;

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

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
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
