package tests.DAOTests;

import DataAccessObjects.PersonDAO;
import DataAccessObjects.TableBuilder;
import ModelClasses.Person;
import ModelClasses.User;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/13/2017.
 */
public class PersonDAOTest {
    Person sarah = new Person("billy", "Sarah", "White", 'f');
    Person mom = new Person("billy", "Mr.", "Danielson", 'f');
    Person dad = new Person("billy", "Mrs.", "McKlasky", 'm');
    Person nate = new Person("Nate_Danielson","billy", "Nate", "Danielson", 'm');
    static User billy = new User("billy", "password", "billy@gmail.com",
            "Billy", "Danielson", 'm');
    Person momNate = new Person("billy", "Bill", "Danielson", 'f');
    Person dadNate = new Person("billy", "Elaine", "McKlasky", 'm');
    @BeforeClass
    public static void setUpClass() throws Exception {
        TableBuilder.buildThemAll();
        TableBuilder.userControl.addUser(billy);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void createSingletonInstance() throws Exception {
        assertFalse(PersonDAO.createSingletonInstance(TableBuilder.getConnection()));
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(PersonDAO.getInstance() != null);
    }

    @Test
    public void addPerson() throws Exception {
        assertTrue(TableBuilder.personControl.addPerson(nate));
        assertFalse(TableBuilder.personControl.addPerson(nate));
    }

    @Test
    public void addParents() throws Exception {
        TableBuilder.personControl.addPerson(nate);
        assertTrue(TableBuilder.personControl.addParents(momNate, dadNate, nate));
    }

    @Test
    public void addSpouse() throws Exception {
        Person billyPerson = TableBuilder.personControl.getPerson(billy.getPersonID());
        assertTrue(TableBuilder.personControl.addSpouse(sarah, billyPerson));
    }

    @Test
    public void getPerson() throws Exception {
        TableBuilder.personControl.addPerson(nate);
        Person temp = TableBuilder.personControl.getPerson(nate.getPersonID());
        temp.equals(nate);
        assertTrue(temp.equals(nate));
    }

    @Test
    public void getAncestors() throws Exception {
        Person billyPerson = TableBuilder.personControl.getPerson(billy.getPersonID());
        TableBuilder.personControl.addParents(mom, dad, billyPerson);
        TableBuilder.personControl.addSpouse(sarah, billyPerson);
        ArrayList<Person> family = TableBuilder.personControl.getAncestors(billy.getUserName());
        assertTrue(family.size() == 4);
    }

    @Test
    public void removeFamilyPersonData() throws Exception {
        Person billyPerson = TableBuilder.personControl.getPerson(billy.getPersonID());
        TableBuilder.personControl.addPerson(nate);
        TableBuilder.personControl.addParents(momNate, dadNate, nate);
        TableBuilder.personControl.addParents(mom, dad, billyPerson);
        TableBuilder.personControl.addSpouse(sarah, billyPerson);
        TableBuilder.personControl.removeFamilyPersonData(billy.getUserName());
        ArrayList<Person> family = TableBuilder.personControl.getAncestors(billy.getUserName());
        assertTrue(family.size() == 0);
    }
}