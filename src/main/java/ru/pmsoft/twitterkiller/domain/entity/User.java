package ru.pmsoft.twitterkiller.domain.entity;

import ru.pmsoft.twitterkiller.domain.util.UserUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable{
    //private List<Twitt> _twitts;
    private String _login;
    private String _passwordHash;
    private String _salt;
    private int _id;
    private String _token = "";
    private Date _expiration = new Date();

    private User() { }

    public User(String login, String password) {
        //_twitts = new ArrayList<Twitt>();
        _login = login;
        _salt = UserUtil.generateSalt();
        _passwordHash = UserUtil.getSHA256(password, _salt);
        _token = UserUtil.generateToken();
    }

    public boolean checkPassword(String password) {
        return _passwordHash.equals(UserUtil.getSHA256(password, _salt));
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String login) {
        _login = login;
    }

    public String getPasswordHash() {
        return _passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        _passwordHash = passwordHash;
    }
    
    public void generatePasswordHash(String password) {
        _passwordHash = UserUtil.getSHA256(password, _salt);
    }

    public String getSalt() {
        return _salt;
    }

    public void setSalt(String salt) {
        _salt = salt;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getToken() {
        return _token;
    }

    public void setToken(String token) {
        _token = token;
    }

    public Date getExpiration() {
        return _expiration;
    }

    public void setExpiration(Date expiration) {
        _expiration = expiration;
    }

    //public void addTwitt (Twitt twitt)
    //{
    //    _twitts.add(twitt);
    //}

}
