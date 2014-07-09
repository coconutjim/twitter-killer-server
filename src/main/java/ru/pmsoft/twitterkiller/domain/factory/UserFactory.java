package ru.pmsoft.twitterkiller.domain.factory;

import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.util.PasswordEncrypter;
import ru.pmsoft.twitterkiller.domain.util.RandomSaltGenerator;
import ru.pmsoft.twitterkiller.domain.util.SHA256PasswordEncrypter;
import ru.pmsoft.twitterkiller.domain.util.SaltGenerator;

import java.security.GeneralSecurityException;


public class UserFactory {

    private SaltGenerator saltGenerator;
    private PasswordEncrypter passwordEncrypter;

    public UserFactory() {
        saltGenerator = new RandomSaltGenerator();
        passwordEncrypter = new SHA256PasswordEncrypter();
    }

    public void setSaltGenerator(SaltGenerator saltGenerator) {
        this.saltGenerator = saltGenerator;
    }

    public void setPasswordEncrypter(PasswordEncrypter passwordEncrypter) {
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
