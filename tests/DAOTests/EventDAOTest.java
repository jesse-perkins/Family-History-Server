package tests.DAOTests;

import DataAccessObjects.EventDAO;
import DataAccessObjects.TableBuilder;
import ModelClasses.Event;
import ModelClasses.Location;
import ModelClasses.Person;
import ModelClasses.User;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/13/2017.
 */
public class EventDAOTest {
    private static Person billyPerson = null;
    private static User billy = new User("billy", "password", "billy@gmail.com",
            "Billy", "Danielson", 'm');
    private static Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
    private static Event birth;
    private static Event baptism;
    private static Event christening;
    private static ArrayList<Event> uploadMe = new ArrayList<>();


    @BeforeClass
    public static void setUpClass() throws Exception {
        TableBuilder.buildThemAll();
        TableBuilder.userControl.addUser(billy);
        billyPerson = TableBuilder.personControl.getPerson(billy.getPersonID());
        birth = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "birth");
        baptism = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1998, loc, "baptism");
        christening = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "christening");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void createSingletonInstance() throws Exception {
        assertFalse(EventDAO.createSingletonInstance(TableBuilder.getConnection()));
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(EventDAO.getInstance() != null);
    }

    @Test
    public void addEvent() throws Exception {
        assertTrue(TableBuilder.eventControl.addEvent(birth)!= null);
        assertTrue(TableBuilder.eventControl.addEvent(birth)== null);
    }

    @Test
    public void getEvent() throws Exception {
        TableBuilder.eventControl.addEvent(birth);
        assertTrue(TableBuilder.eventControl.getEvent(birth.getEventID()).equals(birth));
        assertFalse(TableBuilder.eventControl.getEvent(birth.getEventID()).equals(baptism));
    }

    @Test
    public void uploadEvents() throws Exception {
        uploadMe.add(birth);
        uploadMe.add(baptism);
        uploadMe.add(christening);
        assertTrue(TableBuilder.eventControl.uploadEvents(uploadMe));
    }

    @Test
    public void getPersonEvents() throws Exception {
        uploadMe.add(birth);
        uploadMe.add(baptism);
        uploadMe.add(christening);
        TableBuilder.eventControl.uploadEvents(uploadMe);
        ArrayList<Event> temp =TableBuilder.eventControl.getPersonEvents(billyPerson.getPersonID());
        assertTrue(TableBuilder.eventControl.getPersonEvents(billyPerson.getPersonID()).size() == 3);
    }

    @Test
    public void getFamilyEvents() throws Exception {uploadMe.add(birth);
        uploadMe.add(baptism);
        uploadMe.add(christening);
        TableBuilder.eventControl.uploadEvents(uploadMe);
        assertTrue(TableBuilder.eventControl.getFamilyEvents(billyPerson.getDescendant()).size() == 3);
    }

    @Test
    public void removeFamilyEventData() throws Exception {
        uploadMe.add(birth);
        uploadMe.add(baptism);
        uploadMe.add(christening);
        TableBuilder.eventControl.uploadEvents(uploadMe);
        TableBuilder.eventControl.removeFamilyEventData(billyPerson.getDescendant());
        assertTrue(TableBuilder.eventControl.getFamilyEvents(billyPerson.getDescendant()).size() == 0);
    }

}