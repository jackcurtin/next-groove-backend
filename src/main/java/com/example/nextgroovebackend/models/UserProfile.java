package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinTable(
            name = "user_collections",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Record> recordCollection = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_tone_ratings",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "tone_rating_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tone> userToneRatings;

    @ManyToMany
    @JoinTable(
            name = "user_mood_ratings",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_rating_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Mood> userMoodRatings;

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

    public Set<Record> getRecordCollection() {
        return recordCollection;
    }

    public void setRecordCollection(Set<Record> recordCollection) {
        this.recordCollection = recordCollection;
    }

    public void removeFromCollection(Record record){
        this.recordCollection.remove(record);
        record.getRecordOwners().remove(this);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
