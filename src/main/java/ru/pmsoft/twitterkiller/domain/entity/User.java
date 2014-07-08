package ru.pmsoft.twitterkiller.domain.entity;


import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("JpaObjectClassSignatureInspection")
public class User implements Serializable {
    private String login;
    private String passwordHash;
    private String salt;
    private int id;
    private String token;
    private Date expiration;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
