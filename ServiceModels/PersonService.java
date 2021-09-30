package ServiceModels;

import DataAccessObjects.AuthKeyDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import Handlers.Result.PersonResult;
import ModelClasses.AuthKey;
import ModelClasses.User;

/**
 * <h1>PersonService</h1>
 * <p>
 * Handles event requests for individual persons or all persons related to the logged in user
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class PersonService {
    /**
     * Singleton for the PersonService
     */
    public static final PersonService SINGLETON = new PersonService();

    /**
     * Gets all the person data for the user associated with the authKey and sends it back
     *
     * @param authKey the authKey of the user sending the request
     * @return PersonResult
     */
    public static PersonResult person(String authKey){
        try {
            AuthKeyDAO authControl = AuthKeyDAO.getInstance();
            UserDAO userControl = UserDAO.getInstance();
            PersonDAO personControl = PersonDAO.getInstance();
            if(authControl.checkAuthKey(new AuthKey(Integer.parseInt(authKey)))){
                String temp = authControl.getUser(new AuthKey(Integer.parseInt(authKey)));
                User user = userControl.getUser(temp);
                return new PersonResult(personControl.getAncestors(user.getUserName()));
            }else{
                return new PersonResult(("Error: Invalid Authorization Key."));
            }
        }catch(Exception e){
            System.out.println("Person Service Failed: " + e);
            return new PersonResult(("Person Service Failed: " + e));
        }
    }

    /**
     * Gets the information for the specific person designated by the personID.
     * Users can't grab information from other users.
     *
     * @param authKey the authKey of the user sending the request
     * @param personID the personID of the person requested
     * @return PersonResult
     */
    public static PersonResult person(String authKey, String personID){
        try {
            AuthKeyDAO authControl = AuthKeyDAO.getInstance();
            PersonDAO personControl = PersonDAO.getInstance();
            AuthKey auth = new AuthKey(Integer.parseInt(authKey));
            if(authControl.checkAuthKey(auth)){
                String receiverUsername = authControl.getUser(auth);
                PersonResult result = new PersonResult(personControl.getPerson(personID));

                if(result.getSingle() == null){
                    return new PersonResult("Error: Invalid PersonID.");
                }else if(!result.getSingle().getDescendant().equals(receiverUsername)){
                    return new PersonResult("Error: This person does not belong to the requesting user.");
                }
                return result;
            }else{
                return new PersonResult(("Error: Invalid Authorization Key."));
            }
        }catch(Exception e){
            System.out.println("Person Service Failed: " + e);
            return new PersonResult(("Person Service Failed: " + e));
        }
    }
}
