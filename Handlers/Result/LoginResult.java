package Handlers.Result;

/**
 * <h1>LoginResult</h1>
 * <p>
 * Contains information on the failure of a login or the login's information
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class LoginResult {

    /**
     * The constructor for a successful login
     *
     * @param authToken the new authToken for the user
     * @param userName the username granted to the user
     * @param personID the personID assigned for the user's person
     */
    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    /**
     * Error constructor for the LoginResult object
     *
     * @param errorOccurred the error that occured
     */
    public LoginResult(String errorOccurred){
        message = errorOccurred;
    }

    /**
     * The user's auth token
     */
    private String authToken = null;

    /**
     * The username for the user
     */
    private String userName = null;

    /**
     * The personID for the user's person
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
