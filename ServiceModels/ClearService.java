package ServiceModels;

import DataAccessObjects.TableBuilder;
import Handlers.Result.ClearResult;

/**
 * <h1>ClearService</h1>
 * <p>
 * Clears the database of all data
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/8/2017.
 */
public class ClearService {
    /**
     * Singleton for the ClearService
     */
    public static final ClearService SINGLETON = new ClearService();

    /**
     * Clears the database of all data.
     *
     * @return ClearResult
     */
    public static ClearResult clear(){
        try{
            TableBuilder.resetDatabase();
            return new ClearResult();
        }catch (Exception e){
            System.out.print("Table Clear Failed: " + e);
            return new ClearResult("Table Clear Failed: " + e);
        }
    }
}
