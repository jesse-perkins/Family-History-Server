package ServiceModels;

import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.UserDAO;
import Handlers.Request.LoadRequest;
import Handlers.Result.ClearResult;
import Handlers.Result.LoadResult;
import ModelClasses.Event;
import ModelClasses.Person;
import ModelClasses.User;

/**
 * <h1>LoadService</h1>
 * <p>
 * Loads user, person, and event data into the database . Loading removes ALL other data from the database.
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class LoadService {
    /**
     * Singleton for the LoadService
     */
    public static final LoadService SINGLETON = new LoadService();

    /**
     * Loads the arrays of user, person, and event data inside the request into the database
     *
     * @param request a LoadRequest object containing the information to be loaded into the server
     * @return LoadResult
     */
    public static LoadResult load(LoadRequest request){
        //Wipe out the database
        ClearResult cleared = ClearService.clear();
        if(!cleared.getMessage().equals("Clear succeeded.")){
            return new LoadResult(cleared.getMessage());
        }
        try {
            UserDAO userControl = UserDAO.getInstance();
            PersonDAO personControl  = PersonDAO.getInstance();
            EventDAO eventControl = EventDAO.getInstance();
            // Keep track of the number of users, events, and persons added
            int usersAdded = 0;
            int eventsAdded = 0;
            int personsAdded = 0;
            //Add all the users to the databases
            for ( User current:
                 request.getUsers()) {
                 if(userControl.addUser(current)){
                     usersAdded++;
                 }
            }
            //Adds all the people to the database
            for (Person current:
                 request.getPersons()) {
                 if(personControl.addPerson(current)){
                     personsAdded++;
                 }
            }
            //Adds all the events to the database
            for (Event current:
                    request.getEvents()) {
                if(eventControl.addEvent(current)!= null){
                    eventsAdded++;
                }
            }
            //Constructs the success string and returns it
            StringBuilder holder = new StringBuilder();
            holder.append("Successfully added ");
            holder.append(usersAdded);
            holder.append(" users, ");
            holder.append(personsAdded);
            holder.append(" persons, and ");
            holder.append(eventsAdded);
            holder.append(" events to the database.");
            return new LoadResult(holder.toString());
        }catch(Exception e){
            System.out.println("Load Data Failed: " + e);
            e.printStackTrace();
            return new LoadResult("Load Data Failed: " + e);
        }
    }
}
