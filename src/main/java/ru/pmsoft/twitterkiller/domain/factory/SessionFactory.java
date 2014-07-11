package ru.pmsoft.twitterkiller.domain.factory;

import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;
import ru.pmsoft.twitterkiller.domain.util.StringGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SessionFactory {
    private int tokenLifeTime = 86400;
    private StringGenerator tokenGenerator;
    
    @Inject
    public SessionFactory(@Named("token") StringGenerator tokenGenerator) {
        if (tokenGenerator == null)
            throw new IllegalArgumentException("Parameter 'tokenGenerator' can't be null");
        this.tokenGenerator = tokenGenerator;
    }

    public void setTokenLifeTime(int tokenLifeTime) {
        if (tokenLifeTime <= 0)
            throw new IllegalArgumentException("Parameter 'tokenLifeTime' must be positive");
        this.tokenLifeTime = tokenLifeTime;
    }

    private Date computeExpiration() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, tokenLifeTime);
        return calendar.getTime();
    }

    public UserSession create(User user) {
        return new UserSession(tokenGenerator.generate(), computeExpiration(), user.getId());
    }
}