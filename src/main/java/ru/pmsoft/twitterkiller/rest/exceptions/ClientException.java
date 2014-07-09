package ru.pmsoft.twitterkiller.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;


public class ClientException extends WebApplicationException {
    private String message;
    public ClientException(Status status, String message) {
        super(Response.status(status).
                entity(new ExceptionBody(message)).build());
        this.message = message;
    }
    @Override
    public String getMessage(){
        return message;
    }
}
