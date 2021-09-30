package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Request.LoginRequest;
import Handlers.Result.LoginResult;
import ModelClasses.User;
import ServiceModels.LoginService;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class LoginServiceTest {
    private static User user;

    @BeforeClass
    public static void setUp() throws Exception {
        TableBuilder.buildThemAll();
        user = new User("billy", "password", "billy@gmail.com",
                "Billy", "Fredricks", 'm');
        TableBuilder.userControl.addUser(user);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void login() throws Exception {
        LoginRequest request = new LoginRequest(user.getUserName(), user.getPassword());
        LoginResult result = LoginService.login(request);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getUserName().equals(user.getUserName()));
        assertTrue(result.getAuthToken() != null);
        assertTrue(result.getPersonID().equals(user.getPersonID()));
        request = new LoginRequest("frank", "password");
        result = LoginService.login(request);
        assertTrue(result.getMessage()!= null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getPersonID() == null);
        request = new LoginRequest(user.getUserName(), "wrongpass");
        result = LoginService.login(request);
        assertTrue(result.getMessage()!= null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getPersonID() == null);
    }

}