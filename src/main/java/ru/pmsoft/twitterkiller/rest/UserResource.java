package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.dto.TwitOutput;
import ru.pmsoft.twitterkiller.domain.entity.Twitt;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.TwittRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.util.UserUtil;
import ru.pmsoft.twitterkiller.rest.exceptions.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status;

@Path("user")
public class UserResource {

    //private static long TOKEN_LIFETIME = 86400L; //1 день (в секундах)

    static private UserRepository repository;
    static private TwittRepository repositoryTwitt;


    @Inject
    public UserResource(UserRepository repository, TwittRepository repositoryTwitt) {
        if (repository == null) {
            throw new IllegalArgumentException("Parameter 'repository' can't be null");
        }

        UserResource.repository = repository;
        UserResource.repositoryTwitt = repositoryTwitt;
    }

    @GET
    @Path("/all")
    @Produces("text/plain")
    public String getAllUsers() {
        String all = "";

        for (User user : repository.values()) {
            all += user.getLogin() + "\n";
        }

        return all;
    }

    @POST
    @Produces("application/json")
    @Path("/login")
    public Response authentication(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");

        final User user = repository.getByLogin(login);
        if (user == null)
            throw new ClientException(Status.UNAUTHORIZED, "User is not found");
        if (!user.checkPassword(password))
            throw new ClientException(Status.UNAUTHORIZED, "Password is not correct");
        //если токена не было или он просрочен, то генерируем новый
        if (user.getToken() == null || user.getExpiration() == null || user.getExpiration().before(new Date())) {
            user.setToken(UserUtil.generateToken());
            user.setExpiration(UserUtil.computeExpiration(TimeUnit.DAYS, 1));
            //Обновляем пользователя в БД
            repository.save(user);
        }
        return Response.status(200).entity(new TokenOutput(user.getToken(), user.getExpiration())).build();
    }

    @POST
    @Path("/register")
    @Produces("application/json")
    public Response registration(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!isPasswordCorrect(password))
            throw new ClientException(Status.BAD_REQUEST, "Password can not be empty");
        if (repository.getByLogin(login) != null) {
            throw new ClientException(Status.BAD_REQUEST, "Login is already taken");
        }

        User user = new User(login, password);
        repository.save(user);
        return Response.status(200).entity("User is registered. Your login: " + login).build();
    }

    @POST
    @Path("/twitt/add")
    @Produces("application/json")
    public Response addTwitt(@HeaderParam("login") String login, @HeaderParam("twitt") String text) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");
        if (!isTwittCorrect(text))
            throw new ClientException(Status.BAD_REQUEST, "Twitt can not be empty or less than 140 letters");

        final User user = repository.getByLogin(login);
        if (!isTokenValid(user.getExpiration()))
            throw new ClientException(Status.BAD_REQUEST, "Your token is expired");

        int id_user = user.getId();

        Twitt twitt = new Twitt(id_user, text);
        repositoryTwitt.save(twitt);
        return Response.status(200).entity("Twitt is saved").build();
    }

    @GET
    @Path("/twitt/all")
    @Produces("application/json")
    public Response allTwitts(@HeaderParam("login") String login) {

        if (!isLoginCorrect(login))
            throw new ClientException(Status.BAD_REQUEST, "Login can not be empty");

        final User user = repository.getByLogin(login);

        if (!isTokenValid(user.getExpiration()))
            throw new ClientException(Status.BAD_REQUEST, "Your token is expired");

        List<Twitt> allTwitts = repositoryTwitt.getAllByLogin(login);
        return Response.status(200).entity(new TwitOutput(login, allTwitts)).build();
    }

    private static boolean isLoginCorrect(String login) {
        return !(login == null || login.isEmpty());
    }

    private static boolean isPasswordCorrect(String password) {
        return !(password == null || password.isEmpty());
    }

    private static boolean isTokenValid(Date expiration) {
        return (new Date()).before(expiration);
    }

    private static boolean isTwittCorrect(String twitt) {
        return !(twitt == null || twitt.isEmpty() || twitt.trim().isEmpty() || twitt.trim().length() > 140);
    }

}