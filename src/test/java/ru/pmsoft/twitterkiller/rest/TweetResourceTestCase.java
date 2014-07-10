package ru.pmsoft.twitterkiller.rest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.pmsoft.twitterkiller.domain.dto.TweetOutput;
import ru.pmsoft.twitterkiller.domain.entity.UserSession;
import ru.pmsoft.twitterkiller.domain.entity.Tweet;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.SessionRepository;
import ru.pmsoft.twitterkiller.domain.repository.TweetRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;
import ru.pmsoft.twitterkiller.rest.exceptions.ExceptionBody;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import static org.mockito.Mockito.mock;

public class TweetResourceTestCase {

    private static TweetResource createSystemUnderTest(UserRepository repository,
                                                      SessionRepository sessionRepository,
                                                      TweetRepository tweetRepository) {
        return new TweetResource(repository==null ? mock(UserRepository.class) : repository,
                sessionRepository == null ? mock(SessionRepository.class) : sessionRepository,
                tweetRepository == null ? mock(TweetRepository.class) : tweetRepository);
    }

    @Test (dataProvider = "invalidDataSourceForAddTweet")
    public void addTweet_whenTextOfTweetIsNullOrEmpty_shouldThrowClientException(String token, String text) {
        TweetResource sut = createSystemUnderTest(null, null, null);
        try{
             sut.addTweet(token, text);
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getResponse().getEntity(),
                    new ExceptionBody("Tweet can not be empty or less than 140 letters"));
            return;
        }
        fail();

    }
    @DataProvider
    public Object[][] invalidDataSourceForAddTweet()
    {
        return new Object[][]
                {
                  new Object[]{"foo", null},
                        new Object[]{"foo", "             "},
                        new Object[]{"foo", createLongString()}
                };
    }


    @Test
    public void addTweet_shouldWorkWell()
    {
        SessionRepository sessionRepository =  mock(SessionRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        User user = mock(User.class);
        when(userRepository.getById(any(Integer.class))).thenReturn(user);
        UserSession session = mock(UserSession.class);
        when((sessionRepository.getByToken(any(String.class)))).thenReturn(session);
        when(session.isExpired()).thenReturn(false);
        when(user.getId()).thenReturn(0);

        TweetResource sut = createSystemUnderTest
                (userRepository,
                sessionRepository,
                null);

        Response response = sut.addTweet("foo", "bar");
        assertEquals((String)response.getEntity(), "Tweet is saved");
    }


    @Test
    public void allTweets_whenUsernameIsEmpty_shouldThrowClientException() {
        TweetResource sut = createSystemUnderTest(mock(UserRepository.class),
                        null,
                        null);
        try {
            sut.allTweets("foo", "");
        }
        catch (ClientException ex) {
            assertEquals(ex.getResponse().getEntity(), new ExceptionBody("Login can not be empty"));
            return;
        }
        fail();
    }

    @Test (dataProvider = "InvalidReturnForDifferentTesting")
    public void alltestingmethods_whenSessionIsExpired_shouldThrowClientException(String method)
    {
        SessionRepository sessionRepository =  mock(SessionRepository.class);
        UserSession session = mock(UserSession.class);
        when((sessionRepository.getByToken(any(String.class)))).thenReturn(session);
        when(session.isExpired()).thenReturn(true);
        TweetResource sut = createSystemUnderTest(null,
                sessionRepository,
                null);

        try{
            switch(method)
            {
                case "addTweet": sut.addTweet("foo", "bar"); break;
                case "getTweet":  sut.getTweet("foo", 1); break;
                case "allTweets": sut.allTweets("foo", "bar"); break;
            }
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getResponse().getEntity(),
                    new ExceptionBody("Your token is expired or does not exist"));
            return;
        }
        fail();
    }

    @Test (dataProvider = "InvalidReturnForDifferentTesting")
    public void alltestingmethods_whenSessionIsNull_shouldThrowClientException(String method)
    {
        SessionRepository sessionRepository =  mock(SessionRepository.class);
        UserSession session = null;
        when((sessionRepository.getByToken(any(String.class)))).thenReturn(session);
        TweetResource sut = createSystemUnderTest(null,
                sessionRepository,
                null);

        try{
            switch(method)
            {
                case "addTweet": sut.addTweet("foo", "bar"); break;
                case "getTweet":  sut.getTweet("foo", 1); break;
                case "allTweets": sut.allTweets("foo", "bar"); break;
            }
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getResponse().getEntity(),
                    new ExceptionBody("Your token is expired or does not exist"));
            return;
        }
        fail();
    }


    @Test
    public void allTweets_shouldWorkWell()
    {
        List<Tweet> alltweets = new ArrayList<Tweet>();
        alltweets.add(new Tweet(0, "foo"));
        alltweets.add(new Tweet(0, "bar"));
        TweetRepository tweetRepository = mock(TweetRepository.class);
        SessionRepository sessionRepository =  mock(SessionRepository.class);
        UserSession session = mock(UserSession.class);
        when((sessionRepository.getByToken(any(String.class)))).thenReturn(session);
        when(session.isExpired()).thenReturn(false);
        when(tweetRepository.getAllByLogin(anyString())).thenReturn(alltweets);
        TweetResource sut = createSystemUnderTest(null,
                sessionRepository,
                tweetRepository);

        Response response = sut.allTweets("foo", "bar");
        TweetOutput output = (TweetOutput)response.getEntity();
        assertEquals(output.getTweets(), alltweets);
    }

    @Test
    public void getTweet_shouldWorkWell() {

        SessionRepository sessionRepository =  mock(SessionRepository.class);
        UserSession session = mock(UserSession.class);
        when((sessionRepository.getByToken(any(String.class)))).thenReturn(session);
        when(session.isExpired()).thenReturn(false);
        TweetRepository tweetRepository = mock(TweetRepository.class);
        Tweet tweet = new Tweet(0, "bar");
        when(tweetRepository.getById(anyInt())).thenReturn(tweet);
        TweetResource sut = createSystemUnderTest(null,
                sessionRepository,
                tweetRepository);

        Response response = sut.getTweet("foo", 0);
        assertEquals(response.getEntity(), tweet);
    }

  @DataProvider
    private Object[][] InvalidReturnForDifferentTesting()
  {
      return new Object[][]{
        new Object[]{"allTweets"},
              new Object[]{"getTweet"},
              new Object[]{"addTweet"}
      };
  }

    private static String createLongString()
    {
        String s = "";
        for (int i = 0; i< 150; i++)
            s = s+"1";
        return s;
    }
}
