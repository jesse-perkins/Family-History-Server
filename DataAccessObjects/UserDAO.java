package DataAccessObjects;

import ModelClasses.Person;
import ModelClasses.User;

import java.sql.*;

/**
 * <h1>UserDAO</h1>
 * <p>
 * The UserDAO handles access to the SQLite database functions used by
 * the Family Map Server Application services dealing with the User Class,
 * obfuscating the operation of the database or the type of database from
 * the service itself.
 * </p>
 * <b>Note:</b> This DAO is built on SQLite databases.
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class UserDAO {

    private static UserDAO userDAOSingleton = null;

    private Connection connection = null;

    private UserDAO(Connection connection) {
        this.connection = connection;
    }

    public static boolean createSingletonInstance(Connection connection){
        if(userDAOSingleton == null){
            userDAOSingleton = new UserDAO(connection);
            return true;
        }
        return false; //singleton already exists with connection
    }

    public static void resetInstance(){
        userDAOSingleton = null;
    }
    public static UserDAO getInstance(){
        return userDAOSingleton;
    }
    /**
     * Adds a new user to the database, for use with the load function.
     *
     * @param userIn the user to be added to the database.
     * @return True if the user is added False the user already exists.
     */
    public boolean addUser(User userIn) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("select * from User where username = '" + userIn.getUserName() + "';");
        //verify that there is NOT a user in the User table with this username
        if(!result.isBeforeFirst()) {
            String idHolder = ""+PersonDAO.getDefaultPersonID();
            if(userIn.getPersonID() == null){
                userIn.setPersonID(PersonDAO.getDefaultPersonID());
                PersonDAO.incDefaultPersonID();
            }
            PreparedStatement prepUser = connection.prepareStatement( "INSERT INTO User VALUES(?, ?, ?, ?, ?, ?, ?);");
            prepUser.setString(1, userIn.getUserName());
            prepUser.setString(2, userIn.getPassword());
            prepUser.setString(3, userIn.getEmail());
            prepUser.setString(4, userIn.getFirstName());
            prepUser.setString(5, userIn.getLastName());
            prepUser.setString(6, userIn.getGender().toString());
            prepUser.setString(7, userIn.getPersonID());
            prepUser.addBatch();
            command.execute("BEGIN TRANSACTION;");
            prepUser.executeBatch();
            //Only creates a person for the user if the user has one of the last default ID.
            //This keeps the load function from creating extra persons when loading users into the database
            if(userIn.getPersonID().equals(idHolder)){
                PreparedStatement prepUserPerson;
                prepUserPerson = connection.prepareStatement( "INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
                prepUserPerson.setString(1, userIn.getPersonID());
                prepUserPerson.setString(2, userIn.getUserName());
                prepUserPerson.setString(3, userIn.getFirstName());
                prepUserPerson.setString(4, userIn.getLastName());
                prepUserPerson.setString(5, userIn.getGender().toString());
                prepUserPerson.setString(6, null);
                prepUserPerson.setString(7, null);
                prepUserPerson.setString(8, null);
                prepUserPerson.addBatch();
                prepUserPerson.executeBatch();
            }
            command.execute("END TRANSACTION;");
            return true;
        }
        return false;
    }

    public User getUser(String username) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("select * from User where username = '" + username + "';");
        if(!result.isBeforeFirst()){
            return null;
        }else{
            return new User(result.getString(1), result.getString(2),
                    result.getString(3),result.getString(4),
                    result.getString(5), result.getString(6).charAt(0),
                    result.getString(7));
        }
    }
}
