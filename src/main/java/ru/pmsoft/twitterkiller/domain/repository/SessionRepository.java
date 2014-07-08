package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;


public interface SessionRepository {
    void createOrUpdate(Session session);

    Iterable<Session> getUserSeesions(User user);

    void deleteExpiredUserSessions(User user);
}