package RESTHello;

import domain.entity.User;
import domain.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import rest.Login;

import java.util.Arrays;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {

    UserRepository allTempUsers;
    Login login;

    @Before
    public void create() throws Exception {
        allTempUsers = mock(UserRepository.class);
        User johnUser = new User("John","123456");
        User samUser = new User("Sam","qweqwe");
        when(allTempUsers.getByLogin("John")).thenReturn(johnUser);
        when(allTempUsers.getByLogin("Sam")).thenReturn(samUser);
        when(allTempUsers.values()).thenReturn(Arrays.asList(johnUser,samUser));
        login.setAllUsers(allTempUsers);
    }

    @Test
    public void testAuthenticationNullNull() throws Exception {


        assertEquals(401, login.authentication(null, null).getStatus());
    }

    @Test
    public void testAuthenticationNullPassword() throws Exception {


        assertEquals(401, login.authentication(null, "000").getStatus());
    }

    @Test
    public void testAuthenticationLoginNull() throws Exception {


        assertEquals(401, login.authentication("Anton", null).getStatus());
    }

    @Test
    public void testAuthenticationOK() throws Exception {

        login.registration("Mike", "666");

        assertEquals(200, login.authentication("Mike", "666").getStatus());
    }

    @Test
    public void testAuthenticationBad() throws Exception {

        login.registration("Anton", "101");

        assertEquals(401, login.authentication("Anton", "111").getStatus());
    }

    @Test
    public void testRegistrationNullNull() throws Exception {


        assertEquals(401, login.registration(null, null).getStatus());
    }

    @Test
    public void testRegistrationNullPassword() throws Exception {


        assertEquals(401, login.registration(null, "000").getStatus());
    }

    @Test
    public void testRegistrationLoginNull() throws Exception {


        assertEquals(401, login.registration("Anton", null).getStatus());
    }

    @Test
    public void testRegistrationSameLogin() throws Exception {


        User newUser = new User("Anton", "11111111555");
        allTempUsers.save( newUser);

        assertEquals(400, login.registration("Anton", "11111111555").getStatus());
    }



}