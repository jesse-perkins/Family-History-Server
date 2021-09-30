package DataAccessObjects;

import ModelClasses.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * <h1>EventDAO</h1>
 * <p>
 * The EventDAO handles access to the SQLite database functions used by
 * the Family Map Server Application services dealing with the Event Class,
 * obfuscating the operation of the database or the type of database from
 * the service itself. It is closely linked with the Person DAO, and performs
 * query operations on the Person Table.
 * </p>
 * <b>Note:</b> This DAO is built on SQLite databases.
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class EventDAO {
    /**
     * Getter for the defaultID
     *
     * @return defaultID used in EventDAO
     */
    public static int getDefaultEventID(){
        return defaultEventID;
    }

    /**
     * Increments the value of the defaultID for events used by the EventDAO
     */
    public static void incDefaultEventID(){
        defaultEventID++;
    }

    /**
     * Getter for defaultID that returns the ID in string from instead of in int form.
     *
     * @return defaultID as a string
     */
    public static String defaultEventIDToString(){
        return ""+defaultEventID;
    }

    /**
     * The defaultID given to added events if they don't already have them
     */
    private static int defaultEventID = Math.abs((int) new Date().getTime());

    /**
     * A static EventDAO for use to be initialized internally
     */
    private static EventDAO eventDAOSingleton = null;

    /**
     * Private connection to the database file
     */
    private Connection connection = null;

    /**
     * Private constructor for the EventDAO
     *
     * @param connection a connection to the database file
     */
    private EventDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates the singleton instance of the EventDAO
     *
     * @param connection a connection to the database file
     * @return true if the singleton is created. False if one already exists.
     */
    public static boolean createSingletonInstance(Connection connection){
        if(eventDAOSingleton == null){
            eventDAOSingleton = new EventDAO(connection);
            return true;
        }
        return false; //singleton already exists with connection
    }

    /**
     * Resets the singleton to null
     */
    public static void resetInstance(){
        eventDAOSingleton = null;
    }

    /**
     * A getter for the singleton object
     *
     * @return the singleton EventDAO
     */
    public static EventDAO getInstance(){
        return eventDAOSingleton;
    }

    /**
     * Adds an event to the database, and updates the passed in event with an event ID, personID and descendant
     *
     * @param eventIn the event to be added.
     * @return returns event ID of the event added, -1 if event is already in event table)
     */
    public String addEvent(Event eventIn) throws SQLException{
        if(eventIn.getDescendant() == null || eventIn.getPersonID() == null){
            return null;
        }
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("select * from Event where event_id = '" + eventIn.getEventID() + "';");
        if(result.isBeforeFirst()){ // If there is already an event with the same event_id
            return null;
        }else { //if there is no person in the person table to own this event
            result = command.executeQuery("select * from Person where person_id = '" + eventIn.getPersonID() + "';");
            if(!result.isBeforeFirst()){
                return null;
            }
        }
        /* At this point it has been verified that:
                    1) The event has a person and a descendant
                    2) No other event with the same eventID is in the database
                    3) There is a peron in the person table who owns this event
        */
        String eventID = eventIn.getEventID();
        if(eventID == null){
            eventID = defaultEventIDToString();
            incDefaultEventID();
        }
        PreparedStatement prep = connection.prepareStatement( "INSERT INTO Event VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);");
        prep.setString(1, eventID);
        prep.setString(2, eventIn.getPersonID());
        prep.setString(3,eventIn.getDescendant());
        prep.setDouble(4,eventIn.getLatitude());
        prep.setDouble(5,eventIn.getLongitude());
        prep.setString(6,eventIn.getCountry());
        prep.setString(7,eventIn.getCity());
        prep.setString(8,eventIn.getEventType());
        prep.setInt(9,eventIn.getYear());
        prep.addBatch();
        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);
        eventIn.setEventID(eventID);
        return eventID;
    }

    /**
     * Returns an event based on the eventID provided.
     *
     * @param eventID the ID of the event desired.
     * @return the event corresponding to the ID, or null if the event is not found.
     * @exception SQLException
     */
    public Event getEvent(String eventID) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM Event WHERE event_id = '" + eventID + "';");
        if(!result.isBeforeFirst()) {
            return null;
        }else{
            Event out = new Event(result.getString(1),
                    result.getString(2),result.getString(3),
                    result.getDouble(4), result.getDouble(5),
                    result.getString(6), result.getString(7),
                    result.getString(8), result.getInt(9));
            return out;
        }
    }

    /**
     * Uploads the events given as the parameter into the database
     *
     * @param lifeEvents the ArrayList of events to be uploaded
     * @return true if they are successfully uploaded.
     * @throws Exception Throws errors if the upload fails or database creation fails.
     */
    public boolean uploadEvents(ArrayList<Event> lifeEvents) throws Exception{
        TableBuilder.createDAOs(TableBuilder.getConnection());
        for (Event event:
                lifeEvents) {
            TableBuilder.eventControl.addEvent(event);
        }
        return true;
    }

    /**
     * Gets all the events for the person whose ID is input
     *
     * @param personID the personID for the person whose events are requested.
     * @return an ArrayList of events that belong to the person
     * @throws SQLException
     */
    public ArrayList<Event> getPersonEvents(String personID) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM Event WHERE person_id = '" + personID + "';");
        if(!result.isBeforeFirst()) {
            return new ArrayList<>();
        }else{
            Event temp;
            ArrayList<Event> personEvents = new ArrayList<>();
            while(result.next()){
                temp = new Event(result.getString(1),
                        result.getString(2),result.getString(3),
                        result.getDouble(4), result.getDouble(5),
                        result.getString(6), result.getString(7),
                        result.getString(8), result.getInt(9));
                personEvents.add(temp);
            }
            return personEvents;
        }
    }

    /**
     * Retrieves all the life events related to every ancestor of the root descendant.
     *
     * @param descendantID the ID of the root person of family.
     * @return a list of every event in the lives of all ancestors.
     * @exception SQLException
     */
    public ArrayList<Event> getFamilyEvents(String descendantID) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM Event WHERE descendant = '" + descendantID + "';");
        if(!result.isBeforeFirst()) {
            return new ArrayList<>();
        }else{
            Event temp;
            ArrayList<Event> familyEvents = new ArrayList<>();
            while(result.next()){
                temp = new Event(result.getString(1),
                        result.getString(2),result.getString(3),
                        result.getDouble(4), result.getDouble(5),
                        result.getString(6), result.getString(7),
                        result.getString(8), result.getInt(9));
                familyEvents.add(temp);
            }
            return familyEvents;
        }
    }

    /**
     * Removes every entry in events with a matching descendantID.
     *
     * @param descendantID the ID of the descendant for whose family event data is to be removed
     * @return true if the data is removed, false if the given ID is not a descendantID
     * @throws SQLException
     */
    public boolean removeFamilyEventData(String descendantID) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM Person WHERE descendant = '" + descendantID + "';");
        if(!result.isBeforeFirst()) {
            return false;
        }else{
            command.execute("DELETE FROM Event WHERE descendant = '" + descendantID +"';");
            return true;
        }
    }
}
