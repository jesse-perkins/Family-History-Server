package Handlers.Result;

import ModelClasses.Event;

import java.util.ArrayList;

/**
 * <h1>EventResult</h1>
 * <p>
 * The result of the event call passed to the server
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class EventResult{
    /**
     * The string that holds error information if the Event Service failed
     */
    private String message = null;

    /**
     * The constructor for EventResult objects used to pass errors back to the client
     *
     * @param errorCode the error information
     */
    public EventResult(String errorCode) {
        message = errorCode;
    }

    /**
     * The EventResult object constructor used to return a specific object
     *
     * @param single the requested event
     */
    public EventResult(Event single) {
        this.single = single;
    }

    /**
     * The EventResult constructor used to return an ArrayList of objects relating to a specific user
     *
     * @param familyEvents the ArrayList of events
     */
    public EventResult(ArrayList<Event> familyEvents) {
        this.familyEvents = familyEvents;
    }

    /**
     * A single event object
     */
    private Event single = null;

    /**
     * An ArrayList of events
     */
    private ArrayList<Event> familyEvents = null;

    /**
     * Getter for message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for single
     *
     * @return single
     */
    public Event getSingle() {
        return single;
    }

    /**
     * Getter for familyEvents
     *
     * @return familyEvents
     */
    public ArrayList<Event> getFamilyEvents() {
        return familyEvents;
    }
}
