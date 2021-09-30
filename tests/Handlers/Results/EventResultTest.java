package tests.Handlers.Results;

import Handlers.Result.EventResult;
import ModelClasses.Event;
import ModelClasses.Location;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class EventResultTest {
    @Test
    public void getMessage() throws Exception {
        EventResult result = new EventResult("Error: Database issues.");
        assertTrue(result.getMessage() != null);
        assertTrue(result.getSingle() == null);
        assertTrue(result.getFamilyEvents() == null);
    }

    @Test
    public void getSingle() throws Exception {
        Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
        Event event = new Event("billy", "10000", 1990, loc, "birth");
        EventResult result = new EventResult(event);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getSingle() != null);
        assertTrue(result.getFamilyEvents() == null);
    }

    @Test
    public void getFamilyEvents() throws Exception {
        Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
        Event birth = new Event("billy", "10000", 1990, loc, "birth");
        Event baptism = new Event("billy", "10000", 1998, loc, "baptism");
        Event christening = new Event("billy", "10000", 1990, loc, "christening");
        ArrayList<Event> familyEvents = new ArrayList<>();
        familyEvents.add(birth);
        familyEvents.add(baptism);
        familyEvents.add(christening);
        EventResult result = new EventResult(familyEvents);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getSingle() == null);
        assertTrue(result.getFamilyEvents().size() == 3);
    }

}