package ru.pmsoft.twitterkiller.domain.factory;


import org.testng.annotations.Test;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;
import ru.pmsoft.twitterkiller.domain.factory.SessionFactory;
import ru.pmsoft.twitterkiller.domain.util.StringGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.testng.Assert.assertEquals;

import static org.mockito.Mockito.mock;

public class SessionFactoryTestCase {

    private static SessionFactory createSystemUnderTest(StringGenerator tokenGenerator) {
        return new SessionFactory(tokenGenerator == null ? mock(StringGenerator.class) : tokenGenerator);
    }

    private User dummyUser = new User("LeoTheMagnificent");

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructor_ifTokenIsNull_shouldThrowIllegalArgumentException() {
        new SessionFactory(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setTokenLifeTime_ifLifeTimeBelowZero_shouldThrowIllegalArgumentException() {
        SessionFactory sut = createSystemUnderTest(null);
        sut.setTokenLifeTime(-1);
    }

    @Test
    public void expirationComputingTest() {
        int testTokenLifetime = 100000;
        Calendar testExpirationTime = new GregorianCalendar();
        testExpirationTime.setTime(new Date());
        testExpirationTime.add(Calendar.SECOND, testTokenLifetime);

        SessionFactory sut = createSystemUnderTest(null);
        sut.setTokenLifeTime(testTokenLifetime);
        UserSession testSession = sut.create(dummyUser);

        assertEquals(Math.abs(testSession.getExpiration().getTime() - testExpirationTime.getTime().getTime()) < 1000,
                true);
    }

    @Test
    public void tokenGeneratorTest() {
        final String testString = "LeoTester";
        StringGenerator testStringGenerator = new StringGenerator() {
            @Override
            public String generate() {
                return testString;
            }
        };

        SessionFactory sut = createSystemUnderTest(testStringGenerator);
        UserSession testSession = sut.create(dummyUser);

        assertEquals(testSession.getToken(), testString);
    }
}
