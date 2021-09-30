package tests.CommunicatorTests;

import Communicators.ServerComm;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jesse on 3/16/2017.
 */
public class ServerCommTest {
    @Test
    public void main() throws Exception {
        String[] empty = null;
        ServerComm.main(empty);
        assertTrue(true);
        // If the test reaches this point then nothing has broken in server setup.
        // In depth functionality of the server will be tested through integration testing with the client communicator
        // since I have no idea how to send HTTP stuff inside a machine without using the API default page.
        ServerComm.stopServer();
    }

}