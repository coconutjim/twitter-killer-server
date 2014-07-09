package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;

import java.util.List;

public class DbUserRepository implements UserRepository {

    @Override
    public void createOrUpdate(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getByLogin(String login) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("login", login)).uniqueResult();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    @Override
    public User getById(int id){
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterable<User> getAll() {
        List<User> foundUsers = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            foundUsers = (List<User>) session.createCriteria(User.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return foundUsers;
    }
}