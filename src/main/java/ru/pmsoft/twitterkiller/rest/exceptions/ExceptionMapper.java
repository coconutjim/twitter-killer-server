package ru.pmsoft.twitterkiller.rest.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by Виктор on 07.07.2014.
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        ExceptionBody body = new ExceptionBody("Error on server: "+throwable.getMessage());
        return Response.status(500).entity(body).build();
    }
}
