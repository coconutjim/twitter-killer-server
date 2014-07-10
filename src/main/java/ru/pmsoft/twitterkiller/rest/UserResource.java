package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.factory.SessionFactory;
import ru.pmsoft.twitterkiller.domain.factory.UserFactory;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;

import static javax.ws.rs.core.Response.Status;

@Path("user")
public class UserResource {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private UserFactory userFactory;
    private SessionFactory sessionFactory;
    private TweetRepository repositoryTweet;


    @Inject
    public UserResource(UserRepository userRepository,
                        SessionRepository sessionRepository, TweetRepository repositoryTweet) {
        if (userRepository == null) {
            throw new IllegalArgumentException("Parameter 'userRepository' can't be null");
        }
        if (sessionRepository == null) {
            throw new IllegalArgumentException("Parameter 'sessionRepository' can't be null");
        }
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.repositoryTweet = repositoryTweet;
        userFactory = new UserFactory();
        sessionFactory = new SessionFactory();        
    }

    

   

    @POST
    @Produces("application/json")
    @Path("/login")
    public Response login(@HeaderParam("login") String login, @HeaderParam("password") String password)
            throws GeneralSecurityException {

        if (!UserFactory.isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!UserFactory.isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");

        final User user = userRepository.getByLogin(login);
        if (user == null)
            throw new ClientException(Status.UNAUTHORIZED, "User is not found");
        if (!userFactory.checkPassword(user, password))
            throw new ClientException(Status.UNAUTHORIZED, "Password is not correct");



        Session session = sessionRepository.getByUser(user);
        if (session != null && session.isExpired()) {
            sessionRepository.delete(session);
        }
        if (session == null || session.isExpired()) {
            session = sessionFactory.create(user);
            sessionRepository.create(session);
        }
        return Response.status(200).entity(new TokenOutput(session.getToken(), session.getExpiration())).build();
    }

    @POST
    @Path("/register")
    @Produces("application/json")
    public Response register(@HeaderParam("login") String login, @HeaderParam("password") String password)
            throws GeneralSecurityException {

        if (!UserFactory.isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!UserFactory.isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");
        if (userRepository.getByLogin(login) != null)
            throw new ClientException(Status.BAD_REQUEST, "Login is already taken");

        User user = userFactory.create(login, password);
        userRepository.createOrUpdate(user);
        return Response.status(200).entity("{\"login\":" + "\"" + login + "\"}").build();
    }
}