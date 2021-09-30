package tests.DAOTests;

import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import ModelClasses.User;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/13/2017.
 */
public class UserDAOTest {
    private User billy = new User("billy", "password", "billy@gmail.com",
            "Billy", "Franklin", 'm');
    private User tommy = new User("tommy", "password", "tommy@gmail.com",
            "Tommy", "Franklin", 'm');
    private User gary = new User("gary", "password", "gary@gmail.com",
            "Gary", "Franklin", 'm', "Gary_Franklin");
    @BeforeClass
    public static void setUpClass() throws Exception {
        TableBuilder.buildThemAll();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void createSingletonInstance() throws Exception {
        assertFalse(UserDAO.createSingletonInstance(TableBuilder.getConnection()));
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(UserDAO.getInstance()!= null);
    }

    @Test
    public void addUser() throws Exception {
        assertTrue(TableBuilder.userControl.addUser(billy));
        assertFalse(TableBuilder.userControl.addUser(billy));
        assertTrue(TableBuilder.userControl.addUser(gary));
        TableBuilder.userControl.addUser((gary));
    }

    @Test
    public void getUser() throws Exception {
        assertTrue(TableBuilder.userControl.addUser(tommy));
        User temp = TableBuilder.userControl.getUser(tommy.getUserName());
        boolean equals = true;
        if(!temp.getUserName().equals(tommy.getUserName()) ||
                !temp.getFirstName().equals( tommy.getFirstName()) ||
                !temp.getLastName().equals(tommy.getLastName()) ||
                !temp.getEmail().equals(tommy.getEmail()) ||
                !temp.getGender().equals(tommy.getGender()) ||
                !temp.getPassword().equals(tommy.getPassword()) ||
                !temp.getPersonID().equals(tommy.getPersonID())){
            equals = false;
        }

        assertTrue(equals);
    }

}