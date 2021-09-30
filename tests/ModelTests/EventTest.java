package tests.ModelTests;

import ModelClasses.Event;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/5/2017.
 */
public class EventTest {
    Event thing;
    @Before
    public void setUp() throws Exception {
        thing = new Event("billy", "billy", 123.123, -12.3, "China", "Beijing",
                "Baptism", 1998);
    }

    @Test
    public void getID() throws Exception {
        assertTrue(thing.getEventID() == null);
    }

    @Test
    public void setID() throws Exception {
        thing.setEventID("123abc");
        assertFalse(thing.getEventID() == null);
        assertTrue(thing.getEventID() == "123abc");
    }

    @Test
    public void getLatitude() throws Exception {
        assertTrue(thing.getLatitude() == 123.123);
    }

    @Test
    public void getLongitude() throws Exception {
        assertTrue(thing.getLongitude() == -12.3);
    }

    @Test
    public void getCountry() throws Exception {
        assertTrue(thing.getCountry().equals("China"));
    }

    @Test
    public void getCity() throws Exception {
        assertTrue(thing.getCity().equals("Beijing"));
    }

    @Test
    public void getEventType() throws Exception {
        assertTrue(thing.getEventType().equals("Baptism"));
    }

    @Test
    public void getYear() throws Exception {
        assertTrue(thing.getYear() == 1998);
    }

}