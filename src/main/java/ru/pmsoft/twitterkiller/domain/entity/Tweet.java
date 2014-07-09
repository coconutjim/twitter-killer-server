package ru.pmsoft.twitterkiller.domain.entity;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("JpaObjectClassSignatureInspection")
public class Tweet implements Serializable{
    private int id;
    private String tweet;
    private int id_user;
    private Date date;

    private Tweet() { }

    public Tweet(int id_user, String tweet) {
        this.id_user = id_user;
        this.tweet = tweet;
        this.date = new Date(); //Потом дата сделать
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String twitt) {
        this.tweet = twitt;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
