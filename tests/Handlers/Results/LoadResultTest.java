package tests.Handlers.Results;

import Handlers.Result.LoadResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/14/2017.
 */
public class LoadResultTest {
    @Test
    public void getMessage() throws Exception {
        LoadResult loadResult = new LoadResult("Error: malfunction");
        assertTrue(loadResult.getMessage()!= null);
    }

}