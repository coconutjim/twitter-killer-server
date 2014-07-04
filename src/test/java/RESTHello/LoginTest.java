package RESTHello;

import domain.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import rest.Login;
import java.util.HashMap;
import static org.junit.Assert.*;

public class LoginTest {

    HashMap<String, User> allTempUsers;

    @Before
    public void create() throws Exception {
        allTempUsers = new HashMap<>();
        Login.setAllUsers(allTempUsers);
    }

    @Test
    public void testAuthenticationNullNull() throws Exception {
        Login user = new Login();

        assertEquals(401, user.authentication(null, null).getStatus());
    }

    @Test
    public void testAuthenticationNullPassword() throws Exception {
        Login user = new Login();

        assertEquals(401, user.authentication(null, "000").getStatus());
    }

    @Test
    public void testAuthenticationLoginNull() throws Exception {
        Login user = new Login();

        assertEquals(401, user.authentication("Anton", null).getStatus());
    }

    @Ignore
    @Test
    public void testAuthenticationOK() throws Exception {
        Login user = new Login();
        user.registration("Mike", "666");

        assertEquals(200, user.authentication("Mike", "666").getStatus());
    }

    @Ignore
    @Test
    public void testAuthenticationBad() throws Exception {
        Login user = new Login();
        user.registration("Anton", "101");

        assertEquals(401, user.authentication("Anton", "111").getStatus());
    }

    @Test
    public void testRegistrationNullNull() throws Exception {
        Login user = new Login();

        assertEquals(401, user.registration(null, null).getStatus());
    }

    @Test
    public void testRegistrationNullPassword() throws Exception {
        Login user = new Login();

        assertEquals(401, user.registration(null, "000").getStatus());
    }

    @Test
    public void testRegistrationLoginNull() throws Exception {
        Login user = new Login();

        assertEquals(401, user.registration("Anton", null).getStatus());
    }

    @Test
    public void testRegistrationSameLogin() throws Exception {
        Login user = new Login();

        User newUser = new User("Anton", "111");
        allTempUsers.put("Anton", newUser);

        assertEquals(400, user.registration("Anton", "101").getStatus());
    }

    @After
    public void delete() throws Exception {
        allTempUsers.clear();
    }

}