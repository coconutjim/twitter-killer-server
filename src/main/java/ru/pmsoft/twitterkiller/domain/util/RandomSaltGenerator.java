package ru.pmsoft.twitterkiller.domain.util;


import java.math.BigInteger;
import java.util.Random;

public class RandomSaltGenerator implements StringGenerator {
    @Override
    public String generate() {
        return new BigInteger(130, new Random()).toString(16);
    }
}
