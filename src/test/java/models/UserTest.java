package models;

import domain.entity.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UserTest {
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Anton", "000");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Anton", user.getLogin());
    }

    @Test
    public void testSetName() throws Exception {
        user.setLogin("New one");
        assertEquals("New one", user.getLogin());
    }

    @Test
    public void testSetPasswordHash() throws Exception {
        String temp = user.getPasswordHash();
        user.setPasswordHash("111");
        assertNotEquals(temp, user.getPasswordHash());
    }

    @Test
    public void testCheckTrueUserPassword() throws Exception {
        assertTrue(user.checkPassword("000"));
    }

    @Test
    public void testCheckFalseUserPassword() throws Exception {
        assertEquals(false, user.checkPassword("010"));
    }

    @Ignore
    @Test
    public void testGetSHA256() throws Exception {

    }
}