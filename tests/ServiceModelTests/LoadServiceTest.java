package tests.ServiceModelTests;

import DataAccessObjects.TableBuilder;
import Handlers.Request.LoadRequest;
import Handlers.Result.LoadResult;
import ModelClasses.Event;
import ModelClasses.Location;
import ModelClasses.Person;
import ModelClasses.User;
import ServiceModels.LoadService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class LoadServiceTest {
    private static ArrayList<User> newUsers = new ArrayList<>();
    private static ArrayList<Person> newPersons = new ArrayList<>();
    private static ArrayList<Event> newEvents = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws Exception {
        TableBuilder.buildThemAll();

        Location loc = new Location(123.1234, -123.1234, "Beijing", "china");
        User user1 = new User("billy", "password", "billy@gmail.com","Billy", "Fredricks", 'm', "abcde");
        User user2 = new User("sally", "password", "billy@gmail.com","Sally", "Sanders", 'f', "efghi");
        User user3 = new User("jimmy", "password", "billy@gmail.com","Jimmy", "Jackman", 'm', "jklmn");
        User user4 = new User("jeremy", "password", "billy@gmail.com","Jeremy", "Filasco", 'm',"asdfg");
        User user5 = new User("eliza", "password", "billy@gmail.com","Eliza", "Burton", 'f', "zxcvv");
        User user6 = new User("frank", "password", "billy@gmail.com","Frank", "Carter", 'm', "ghjkl");

        newUsers.add(user1);
        newUsers.add(user2);
        newUsers.add(user3);
        newUsers.add(user4);
        newUsers.add(user5);
        newUsers.add(user6);
        newUsers.add(user1);

        Person user1Person = new Person(user1.getPersonID(), user1.getUserName(), user1.getFirstName(), user1.getLastName(),user1.getGender(), "a1234asa" , "a1234asd" , null);
        Person user2Person = new Person(user2.getPersonID(), user2.getUserName(), user2.getFirstName(), user2.getLastName(),user2.getGender(),"b1234asb", "b1234asd", null);
        Person user3Person = new Person(user3.getPersonID(), user3.getUserName(), user3.getFirstName(), user3.getLastName(),user3.getGender(),"c1234asd", "c1234asc", null);
        Person user4Person = new Person(user4.getPersonID(), user4.getUserName(), user4.getFirstName(), user4.getLastName(),user4.getGender(),"d1234asd", "d1234ase", null);
        Person user5Person = new Person(user5.getPersonID(), user5.getUserName(), user5.getFirstName(), user5.getLastName(),user5.getGender(),"e1234asd", "e1234asf", null);
        Person user6Person = new Person(user6.getPersonID(), user6.getUserName(), user6.getFirstName(), user6.getLastName(),user6.getGender(),"f1234asd", "f1234asg", null);

        Person user1Mom = new Person("a1234asd", user1.getUserName(), "Sarah", "White", 'f', null, null, "a1234asa");
        Person user2Mom = new Person("b1234asd", user2.getUserName(), "Stacy", "Green", 'f', null, null, "b1234asb");
        Person user3Mom = new Person("c1234asd", user3.getUserName(), "Sally", "Christof", 'f', null, null, "c1234asc");
        Person user4Mom = new Person("d1234asd", user4.getUserName(), "Emily", "Sarjenkov", 'f', null, null, "d1234ase");
        Person user5Mom = new Person("e1234asd", user5.getUserName(), "Erica", "Wilks", 'f', null, null, "e1234asf");
        Person user6Mom = new Person("f1234asd", user6.getUserName(), "Eliza", "Anderson", 'f', null, null, "f1234asg");

        Person user1Dad = new Person("a1234asa", user1.getUserName(), "Bill", user1.getLastName(), 'm', null, null, "a1234asd");
        Person user2Dad = new Person("b1234asb", user2.getUserName(), "Stan", user2.getLastName(), 'm', null, null, "b1234asd");
        Person user3Dad = new Person("c1234asc", user3.getUserName(), "Sebastian", user3.getLastName(), 'm', null, null, "c1234asd");
        Person user4Dad = new Person("d1234ase", user4.getUserName(), "Aaron", user4.getLastName(), 'm', null, null, "d1234asd");
        Person user5Dad = new Person("e1234asf", user5.getUserName(), "Eric", user5.getLastName(), 'm', null, null, "e1234asd");
        Person user6Dad = new Person("f1234asg", user6.getUserName(), "Elias", user6.getLastName(), 'm', null, null, "f1234asd");

        newPersons.add(user1Person);
        newPersons.add(user2Person);
        newPersons.add(user3Person);
        newPersons.add(user4Person);
        newPersons.add(user5Person);
        newPersons.add(user6Person);

        newPersons.add(user1Mom);
        newPersons.add(user2Mom);
        newPersons.add(user3Mom);
        newPersons.add(user4Mom);
        newPersons.add(user5Mom);
        newPersons.add(user6Mom);

        newPersons.add(user1Dad);
        newPersons.add(user2Dad);
        newPersons.add(user3Dad);
        newPersons.add(user4Dad);
        newPersons.add(user5Dad);
        newPersons.add(user6Dad);

        Event birth1 = new Event(user1Person.getDescendant(), user1Person.getPersonID(), 1990, loc, "birth");
        Event birth2 = new Event(user2Person.getDescendant(), user2Person.getPersonID(), 1990, loc, "birth");
        Event birth3 = new Event(user3Person.getDescendant(), user3Person.getPersonID(), 1990, loc, "birth");
        Event birth4 = new Event(user4Person.getDescendant(), user4Person.getPersonID(), 1990, loc, "birth");
        Event birth5 = new Event(user5Person.getDescendant(), user5Person.getPersonID(), 1990, loc, "birth");
        Event birth6 = new Event(user6Person.getDescendant(), user6Person.getPersonID(), 1990, loc, "birth");

        Event birth7 = new Event(user1Mom.getDescendant(), user1Mom.getPersonID(), 1990, loc, "birth");
        Event birth8 = new Event(user2Mom.getDescendant(), user2Mom.getPersonID(), 1990, loc, "birth");
        Event birth9 = new Event(user3Mom.getDescendant(), user3Mom.getPersonID(), 1990, loc, "birth");
        Event birtha = new Event(user4Mom.getDescendant(), user4Mom.getPersonID(), 1990, loc, "birth");
        Event birthb = new Event(user5Mom.getDescendant(), user5Mom.getPersonID(), 1990, loc, "birth");
        Event birthc = new Event(user6Mom.getDescendant(), user6Mom.getPersonID(), 1990, loc, "birth");

        Event birthd = new Event(user1Dad.getDescendant(), user1Dad.getPersonID(), 1990, loc, "birth");
        Event birthe = new Event(user2Dad.getDescendant(), user2Dad.getPersonID(), 1990, loc, "birth");
        Event birthf = new Event(user3Dad.getDescendant(), user3Dad.getPersonID(), 1990, loc, "birth");
        Event birthg = new Event(user4Dad.getDescendant(), user4Dad.getPersonID(), 1990, loc, "birth");
        Event birthh = new Event(user5Dad.getDescendant(), user5Dad.getPersonID(), 1990, loc, "birth");
        Event birthi = new Event(user6Dad.getDescendant(), user6Dad.getPersonID(), 1990, loc, "birth");

        newEvents.add(birth1);
        newEvents.add(birth2);
        newEvents.add(birth3);
        newEvents.add(birth4);
        newEvents.add(birth5);
        newEvents.add(birth6);
        newEvents.add(birth7);
        newEvents.add(birth8);
        newEvents.add(birth9);
        newEvents.add(birtha);
        newEvents.add(birthb);
        newEvents.add(birthc);
        newEvents.add(birthd);
        newEvents.add(birthe);
        newEvents.add(birthf);
        newEvents.add(birthg);
        newEvents.add(birthh);
        newEvents.add(birthi);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }
    @Test
    public void load() throws Exception {
        LoadRequest request = new LoadRequest(newUsers, newPersons, newEvents);
        LoadResult result = LoadService.load(request);
        assertTrue(result.getMessage() != null);
    }

}