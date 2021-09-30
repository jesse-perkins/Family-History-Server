package DataAccessObjects;

import ModelClasses.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * <h1>PersonDAO</h1>
 * <p>
 * The PersonDAO handles access to the SQLite database functions used by
 * the Family Map Server Application services dealing with the Person Class,
 * obfuscating the operation of the database or the type of database from
 * the service itself.
 * </p>
 * <b>Note:</b> This DAO is built on SQLite databases.
 *
 * @author Jesse White
 * @version 0.1
 * @since 2017-2-15
 */
public class PersonDAO {
    /**
     * Getter for the defaultID
     *
     * @return defaultID used in PersonDAO
     */
    public static int getDefaultPersonID(){
        return defaultPersonID;
    }

    /**
     * Increments the value of the defaultID for events used by the PersonDAO
     */
    public static void incDefaultPersonID(){
        defaultPersonID++;
    }

    /**
     * The defaultID given to added persons if they don't already have them
     */
    private static int defaultPersonID = Math.abs((int) new Date().getTime());

    /**
     * A static PersonDAO for use to be initialized internally
     */
    private static PersonDAO personDAOSingleton = null;

    /**
     * Private connection to the database file
     */
    private Connection connection = null;

    /**
     * Private constructor for the PersonDAO
     *
     * @param connection a connection to the database file
     */
    private PersonDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates the singleton instance of the PersonDAO
     *
     * @param connection a connection to the database file
     * @return true if the singleton is created. False if one already exists.
     */
    public static boolean createSingletonInstance(Connection connection) {
        if (personDAOSingleton == null) {
            personDAOSingleton = new PersonDAO(connection);
            return true;
        }
        return false; //singleton already exists with connection
    }

    /**
     * Resets the singleton to null
     */
    public static void resetInstance(){
        personDAOSingleton = null;
    }

    /**
     * A getter for the singleton object
     *
     * @return the singleton PersonDAO
     */
    public static PersonDAO getInstance() {
        return personDAOSingleton;
    }

