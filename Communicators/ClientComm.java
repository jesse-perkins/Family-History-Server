package Communicators;

import Handlers.Request.LoadRequest;
import Handlers.Request.LoginRequest;
import Handlers.Request.RegisterRequest;
import Handlers.Result.*;
import ModelClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Jesse on 3/9/2017.
 */
public class ClientComm {
    //Constants used to construct URLs, set HTTP_METHOD, and handle handler URL Designations
    private static final String SERVER_HOST = "http://localhost";
    private static final String URL_PREFIX = "" + SERVER_HOST + ":" + ServerComm.SERVER_PORT_NUM;
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static final String DEFAULT_HANDLER_DESIGNATOR= "/";
    private static final String CLEAR_HANDLER_DESIGNATOR= "/clear";
    private static final String LOAD_HANDLER_DESIGNATOR= "/load";
    private static final String FILL_HANDLER_DESIGNATOR= "/fill";
    private static final String LOGIN_HANDLER_DESIGNATOR= "/user/login";
    private static final String REGISTER_HANDLER_DESIGNATOR= "/user/register";
    private static final String EVENT_HANDLER_DESIGNATOR= "/event";
    private static final String PERSON_HANDLER_DESIGNATOR= "/person";
    private static final String AUTHORIZATION_HEADER= "Authorization";

    /**
     * Private instance of the ClientComm
     */
    private static ClientComm SINGLETON = new ClientComm();

    /**
     * Getter for the private instance of the ClientComm
     *
     * @return the ClientComm singleton instance
     */
    public static ClientComm getSINGLETON() {
        return SINGLETON;
    }

    /**
     * Internal Gson used to encode and decode HTTP communications
     */
    private static Gson gson = new Gson();

    /**
     * Private  ClientComm constructor
     */
    private ClientComm(){}

