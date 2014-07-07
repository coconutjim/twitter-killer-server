package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;

import java.util.List;

public class DbUserRepository implements UserRepository {

    @Override
    public void createOrUpdate(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        try {
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("login", login)).uniqueResult();
        } finally {
            session.close();
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<User> getAll() {
        List<User> foundUsers = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            foundUsers = (List<User>) session.createCriteria(User.class).list();
        } finally {
            session.close();
        }
        return foundUsers;
    }
}