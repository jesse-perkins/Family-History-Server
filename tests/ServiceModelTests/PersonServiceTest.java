package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Result.PersonResult;
import ModelClasses.AuthKey;
import ModelClasses.Person;
import ModelClasses.User;
import ServiceModels.PersonService;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class PersonServiceTest {
    private static Person billyPerson;
    private static Person billyMom;
    private static Person billyDad;
    private static Person billyMMom;
    private static Person billyMDad;
    private static Person billyDDad;
    private static Person billyDMom;
    private static AuthKey key;

    @BeforeClass
    public static void setUp() throws Exception {

        TableBuilder.buildThemAll();
        User user = new User("billy", "password", "billy@gmail.com",
                "Billy", "Fredricks", 'm');
        TableBuilder.userControl.addUser(user);
        billyPerson = TableBuilder.personControl.getPerson(user.getPersonID());
        billyMom = new Person(user.getUserName(), "Martha", "Hoss", 'f');
        billyMMom = new Person(user.getUserName(), "Mary", "Grant", 'f');
        billyMDad = new Person(user.getUserName(), "Don", "Hoss", 'm');
        billyDad = new Person(user.getUserName(), "David", "Fredricks", 'm');
        billyDDad = new Person(user.getUserName(), "Annie", "Calvin", 'f');
        billyDMom = new Person(user.getUserName(), "Frank", "Fredricks", 'm');
        TableBuilder.personControl.addParents(billyMom, billyDad, billyPerson);
        TableBuilder.personControl.addParents(billyMMom, billyMDad, billyMom);
        TableBuilder.personControl.addParents(billyDMom, billyDDad, billyDad);
        key = TableBuilder.authControl.addAuthToken(user.getUserName());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void person() throws Exception {
        PersonResult result = PersonService.person("" + key.getKey());
        assertTrue(result.getFamily() != null);
        assertTrue(result.getFamily().size() == 7);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getSingle() == null);
        result = PersonService.person("123");
        assertTrue(result.getFamily() == null);
        assertTrue(result.getMessage() != null);
        assertTrue(result.getSingle() == null);
    }

    @Test
    public void person1() throws Exception {
        PersonResult result = PersonService.person("" + key.getKey(), billyPerson.getPersonID());
        assertTrue(result.getFamily() == null);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getSingle() != null);
        assertTrue(billyPerson.equals(result.getSingle()));
        result = PersonService.person("" + key.getKey(), billyDMom.getPersonID());
        assertTrue(result.getFamily() == null);
        assertTrue(result.getMessage() == null);
        assertTrue(result.getSingle() != null);
        assertTrue(billyDMom.equals(result.getSingle()));
        result = PersonService.person("123", billyPerson.getPersonID());
        assertTrue(result.getFamily() == null);
        assertTrue(result.getMessage() != null);
        assertTrue(result.getSingle() == null);
        result = PersonService.person(""+ key.getKey(), "1234");
        assertTrue(result.getFamily() == null);
        assertTrue(result.getMessage() != null);
        assertTrue(result.getSingle() == null);
    }

}