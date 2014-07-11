package ru.pmsoft.twitterkiller.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("JpaObjectClassSignatureInspection")
public class Tweet implements Serializable {
    private int id;
    private String text;
    private int userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd'T'HH:mm:ssZ", timezone = "GMT+4")
    private Date date;

    private Tweet() {
    }

    public Tweet(int userId, String text) {
        this.userId = userId;
        this.text = text;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String tweet) {
        this.text = tweet;
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
