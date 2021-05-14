package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "profiles")
public class UserProfile {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_collections",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Record> recordCollection;

    public UserProfile() {
    }

    public UserProfile(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Record> getRecordCollection() {
        return recordCollection;
    }

    public void setRecordCollection(List<Record> recordCollection) {
        this.recordCollection = recordCollection;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
