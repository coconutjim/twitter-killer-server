package ru.pmsoft.twitterkiller.domain.entity;

import java.util.Date;

public class Session {
    private String id;
    private String token;
    private Date expiration;
    private String userId;

    public Session() {
    }

    public Session(String token, Date expiration, String userId) {
        this.token = token;
        this.expiration = expiration;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isExpired() {
        return (new Date()).after(expiration);
    }
}
