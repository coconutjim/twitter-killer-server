package ru.pmsoft.twitterkiller.domain.repository;

import org.hibernate.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.HibernateUtil;

import java.util.List;

/**
 * Created by Anton on 05/07/2014.
 */
public class BDRepository implements UserRepository {

    @Override
    public void save(User user) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User getByLogin(String name) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<User> foundUsers = (List<User>) session.createQuery("select user from User user where user.login = '" + name + "'").list();
        session.close();

        if(foundUsers.size() == 0)
            return null;
        return foundUsers.get(0);
    }

    @Override
    public void updateUserTokenAndExpiration (User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "UPDATE User set token= :userToken, " +
                "expiration = :userExpiration" +
                " WHERE login = :userLogin";
        session.createQuery(hql)
            .setParameter("userToken", user.getToken())
            .setParameter("userExpiration", user.getExpiration())
            .setParameter("userLogin", user.getLogin())
            .executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Iterable<User> values() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<User> foundUsers = (List<User>) session.createQuery("select user from User user").list();

        session.close();
        return foundUsers;
    }
}