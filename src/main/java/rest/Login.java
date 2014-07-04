package rest;

import domain.entity.User;
import domain.repository.UserRepository;
import domain.util.UserUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;

@Path("user")
public class Login {

    private UserRepository allUsers;

    /* A day and one millisecond */
    private final long EXPIRATION_TIME = 86400001L;

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
    @Path("/login")
    public Response authentication(@HeaderParam("login") String userLogin, @HeaderParam("password") String userPassword) {

        if (!isCorrectLoginPassword(userLogin, userPassword))
            return Response.status(401).entity("Login or password is incorrect.").build();

        User user = allUsers.getByLogin(userLogin);
        boolean check = (user != null) && user.checkPassword(userPassword);

        if (check) {

            if(user.getToken() == null){
                user.setToken(UserUtil.generateToken());
                user.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
            }
            return Response.status(200).entity(user.getToken()).build();
        }
        else
            return Response.status(401).entity("Login or password is incorrect.").build();
    }

    @POST
    @Path("/reg")
    public Response registration(@HeaderParam("login") String userLogin, @HeaderParam("password") String userPassword) {

        if (!isCorrectLoginPassword(userLogin, userPassword))
            return Response.status(401).entity("Login or password is incorrect.").build();

        User newUser = new User(userLogin, userPassword);

        if (allUsers.getByLogin(userLogin) == null)
            allUsers.save(newUser);
        else
            return Response.status(401).entity("Login is not available.").build();

        return Response.status(200).entity("User is registered. Your login: " + userLogin).build();
    }

    private static boolean isCorrectLoginPassword(String userLogin, String userPassword) {
        if (userLogin == null
                || userLogin.equals("")
                || userPassword == null
                || userPassword.equals("")
                || userPassword.length() <= 8)
            return false;
        return true;
    }

    public void setAllUsers(UserRepository allUsers) {
        this.allUsers = allUsers;
    }
}