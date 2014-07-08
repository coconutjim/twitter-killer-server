package ru.pmsoft.twitterkiller.domain.util;

import java.security.GeneralSecurityException;

public interface PasswordEncrypter {
    String encrypt(String value) throws GeneralSecurityException;
}
