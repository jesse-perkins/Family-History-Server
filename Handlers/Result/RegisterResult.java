package Handlers.Result;

import ModelClasses.AuthKey;

/**
 * <h1>RegisterResult</h1>
 * <p>
 * The result of a request to register a new user.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class RegisterResult {

    /**
     * Constructor for a successful register attempt
     *
     * @param token the authToken of the user logged in
     * @param userName the username of the user
     * @param personID the personID of the person associated with the user
     */
    public RegisterResult(AuthKey token, String userName, String personID) {
        this.authToken = ""+token.getKey();
        this.userName = userName;
        this.personID = personID;
    }

    /**
     * Error constructor for the Result object
     *
     * @param errorOccurred the error that occurred
     */
    public RegisterResult(String errorOccurred){
        message = errorOccurred;
    }


    /**
     * The authToken of the user
     */
    private String authToken = null;

    /**
     * The username of the user
     */
    private String userName = null;

    /**
     * The personID of the user's person
     */
    private String personID = null;

    /**
     * Error code to be passed back to the server.
     */
    private String message = null;

    /**
     * Getter for authToken
     *
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for personID
     *
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Getter for message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
