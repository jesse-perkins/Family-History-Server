package Handlers.Request;

/**
 * <h1>LoginRequest</h1>
 * <p>
 * A request to login the user. Contains a password and username
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class LoginRequest {
    /**
     * Username of the user to be logged in.
     */
    private String userName;

    /**
     * Password of the user to be logged in.
     */
    private String password;

    /**
     * Constructor for the LoginRequest
     *
     * @param userName The username of the user
     * @param password the password of the user
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
     * Getter for password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
