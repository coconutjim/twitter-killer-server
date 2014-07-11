package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;

public interface UserRepository {
    void createOrUpdate(User user);

    User getByLogin(String login);

    User getById(int id);
}