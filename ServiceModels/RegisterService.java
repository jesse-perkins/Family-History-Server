package ServiceModels;

import DataAccessObjects.AuthKeyDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import Handlers.Request.RegisterRequest;
import Handlers.Result.RegisterResult;
import ModelClasses.AuthKey;
import ModelClasses.Person;
import ModelClasses.User;

/**
 * <h1>RegisterService</h1>
 * <p>
 * Creates and inputs data for the new user. Also generates 4 generations of family events and persons for the user.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class RegisterService {
    /**
     * Singleton for the RegisterService
     */
    public static final RegisterService SINGLETON = new RegisterService();

    /**
     * Registers the user and returns login information
     *
     * @param request the request conatining user data.
     * @return RegisterResult
     */
    public static RegisterResult register(RegisterRequest request){
        //Check for missing values
        if(request.getUserName() == null ||
                request.getEmail() == null ||
                request.getFirstName() == null ||
                request.getLastName() == null ||
                request.getPassword() == null ||
                request.getGender() == null){
            return new RegisterResult("Error: Missing value detected!");
        } //verify valid gender
        else if(!request.getGender().equals('m') && !request.getGender().equals('f')){
            return new RegisterResult("Error: Invalid Gender value detected!");
        }
        try {
            UserDAO userControl = UserDAO.getInstance();
            AuthKeyDAO authKeyControl = AuthKeyDAO.getInstance();
            //verify unique username
            if (userControl.getUser(request.getUserName()) != null){
                return new RegisterResult(("Error: Username Already Taken."));
            } else {
                //create new user and add to database
                User newGuy = new User(request.getUserName(), request.getPassword(), request.getEmail(),
                        request.getFirstName(), request.getLastName(), request.getGender());
                userControl.addUser(newGuy);
                //create 4 generations of data
                FillService.SINGLETON.fill(4, newGuy.getUserName());
                AuthKey key = authKeyControl.addAuthToken(newGuy.getUserName());
                return new RegisterResult(key, newGuy.getUserName(), newGuy.getPersonID());
            }
        }catch(Exception e){
            System.out.println("Register User Failed: " + e);
            return new RegisterResult(("Register User Failed: " + e));
        }
    }
}
