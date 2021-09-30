package DataAccessObjects;

import ModelClasses.AuthKey;
import ModelClasses.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <h1>AuthKeyDAO</h1>
 * <p>
 * The AuthKeyDAO handles access to the SQLite database functions used by
 * the Family Map Server Application services dealing with the AuthKey Class,
 * obfuscating the operation of the database or the type of database from
 * the service itself.
 * </p>
 * <b>Note:</b> This DAO is built on SQLite databases.
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class AuthKeyDAO {
    /**
     * A static AuthKeyDAO for use to be initialized internally
     */
    private static AuthKeyDAO authKeyDAOSingleton = null;

    /**
     * Private connection to the database file
     */
    private Connection connection = null;

    /**
     * Private constructor for the AuthKeyDAO
     *
     * @param connection a connection to the database file
     */
    private AuthKeyDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates the singleton instance of the AuthKeyDAO
     *
     * @param connection a connection to the database file
     * @return true if the singleton is created. False if one already exists.
     */
    public static boolean createSingletonInstance(Connection connection){
        //If there is already a singleton that hasn't been torn down, don't create a new one.
        if(authKeyDAOSingleton == null){
            //Create a new AuthKeyDAO and make it the singleton
            authKeyDAOSingleton = new AuthKeyDAO(connection);
            return true;
        }
        return false; //singleton already exists with a connection
    }

    /**
     * Resets the singleton to null
     */
    public static void resetInstance(){
        authKeyDAOSingleton = null;
    }

    /**
     * A getter for the singleton object
     *
     * @return the singleton AuthKeyDAO
     */
    public static AuthKeyDAO getInstance(){
        return authKeyDAOSingleton;
    }

    /**
     * Generates a new authorization token for the user with the given ID number.
     *
     * @param userName ID of the user who needs a new authorization key.
     * @return the new AuthKey. Null if the user doesn't exist
     * @throws SQLException
     */
    public AuthKey addAuthToken(String userName) throws SQLException {
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM User WHERE username ='" + userName + "';");
        if (!result.isBeforeFirst()) {
            return null;
        } else {
            command.executeUpdate("INSERT INTO AuthToken values(NULL, '" + userName + "');");
            result = command.executeQuery("SELECT last_insert_rowid();");
            return new AuthKey(result.getInt(1));
        }
    }

    /**
     * Checks to see if the authorization key already exists.
     *
     * @param authKey the authorization token to be checked.
     * @return true if the key exists, false if it does not.
     * @throws SQLException
     */
    public boolean checkAuthKey(AuthKey authKey) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM AuthToken WHERE token_id ='" + authKey.getKey() + "';");
        if(!result.isBeforeFirst()){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Returns the username of the user assigned the provided authorization key.
     *
     * @param authKey the authorization key of the user.
     * @return the username of the user to whom the authorization key belongs. null if the key is not in use
     * @throws SQLException
     */
    public String getUser(AuthKey authKey) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM AuthToken WHERE token_id ='" + authKey.getKey() + "';");
        if(!result.isBeforeFirst()){
            return null;
        }else {
            return result.getString("username");
        }
    }
}
