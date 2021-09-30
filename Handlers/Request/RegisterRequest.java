package Handlers.Request;

import ModelClasses.User;

/**
 * <h1>RegisterRequest</h1>
 * <p>
 * A request to register a new user. Contains username, password, email, gender, first name, and last name.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class RegisterRequest {

    /**
     * The username of the new user.
     */
    private String userName= null;

    /**
     * The password of the new user.
     */
    private String password = null;

    /**
     * The email of the new user
     */
    private String email = null;

    /**
     * The firstName of the new user
     */
    private String firstName = null;

    /**
     * The lastName of the new user
     */
    private String lastName = null;

    /**
     * The gender of the new user
     */
    private Character gender = null;

    /**
     * The contructor for the RegisterRequest object
     *
     * @param userName user's username
     * @param password user's password
     * @param email user's email address
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param gender user's gender ('m' or 'f')
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, Character gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    /**
     * Getter for email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for firstName
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for gender
     *
     * @return gender
     */
    public Character getGender() {
        return gender;
    }
}
