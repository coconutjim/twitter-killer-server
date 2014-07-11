package ru.pmsoft.twitterkiller.domain.factory;

import org.testng.annotations.Test;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.PasswordEncrypter;
import ru.pmsoft.twitterkiller.domain.util.StringGenerator;

import java.security.GeneralSecurityException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserFactoryTest {
    private static UserFactory createSystemUnderTest(StringGenerator saltGenerator, PasswordEncrypter passwordEncrypter) {

        return new UserFactory(saltGenerator == null ? mock(StringGenerator.class) : saltGenerator,
                passwordEncrypter == null ? mock(PasswordEncrypter.class) : passwordEncrypter);


    }

    @Test
    public void create_ShouldWorkWell() throws GeneralSecurityException {
        StringGenerator saltGenerator = mock(StringGenerator.class);
        when(saltGenerator.generate()).thenReturn("foo");
        PasswordEncrypter passwordEncrypter = mock(PasswordEncrypter.class);
        when(passwordEncrypter.encrypt(any(String.class))).thenReturn("bar");
        UserFactory sut = createSystemUnderTest(saltGenerator, passwordEncrypter);
        User user = sut.create("foo", "bar");
        assertEquals(user.getSalt(), "foo");
        assertEquals(user.getPasswordHash(), "bar");
    }

    @Test
    public void checkPassword_WorkWell() throws GeneralSecurityException {
        String s = "foo";
        StringGenerator saltGenerator = mock(StringGenerator.class);
        when(saltGenerator.generate()).thenReturn("foo");
        PasswordEncrypter passwordEncrypter = mock(PasswordEncrypter.class);
        when(passwordEncrypter.encrypt(anyString())).thenReturn(s + "bar");
        UserFactory sut = createSystemUnderTest(saltGenerator, passwordEncrypter);
        User user = sut.create("foo", s);
        user.setPasswordHash(passwordEncrypter.encrypt(s));
        assertTrue(sut.checkPassword(user, s));

    }

}