package tests.ModelTests;

import ModelClasses.AuthKey;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/5/2017.
 */
public class AuthKeyTest {
    AuthKey key;
    @Before
    public void setUp() throws Exception {
        key = new AuthKey(12345);
    }

    @Test
    public void getKey() throws Exception {
        assertTrue(key.getKey()== 12345);
    }

}
