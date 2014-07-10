package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;


public interface SessionRepository {
    void create(UserSession userSession);
    UserSession getByUser(User user);
    UserSession getByToken(String token);
    void delete(UserSession userSession);
}