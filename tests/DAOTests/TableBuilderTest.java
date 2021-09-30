package tests.DAOTests;

import DataAccessObjects.*;
import ModelClasses.AuthKey;
import org.junit.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/5/2017.
 */
public class TableBuilderTest {
    @BeforeClass
    public static void setUpClass() throws Exception{
        TableBuilder.buildThemAll();
    }

    @AfterClass
    public static void tearDownClass() throws Exception{
        TableBuilder.resetDatabase();
        TableBuilder.closeConnection();
    }

    @Test
    public void buildThemAll() throws Exception {
        TableBuilder.buildThemAll();
        assertTrue(true);
    }

    @Test
    public void killTables() throws Exception {
        TableBuilder.killTables(TableBuilder.getConnection());
        assertTrue(true);
    }

    @Test
    public void resetTables() throws Exception{
        TableBuilder.resetDatabase();
        assertTrue(true);
    }
}