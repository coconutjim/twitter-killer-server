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
import java.util.logging.Logger;

@Path("tweet")
public class TweetResource {


    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private TweetRepository tweetRepository;


    @Inject
    public TweetResource(UserRepository userRepository,
                         SessionRepository sessionRepository,
                         TweetRepository tweetRepository) {

        if (userRepository == null)
            throw new IllegalArgumentException("Parameter 'userRepository' can't be null");
        if (sessionRepository == null)
            throw new IllegalArgumentException("Parameter 'sessionRepository' can't be null");
        if (tweetRepository == null)
            throw new IllegalArgumentException("Parameter 'tweetRepository' can't be null");

        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.tweetRepository = tweetRepository;
    }

    private static boolean isTweetCorrect(String tweet) {
        return !(tweet == null || tweet.isEmpty() || tweet.trim().isEmpty() || tweet.length() > 140);
    }

    private static boolean isTokenCorrect(String token){
        return !(token == null || token.trim().isEmpty());
    }

    @POST
    @Path("/add")
    public Response addTweet(@HeaderParam("token") String token, String text) {

        if (!isTweetCorrect(text))
            throw new ClientException(Response.Status.BAD_REQUEST, "Tweet can not be empty or less than 140 letters");
        if (!isTokenCorrect(token))
            throw new ClientException(Response.Status.BAD_REQUEST,"Token can not be empty");

        UserSession userSession = sessionRepository.getByToken(token);

        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is invalid");

        User user = userRepository.getById(userSession.getUserId());
        int id_user = user.getId();

        Tweet tweet = new Tweet(id_user, text);
        tweetRepository.createOrUpdate(tweet);
        return Response.status(200).entity("Tweet is saved").build();
    }

    @GET
    @Path("/user/{login}")
    @Produces("application/json")
    public Response allTweets(@HeaderParam("token") String token, @PathParam("login") String username) {

        if (!UserFactory.isLoginCorrect(username))
            throw new ClientException(Response.Status.BAD_REQUEST, "Login can not be empty");

        if(userRepository.getByLogin(username) == null)
            throw new ClientException(Response.Status.BAD_REQUEST, "User is not found");

        UserSession userSession = sessionRepository.getByToken(token);
        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is invalid");

        List<Tweet> tweets = tweetRepository.getAllByLogin(username);
        return Response.status(200).entity(new TweetOutput(tweets)).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getTweet(@HeaderParam("token") String token, @PathParam("id") int tweetId) {

        if (!isTokenCorrect(token))
            throw new ClientException(Response.Status.BAD_REQUEST,"Token was empty");
        UserSession userSession = sessionRepository.getByToken(token);
        if (userSession == null || userSession.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is invalid");

        Tweet tweet = tweetRepository.getById(tweetId);
        if(tweet == null)
            throw new ClientException(Response.Status.BAD_REQUEST,"Tweet by this id was not found");

        return Response.status(200).entity(tweet).build();
    }
}
