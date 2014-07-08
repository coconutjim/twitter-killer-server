package ru.pmsoft.twitterkiller.domain.factory;

import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.TokenGenerator;
import ru.pmsoft.twitterkiller.domain.util.UUIDGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SessionFactory {
    int tokenLifeTime = 86400;
    private TokenGenerator tokenGenerator;

    public SessionFactory() {
        tokenGenerator = new UUIDGenerator();
    }

    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        if (tokenGenerator == null)
            throw new IllegalArgumentException("Parameter 'tokenGenerator' can't be null");
        this.tokenGenerator = tokenGenerator;
    }

    private Date computeExpiration() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, tokenLifeTime);
        return calendar.getTime();
    }

    public void setTokenLifeTime(int tokenLifeTime) {
        this.tokenLifeTime = tokenLifeTime;
    }

    public Session create(User user) {
        Session session = new Session(tokenGenerator.generate(), computeExpiration(), user.getId());
        return session;
    }
}