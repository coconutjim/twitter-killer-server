package domain.repository;

import domain.entity.User;

public interface UserRepository {
    void save(User user);
    User getByLogin(String name);
    Iterable<User> values();
}
