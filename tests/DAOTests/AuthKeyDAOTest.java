package tests.DAOTests;

import DataAccessObjects.AuthKeyDAO;
import DataAccessObjects.TableBuilder;
import ModelClasses.AuthKey;
import ModelClasses.User;
import org.junit.*;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/13/2017.
 */
public class AuthKeyDAOTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
         TableBuilder.buildThemAll();
         TableBuilder.userControl.addUser(new User("billy", "password", "billy@gmail.com",
                 "Billy", "Danielson", 'm'));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void createSingletonInstance() throws Exception {
        assertFalse(AuthKeyDAO.createSingletonInstance(TableBuilder.getConnection()));
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(AuthKeyDAO.getInstance() != null);
    }

    @Test
    public void addAuthToken() throws Exception {
        TableBuilder.authControl.addAuthToken("billy");
    }

    @Test
    public void checkAuthKey() throws Exception {
        AuthKey key = TableBuilder.authControl.addAuthToken("billy");
        assertTrue(TableBuilder.authControl.checkAuthKey(key));
    }

    @Test
    public void getUser() throws Exception {
        AuthKey key = TableBuilder.authControl.addAuthToken("billy");
        assertTrue(TableBuilder.authControl.getUser(key).equals("billy"));
    }

}
