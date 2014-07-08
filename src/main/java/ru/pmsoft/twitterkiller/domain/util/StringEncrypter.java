package ru.pmsoft.twitterkiller.domain.util;

import java.security.GeneralSecurityException;

public interface StringEncrypter {
    String encrypt(String value) throws GeneralSecurityException;
}
