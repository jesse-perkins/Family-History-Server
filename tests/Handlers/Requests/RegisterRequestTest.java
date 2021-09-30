package tests.Handlers.Requests;

import Handlers.Request.RegisterRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class RegisterRequestTest {
    private static RegisterRequest request;
    @BeforeClass
    public static void setUp() throws Exception{
        request = new RegisterRequest("billy", "password", "billy@gmail.com",
                "Billy", "Fredricks", 'm');
    }
    @Test
    public void getUserName() throws Exception {
        assertTrue(request.getUserName().equals("billy"));
    }

    @Test
    public void getPassword() throws Exception {
        assertTrue(request.getPassword().equals("password"));
    }

    @Test
    public void getEmail() throws Exception {
        assertTrue(request.getEmail().equals("billy@gmail.com"));
    }

    @Test
    public void getFirstName() throws Exception {
        assertTrue(request.getFirstName().equals("Billy"));
    }

    @Test
    public void getLastName() throws Exception {
        assertTrue(request.getLastName().equals("Fredricks"));
    }

    @Test
    public void getGender() throws Exception {
        assertTrue(request.getGender().equals('m'));
    }

}