package ServiceModels;

import DataAccessObjects.AuthKeyDAO;
import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import Handlers.Request.LoginRequest;
import Handlers.Result.LoginResult;
import ModelClasses.AuthKey;
import ModelClasses.User;

/**
 * <h1>LoginService</h1>
 * <p>
 * Logs in a user if the user is registered and has a matching password.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class LoginService {
    /**
     * Singleton for the LoginService
     */
    public static final LoginService SINGLETON = new LoginService();

    /**
     * Logs the user in. User must already be registered
     *
     * @param request the LoginRequest for the user
     * @return LoginResult
     */
    public static LoginResult login(LoginRequest request) {
        try {
            UserDAO userControl = UserDAO.getInstance();
            AuthKeyDAO authKeyControl = AuthKeyDAO.getInstance();
            User holder = userControl.getUser(request.getUserName());
            //Make sure the user exists
            if(holder == null){
                return new LoginResult(("Login User Failed: User Does Not Exist"));
            } //Make sure the right password is used
            else if(!holder.getPassword().equals(request.getPassword())){
                return new LoginResult(("Login User Failed: Invalid Password"));
            } else{
                AuthKey key = authKeyControl.addAuthToken(holder.getUserName());
                return new LoginResult(key.getStringKey(), holder.getUserName(), holder.getPersonID());
            }
        } catch (Exception e) {
            System.out.println("Login User Failed:" + e);
            return new LoginResult(("Login User Failed:" + e));
        }
    }
}
