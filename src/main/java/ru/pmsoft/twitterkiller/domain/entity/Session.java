package ru.pmsoft.twitterkiller.domain.entity;

import java.util.Date;

/**
 * Created by Андрей on 07.07.2014.
 */
public class Session {
    private int id;
    private String token;
    private Date expiration;
    private int userId;

    private Session() {
    }


}
