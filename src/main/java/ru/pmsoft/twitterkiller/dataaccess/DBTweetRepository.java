package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.Tweet;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;

import java.util.List;


public class DBTweetRepository implements TweetRepository {

    @Override
    public void createOrUpdate(Tweet tweet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            session.saveOrUpdate(tweet);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    public List<Tweet> getAllByLogin(String name) {
        Session session = null;
        List<Tweet> foundTweet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            User ourUser = ((List<User>) session.createQuery("select user from User user where user.login = '" + name + "'").list()).get(0);

            foundTweet = (List<Tweet>) session.createCriteria(Tweet.class).add(Restrictions.eq("id_user", ourUser.getId())).list();

        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return foundTweet;
    }

    @Override
    public Tweet getById(String tweetId) {
        Session session = null;
        Tweet foundTweet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            foundTweet = (Tweet) session.createCriteria(Tweet.class).add(Restrictions.eq("id", tweetId)).list().get(0);

        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return foundTweet;
    }
}