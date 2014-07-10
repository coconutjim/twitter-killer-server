package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;

import java.util.HashMap;

public class InMemoryUserRepository implements UserRepository {
    HashMap<String, User> users = new HashMap<>();

    @Override
    public void createOrUpdate(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public User getByLogin(String login) {
        return users.get(login);
    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public Iterable<User> getAll() {
       return users.values();
    }
}
