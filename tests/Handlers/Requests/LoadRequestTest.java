package tests.Handlers.Requests;

import Handlers.Request.LoadRequest;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/15/2017.
 */
public class LoadRequestTest {
    LoadRequest request = new LoadRequest(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @Test
    public void getNewUsers() throws Exception {
        assertTrue(request.getUsers() != null);
    }

    @Test
    public void getNewPersons() throws Exception {
        assertTrue(request.getPersons() != null);
    }

    @Test
    public void getNewEvents() throws Exception {
        assertTrue(request.getEvents() != null);
    }

}
