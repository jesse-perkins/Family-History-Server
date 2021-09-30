package tests.CommunicatorTests;

import Communicators.ServerComm;
import Communicators.ServerProxy;
import DataAccessObjects.TableBuilder;
import Handlers.Request.LoadRequest;
import Handlers.Request.LoginRequest;
import Handlers.Request.RegisterRequest;
import Handlers.Result.*;
import ModelClasses.Event;
import ModelClasses.Person;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.corba.se.spi.activation.Server;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/18/2017.
 */
public class ServerProxyTest {
    ServerProxy serverProxy = new ServerProxy();
    @BeforeClass
    public static void setUp() throws Exception {
        ServerComm.testRunner();
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
    public void registerUser() throws Exception {
        TableBuilder.resetDatabase();
        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult result = serverProxy.registerUser(request);
        assertTrue(result.getPersonID() != null);
        assertTrue(result.getUserName() != null);
        assertTrue(result.getAuthToken() != null);
        assertTrue(result.getMessage() == null);
        result = serverProxy.registerUser(request);
        assertTrue(result.getPersonID() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getMessage() != null);
        // System.out.print(result.getMessage());
    }

    @Test
    public void loginUser() throws Exception {
        LoginRequest request1 = new LoginRequest("billy", "password");
        LoginResult result = serverProxy.loginUser(request1);

        assertTrue(result.getPersonID() == null);
        assertTrue(result.getUserName() == null);
        assertTrue(result.getAuthToken() == null);
        assertTrue(result.getMessage() != null);

        // System.out.print(result.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        serverProxy.registerUser(request);
        result = serverProxy.loginUser(request1);
        assertTrue(result.getPersonID() != null);
        assertTrue(result.getUserName() != null);
        assertTrue(result.getAuthToken() != null);
        assertTrue(result.getMessage() == null);
    }

    @Test
    public void clear() throws Exception {
        ClearResult result = serverProxy.clear();
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
        LoadResult loadResult = serverProxy.load(request);
        assertTrue(loadResult.getMessage().equals("Successfully added 1 users, 1 persons, and 1 events to the database."));
    }

    @Test
    public void getPerson() throws Exception {
        TableBuilder.resetDatabase();
        PersonResult personResult = serverProxy.getPerson("101");
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);

        personResult = serverProxy.getPerson(registerResult.getAuthToken());
        assertTrue(personResult.getFamily() != null);
        assertTrue(personResult.getMessage() == null);
        assertTrue(personResult.getSingle() == null);

        for (Person current :
                personResult.getFamily()) {
            // System.out.print(current.getPersonID() + "  ");
        }
    }

    @Test
    public void getPerson1() throws Exception {
        TableBuilder.resetDatabase();
        PersonResult personResult = serverProxy.getPerson("10000", "101");
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);

        personResult = serverProxy.getPerson("0", registerResult.getAuthToken());
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() != null);
        assertTrue(personResult.getSingle() == null);

        // System.out.println(personResult.getMessage());

        personResult = serverProxy.getPerson(registerResult.getPersonID(), registerResult.getAuthToken());
        assertTrue(personResult.getFamily() == null);
        assertTrue(personResult.getMessage() == null);
        assertTrue(personResult.getSingle() != null);

        // System.out.print(personResult.getSingle().getPersonID());
    }

    @Test
    public void getEvent() throws Exception {
        TableBuilder.resetDatabase();
        EventResult eventResult = serverProxy.getEvent("101");
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() != null);
        assertTrue(eventResult.getSingle() == null);

        // System.out.println(eventResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);

        eventResult = serverProxy.getEvent(registerResult.getAuthToken());
        assertTrue(eventResult.getFamilyEvents() != null);
        assertTrue(eventResult.getMessage() == null);
        assertTrue(eventResult.getSingle() == null);
        for (Event current :
                eventResult.getFamilyEvents()) {
            // System.out.print(current.getEventID() + "  ");
        }
    }

    @Test
    public void getEvent1() throws Exception {
        TableBuilder.resetDatabase();
        EventResult eventResult = serverProxy.getEvent("10000","101");
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() != null);
        assertTrue(eventResult.getSingle() == null);

        // System.out.println(eventResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);

        eventResult = serverProxy.getEvent("0", registerResult.getAuthToken());
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
        LoadResult loadResult = serverProxy.load(loadRequest);

        LoginResult loginResult = serverProxy.loginUser(new LoginRequest(loadRequest.getUsers().get(0).getUserName(), loadRequest.getUsers().get(0).getPassword()));

        eventResult = serverProxy.getEvent(loadRequest.getEvents().get(0).getEventID(), loginResult.getAuthToken());

        assertTrue(eventResult.getSingle() != null);
        assertTrue(eventResult.getFamilyEvents() == null);
        assertTrue(eventResult.getMessage() == null);
        assertTrue(eventResult.getSingle().getEventID().equals(loadRequest.getEvents().get(0).getEventID()));
    }

    @Test
    public void fill() throws Exception{
        TableBuilder.resetDatabase();
        FillResult fillResult = serverProxy.fill("bill");
        // System.out.println(fillResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);
        PersonResult personResult = serverProxy.getPerson(registerResult.getAuthToken());
        EventResult eventResult = serverProxy.getEvent(registerResult.getAuthToken());

        fillResult = serverProxy.fill(registerResult.getUserName());
        PersonResult personResult1 = serverProxy.getPerson(registerResult.getAuthToken());
        EventResult eventResult1 = serverProxy.getEvent(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));
    }

    @Test
    public void fill1() throws Exception{

        FillResult fillResult = serverProxy.fill("bill", 5);
        // System.out.println(fillResult.getMessage());

        RegisterRequest request = new RegisterRequest("billy", "password", "billy@gmail.com", "William", "Thatcher", 'm');
        RegisterResult registerResult = serverProxy.registerUser(request);
        PersonResult personResult = serverProxy.getPerson(registerResult.getAuthToken());
        EventResult eventResult = serverProxy.getEvent(registerResult.getAuthToken());

        fillResult = serverProxy.fill(registerResult.getUserName(), 5);
        PersonResult personResult1 = serverProxy.getPerson(registerResult.getAuthToken());
        EventResult eventResult1 = serverProxy.getEvent(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));

        fillResult = serverProxy.fill(registerResult.getUserName(), 1);
        personResult1 = serverProxy.getPerson(registerResult.getAuthToken());
        eventResult1 = serverProxy.getEvent(registerResult.getAuthToken());
        // System.out.println(fillResult.getMessage());
        assertFalse(personResult.getFamily().get(1).getPersonID().equals(personResult1.getFamily().get(0).getPersonID()));
        assertFalse(eventResult.getFamilyEvents().get(1).getEventID().equals(eventResult1.getFamilyEvents().get(0).getEventID()));
    }
}