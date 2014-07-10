package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TweetOutput;
import ru.pmsoft.twitterkiller.domain.entity.Tweet;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;
import ru.pmsoft.twitterkiller.domain.factory.UserFactory;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("tweet")
public class TweetResource {


    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private TweetRepository repositoryTweet;


    @Inject
    public TweetResource(UserRepository userRepository,
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
    }

    private static boolean isTweetCorrect(String tweet) {
        return !(tweet == null || tweet.isEmpty() || tweet.trim().isEmpty() || tweet.trim().length() > 140);
    }

    private static boolean isTokenCorrect(String token){
        return !(token == null || token.trim().isEmpty());
    }

    @POST
    @Path("/add")
    @Produces("application/json")
    @Consumes("text/plain")
    public Response addTweet(@HeaderParam("token") String token, String text) {

        if (!isTweetCorrect(text))
            throw new ClientException(Response.Status.BAD_REQUEST, "Tweet can not be empty or less than 140 letters");
        if (!isTokenCorrect(token))
            throw new ClientException(Response.Status.BAD_REQUEST,"Token was empty");

        UserSession userSession = sessionRepository.getByToken(token);
        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is expired or does not exist");
        User user = userRepository.getById(userSession.getUserId());
        int id_user = user.getId();

        Tweet tweet = new Tweet(id_user, text);
        repositoryTweet.createOrUpdate(tweet);
        return Response.status(200).entity("Tweet is saved").build();
    }

    @GET
    @Path("/user/{login}")
    @Produces("application/json")
    public Response allTweets(@HeaderParam("token") String token, @PathParam("login") String username) {

        if (!UserFactory.isLoginCorrect(username))
            throw new ClientException(Response.Status.BAD_REQUEST, "Login can not be empty");
        UserSession userSession = sessionRepository.getByToken(token);
        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is expired or does not exist");

        List<Tweet> Tweets = repositoryTweet.getAllByLogin(username);
        return Response.status(200).entity(new TweetOutput(Tweets)).build();
    }

    @GET
    @Path("/{id:^[0-9]*$}")
    @Produces("application/json")
    public Response getTweet(@HeaderParam("token") String token, @PathParam("id") int tweetId) {
        if (!isTokenCorrect(token))
            throw new ClientException(Response.Status.BAD_REQUEST,"Token was empty");
        UserSession userSession = sessionRepository.getByToken(token);
        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is expired or does not exist");
        Tweet tweet = repositoryTweet.getById(tweetId);
        return Response.status(200).entity(tweet).build();
    }
}
