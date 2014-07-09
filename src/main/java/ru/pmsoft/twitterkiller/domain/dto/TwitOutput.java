package ru.pmsoft.twitterkiller.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.pmsoft.twitterkiller.domain.entity.Twitt;
import ru.pmsoft.twitterkiller.domain.util.JsonDateSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 05.07.2014.
 */
public class TwitOutput {
    private String login;
    private List<Twitt> allTwitts;

    public TwitOutput(String login, List<Twitt> allTwitts) {
        this.login = login;
        this.allTwitts = allTwitts;
    }

    public String getLogin() {
        return login;
    }

    public List<Twitt> getAllTwitts() {
        return allTwitts;
    }
}
