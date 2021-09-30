package ServiceModels;

import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.TableBuilder;
import DataAccessObjects.UserDAO;
import Handlers.Result.FillResult;
import ModelClasses.Event;
import ModelClasses.Location;
import ModelClasses.Person;
import ModelClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>FillService</h1>
 * <p>
 * Replaces all the family information and data for the loged in user with a specified number of generations
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class FillService {
    /**
     * Singleton for the FillService
     */
    public static final FillService SINGLETON = new FillService();

    //Constants marking the locations of the Jsons which hold name and location information
//    private static final String FNAME_FILE_LOCATION = "\\src\\Jsons\\fnames.json";
//    private static final String MNAME_FILE_LOCATION = "\\src\\Jsons\\mnames.json";
//    private static final String SNAME_FILE_LOCATION = "\\src\\Jsons\\snames.json";
//    private static final String LOC_FILE_LOCATION = "\\src\\Jsons\\locations.json";

    private static final String FNAME_FILE_LOCATION = "/src/Jsons/fnames.json";
    private static final String MNAME_FILE_LOCATION = "/src/Jsons/mnames.json";
    private static final String SNAME_FILE_LOCATION = "/src/Jsons/snames.json";
    private static final String LOC_FILE_LOCATION = "/src/Jsons/locations.json";

    /**
     * The year used as the basis for date information in the fill functions
     */
    private static final int YEAR = 2017;

    /**
     * An internal Gson used for decoding the Jsons which hold name and location information
     */
    private Gson gson = new Gson();

    /**
     * The ArrayList of female names.
     */
    private ArrayList<String> femaleNames= new ArrayList<>();

    /**
     * The ArrayList of male names.
     */
    private ArrayList<String> maleNames = new ArrayList<>();
    /**
     * The ArrayList of surnames.
     */
    private ArrayList<String> surnames = new ArrayList<>();

    /**
     * The ArrayList of locations.
     */
    private ArrayList<Location> locations = new ArrayList<>();

    /**
     * The ArrayList of eventTypes.
     */
    private ArrayList<String> eventTypes = new ArrayList<>();

    /**
     * The private FillService constructor. The constructor handles converting the Jsons holding useful name and location
     * information into the ArrayLists that will be used during person and event generation.
     */
    private FillService(){
        try {
            //Pump info into femaleNames ArrayList
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + FNAME_FILE_LOCATION;

            File femaleNameFile = new File(filePath);
            InputStream femaleNameStream = new FileInputStream(femaleNameFile);
            InputStreamReader females = new InputStreamReader(femaleNameStream);
            JsonObject femaleObject = gson.fromJson(females, JsonObject.class);

            for (JsonElement current:
                 femaleObject.getAsJsonArray("data")) {
                femaleNames.add(gson.fromJson(current, String.class));
            }

            //Pump info into maleNames ArrayList
            filePath = new File("").getAbsolutePath();
            filePath = filePath + MNAME_FILE_LOCATION;

            File maleNameFile = new File(filePath);
            InputStream maleNameStream = new FileInputStream(maleNameFile);
            InputStreamReader males = new InputStreamReader(maleNameStream);
            JsonObject maleObject = gson.fromJson(males, JsonObject.class);

            for (JsonElement current:
                    maleObject.getAsJsonArray("data")) {
                maleNames.add(gson.fromJson(current, String.class));
            }

            //Pump info into surnames ArrayList
            filePath = new File("").getAbsolutePath();
            filePath = filePath + SNAME_FILE_LOCATION;

            File surnameFile = new File(filePath);
            InputStream surnameStream = new FileInputStream(surnameFile);
            InputStreamReader surname = new InputStreamReader(surnameStream);
            JsonObject surnameObject = gson.fromJson(surname, JsonObject.class);

            for (JsonElement current:
                    surnameObject.getAsJsonArray("data")) {
                surnames.add(gson.fromJson(current, String.class));
            }

            //Pump info into locations ArrayList
            filePath = new File("").getAbsolutePath();
            filePath = filePath + LOC_FILE_LOCATION;

            File locationFile = new File(filePath);
            InputStream locationStream = new FileInputStream(locationFile);
            InputStreamReader location = new InputStreamReader(locationStream);
            JsonObject locationJsonObject = gson.fromJson(location, JsonObject.class);

            for (JsonElement current:
                locationJsonObject.getAsJsonArray("data")) {
                locations.add(gson.fromJson(current, Location.class));
            }

            //Create and add eventTypes to the EventType ArrayList
            eventTypes.add("Birth");
            eventTypes.add("Baptism");
            eventTypes.add( "Death");
            eventTypes.add("Marriage");
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * The fill service. Generates the specified number of generations worth of person and event data for the
     * specified user. WIPES OUT ALL INFORMATION IN THE DATABASE FOR THIS USER
     *
     * @param genNum the number of generations of information to be generated
     * @param username the userName of the user for whom data will be generated
     * @return FillResult
     */
    public FillResult fill(int genNum, String username) {
        try {
            EventDAO eventControl = EventDAO.getInstance();
            PersonDAO personControl = PersonDAO.getInstance();
            UserDAO userControl = UserDAO.getInstance();
            User temp = userControl.getUser(username);
            if(temp != null){
                //Clear all person and event data for this user and reinstate the person for the user
                eventControl.removeFamilyEventData(temp.getUserName());
                personControl.removeFamilyPersonData(temp.getUserName());
                //Creates and enters a new person for the user
                Person userPerson = new Person(temp.getPersonID(), temp.getUserName() ,temp.getFirstName(),
                        temp.getLastName(), temp.getGender());
                personControl.addPerson(userPerson);
                //Runs fillHelper
                int[] holder = fillHelper(userPerson, genNum);
                return new FillResult(holder[0], holder[1]);
            }else{
                return new FillResult(("Error: Invalid Username."));
            }
        }catch(Exception e){
            System.out.println("Fill Service Failed: " + e);
            e.printStackTrace();
            return new FillResult(("Fill Service Failed: " + e));
        }
    }

    /**
     * An iterative fill person function that works within the fill service
     *
     * @param descendant the person object of the user to receive generated information
     * @param genNum the number of generations to be implemented
     * @return an array of 2 ints that are:
     *          [0] the number of people added to the database and
     *          [1] the number of events added to the database
     * @throws Exception
     */
    private int[] fillHelper(Person descendant, int genNum) throws Exception{
        EventDAO eventControl = EventDAO.getInstance();
        PersonDAO personControl = PersonDAO.getInstance();
        //This ArrayList is used to keep track of who still needs parents
        ArrayList<Person> current = new ArrayList<>();
        Random rand = new Random();
        int year = YEAR;
        current.add(descendant);
        //Creates event data for the user person and enters it into the database
        ArrayList<Event> lifeEvents = personEventMaker(descendant, year);
        eventControl.uploadEvents(lifeEvents);
        //initializes counters to keep track of the number of people and events added
        int numPersons = 1;
        int numEvents = lifeEvents.size();
        lifeEvents.clear();
        //Only run the outer loop for the number of generations wanted
        for (int i = 0; i < genNum; i++){
            // This ArrayList is used to keep track of who was generated and will need parents in the following iteration
            ArrayList<Person> next = new ArrayList<>();
            year -= rand.nextInt(6)+ 30;
            //Run the inner loop for every person who needs parents on this iteration
            for (int j = 0; j < current.size(); j++){
                //Create parents
                Person mom = makePerson(current.get(j), "mom");
                Person dad = makePerson(current.get(j), "dad");
                //Add parents to the next ArrayList, the database and keep count.
                next.add(mom);
                numPersons++;
                next.add(dad);
                numPersons++;
                personControl.addParents(mom, dad, current.get(j));
                //Marry the parents and create the same event for each of them. Keep count of events
                lifeEvents = marriageMaker(mom, dad,year);
                eventControl.uploadEvents(lifeEvents);
                numEvents += lifeEvents.size();
                lifeEvents.clear();
                //Create life events for the parents and keep count
                lifeEvents = personEventMaker(mom, year);
                eventControl.uploadEvents(lifeEvents);
                numEvents += lifeEvents.size();
                lifeEvents.clear();
                lifeEvents = personEventMaker(dad, year);
                eventControl.uploadEvents(lifeEvents);
                numEvents += lifeEvents.size();
                lifeEvents.clear();
            }
            //Empty the current iteration of people who need parents and load next into current
            current.clear();
            current.addAll(next);
        }
        //Create the array for persons created and events created and return it
        int[] result = {numPersons, numEvents};
        return result;
    }

    /**
     * Create a new parent person based on input information
     *
     * @param child the child of the person to be created
     * @param parentType if the person is "mom" or "dad"
     * @return the created Person object
     * @throws Exception
     */
    private Person makePerson(Person child, String parentType) throws Exception{
        //Make sure the parent type is of the expected type
        if(!parentType.equals("mom") && !parentType.equals("dad")){
            throw new Exception("Invalid parent type in makePerson, which is inside fillService " + parentType);
        }
        Random rand = new Random();
        char gender;
        String firstName;
        String surname;
        // Set parent information based on parent gender. Moms get maiden names, dads have the same surname as the child
        if(parentType.equals("mom")){
            gender = 'f';
            firstName = femaleNames.get(rand.nextInt(femaleNames.size()));
            surname = surnames.get(rand.nextInt(surnames.size()));
        }else{
            gender = 'm';
            firstName = maleNames.get(rand.nextInt(maleNames.size()));
            surname = child.getLastName();
        }
        //Create the parent object and return it
        Person parent = new Person(null, child.getDescendant(), firstName, surname, gender);
        return parent;
    }

    /**
     * Generate life events based on year. Anyone born before 1930 is dead
     *
     * @param person the person the event will be made for
     * @param year the year of the birth of this person's child
     * @return an ArrayList of Events for the person
     * @throws Exception
     */
    private ArrayList<Event> personEventMaker (Person person, int year) throws Exception{
        int numEvents;
        ArrayList<Event> lifeEvents = new ArrayList<>();
        Random rand = new Random();
        //The number of events in a person's life who isn't dead
        numEvents = 2;
        //Figure out the year of birth.
        year -= (rand.nextInt(6) + 23);
        //If birth year is before 1930, they get a death event
        if (year < 1930){
            numEvents = 3;
        }
        int eventYear = year;
        //Iteratively generate life events. First birth, then baptism, then death.
        for(int i = 0; i < numEvents; i++){
            Event temp = new Event(person.getDescendant(), person.getPersonID(),
                    eventYear, locations.get(rand.nextInt(locations.size())), eventTypes.get(i));
            lifeEvents.add(temp);
            if(i == 0) {
                //Baptism occurs 8 years after birth
                eventYear += 8;
            }else {
                //Death occurs between 50 and 80 years after baptism. Max lifespan is 88 years
                eventYear += rand.nextInt(31)+50;
            }
        }
        return lifeEvents;
    }

    /**
     * Update information so that the two people are married
     *
     * @param wife the wife
     * @param husband the husband
     * @param year the year the marriage took place
     * @return ArrayList containing a marriage event, one each for husband and wife
     */
    private ArrayList<Event> marriageMaker(Person wife, Person husband, int year) {
        ArrayList<Event> lifeEvents = new ArrayList<>();
        Random rand = new Random();
        Location loc = locations.get(rand.nextInt(locations.size()));
        //Generate the marriage events
        Event wifeMarriage = new Event(wife.getDescendant(), wife.getPersonID(),
                year, loc, eventTypes.get(3));
        Event husbandMarriage = new Event(husband.getDescendant(), husband.getPersonID(),
                year, loc, eventTypes.get(3));
        lifeEvents.add(wifeMarriage);
        lifeEvents.add(husbandMarriage);
        return lifeEvents;
    }
}
