package ru.pmsoft.twitterkiller.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.pmsoft.twitterkiller.domain.util.JsonDateSerializer;

import java.util.Date;


public class TokenOutput {
    private String token;
    private Date expiration;

    public TokenOutput(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getExpiration() {
        return expiration;
    }
}
