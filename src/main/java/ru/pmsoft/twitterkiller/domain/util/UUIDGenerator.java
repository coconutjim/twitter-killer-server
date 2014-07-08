package ru.pmsoft.twitterkiller.domain.util;

import java.util.UUID;

public class UUIDGenerator implements TokenGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
