package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Result.ClearResult;
import ModelClasses.Event;
import ModelClasses.Location;
import ModelClasses.Person;
import ModelClasses.User;
import ServiceModels.ClearService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class ClearServiceTest {
    @Before
    public void setUp() throws Exception {
        TableBuilder.buildThemAll();
        Person mom = new Person("billy", "Mr.", "Danielson", 'f');
        Person dad = new Person("billy", "Mrs.", "McKlasky", 'm');
        User billy = new User("billy", "password", "billy@gmail.com",
                "Billy", "Danielson", 'm');
        TableBuilder.userControl.addUser(billy);
        Person billyPerson = TableBuilder.personControl.getPerson(billy.getPersonID());
        Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
        Event birth = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "birth");
        Event baptism = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1998, loc, "baptism");
        Event christening = new Event(billyPerson.getDescendant(), billyPerson.getPersonID(), 1990, loc, "christening");
        TableBuilder.userControl.addUser(billy);
        TableBuilder.personControl.addParents(mom, dad, billyPerson);
        TableBuilder.eventControl.addEvent(birth);
        TableBuilder.eventControl.addEvent(baptism);
        TableBuilder.eventControl.addEvent(christening);
    }

    @After
    public void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void clear() throws Exception {
        ClearResult result = ClearService.clear();
        assertTrue(result.getMessage().equals(ClearResult.SUCCESS_STRING));
    }

}
