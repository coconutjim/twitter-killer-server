package ru.pmsoft.twitterkiller.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class Twitt implements Serializable{
    private int id;
    private String twitt;
    private int id_user;
    private Date date;

    private Twitt() { }

    public Twitt(int id_user, String twitt) {
        this.id_user = id_user;
        this.twitt = twitt;
        this.date = new Date(); //Потом дата сделать
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTwitt() {
        return twitt;
    }

    public void setTwitt(String twitt) {
        this.twitt = twitt;
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
