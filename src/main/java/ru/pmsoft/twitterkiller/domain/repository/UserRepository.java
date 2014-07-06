package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;

public interface UserRepository {
    void save(User user); //create or update user
    User getByLogin(String name);
    Iterable<User> values();
}