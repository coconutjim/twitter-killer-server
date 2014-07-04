package RESTHello;

import domain.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import rest.Login;
import java.util.HashMap;
import static org.junit.Assert.*;

public class LoginTest {

    @Ignore
    @Test
    public void testAuthentication() throws Exception {

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
        HashMap<String, User> allUsers = new HashMap<>();

        User newUser = new User("Anton", "111");
        allUsers.put("Anton", newUser);
        Login.setAllUsers(allUsers);

        assertEquals(400, user.registration("Anton", "101").getStatus());
    }
}