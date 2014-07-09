package ru.pmsoft.twitterkiller.domain.dto;

import ru.pmsoft.twitterkiller.domain.entity.Tweet;

import java.util.List;


public class TwitOutput {

    private List<Tweet> allTweets;

    public TwitOutput(List<Tweet> allTweets) {
        this.allTweets = allTweets;
    }

    public List<Tweet> getAllTweets() {
        return allTweets;
    }
}
