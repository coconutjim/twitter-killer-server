package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.services.SessionService;
import ru.pmsoft.twitterkiller.domain.util.UserUtil;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status;

@Path("user")
public class UserResource {

    private static final SessionService sessionService;

    static {
        sessionService = new SessionService();
    }
    private static long TOKEN_LIFETIME = 86400L; //1 день (в секундах)
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Inject
    public UserResource(UserRepository userRepository,
                        SessionRepository sessionRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("Parameter 'userRepository' can't be null");
        }
        if (sessionRepository == null) {
            throw new IllegalArgumentException("Parameter 'sessionRepository' can't be null");
        }
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;

    }

    private static boolean isLoginCorrect(String login) {
        return !(login == null || login.isEmpty());
    }

    private static boolean isPasswordCorrect(String password) {
        return !(password == null || password.isEmpty());
    }

    @POST
    @Produces("application/json")
    @Path("/login")
    public Response login(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");

        final User user = userRepository.getByLogin(login);
        if (user == null)
            throw new ClientException(Status.UNAUTHORIZED, "User is not found");
        if (!user.checkPassword(password))
            throw new ClientException(Status.UNAUTHORIZED, "Password is not correct");


        //TODO: переделать null-проверку

        if (user.getToken() == null || user.getExpiration() == null || user.getExpiration().before(new Date())) {
            //TODO: класс для Token'a
            user.setToken(UserUtil.generateToken());
            user.setExpiration(UserUtil.computeExpiration(TimeUnit.DAYS, 1));
            userRepository.createOrUpdate(user);
        }
        return Response.status(200).entity(new TokenOutput(user.getToken(), user.getExpiration())).build();
    }

    @POST
    @Path("/register")
    @Produces("application/json")
    public Response register(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");
        if (userRepository.getByLogin(login) != null)
            throw new ClientException(Status.BAD_REQUEST, "Login is already taken");

        User user = new User(login, password);
        userRepository.createOrUpdate(user);
        return Response.status(200).entity("User is registered. Your login: " + login).build();
    }

    @GET
    @Path("/error/")
    @Produces("application/json")
    public Response getError() throws Exception {
        throw new Exception();
    }
}