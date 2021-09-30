package tests.Handlers.Results;

import Handlers.Result.LoginResult;
import ModelClasses.AuthKey;
import ModelClasses.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class LoginResultTest {
    Person billy = new Person("Billy_Sanders", "billy","Billy","Sanders",'m');
    AuthKey key = new AuthKey(10000);
    String userName = "billy";
    @Test
    public void getToken() throws Exception {
        LoginResult result = new LoginResult(key.getStringKey(), userName, billy.getPersonID());
        assertTrue(result.getMessage()== null);
        assertTrue(result.getAuthToken() != null);
    }

    @Test
    public void getUserName() throws Exception {
        LoginResult result = new LoginResult(key.getStringKey(), userName, billy.getPersonID());
        assertTrue(result.getMessage()== null);
        assertTrue(result.getUserName() != null);
    }

    @Test
    public void getPersonID() throws Exception {
        LoginResult result = new LoginResult(key.getStringKey(), userName, billy.getPersonID());
        assertTrue(result.getMessage()== null);
        assertTrue(result.getPersonID() != null);
    }

    @Test
    public void getMessage() throws Exception {
        LoginResult result = new LoginResult("Error");
        assertTrue(result.getMessage()!= null);
        assertTrue(result.getPersonID() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getAuthToken() == null);
    }

}