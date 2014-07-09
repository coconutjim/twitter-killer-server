package ru.pmsoft.twitterkiller.dataaccess;

import org.hibernate.criterion.Restrictions;
import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;

public class DbSessionRepository implements SessionRepository {

    @Override
    public void create(Session session) {
        org.hibernate.Session dbSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            dbSession.beginTransaction();
            dbSession.saveOrUpdate(session);
            dbSession.getTransaction().commit();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
    }

    @Override
    public Session getByUser(User user) {
        org.hibernate.Session dbSession = null;
        Session session = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            session = (Session) dbSession.createCriteria(Session.class)
                    .add(Restrictions.eq("userId", user.getId())).uniqueResult();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
        return session;
    }

    @Override
    public Session getByToken(String token) {
        org.hibernate.Session dbSession = null;
        Session session = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            session = (Session) dbSession.createCriteria(Session.class)
                    .add(Restrictions.eq("token", token)).uniqueResult();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
        return session;
    }

    @Override
    public void delete(Session session) {
        org.hibernate.Session dbSession = null;
        try {
            dbSession = HibernateUtil.getSessionFactory().openSession();
            dbSession.beginTransaction();
            dbSession.delete(session);
            dbSession.getTransaction().commit();
        } finally {
            if (dbSession != null && dbSession.isOpen())
                dbSession.close();
        }
    }
}
