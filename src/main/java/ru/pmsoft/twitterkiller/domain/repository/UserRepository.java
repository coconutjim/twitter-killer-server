package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;

public interface UserRepository {
    void save(User user);
    void updateUserTokenAndExpiration (User user);
    User getByLogin(String name);
    Iterable<User> values();
}