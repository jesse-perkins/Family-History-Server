package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Result.FillResult;
import ModelClasses.User;
import ServiceModels.FillService;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class FillServiceTest {
    private static User user = new User("billy", "password", "billy@gmail.com",
            "Billy", "Fredricks", 'm');
    @BeforeClass
    public static void setUp() throws Exception {
        TableBuilder.buildThemAll();
        TableBuilder.userControl.addUser(user);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void fill() throws Exception {
        FillResult result = FillService.SINGLETON.fill(4, user.getUserName());
        assertTrue(result.getMessage() != null);
        result = FillService.SINGLETON.fill(4, user.getUserName());
        assertTrue(result.getMessage() != null);
        result = FillService.SINGLETON.fill(4, "timmy");
        assertTrue(result.getMessage() != null);
    }
}