package Handlers.Result;

/**
 * <h1>ClearResult</h1>
 * <p>
 * Returns a message detailing the success or failure of the clear command
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */

public class ClearResult {
    /**
     * The default string for a successful clear
     */
    public static final String SUCCESS_STRING = "Clear succeeded.";

    /**
     * the message passed back inside the result
     */
    private String message = null;

    /**
     * Successful constructor for the ClearResult object
     */
    public ClearResult() {
        this.message = SUCCESS_STRING;
    }

    /**
     * Getter for message
     *
     * @return message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Constructor for the ClearResult object, used when an error has occured
     *
     * @param error the error information to be returned to the client
     */
    public ClearResult(String error) {
        message = error;
    }

}
