package ru.pmsoft.twitterkiller.domain.util;

import java.util.UUID;

public class UUIDGenerator implements StringGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
