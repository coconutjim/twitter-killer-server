package rest;

import domain.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("user")
public class Login {

    static private HashMap<String, User> allUsers = new HashMap<>();

    public static void setAllUsers(HashMap<String, User> allUsers) {
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
    @Path("/login")
    public Response authentication(@HeaderParam("login") String userLogin, @HeaderParam("password") String userPassword) {

        if (!isCorrectLoginPassword(userLogin, userPassword))
            return Response.status(401).entity("Login or password is incorrect.").build();

        boolean check = allUsers.get(userLogin).checkPassword(userPassword);

        if (check)
            return Response.status(200).entity("Password is correct. Your login: " + userLogin).build();
        else
            return Response.status(401).entity("Login or password is incorrect.").build();
    }

    @POST
    @Path("/reg")
    public Response registration(@HeaderParam("login") String userLogin, @HeaderParam("password") String userPassword) {

        if (!isCorrectLoginPassword(userLogin, userPassword))
            return Response.status(401).entity("Login or password is incorrect.").build();

        User newUser = new User(userLogin, userPassword);

        if (allUsers.get(userLogin) == null)
            allUsers.put(userLogin, newUser);
        else
            return Response.status(400).entity("Login is not available.").build();

        return Response.status(200).entity("User is registered. Your login: " + userLogin).build();
    }

    private static boolean isCorrectLoginPassword(String userLogin, String userPassword) {
        if (userLogin == null
                || userLogin.equals("")
                || userPassword == null
                || userPassword.equals(""))
            return false;
        return true;
    }
}