package tests.Handlers.Results;

import Handlers.Result.PersonResult;
import ModelClasses.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/15/2017.
 */
public class PersonResultTest {
    Person billy = new Person("Billy_Sanders", "billy","Billy","Sanders",'m');
    PersonResult onePerson = new PersonResult(billy);
    PersonResult error = new PersonResult("Error Ocurred");
    PersonResult family = new PersonResult(new ArrayList<>());

    @Test
    public void getSingle() throws Exception {
        assertTrue(onePerson.getSingle() != null);
        assertTrue(onePerson.getMessage() == null);
        assertTrue(onePerson.getFamily() == null);
    }

    @Test
    public void getFamily() throws Exception {
        assertTrue(error.getSingle() == null);
        assertTrue(error.getMessage() != null);
        assertTrue(error.getFamily() == null);
    }

    @Test
    public void getMessage() throws Exception {
        assertTrue(family.getSingle() == null);
        assertTrue(family.getMessage() == null);
        assertTrue(family.getFamily() != null);
    }

}