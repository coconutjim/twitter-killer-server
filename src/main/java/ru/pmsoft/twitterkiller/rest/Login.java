package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.HashMapRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.domain.util.UserUtil;
import ru.pmsoft.twitterkiller.rest.exceptions.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status;

@Path("user")
public class Login {

    private static long TOKEN_LIFETIME = 86400L; //1 день (в секундах)

    static private UserRepository allUsers = new HashMapRepository();

    public Login() {
    }

    public Login(UserRepository allUsers) {
        Login.allUsers = allUsers;
    }

    @GET
    @Path("/all")
    @Produces("text/plain")
    public String getAllUsers() {
        String all = "";
        for (User user : allUsers.values()) {
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

        final User user = allUsers.getByLogin(login);
        if (user == null)
            throw new ClientException(Status.UNAUTHORIZED, "User is not found");
        if (!user.checkPassword(password))
            throw new ClientException(Status.UNAUTHORIZED, "Password is not correct");
        //если токена не было или он просрочен, то генерируем новый
        if (user.getToken() == null || user.getExpiration() == null || user.getExpiration().before(new Date())) {
            user.setToken(UserUtil.generateToken());
            user.setExpiration(UserUtil.computeExpiration(TimeUnit.DAYS, 1));
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
        if (allUsers.getByLogin(login) != null) {
            throw new ClientException(Status.BAD_REQUEST, "Login is already taken");
        }

        User user = new User(login, password);
        allUsers.save(user);
        return Response.status(200).entity("User is registered. Your login: " + login).build();
    }

    private static boolean isLoginCorrect(String login) {
        return !(login == null || login.isEmpty());
    }

    private static boolean isPasswordCorrect(String password) {
        return !(password == null || password.isEmpty());
    }
}