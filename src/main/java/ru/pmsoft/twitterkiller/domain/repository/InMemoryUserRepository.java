package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;

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
    public Iterable<User> getAll() {
       return users.values();
    }
}
