package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;

public class DbSessionRepository implements SessionRepository {

    @Override
    public void create(UserSession userSession) {
        Session dbSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            dbSession.beginTransaction();
            dbSession.saveOrUpdate(userSession);
            dbSession.getTransaction().commit();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
    }

    @Override
    public UserSession getByUser(User user) {
        Session dbSession = null;
        UserSession userSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            userSession = (UserSession) dbSession.createCriteria(UserSession.class)
                    .add(Restrictions.eq("userId", user.getId())).uniqueResult();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
        return userSession;
    }

    @Override
    public UserSession getByToken(String token) {
        Session dbSession = null;
        UserSession userSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            userSession = (UserSession) dbSession.createCriteria(UserSession.class)
                    .add(Restrictions.eq("token", token)).uniqueResult();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
        return userSession;
    }

    @Override
    public void delete(UserSession userSession) {
        Session dbSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            dbSession.beginTransaction();
            dbSession.delete(userSession);
            dbSession.getTransaction().commit();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
    }
}
