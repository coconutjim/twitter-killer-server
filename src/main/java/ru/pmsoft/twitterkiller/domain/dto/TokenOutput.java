package ru.pmsoft.twitterkiller.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD'T'HH:mm:ss.SSSZ", timezone = "GMT+4")
    public Date getExpiration() {
        return expiration;
    }
}
