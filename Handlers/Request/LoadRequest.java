package Handlers.Request;

import ModelClasses.Event;
import ModelClasses.Person;
import ModelClasses.User;

import java.util.ArrayList;


/**
 * <h1>LoadRequest</h1>
 * <p>
 * A request to load the data conatined within this object into the database. Consists of 3 ArrayLists:
 * 1) Users to be added to the database
 * 2) Persons to be added to the database
 * 3) Events to be added to the database
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class LoadRequest {
    /**
     * The ArrayList of users to be added to the database
     */
    private ArrayList<User> users = null;

    /**
     * The ArrayList of persons to be added to the database
     */
    private ArrayList<Person> persons = null;

    /**
     * The ArrayList of events to be added to the database
     */
    private ArrayList<Event> events = null;

    /**
     * The constructor for the LoadRequest object
     *
     * @param users  The ArrayList of users to be added to the database
     * @param persons The ArrayList of persons to be added to the database
     * @param events The ArrayList of events to be added to the database
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     * Getter for the users ArrayList
     *
     * @return users ArrayList
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Getter for the persons ArrayList
     *
     * @return persons ArrayList
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Getter for the events ArrayList
     *
     * @return events ArrayList
     */
    public ArrayList<Event> getEvents() {
        return events;
    }
}
