package tests.ModelTests;

import ModelClasses.Event;
import ModelClasses.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/5/2017.
 */
public class PersonTest {
    Person guy;
    @Before
    public void setUp() throws Exception {
        guy = new Person("Billy_Franklin", "Billy_Franklin", "Billy", "Franklin",'m');
    }

    @Test
    public void getPersonID() throws Exception {
        assertTrue(guy.getPersonID() == "Billy_Franklin");
    }

    @Test
    public void getFather() throws Exception {
        assertTrue(guy.getFather() == null);
    }

    @Test
    public void setFather() throws Exception {
        guy.setFather("Barret_Franklin");
        assertFalse(guy.getFather() == null);
        assertTrue(guy.getFather().equals("Barret_Franklin"));
    }
    @Test
    public void getMother() throws Exception {
        assertTrue(guy.getMother() == null);
    }

    @Test
    public void setMother() throws Exception {
        guy.setMother("Donna_Elise");
        assertFalse(guy.getMother() == null);
        assertTrue(guy.getMother().equals("Donna_Elise"));
    }

    @Test
    public void getSpouse() throws Exception {
        assertTrue(guy.getSpouse() == null);
    }

    @Test
    public void setSpouse() throws Exception {
        guy.setSpouse("Mary_Fox");
        assertFalse(guy.getSpouse() == null);
        assertTrue(guy.getSpouse().equals("Mary_Fox"));
    }

    @Test
    public void getPersonalEvents() throws Exception {
        assertTrue(guy.getPersonalEvents() == null);
    }

    @Test
    public void setPersonalEvents() throws Exception {
        guy.setPersonalEvents(new TreeSet<Event>());
        assertFalse(guy.getPersonalEvents() == null);
    }

    @Test
    public void getFirstName() throws Exception {
        assertTrue(guy.getFirstName().equals("Billy"));
    }

    @Test
    public void getLastName() throws Exception {
        assertTrue(guy.getLastName().equals("Franklin"));
    }
//"Billy_Franklin", "Billy_Franklin", "Billy", "Franklin",
    @Test
    public void getGender() throws Exception {
        assertTrue(guy.getGender().equals('m'));
    }

    @Test
    public void getRootDescendant() throws Exception {
        assertTrue(guy.getDescendant().equals("Billy_Franklin"));
    }

    @Test
    public void setRootDescendant() throws Exception {
        guy.setDescendant(null);
        assertTrue(guy.getDescendant() == null);
    }

    @Test
    public void equals() throws Exception {
        assertTrue(guy.equals(guy));
        guy.setMother("Sarah");
        guy.setFather("Bob");
        guy.setSpouse("Claire");
        assertTrue(guy.equals(guy));
    }


}