    /**
     * Opens a connection to the server pointed at a specific context, with an authcode,
     * methodType and object to be sent
     *
     * @param contextID the URL designator that will tell the server what we want it to do
     * @param authorizationCode the authorization code of the user who is logged in
     * @param methodType GET or POST, depending on whether information is being sent or not
     * @param sendObj true if an object will be sent, false if it will not.
     * @return an HTTPConnection to the server formatted based on the inputs.
     */
    private HttpURLConnection openConnection (String contextID, String authorizationCode,
                                              String methodType, boolean sendObj){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(URL_PREFIX + contextID);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(methodType); //get or post
            connection.setDoOutput(sendObj);         //Whether or not there will be an object set
            connection.setRequestProperty(AUTHORIZATION_HEADER, authorizationCode); //plugs in the authToken
            connection.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Converts things being sent to the server into Jsons and sends them
     *
     * @param connection the HTTPConnection to the server
     * @param sendMe the object to be sent to the server
     */
    private void sendToServerComm(HttpURLConnection connection , Object sendMe){
        try{
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            gson.toJson(sendMe, writer);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Receives responses from the server and converts Jsons sent back from the server into
     * the Class designated by klass
     *
     * @param connection the HTTPConnection to the server
     * @param klass the Class of the object being sent back from the server
     * @return the object sent back from the server as an Object.class object
     */
    private Object getResponse(HttpURLConnection connection, Class<?> klass){
        Object response = null;
        try{
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                //empty response
                if(connection.getContentLength() == 0){
                    System.out.println("Response Body was empty");
                }
                //response has something in it
                else if(connection.getContentLength() == -1){
//                    System.out.println("Response Body contained information");
                    InputStreamReader reader =  new InputStreamReader(connection.getInputStream());
                    response = gson.fromJson(reader, klass);
                    reader.close();
                }
            }else{
                throw new Exception("http code " + connection.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            try{
                connection.getInputStream().close();
            }catch (IOException e){/*ignore close errors*/}
        }
        return response;
    }

    //COMMANDS

    /**
     * Creates opens a connection to the sever calling for the default handler
     */
    public void defaultPage(){
        openConnection(DEFAULT_HANDLER_DESIGNATOR, null, HTTP_GET, false);
    }

    /**
     * Opens a connection to the server that asks the server to clear the database.
     *
     * @return a ClearResult object containing information on if the clear was successful
     */
    public ClearResult clear(){
        HttpURLConnection connection = openConnection(CLEAR_HANDLER_DESIGNATOR, null, HTTP_GET, false);
        return (ClearResult) getResponse(connection, ClearResult.class);
    }

    /**
     * Opens a connection to the server that asks the server to load the database with the JsonObject
     * being sent over.
     *
     * @param request Should have an Array for user, person, and event.
     * @return a LoadResult Object containing the returned status information
     */
    public LoadResult load(LoadRequest request){
        HttpURLConnection connection = openConnection(LOAD_HANDLER_DESIGNATOR, null, HTTP_POST, true);
        sendToServerComm(connection, request);
        return (LoadResult) getResponse(connection, LoadResult.class);
    }

    /**
     * Calls the fillWithGenNum function and provides a default number of generations, in this case, 4.
     *
     * @param userName the user who will have their family information replaced
     * @return a FillResult Object containing the returned status information
     */
    public FillResult fill(String userName){
        return fillWithGenNum(userName, 4);
    }

    /**
     * Opens a connection to the server that asks the server to load the database using the fill command
     * for the specified number of generations.
     *
     * @param userName the user who will have their family information replaced.
     * @param numGens the number of generations that will be created for the user
     * @return a FillResult Object containing the returned status information
     */
    public FillResult fillWithGenNum(String userName, int numGens){
        String connectionDesignation = FILL_HANDLER_DESIGNATOR + "/" + userName + "/" + numGens;
        HttpURLConnection connection = openConnection(connectionDesignation, null, HTTP_GET, false);
        return (FillResult) getResponse(connection, FillResult.class);
    }

    /**
     * Logs in a user. User must already be registered
     *
     * @param request a LoginRequest Object that contains login information
     * @return a LoginResult Object containing the returned status information
     */
    public LoginResult login(LoginRequest request){
        HttpURLConnection connection = openConnection(LOGIN_HANDLER_DESIGNATOR, null, HTTP_POST, true);
        sendToServerComm(connection, request);
        return (LoginResult) getResponse(connection, LoginResult.class);
    }

    /**
     * Registers a user. UserName in RegisterRequest MUST be unique
     *
     * @param registerMe a RegisterRequest Object that contains registration information
     * @return a RegisterResult Object containing the returned status information
     */
    public RegisterResult register(RegisterRequest registerMe){
        HttpURLConnection connection = openConnection(REGISTER_HANDLER_DESIGNATOR, null, HTTP_POST, true);
        sendToServerComm(connection, registerMe);
        return (RegisterResult) getResponse(connection, RegisterResult.class);
    }

    /**
     * Requests all the information on the events for every person related to the User whose
     * authToken is being sent
     *
     * @param authToken The authtoken of the user sending the request.
     * @return an EventResult Object containing the returned status information
     */
    public EventResult event(String authToken){
        return eventWithID(null, authToken);
    }

    /**
     * Requests the Event information about the event designated by the eventID.
     *
     * @param eventID the eventID of the desired event
     * @param authToken the authToken of the user.
     * @return an EventResult Object containing the returned status information
     */
    public EventResult eventWithID(String eventID, String authToken){
        String connectionDesignation;
        if(eventID == null){
            connectionDesignation = EVENT_HANDLER_DESIGNATOR;
        } else{
            connectionDesignation = EVENT_HANDLER_DESIGNATOR + "/" + eventID;
        }
        HttpURLConnection connection = openConnection(connectionDesignation, authToken, HTTP_GET, false);
        return (EventResult) getResponse(connection, EventResult.class);
    }

    /**
     * Requests all the information on every person related to the User whose
     * authToken is being sent
     *
     * @param authToken The authtoken of the user sending the request.
     * @return a PersonResult Object containing the returned status information
     */
    public PersonResult person(String authToken){
       return personWithID(null, authToken);
    }

    /**
     *  Requests the person information about the person designated by the personID.
     *
     * @param personID the personID of the desired person
     * @param authToken the authToken of the user.
     * @return a PersonResult Object containing the returned status information
     */
    public PersonResult personWithID(String personID, String authToken){
        String connectionDesignation;
        if(personID == null){
            connectionDesignation = PERSON_HANDLER_DESIGNATOR;
        } else{
            connectionDesignation = PERSON_HANDLER_DESIGNATOR + "/" + personID;
        }
        HttpURLConnection connection = openConnection(connectionDesignation, authToken, HTTP_GET, false);
        return (PersonResult) getResponse(connection, PersonResult.class);
    }


}
