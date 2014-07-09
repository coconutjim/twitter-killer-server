package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;


public interface SessionRepository {
    void create(Session session);

    Session getByUser(User user);

    Session getByToken(String token);

    void delete(Session session);
}