package ru.pmsoft.twitterkiller.domain.dto;

import ru.pmsoft.twitterkiller.domain.entity.Tweet;

import java.util.List;


public class TweetOutput {

    private final List<Tweet> tweets;

    public TweetOutput(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
