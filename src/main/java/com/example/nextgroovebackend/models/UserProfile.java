package com.example.nextgroovebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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

    @OneToMany (mappedBy = "userProfile", orphanRemoval = true)
    @JsonIgnore
    private Set <Mood> moodRatings;

    @OneToMany (mappedBy = "userProfile", orphanRemoval = true)
    @JsonIgnore
    private Set <Tone> toneRatings;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinTable(
            name = "user_collections",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albumCollection;

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

    public List<Album> getAlbumCollection() {
        return albumCollection;
    }

    public void setRecordCollection(List<Album> albumCollection) {
        this.albumCollection = albumCollection;
    }

    public void removeFromCollection(Album album){
        this.albumCollection.remove(album);
        album.getAlbumOwners().remove(this);
    }
}
