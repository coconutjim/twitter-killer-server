package ru.pmsoft.twitterkiller.rest.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionBody {
    private String message;

    public ExceptionBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
