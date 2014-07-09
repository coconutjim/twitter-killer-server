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

    @Override
    public boolean equals(Object obj) {
        if (obj==null)
            return false;
        if (!(obj instanceof ExceptionBody))
            return false;
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
