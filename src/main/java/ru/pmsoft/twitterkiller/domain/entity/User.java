package ru.pmsoft.twitterkiller.domain.entity;

import ru.pmsoft.twitterkiller.domain.util.UserUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("JpaObjectClassSignatureInspection")
public class User implements Serializable {
    private String login;
    private String passwordHash;
    private String salt;
    private int id;
    private String token;
    private Date expiration;
    private Set<Session> sessions;

    private User() {
    }

    public User(String login, String password) {
        this.login = login;
        salt = UserUtil.generateSalt();
        this.passwordHash = UserUtil.getSHA256(password, salt);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public boolean checkPassword(String password) {
        return passwordHash.equals(UserUtil.getSHA256(password, salt));
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
}
