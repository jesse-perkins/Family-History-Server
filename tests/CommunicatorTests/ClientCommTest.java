package tests.CommunicatorTests;

import Communicators.ClientComm;
import Communicators.ServerComm;
import DataAccessObjects.TableBuilder;
import Handlers.Request.LoadRequest;
import Handlers.Request.LoginRequest;
import Handlers.Request.RegisterRequest;
import Handlers.Result.*;
import ModelClasses.Event;
import ModelClasses.Person;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.sun.corba.se.spi.activation.Server;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class ClientCommTest {
    private static ClientComm client;
    @BeforeClass
    public static void setUpClass() throws Exception {
        ServerComm.testRunner();
        client = ClientComm.getSINGLETON();
    }

    @AfterClass
    public static void teardownClass() throws Exception{
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
        ServerComm.stopServer();
    }

    @After
    public void teardown() throws Exception{
        TableBuilder.resetDatabase();
    }

    @Test
    public void defaultPage() throws Exception {
        client.defaultPage();
        assertTrue(true); //making it this far means nothing broke. Tests in browser show that it works there.
    }

    @Test
    public void clear() throws Exception {
        ClearResult result = client.clear();
        assertTrue(result.getMessage().equals(ClearResult.SUCCESS_STRING));
    }

    @Test
    public void load() throws Exception {
        Gson gson = new Gson();
        String path = new File("").getAbsolutePath();
        File testFile = new File(path +"/src/Jsons/example.json");
        InputStream testFileStream = new FileInputStream(testFile);
        InputStreamReader test = new InputStreamReader(testFileStream);
        LoadRequest request = gson.fromJson(test, LoadRequest.class);
        LoadResult loadResult = client.load(request);
        assertTrue(loadResult.getMessage().equals("Successfully added 1 users, 1 persons, and 1 events to the database."));
    }

    @Test
    public void fill() throws Exception {
        FillResult fillResult = client.fill("bill");
        // System.out.println(fillResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);
        PersonResult personResult = client.person(registerResult.getAuthToken());
        EventResult eventResult = client.event(registerResult.getAuthToken());

        fillResult = client.fill(registerResult.getUserName());
        PersonResult personResult1 = client.person(registerResult.getAuthToken());
        EventResult eventResult1 = client.event(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));
    }

    @Test
    public void fillWithGenNum() throws Exception {
        FillResult fillResult = client.fillWithGenNum("bill", 5);
        // System.out.println(fillResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);
        PersonResult personResult = client.person(registerResult.getAuthToken());
        EventResult eventResult = client.event(registerResult.getAuthToken());

        fillResult = client.fillWithGenNum(registerResult.getUserName(), 5);
        PersonResult personResult1 = client.person(registerResult.getAuthToken());
        EventResult eventResult1 = client.event(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));

        fillResult = client.fillWithGenNum(registerResult.getUserName(), 1);
        personResult1 = client.person(registerResult.getAuthToken());
        eventResult1 = client.event(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));
    }

    @Test
    public void login() throws Exception {
        LoginRequest request1 = new LoginRequest("billy", "password");
        LoginResult result = client.login(request1);

        assertTrue(result.getPersonID() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getMessage() != null);

        // System.out.print(result.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        client.register(request);
        result = client.login(request1);
        assertTrue(result.getPersonID() != null);
        assertTrue(result.getUserName() != null);
        assertTrue(result.getAuthToken() != null);
        assertTrue(result.getMessage() == null);
    }

    @Test
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult result = client.register(request);
        assertTrue(result.getPersonID() != null);
        assertTrue(result.getUserName() != null);
        assertTrue(result.getAuthToken() != null);
        assertTrue(result.getMessage() == null);
        result = client.register(request);
        assertTrue(result.getPersonID() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getMessage() != null);
        // System.out.print(result.getMessage());
    }

    @Test
    public void event() throws Exception {
        EventResult eventResult = client.event("101");
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() != null);
        assertTrue(eventResult.getSingle() == null);

        // System.out.println(eventResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);

        eventResult = client.event(registerResult.getAuthToken());
        assertTrue(eventResult.getFamilyEvents() != null);
        assertTrue(eventResult.getMessage() == null);
        assertTrue(eventResult.getSingle() == null);
        for (Event current :
             eventResult.getFamilyEvents()) {
            // System.out.print(current.getEventID() + "  ");
        }
    }

    @Test
    public void eventWithID() throws Exception {
        EventResult eventResult = client.eventWithID("10000","101");
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() != null);
        assertTrue(eventResult.getSingle() == null);

        // System.out.println(eventResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);

        eventResult = client.eventWithID("0", registerResult.getAuthToken());
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() != null);
        assertTrue(eventResult.getSingle() == null);

        // System.out.println(eventResult.getMessage());

        Gson gson = new Gson();
        String path = new File("").getAbsolutePath();
        File testFile = new File(path +"/src/Jsons/example.json");
        InputStream testFileStream = new FileInputStream(testFile);
        InputStreamReader test = new InputStreamReader(testFileStream);
        LoadRequest loadRequest = gson.fromJson(test, LoadRequest.class);
        LoadResult loadResult = client.load(loadRequest);

        LoginResult loginResult = client.login(new LoginRequest(loadRequest.getUsers().get(0).getUserName(), loadRequest.getUsers().get(0).getPassword()));

        eventResult = client.eventWithID(loadRequest.getEvents().get(0).getEventID(), loginResult.getAuthToken());

        assertTrue(eventResult.getSingle() != null);
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() == null);
        assertTrue(eventResult.getSingle().getEventID().equals(loadRequest.getEvents().get(0).getEventID()));
    }

    @Test
    public void person() throws Exception {
        PersonResult personResult = client.person("101");
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);

        personResult = client.person(registerResult.getAuthToken());
        assertTrue(personResult.getFamily() != null);
        assertTrue(personResult.getMessage() == null);
        assertTrue(personResult.getSingle() == null);

        for (Person current :
                personResult.getFamily()) {
            // System.out.print(current.getPersonID() + "  ");
        }
    }

    @Test
    public void personWithID() throws Exception {
        PersonResult personResult = client.personWithID("10000", "101");
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = client.register(request);

        personResult = client.personWithID("0", registerResult.getAuthToken());
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        personResult = client.personWithID(registerResult.getPersonID(), registerResult.getAuthToken());
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() == null);
        assertTrue(personResult.getSingle() != null);

        // System.out.print(personResult.getSingle().getPersonID());
    }

}
