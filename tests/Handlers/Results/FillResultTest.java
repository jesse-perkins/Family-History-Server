package tests.Handlers.Results;

import Handlers.Result.FillResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class FillResultTest {
    @Test
    public void getMessage() throws Exception {
        FillResult result = new FillResult("Error: malfunction");
        FillResult fillResult = new FillResult(10,32);
        assertTrue(result.getMessage() != null);
        assertTrue(fillResult.getMessage() != null);
        assertTrue(fillResult.getMessage().equals("Successfully added 10 persons and 32 events to the database"));
    }

}