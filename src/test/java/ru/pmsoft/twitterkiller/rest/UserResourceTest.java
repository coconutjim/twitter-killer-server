package ru.pmsoft.twitterkiller.rest;

import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.pmsoft.twitterkiller.domain.dto.TokenOutput;
import ru.pmsoft.twitterkiller.domain.entity.User;
import ru.pmsoft.twitterkiller.domain.repository.InMemoryUserRepository;
import ru.pmsoft.twitterkiller.domain.repository.UserRepository;
import ru.pmsoft.twitterkiller.rest.exceptions.ClientException;

import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Александра on 07.07.14.
 */
public class UserResourceTest extends TestCase {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    public UserRepository hashrep = new InMemoryUserRepository();

    @Test(expected = ClientException.class)
    public void testAuthentication_hasToThrow_ClientException_LoginIsNotCorrect() throws Exception {
        try{
              UserResource sut = new UserResource(hashrep);
              sut.authentication(null, null);
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getMessage(), new ClientException(Response.Status.BAD_REQUEST, "Login can not be empty").getMessage());
        }

    }
    @Test(expected = ClientException.class)
    public void testAuthentication_hasToThrow_ClientException_PasswordDoesNotExist() throws Exception {
        try
        {
            UserRepository hashrep = new InMemoryUserRepository();
            UserResource sut = new UserResource(hashrep);
            sut.authentication("user", null);
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getMessage(), new ClientException(Response.Status.BAD_REQUEST, "Password can not be empty").getMessage());
        }
    }
    @Test(expected = ClientException.class)
    public void testAuthentication_hasToThrow_ClientException_UserIsNotFound() throws Exception {
        try{
        UserRepository hashrep = new InMemoryUserRepository();
        UserResource sut = new UserResource(hashrep);
        assertEquals(sut.authentication("user", "password"), new ClientException(Response.Status.BAD_REQUEST, "User is not found").getMessage());
        }
        catch (ClientException ex)
        {
            System.out.print(ex.getMessage());
        }
    }
    @Test(expected = ClientException.class)
    public void testAuthentication_hasToThrow_ClientException_PasswordIsNotCorrect() throws Exception {

        try
        {
             UserRepository hashrep = new InMemoryUserRepository();
             User dummy = new User("user", "password");
             dummy.setExpiration(new Date());
             hashrep.save(dummy);
             UserResource sut = new UserResource(hashrep);
             assertEquals(sut.authentication("user", "0"), new ClientException(Response.Status.BAD_REQUEST, "Password is not correct").getMessage());
        }
        catch(ClientException ex)
        {
            System.out.print(ex.getMessage());
        }

    }
    @Test
    public void testAuthentication_hasToChangeTokenAndExpiration() throws Exception {
        UserRepository hashrep = new InMemoryUserRepository();
        User dummy = new User("user", "password");
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR) - 1);
        dummy.setExpiration(calendar.getTime());
        hashrep.save(dummy);
        UserResource sut = new UserResource(hashrep);
        sut.authentication("user", "password");
        Date expiration = dummy.getExpiration();
        assertNotSame(calendar.getTime(), expiration);
    }
    @Test
    public void testAuthentication_hasToReturnJSONwithExpirationAndToken() throws Exception {
        UserRepository hashrep = new InMemoryUserRepository();
        User dummy = new User("user", "password");
        dummy.setExpiration(new Date());
        hashrep.save(dummy);
        UserResource sut = new UserResource(hashrep);
        Response resp =  sut.authentication("user", "password");
        TokenOutput token = (TokenOutput)resp.getEntity();
        assertEquals(dummy.getToken(), token.getToken());
        assertEquals(dummy.getExpiration(), token.getExpiration());
    }


    @Test(expected = ClientException.class)
    public void testRegistration_hasToThrow_ClientException_LoginIsNotCorrect() throws Exception {
        try{
        UserRepository hashrep = new InMemoryUserRepository();
        UserResource sut = new UserResource(hashrep);
        sut.registration(null, null);
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getMessage(), new ClientException(Response.Status.BAD_REQUEST, "Login can not be empty").getMessage());
        }
    }
    @Test(expected = ClientException.class)
    public void testRegistration_hasToThrow_ClientException_PasswordDoesNotExist() throws Exception {
        try{
        UserRepository hashrep = new InMemoryUserRepository();
        UserResource sut = new UserResource(hashrep);
        sut.registration("user", null);
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getMessage(), new ClientException(Response.Status.BAD_REQUEST, "Password can not be empty").getMessage());
        }
    }
    @Test(expected = ClientException.class)
    public void testRegistration_hasToThrow_ClientException_SuchLoginAlreadyExist() throws Exception {
        try{
        UserRepository hashrep = new InMemoryUserRepository();
        hashrep.save(new User("user", "password"));
        UserResource sut = new UserResource(hashrep);
        sut.registration("user", "password");
        }
        catch (ClientException ex)
        {
            assertEquals(ex.getMessage(), new ClientException(Response.Status.BAD_REQUEST, "Login is already taken").getMessage());
        }
    }
    @Test
    public void testRegistration_OK() throws Exception {
        UserRepository hashrep = new InMemoryUserRepository();
        String login = "user2";
        hashrep.save(new User("user1", "password"));
        UserResource sut = new UserResource(hashrep);
        Response resp =  sut.registration(login, "password");
        String s = (String)resp.getEntity();
        String arr[] = s.split(":");
        arr[1] = arr[1].trim();
        assertTrue(arr[1].equals(login));
    }
}
