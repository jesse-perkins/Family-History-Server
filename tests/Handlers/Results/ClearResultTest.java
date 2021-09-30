package tests.Handlers.Results;

import Handlers.Result.ClearResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class ClearResultTest {
    @Test
    public void getMessage() throws Exception {
        ClearResult result = new ClearResult();
        assertTrue(result.getMessage().equals("Clear succeeded."));
        result = new ClearResult("Table build failed.");
        assertFalse(result.getMessage().equals("Clear succeeded."));
    }

}
