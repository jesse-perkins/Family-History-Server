package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Request.RegisterRequest;
import Handlers.Result.RegisterResult;
import ModelClasses.User;
import ServiceModels.RegisterService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class RegisterServiceTest {
    private static User user;

    @BeforeClass
    public static void setUp() throws Exception {
        TableBuilder.buildThemAll();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }
    @Test
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest("billy", "password",
                "billy@gmail.com","Billy", "Fredricks", 'm');
        RegisterResult result = RegisterService.register(request);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getUserName() != null);
        assertTrue(result.getPersonID() != null);
        assertTrue(result.getAuthToken() != null);
        result = RegisterService.register(request);
        assertTrue(result.getMessage() != null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getPersonID() == null);
        assertTrue(result.getAuthToken() == null);
    }

}