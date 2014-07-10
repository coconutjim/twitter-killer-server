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

            List<User> ourUserList = (List<User>) session.createQuery("select user from User user where user.login = '" + name + "'").list();
            if(!ourUserList.isEmpty())
            foundTweet = (List<Tweet>) session.createCriteria(Tweet.class).add(Restrictions.eq("id_user", ourUserList.get(0).getId())).list();

        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return foundTweet;
    }

    @Override
    public Tweet getById(int tweetId) {
        Session session = null;
        Tweet foundTweet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            List<Tweet> resultList =  session.createCriteria(Tweet.class).add(Restrictions.eq("id", tweetId)).list();
            if(!resultList.isEmpty()) foundTweet = resultList.get(0);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return foundTweet;
    }
}