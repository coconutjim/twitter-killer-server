package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.Twitt;

import java.util.List;

/**
 * Created by Anton on 07/07/2014.
 */
public interface TwittRepository {
    void save(Twitt twitt);
    List<Twitt> getAllByLogin(String name);
    Iterable<Twitt> values();
}
