package ru.pmsoft.twitterkiller.domain.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.Twitt;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.TwittRepository;
import ru.pmsoft.twitterkiller.domain.util.HibernateUtil;

import java.util.List;

/**
 * Created by Anton on 05/07/2014.
 */
public class DBTwittRepository implements TwittRepository {

    @Override
    public void save(Twitt twitt) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.saveOrUpdate(twitt);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Twitt> getAllByLogin(String name) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        User ourUser  = ((List<User>) session.createQuery("select user from User user where user.login = '" + name + "'").list()).get(0);

        List<Twitt> foundTwitt = (List<Twitt>) session.createCriteria(Twitt.class).add(Restrictions.eq("id_user", ourUser.getId())).list();

        session.close();
        return foundTwitt;
    }

    @Override
    public Iterable<Twitt> values() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Twitt> foundUsers = (List<Twitt>) session.createQuery("select user from User user").list();
     //   List<User> foundUsers = (List<User>) session.createCriteria(User.class).list();
        session.close();
        return foundUsers;
    }
}