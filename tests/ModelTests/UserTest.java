package tests.ModelTests;

import ModelClasses.AuthKey;
import ModelClasses.User;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/5/2017.
 */
public class UserTest {
    User newGuy;
    @Before
    public void setUp() throws Exception {
        newGuy = new User("billy", "pass", "billy@gmail.com", "Billy",
                "Franklin", 'm', "Billy_Franklin");
    }

    @Test
    public void getUserName() throws Exception {
        assertTrue(newGuy.getUserName().equals("billy"));
    }

    @Test
    public void getPassword() throws Exception {
        assertTrue(newGuy.getPassword().equals("pass"));
    }

    @Test
    public void getEmail() throws Exception {
        assertTrue(newGuy.getEmail().equals("billy@gmail.com"));
    }

    @Test
    public void getAuthKeys() throws Exception {
        assertTrue(newGuy.getAuthKeys() == null);
    }

    @Test
    public void setAuthKeys() throws Exception {
        TreeSet<AuthKey> holder = new TreeSet<>();
        holder.add(new AuthKey(12345));
        holder.add(new AuthKey(11111));
        holder.add(new AuthKey(10000));
        newGuy.setAuthKeys(holder);
        assertTrue(newGuy.getAuthKeys() != null);
    }

    @Test
    public void getFirstName() throws Exception {
        assertTrue(newGuy.getFirstName().equals("Billy"));
    }

    @Test
    public void getLastName() throws Exception {
        assertTrue(newGuy.getLastName().equals("Franklin"));
    }

    @Test
    public void getGender() throws Exception {
        assertTrue(newGuy.getGender() == 'm');
    }

    @Test
    public void getRootPersonID() throws Exception {
        assertTrue(newGuy.getPersonID() == "Billy_Franklin");
    }

    @Test
    public void setRootPersonID() throws Exception {
        newGuy.setPersonID("William_Franklin");
        assertTrue(newGuy.getPersonID() == "William_Franklin");
        assertFalse(newGuy.getPersonID() == "Billy_Franklin");
    }
}