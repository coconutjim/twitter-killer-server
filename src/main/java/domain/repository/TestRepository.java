package domain.repository;

import domain.entity.User;

/**
 * Created by Lev on 04.07.14.
 */
public class TestRepository implements UserRepository {

    @Override
    public void save(User user) {
        // My code
    }

    @Override
    public User getByLogin(String name) {
        // My code
        return null;
    }

    @Override
    public Iterable<User> values() {
        // My code
        return null;
    }
}
