package Communicators;

import Handlers.Request.*;
import Handlers.Result.*;
import com.google.gson.JsonObject;

/**
 * <h1>ServerProxy</h1>
 * <p>
 * The ServerProxy is essentially an amalgamation of the different services that will
 * be used by the client in the Family Map Server Application.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class ServerProxy {
    private  ClientComm client = ClientComm.getSINGLETON();

    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an authorization token.
     *
     * @param request an object containing the information needed to register the user.
     * @return the result of the registration request.
     */
    public RegisterResult registerUser(RegisterRequest request) {
        return client.register(request);
    }

    /**
     * Logs in the user and returns an authorization token.
     *
     * @param request an object containing the information needed to log the user in.
     * @return the result of the login request.
     */
    public LoginResult loginUser(LoginRequest request) {
        return client.login(request);
    }

    /**
     * Clears the entire database of all data. DROP ALL THE TABLES!!
     *
     * @return the result of the clear request.
     */
    public ClearResult clear() {
        return client.clear();
    }
    /**
     * Clears all data from the database, then loads the user, person, and
     * event data carried in the request into the database.
     *
     * @param request a LoadRequest containing the properly formatted user, person, and event data
     *                needed to load te user's family into the database.
     * @return the result of the load user family request.
     */
    public LoadResult load(LoadRequest request) {
        return client.load(request);
    }

    /**
     * Returns an array of Person objects with the decendant designated by the authtoken.
     *
     * @param authtoken the auth token of the user
     * @return the result of the getPerson request.
     */
    public PersonResult getPerson(String authtoken) {
            return client.person(authtoken);
    }
    /**
     * Returns the single Person object with the specified ID.
     *
     * @param personID the ID of the person to be retrieved
     * @param authtoken the auth token of the user
     * @return the result of the getPerson request.
     */
    public PersonResult getPerson(String personID, String authtoken) {
            return client.personWithID(personID, authtoken);
    }
    /**
     * Returns an array of Event objects with the decendant designated by the authtoken.
     *
     * @param authtoken the auth token of the user
     * @return the result of the getEvent request.
     */
    public EventResult getEvent(String authtoken) {
        return client.event(authtoken);
    }

    /**
     * Returns the single Event object with the specified ID.
     *
     * @param eventID the eventID of the event to be retrieved
     * @param authtoken the auth token of the user
     * @return the result of the getEvent request.
     */
    public EventResult getEvent(String eventID, String authtoken) {
        return client.eventWithID(eventID, authtoken);
    }

    /**
     * Deletes all user data for the specified userName and creates new informaton for that user.
     * Does 4 generations.
     *
     * @param username the username of the user to have their information replaced
     * @return the result of the fill command in a string
     */
    public FillResult fill(String username){
        return client.fill(username);
    }

    /**
     * Deletes all user data for the specified userName and creates new informaton for that user.
     * Does the number of generations passed in.
     *
     * @param username the username of the user to have their information replaced
     * @param genNum the number of generations to be generated
     * @return the result of the fill command in a string
     */
    public FillResult fill(String username, int genNum){
        return client.fillWithGenNum(username, genNum);
    }
}
