package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dataaccess.DbUserRepository;
import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.util.UserUtil;
import ru.pmsoft.twitterkiller.rest.exceptions.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status;

@Path("user")
public class UserResource {

    private static long TOKEN_LIFETIME = 86400L; //1 день (в секундах)

    static private UserRepository repository;

    @Inject
    public UserResource(UserRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Parameter 'repository' can't be null");
        }

        UserResource.repository = repository;
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

    @GET
    @Path("/error/")
    @Produces("application/json")
    public Response getError() throws Exception{
        throw new Exception();
    }

    private static boolean isLoginCorrect(String login) {
        return !(login == null || login.isEmpty());
    }

    private static boolean isPasswordCorrect(String password) {
        return !(password == null || password.isEmpty());
    }
}