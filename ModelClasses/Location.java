package ModelClasses;

/**
 * Created by Jesse on 3/9/2017.
 */
public class Location {

    /**
     * The latitude at which the event took place.
     */
    private double latitude;

    /**
     * The longitude at which the event took place.
     */
    private double longitude;

    /**
     * The city in which the event took place.
     */
    private String city = null;

    /**
     * The country in which the event took place.
     */
    private String country = null;

    /**
     * Constructor for location
     *
     * @param latitude the latitude at which the event took place.
     * @param longitude the longitude at which the event took place.
     * @param country the country in which the event took place.
     * @param city the city in which the event took place.
     */
    public Location(double latitude, double longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    /**
     * Getter for latitude
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
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
     * Getter for country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }
}
