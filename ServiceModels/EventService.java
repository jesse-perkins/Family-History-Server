package ServiceModels;

import DataAccessObjects.AuthKeyDAO;
import DataAccessObjects.EventDAO;
import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import Handlers.Result.EventResult;
import ModelClasses.AuthKey;
import ModelClasses.User;

/**
 * <h1>EventService</h1>
 * <p>
 * Handles event requests for individual events or all events related to the logged in user
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class EventService {
    /**
     * Singleton for the EventService
     */
    public static final EventService SINGLETON = new EventService();

    /**
     *  Checks Authkey to verify authorization, then returns a specific event
     *  from the database by way of the DAOs
     *
     * @param authToken the authToken of the user
     * @param eventID the eventID of the requested event
     * @return EventResult
     */
    public static EventResult event(String authToken, String eventID){
        try {
            AuthKeyDAO authControl = AuthKeyDAO.getInstance();
            EventDAO eventControl = EventDAO.getInstance();
            AuthKey auth = new AuthKey(Integer.parseInt(authToken));
            if(authControl.checkAuthKey(auth)){
                String receiverUsername = authControl.getUser(auth);
                EventResult result = new EventResult(eventControl.getEvent(eventID));
                if(result.getSingle() == null){
                    return new EventResult("Error: Invalid EventID.");
                }else if(!result.getSingle().getDescendant().equals(receiverUsername)){
                    return new EventResult("Error: This event does not belong to the requesting user.");
                }
                return result;
            }else{
                return new EventResult("Error: Invalid Authorization Key.");
            }
        }catch(Exception e){
            System.out.println("Event Service Failed: " + e);
            return new EventResult("Event Service Failed: " + e);
        }
    }

    /**
     *  Checks Authkey to verify authorization, then returns a all events
     *  from the database related to the user whose authorization key this is.
     *
     * @param authToken the authToken of the user
     * @return EventResult
     */
    public static EventResult event(String authToken){
        try {
            AuthKeyDAO authControl = AuthKeyDAO.getInstance();
            EventDAO eventControl = EventDAO.getInstance();
            UserDAO userControl = UserDAO.getInstance();
            AuthKey temp = new AuthKey(Integer.parseInt(authToken));
            if(authControl.checkAuthKey(temp)){
                String username = authControl.getUser(temp);
                User user = userControl.getUser(username);
                return new EventResult(eventControl.getFamilyEvents(user.getUserName()));
            }else{
                return new EventResult("Error: Invalid Authorization Key.");
            }
        }catch(Exception e){
            System.out.println("Event Service Failed: " + e);
            return new EventResult("Event Service Failed: " + e);
        }
    }
}
