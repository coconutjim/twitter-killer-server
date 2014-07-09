package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.Tweet;

import java.util.List;

/**
 * Created by Anton on 07/07/2014.
 */
public interface TweetRepository {
    void save(Tweet tweet);
    List<Tweet> getAllByLogin(String name);
    public Tweet getAllById(int tweetId);
}
