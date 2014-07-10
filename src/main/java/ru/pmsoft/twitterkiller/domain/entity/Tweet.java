package ru.pmsoft.twitterkiller.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("JpaObjectClassSignatureInspection")
public class Tweet implements Serializable{
    private int id;
    private String tweet;
    private int userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd'T'HH:mm:ssZ", timezone = "GMT+4")
    private Date date;

    private Tweet() { }

    public Tweet(int userId, String tweet) {
        this.userId = userId;
        this.tweet = tweet;
        this.date = new Date();
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

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id_user) {
        this.userId = id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
