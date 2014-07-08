package ru.pmsoft.twitterkiller.domain.entity;

import java.util.Date;

/**
 * Created by Андрей on 07.07.2014.
 */
public class Session implements Cloneable {
    private int id;
    private String token;
    private Date expiration;
    private int userId;

    public Session() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
