package ModelClasses;

import java.util.TreeSet;

/**
 * <h1>User</h1>
 * <p>
 * The User class represents an the end user of the Family Map Server
 * Application and is used internally by the server to pass information
 * to and from internal handlers, services, and DAOs.
 * </p>
 * <b>NOTE:</b> Extends Person
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class User {
    /**
     * The userName of the User.
     */
    private String userName = null;
    /**
     * The password of the User.
     */
    private String password = null;
    /**
     * The email address of the User.
     */
    private String email = null;
    /**
     * The first name of the person.
     */
    private String firstName = null;
    /**
     * The last name of the person.
     */
    private String lastName = null;
    /**
     * The gender of the person. Should be 'f' for female or 'm' for male
     */
    private Character gender = null;
    /**
     * A set of all the authorization keys being used by this user.
     */
    private TreeSet<AuthKey> authKeys = null;
    /**
     * The corresponding person to the user
     */
    private String personID = null;
    /**
     * The active constructor for the User class.
     *
     * Creates a User.
     *
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @param gender the gender of the person written as a string. Either "male" or "female".
     * @param userName the name to be used to log into the application.
     * @param password the password to be used during log in.
     * @param email the email address registered to this user.
     */
    public User(String userName, String password, String email, String firstName,
                String lastName, Character gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * The active constructor for the User class, including personID
     *
     * Creates a User.
     *
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @param gender the gender of the person written as a string. Either "male" or "female".
     * @param userName the name to be used to log into the application.
     * @param password the password to be used during log in.
     * @param email the email address registered to this user.
     * @param personID the ID of the person associated with the user
     */
    public User(String userName, String password, String email, String firstName,
                String lastName, Character gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * Getter for userName
     *
     * @return name of user
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
     * Getter for tree of authkeys related to the user. Not currently in use.
     *
     * @return authKeys
     */
    public TreeSet<AuthKey> getAuthKeys() {
        return authKeys;
    }

    /**
     * Setter for tree of authkeys related to the user. Not currently in use.
     *
     * @param authKeys the TreeSet of AuthKey that will replace the set in the user
     */
    public void setAuthKeys(TreeSet<AuthKey> authKeys) {
        this.authKeys = authKeys;
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

    /**
     * Getter for personID
     *
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Setter for personID
     *
     * @param personID the string that will become the personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Setter for personID, sets an integer to be the ID
     *
     * @param value the int that will be converted into as string as the personID
     */
    public void setPersonID(int value){
        StringBuilder holder = new StringBuilder();
        holder.append(value);
        this.personID = holder.toString();
    }
}
