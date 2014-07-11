package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.Tweet;

import java.util.List;

public interface TweetRepository {
    void createOrUpdate(Tweet tweet);

    List<Tweet> getAllByLogin(String name);

    public Tweet getById(int tweetId);
}
