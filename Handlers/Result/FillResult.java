package Handlers.Result;

/**
 * <h1>FillResult</h1>
 * <p>
 * The result of the fill service
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class FillResult{
    /**
     * Error constructor for the FillResult object
     *
     * @param errorCode the error that occurred
     */
    public FillResult(String errorCode) {
        message = errorCode;
    }

    /**
     * Error code to be passed back to the server.
     */
    private String message = null;

    /**
     * Success constructor for the FillResult object
     *
     * @param numPersons the number of people added to the databae by the fill command
     * @param numEvents the number of events added to the database by the fill command
     */
    public FillResult(int numPersons, int numEvents) {
        StringBuilder holder = new StringBuilder();
        holder.append("Successfully added " + numPersons + " persons and " + numEvents + " events to the database");
        message = holder.toString();
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