    /**
     * Adds a complete person to the person database.
     *
     * @param personIn the person to be added to the database.
     * @return true if the person is added, false the person already exists.
     * @throws SQLException
     */
    public boolean addPerson(Person personIn) throws SQLException {
        Statement command = connection.createStatement();
        if (personDAOSingleton.getPerson(personIn.getPersonID()) != null) {
            return false;
        } else {
            //If the descendant is not set, does not add a person to the database.
            //Descendant MUST be the username of the user
            ResultSet result = command.executeQuery("SELECT * FROM User WHERE username = '" + personIn.getDescendant() + "';");
            if(!result.isBeforeFirst()){
                return false;
            }else if(personIn.getPersonID() == null){//automatically assigns a default personID if there is no predetermined ID
                personIn.setPersonID(defaultPersonID);
                defaultPersonID++;
            }
            PreparedStatement prepUserPerson = connection.prepareStatement( "INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
            prepUserPerson.setString(1, personIn.getPersonID());
            prepUserPerson.setString(2, personIn.getDescendant());
            prepUserPerson.setString(3, personIn.getFirstName());
            prepUserPerson.setString(4, personIn.getLastName());
            prepUserPerson.setString(5, personIn.getGender().toString());
            prepUserPerson.setString(6, personIn.getFather());
            prepUserPerson.setString(7, personIn.getMother());
            prepUserPerson.setString(8, personIn.getSpouse());
            prepUserPerson.addBatch();
            command.execute("BEGIN TRANSACTION;");
            prepUserPerson.executeBatch();
            command.execute("END TRANSACTION;");
            return true;
        }
    }

    /**
     * Adds a two persons to the database, marks them as spouses, makes their personIDs
     * the entries for father and mother in child. Both parents must have the same
     * descendant as the child. The child must not already have parents.
     *
     * @param mother the mother of the child
     * @param father the father of the child
     * @param child the child
     * @return returns true if the parents are successfully added, false if not.
     * @throws SQLException
     */
    public boolean addParents(Person mother, Person father, Person child) throws SQLException {
        if (child == null) {//child does not exist
            return false;
        } else if(mother.getDescendant() == null || father.getDescendant() == null || child.getDescendant() == null){ //persons don't have descendants
                return false;
        } else if(!child.getDescendant().equals(mother.getDescendant()) ||
                !child.getDescendant().equals(father.getDescendant())){ //parents have a different descendant from child
            return false;
        } else {
            addPerson(mother);
            addPerson(father);
            child.setMother(mother.getPersonID());
            child.setFather(father.getPersonID());
            personDAOSingleton.addSpouse(mother, father);
            Statement command = connection.createStatement();
            command.executeUpdate("UPDATE Person SET father = '" + father.getPersonID() +
                    "', mother = '" + mother.getPersonID() +
                    "' WHERE person_id = '" + child.getPersonID() + "';");
            return true;
        }
    }

    /**
     * Modifies the passed in persons by assigning Spouse to person's spouse value, and
     * person to the spouses spouse value. Also inserts the spous into the database if
     * they haven't already been inserted.
     *
     * @param spouse the person object to become person's spouse
     * @param person the person object to become spouse's spouse
     * @return true if the spouse is added correctly
     * @throws SQLException
     */
    public boolean addSpouse(Person spouse, Person person) throws SQLException {
        if(personDAOSingleton.getPerson(person.getPersonID()) != null) {
            addPerson(spouse);
            person.setSpouse(spouse.getPersonID());
            spouse.setSpouse(person.getPersonID());
            Statement command = connection.createStatement();
            command.executeUpdate("UPDATE Person SET spouse = '" + spouse.getPersonID() +
                    "' WHERE person_id = '" + person.getPersonID() + "';");
            command.executeUpdate("UPDATE Person SET spouse = '" + person.getPersonID() +
                    "' WHERE person_id = '" + spouse.getPersonID() + "';");
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Grabs data and constructs a person object based on the person ID passed in.
     *
     * @param personID the ID of the person to be retrieved.
     * @return the object to which the ID belongs.
     * @throws SQLException
     */
    public Person getPerson(String personID) throws SQLException {
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("select * from Person where person_id = '" + personID + "';");
        if (!result.isBeforeFirst()) {
            return null;
        } else {
            Person out = new Person(result.getString(1), result.getString(2),
                    result.getString(3), result.getString(4),
                    result.getString(5).charAt(0), result.getString(6),
                    result.getString(7), result.getString(8));
            if(!(out.getMother() == null) && out.getMother().toLowerCase().equals("null")){
                out.setMother(null);
            }
            if(!(out.getFather() == null) && out.getFather().toLowerCase().equals("null")){
                out.setFather(null);
            }
            if(!(out.getSpouse() == null) && out.getSpouse().toLowerCase().equals("null")){
                out.setSpouse(null);
            }
            return out;
        }
    }

    /**
     * Retrieves all the ancestors of the descendant ID is passed in.
     *
     * @param descendant the ID of a registered users person object in the database.
     * @return all the people related to the user in a list of person objects.
     * @throws SQLException
     */
    public ArrayList<Person> getAncestors(String descendant) throws SQLException {
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("select * from Person where descendant = '" + descendant + "';");
        if (!result.isBeforeFirst()) {
            return new ArrayList<>();
        } else {
            Person temp;
            ArrayList<Person> out = new ArrayList<>();
            while (result.next()){
                temp = new Person(result.getString(1), result.getString(2),
                        result.getString(3), result.getString(4),
                        result.getString(5).charAt(0), result.getString(6),
                        result.getString(7), result.getString(8));
                if(!(temp.getMother() == null) && temp.getMother().toLowerCase().equals("null")){
                    temp.setMother(null);
                }
                if(!(temp.getFather() == null) && temp.getFather().toLowerCase().equals("null")){
                    temp.setFather(null);
                }
                if(!(temp.getSpouse() == null) && temp.getSpouse().toLowerCase().equals("null")){
                    temp.setSpouse(null);
                }
                out.add(temp);
            }
            return out;
        }
    }

    /**
     * Removes every entry in Person with the rootDescendant ID that matches the person id associated with
     * the given user.
     *
     * @param descendantID the ID of the descendant for whose family person data is to be removed
     * @return true if the data is removed, false if the given ID is not a descendantID
     * @throws SQLException
     */
    public boolean removeFamilyPersonData(String descendantID) throws SQLException{
        Statement command = connection.createStatement();
        ResultSet result = command.executeQuery("SELECT * FROM Person WHERE descendant = '" + descendantID + "';");
        if(!result.isBeforeFirst()) {
            return false;
        }else{
            command.execute("DELETE FROM Person WHERE descendant = '" + descendantID +"';");
            return true;
        }
    }
}
