package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.dataaccess.HibernateUtil;
import ru.pmsoft.twitterkiller.domain.entity.Tweet;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;


import java.util.List;


public class DBTweetRepository implements TweetRepository {

    @Override
    public void save(Tweet tweet) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        session.saveOrUpdate(tweet);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Tweet> getAllByLogin(String name) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        User ourUser  = ((List<User>) session.createQuery("select user from User user where user.login = '" + name + "'").list()).get(0);

        List<Tweet> foundTweet = (List<Tweet>) session.createCriteria(Tweet.class).add(Restrictions.eq("id_user", ourUser.getId())).list();

        session.close();
        return foundTweet;
    }

    @Override
    public Iterable<Tweet> values() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Tweet> foundUsers = (List<Tweet>) session.createQuery("select user from User user").list();
     //   List<User> foundUsers = (List<User>) session.createCriteria(User.class).list();
        session.close();
        return foundUsers;
    }
}