package ru.pmsoft.twitterkiller.rest;

import ru.pmsoft.twitterkiller.domain.dto.TwitOutput;
import ru.pmsoft.twitterkiller.domain.entity.Session;
import ru.pmsoft.twitterkiller.domain.entity.Tweet;
import ru.pmsoft.twitterkiller.domain.entity.User;
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

    @POST
    @Path("/add")
    @Produces("application/json")
    @Consumes("text/plain")
    public Response addTweet(@HeaderParam("token") String token,  String text) {

        if (!isTweetCorrect(text))
            throw new ClientException(Response.Status.BAD_REQUEST, "Tweet can not be empty or less than 140 letters");


        Session session = sessionRepository.getByToken(token);
        User user = userRepository.getById(session.getUserId());
        if (session == null || session.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is expired or does not exist");

        int id_user = user.getId();

       Tweet tweet = new Tweet(id_user, text);
        repositoryTweet.save(tweet);
        return Response.status(200).entity("Tweet is saved").build();
    }

    @GET
    @Path("/user/{login}")
    @Produces("application/json")
    public Response allTweets(@HeaderParam("token") String token, @PathParam("login") String username) {

        if (!UserFactory.isLoginCorrect(username))
            throw new ClientException(Response.Status.BAD_REQUEST, "Login can not be empty");
        Session session = sessionRepository.getByToken(token);
        if (session != null && session.isExpired())
            throw new ClientException(Response.Status.UNAUTHORIZED, "Your token is expired or does not exist");

        List<Tweet> Tweets = repositoryTweet.getAllByLogin(username);
        return Response.status(200).entity(new TwitOutput(Tweets)).build();
    }


    private static boolean isTweetCorrect(String tweet) {
        return !(tweet == null || tweet.isEmpty() || tweet.trim().isEmpty() || tweet.trim().length() > 140);
    }
}
