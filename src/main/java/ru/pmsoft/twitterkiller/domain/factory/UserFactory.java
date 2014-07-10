package ru.pmsoft.twitterkiller.domain.factory;

import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.PasswordEncrypter;
import ru.pmsoft.twitterkiller.domain.util.StringGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.GeneralSecurityException;

public class UserFactory {

    private StringGenerator saltGenerator;
    private PasswordEncrypter passwordEncrypter;

    @Inject
    public UserFactory(@Named("salt") StringGenerator saltGenerator,
                       PasswordEncrypter passwordEncrypter) {
        this.saltGenerator = saltGenerator;
        this.passwordEncrypter = passwordEncrypter;
    }

    public User create(String login, String password) throws GeneralSecurityException {
        User user = new User(login);
        user.setSalt(saltGenerator.generate());
        user.setPasswordHash(passwordEncrypter.encrypt(password + user.getSalt()));
        return user;
    }

    public boolean checkPassword(User user, String password) throws GeneralSecurityException {
        return user.getPasswordHash().equals(
                passwordEncrypter.encrypt(password + user.getSalt()));
    }

    public static boolean isLoginCorrect(String login) {
        return !(login == null || login.isEmpty());
    }

    public static boolean isPasswordCorrect(String password) {
        return !(password == null || password.isEmpty());
    }




}
