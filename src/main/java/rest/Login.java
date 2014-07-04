package rest;

import domain.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by Андрей on 03.07.2014.
 */
@Path("user")
public class Login {


    static HashMap<String, User> allUsers = new HashMap<>();


    public static void setAllUsers(HashMap<String, User> allUsers) {
        Login.allUsers = allUsers;
    }


    @GET
    @Path("/all")
    @Produces("text/plain")
    public String doNotKnow() {
        String all = "";
        for(User user : allUsers.values()) {
            all += user.getLogin() + "\n";
        }

        return all;
    }

    @POST
    @Path("/login")
    public Response authentication(@HeaderParam("login") String login, @HeaderParam("password") String password) {
        boolean check =  allUsers.get(login).checkPassword(password);
        return Response.status(200).entity("Password correct? " + check + " Login: " + login).build();
    }

    @POST
    @Path("/reg")
    public Response registration(@HeaderParam("login") String login, @HeaderParam("password") String password) {
        User newUser = new User(login, password);
        allUsers.put(login, newUser);

        return Response.status(200).entity("User is logged in. Login: " + login).build();
    }}
