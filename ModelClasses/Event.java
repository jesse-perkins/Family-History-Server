package ModelClasses;

/**
 * <h1>Event</h1>
 * <p>
 * The Event class represents an event in the life of an individual
 * in the Family Map Server Application. It is used internally by
 * the server to pass information to and from internal handlers,
 * services, and DAOs.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class Event {
    /**
     * The eventID which relates to the root descendant of this event
     */
    private String descendant = null;
    /**
     * The unique eventID which relates to this Event and this Event only
     */
    private String eventID = null;
    /**
     * The eventID of the person which relates to the owner of this event
     */
    private String personID = null;
    /**
     * The latitude at which the event took place.
     */
    private double latitude = -1;
    /**
     * The longitude at which the event took place.
     */
    private double longitude = -1;
    /**
     * The country in which the event took place.
     */
    private String country = null;
    /**
     * The city in which the event took place.
     */
    private String city = null;
    /**
     * The type of event that occurred
     */
    private String eventType = null;
    /**
     * The year the event took place
     */
    private int year = -1;

    /**
     * The active constructor for the Event class.
     *
     * Sets the latitude, longitude, country, city, event type, and year
     * of the event based on inputs and generates its own unique key.
     *
     * @param eventID the ID of the event
     * @param personID the ID of the person who owns the event
     * @param descendant the username of the user who owns the person who owns the event
     * @param latitude the latitude at which the event took place.
     * @param longitude the longitude at which the event took place.
     * @param country the country in which the event took place.
     * @param city the city in which the event took place.
     * @param eventType the type of event that occurred
     * @param year the year the event took place.
     */

    public Event(String eventID, String personID, String descendant, double latitude,
                 double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * The active constructor for the Event class.
     *
     * Sets the latitude, longitude, country, city, event type, and year
     * of the event based on inputs and generates its own unique key. Does
     * NOT have the eventID in the initializer.
     *
     * @param personID the ID of the person who owns the event
     * @param descendant the username of the user who owns the person who owns the event
     * @param latitude the latitude at which the event took place.
     * @param longitude the longitude at which the event took place.
     * @param country the country in which the event took place.
     * @param city the city in which the event took place.
     * @param eventType the type of event that occurred
     * @param year the year the event took place.
     */
    public Event(String descendant, String personID, double latitude,
                 double longitude, String country, String city, String eventType, int year) {
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * The active constructor for the Event class.
     *
     * Sets the latitude, longitude, country, city, event type, and year
     * of the event based on inputs and generates its own unique key. Does
     * NOT have the eventID in the initializer.
     *
     * @param personID the ID of the person who owns the event
     * @param descendant the username of the user who owns the person who owns the event
     * @param location the location of the event
     * @param eventType the type of event that occurred
     * @param year the year the event took place.
     */
    public Event(String descendant, String personID, int year, Location location, String eventType) {
        this.descendant = descendant;
        this.personID = personID;
        this.year = year;
        this.eventType = eventType;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.country = location.getCountry();
        this.city = location.getCity();
    }

    /**
     * Getter for event ID
     *
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Setter for eventID
     *
     * @param eventID the id to replace the events current eventID
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Getter for descendant
     *
     * @return descendant
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * Setter for descendant
     *
     * @param descendant the string that will replace descendant
     */
    public void setDescendant(String descendant) {
        this.descendant = descendant;
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
     * @param personID the string that will replace personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Getter for latitude
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Getter for country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Getter for city
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter for eventType
     *
     * @return eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Getter for year
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Implements a comparing function.
     *
     * @param other the event to be compared this event
     * @return True if the events are the same, false if not
     */
    public boolean equals(Event other){
        if(other.getClass() != this.getClass()){
            return false;
        }
        if(((this.getEventID() == null && other.getEventID() == null) || this.getEventID().equals(other.getEventID())) &&
                (this.getEventType().equals(other.getEventType())) &&
                (this.getCity().equals(other.getCity())) &&
                (this.getCountry().equals(other.getCountry())) &&
                (this.getYear() == other.getYear())
                ){
            Double resultLat = this.getLatitude()- other.getLatitude();
            Double resultLong = this.getLongitude() - other.getLongitude();
            if(resultLat < .001 && resultLong <.001) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
