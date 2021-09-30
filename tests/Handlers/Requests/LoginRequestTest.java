package tests.Handlers.Requests;

import Handlers.Request.LoginRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/15/2017.
 */
public class LoginRequestTest {
    LoginRequest request;
    @Before
    public void setUp(){
        request = new LoginRequest("billy", "password");
    }
    @Test
    public void getUserName() throws Exception {
        assertTrue(request.getUserName().equals("billy"));
    }

    @Test
    public void getPassword() throws Exception {
        assertTrue(request.getPassword().equals("password"));
    }

}