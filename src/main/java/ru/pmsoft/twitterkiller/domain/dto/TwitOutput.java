package ru.pmsoft.twitterkiller.domain.dto;

import ru.pmsoft.twitterkiller.domain.entity.Tweet;

import java.util.List;


public class TwitOutput {

    private List<Tweet> tweets;

    public TwitOutput(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
