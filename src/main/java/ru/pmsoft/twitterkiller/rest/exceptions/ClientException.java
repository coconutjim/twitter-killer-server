package ru.pmsoft.twitterkiller.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;

/**
 * Created by Андрей on 05.07.2014.
 */
public class ClientException extends WebApplicationException {
    public ClientException(Status status, String message) {
        super(Response.status(status).
                entity(new ExceptionBody(message)).build());
    }
}
