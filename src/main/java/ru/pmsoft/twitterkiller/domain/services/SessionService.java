package ru.pmsoft.twitterkiller.domain.services;

import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.StringGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SessionService {
    StringGenerator tokenGenerator;
    Session temp;
    int tokenLifeTime = 86400;

    //TODO: set default generator
    public SessionService() {
    }
    public SessionService(StringGenerator tokenGenerator) {
        setTokenGenerator(tokenGenerator);
    }

    /**
     * Создает сервис для конструирования объектов класса Session и работы с ним
     *
     * @param tokenGenerator алгоритм генерации token'a пользователя
     * @param tokenLifeTime  время жизни token'a (в секундах)
     */
    public SessionService(StringGenerator tokenGenerator, int tokenLifeTime) {
        this(tokenGenerator);
        setTokenLifeTime(tokenLifeTime);
    }

    public void setTokenLifeTime(int tokenLifeTime) {
        if (tokenLifeTime < 0)
            throw new IllegalArgumentException("Parameter 'tokenLifeTime' can't be negative");
        this.tokenLifeTime = tokenLifeTime;
    }

    public void setTokenGenerator(StringGenerator tokenGenerator) {
        if (tokenGenerator == null)
            throw new IllegalArgumentException("Parameter 'tokenGenerator' can't be null");
        this.tokenGenerator = tokenGenerator;
    }

    public SessionService setUser(User user) {
        if (temp == null)
            temp = new Session();
        temp.setUserId(user.getId());
        return this;
    }

    private Date computeExpiration() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, tokenLifeTime);
        return calendar.getTime();
    }

    public Session build() {
        Session copy = null;
        try {
            copy = (Session) temp.clone();
            copy.setToken(tokenGenerator.generate());
            copy.setExpiration(computeExpiration());
        } catch (CloneNotSupportedException e) {
            //will never be thrown
        }
        temp = null;
        return copy;
    }


}
