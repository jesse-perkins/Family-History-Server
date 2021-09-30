package DataAccessObjects;

import java.sql.*;
/**
 * <h1>TableBuilder</h1>
 * <p>
 * The TableBuilder handles access to the SQLite database used by
 * the Family Map Server Application obfuscating the operation of
 * the database or the type of database from the user.
 * </p>
 * <b>Note:</b> This DAO is built on SQLite databases.
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-3-1.
 */
public class TableBuilder {

    /**
     * An AuthKeyDAO used used mostly for testing.
     */
    public static AuthKeyDAO authControl = null;

    /**
     * An UserDAO used used mostly for testing.
     */
    public static UserDAO userControl = null;

    /**
     * An PersonDAO used used mostly for testing.
     */
    public static PersonDAO personControl = null;

    /**
     * An EventDAO used used mostly for testing.
     */
    public static EventDAO eventControl = null;

    /**
     * An internal connection to the database file
     */
    private static Connection connect = null;

    /**
     * Opens a connection to the specified database file and clears it,
     * then creates a new database structure. Also initializes the DAO
     * singletons so that they can be called and used in services.
     *
     * @return a connection to the database file being used.
     * @throws Exception
     */
    public static Connection buildThemAll() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:FamilyMap.db");
        killTables(connection);
        buildPersonTable(connection);
        buildUserTable(connection);
        buildAuthTokenTable(connection);
        buildEventTable(connection);
        createDAOs(connection);
        connect = connection;
        return connection;
    }

    /**
     * Gets a cnnection to the database file. For use testing.
     *
     * @return a connection to the database
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:FamilyMap.db");
    }

    /**
     * Initializes the DAO singletons
     *
     * @param connection a connection to the database file
     */
    public static void  createDAOs(Connection connection){
        AuthKeyDAO.createSingletonInstance(connection);
        authControl = AuthKeyDAO.getInstance();

        UserDAO.createSingletonInstance(connection);
        userControl = UserDAO.getInstance();

        PersonDAO.createSingletonInstance(connection);
        personControl = PersonDAO.getInstance();

        EventDAO.createSingletonInstance(connection);
        eventControl = EventDAO.getInstance();
    }

    /**
     * Drops all tables related to the Family Map Server
     *
     * @param connection a connection to the database file
     * @throws SQLException
     */
    public static void killTables(Connection connection) throws SQLException {
        Statement command = connection.createStatement();
        command.execute("BEGIN TRANSACTION;");
        command.executeUpdate("drop table if exists Person;");
        command.executeUpdate("drop table if exists User;");
        command.executeUpdate("drop table if exists AuthToken;");
        command.executeUpdate("drop table if exists Event;");
        command.execute("END TRANSACTION;");
    }

    /**
     * Constructs the database table for person in the database file
     *
     * @param connection a connection to the database file
     */
    private static void buildPersonTable(Connection connection){
        try{
            Statement command = connection.createStatement();
            command.executeUpdate("create table Person ( " +
                    "person_id varchar(255) not null primary key," +
                    "descendant varchar(255) not null references User(username)," +
                    "first_name varchar(255) not null," +
                    "last_name varchar(255) not null," +
                    "gender char not null," +
                    "father varchar(255)," +
                    "mother varchar(255)," +
                    "spouse varchar(255));");
        }catch(SQLException e){
            System.err.println("Person Table:" + e);
        }
    }

    /**
     * Constructs the database table for user in the database file
     *
     * @param connection a connection to the database file
     */
    private static void buildUserTable(Connection connection){
        try{
            Statement command = connection.createStatement();
            command.executeUpdate("create table User ( " +
                    "username varchar(255) not null primary key," +
                    "password varchar(255) not null," +
                    "email varchar(255) not null," +
                    "first_name varchar(255) not null," +
                    "last_name varchar(255) not null," +
                    "gender char not null," +
                    "person_id varchar(255) not null references Person(person_id) DEFERRABLE INITIALLY DEFERRED);");
        }catch(SQLException e){
            System.err.println("User Table:" + e);
        }
    }
    /**
     * Constructs the database table for authKey in the database file
     *
     * @param connection a connection to the database file
     */
    private static void buildAuthTokenTable(Connection connection){
        try{
            Statement command = connection.createStatement();
            command.executeUpdate("create table AuthToken ( " +
                    "token_id integer not null primary key autoincrement," +
                    "username string not null references User(username));");
        }catch(SQLException e){
            System.err.println("AuthToken Table:" + e);
        }
    }

    /**
     * Constructs the database table for event in the database file
     *
     * @param connection a connection to the database file
     */
    private static void buildEventTable(Connection connection){
        try{
            Statement command = connection.createStatement();
            command.executeUpdate("create table Event ( " +
                    "event_id string not null primary key," +
                    "person_id string not null references Person(person_id)," +
                    "descendant string not null references Person(person_id)," +
                    "latitude float not null," +
                    "longitude float not null," +
                    "country varchar(255) not null," +
                    "city varchar(255) not null," +
                    "event_type varchar(255) not null," +
                    "year integer not null);");
        }catch(SQLException e){
            System.err.println("Event Table:" + e);
        }
    }

    /**
     * Resets the database file using the internal connection to
     * the database file. Also resets the DAO singletons.
     *
     * @throws Exception
     */
    public static void  resetDatabase() throws Exception{
        if(connect != null) {
            connect.close();
        }
        authControl.resetInstance();
        personControl.resetInstance();
        userControl.resetInstance();
        eventControl.resetInstance();
        buildThemAll();
    }

    public static void closeConnection() throws SQLException{
        if(connect!=null){
            connect.close();
            authControl.resetInstance();
            userControl.resetInstance();
            personControl.resetInstance();
            eventControl.resetInstance();
        }
    }

    /**
     * Creates a connection to the database and initializes the DAOs. Maintains data persistence.
     *
     * @throws Exception
     */
    public static void connectToDatabase() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:FamilyMap.db");
        createDAOs(connection);
        connect = connection;
    }
}
