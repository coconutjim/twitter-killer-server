package ru.pmsoft.twitterkiller.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("JpaObjectClassSignatureInspection")
public class Tweet implements Serializable{
    private String id;
    private String tweet;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT+4")
    private String id_user;
    private Date date;

    private Tweet() { }

    public Tweet(String id_user, String tweet) {
        this.id_user = id_user;
        this.tweet = tweet;
        this.date = new Date(); //Потом дата сделать
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
