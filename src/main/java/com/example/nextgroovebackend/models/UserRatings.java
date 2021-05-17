package com.example.nextgroovebackend.models;

import javax.persistence.OneToOne;
import java.util.List;

public class UserRatings {
    private List<Tone> userToneRatings;
    private List<Mood> userMoodRatings;

    public UserRatings() {
    }

    public UserRatings(List<Tone> userToneRatings, List<Mood> userMoodRatings) {
        this.userToneRatings = userToneRatings;
        this.userMoodRatings = userMoodRatings;
    }

    public List<Tone> getUserToneRatings() {
        return userToneRatings;
    }

    public void setUserToneRatings(List<Tone> userToneRatings) {
        this.userToneRatings = userToneRatings;
    }

    public List<Mood> getUserMoodRatings() {
        return userMoodRatings;
    }

    public void setUserMoodRatings(List<Mood> userMoodRatings) {
        this.userMoodRatings = userMoodRatings;
    }
}
