package Handlers.Result;

import ModelClasses.Person;

import java.util.ArrayList;

/**
 * <h1>PersonResult</h1>
 * <p>
 * The result of looking up a specific person or every person related to the user issuing the request
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class PersonResult {

    /**
     * Error constructor for the Result object
     *
     * @param errorCode the error that occured
     */
    public PersonResult(String errorCode) {
        message = errorCode;
    }

    /**
     * The constructor for the PersonResult when only one person was requested
     *
     * @param single the person to be returned to the client
     */
    public PersonResult(Person single) {
        this.single = single;
    }

    /**
     * The constructor for the PersonResult when the entire family is requested
     *
     * @param family the ArrayList of persons to be returned to the client
     */
    public PersonResult(ArrayList<Person> family) {
        this.family = family;
    }

    /**
     * The specifically requested person
     */
    private Person single = null;

    /**
     * All relatives of the user issuing the request
     */
    private ArrayList<Person> family = null;

    /**
     * Error code to be passed back to the server.
     */
    private String message = null;

    /**
     * Getter for single
     *
     * @return single
     */
    public Person getSingle() {
        return single;
    }

    /**
     * Getter for family
     *
     * @return family
     */
    public ArrayList<Person> getFamily() {
        return family;
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
