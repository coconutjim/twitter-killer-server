package ru.pmsoft.twitterkiller.domain.repository;

import ru.pmsoft.twitterkiller.domain.entity.User;

import java.util.HashMap;

/**
 * Created by Андрей on 05.07.2014.
 */
public class HashMapRepository implements UserRepository {
    HashMap<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public void updateUserTokenAndExpiration (User user) {
        //Nothing to do, it is only for DB
    }

    @Override
    public User getByLogin(String name) {
        return users.get(name);
    }

    @Override
    public Iterable<User> values() {
       return users.values();
    }
}
