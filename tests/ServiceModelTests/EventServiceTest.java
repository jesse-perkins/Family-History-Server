package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Result.EventResult;
import ModelClasses.*;
import ServiceModels.EventService;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/15/2017.
 */
public class EventServiceTest {
    private static Event birth;
    private static Event baptism;
    private static Event christening;
    private static AuthKey key;
    @BeforeClass
    public static void setUp() throws Exception {
        TableBuilder.buildThemAll();
        User user = new User("billy", "password", "billy@gmail.com",
                "Billy", "Fredricks", 'm');
        TableBuilder.userControl.addUser(user);
        Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
        Person billyPerson = TableBuilder.personControl.getPerson(user.getPersonID());
        birth = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "birth");
        baptism = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1998, loc, "baptism");
        christening = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "christening");
        ArrayList<Event> events = new ArrayList<>();
        events.add(birth);
        events.add(baptism);
        events.add(christening);
        TableBuilder.eventControl.uploadEvents(events);
        key = TableBuilder.authControl.addAuthToken(user.getUserName());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void event() throws Exception {
        EventResult result = EventService.event(""+key.getKey());
        assertTrue(result.getMessage() == null);
        assertTrue(result.getFamilyEvents() != null);
        assertTrue(result.getFamilyEvents().size() == 3);
        assertTrue(result.getSingle() == null);
        assertTrue(result.getFamilyEvents().get(0).equals(birth));
        assertTrue(result.getFamilyEvents().get(1).equals(baptism));
        assertTrue(result.getFamilyEvents().get(2).equals(christening));
        result = EventService.event("123");
        assertTrue(result.getMessage() != null);
        assertTrue(result.getMessage().equals("Error: Invalid Authorization Key."));
    }

    @Test
    public void event1() throws Exception {
        EventResult result = EventService.event(""+key.getKey(), birth.getEventID());
        assertTrue(result.getMessage() == null);
        assertTrue(result.getFamilyEvents() == null);
        assertTrue(result.getSingle() != null);
        result.getSingle().equals(birth);
        result = EventService.event("123", birth.getEventID());
        assertTrue(result.getMessage() != null);
        assertTrue(result.getFamilyEvents() == null);
        assertTrue(result.getSingle() == null);
        result = EventService.event(""+key.getKey(), "1234");
        assertTrue(result.getMessage() != null);
    }

}