package Handlers.Result;

/**
 * <h1>LoadResult</h1>
 * <p>
 * Contains a message regarding the success or failure of a load attempt
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 2/15/2017.
 */
public class LoadResult{

    /**
     * Message to be passed back to the server.
     */
    private String message = null;

    /**
     * Constructor for the LoadResult object
     *
     * @param successOrFailure the string detailing the success or failure of the LoadService
     */
    public LoadResult(String successOrFailure){
        message = successOrFailure;
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
