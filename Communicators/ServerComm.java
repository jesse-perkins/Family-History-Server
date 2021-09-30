package Communicators;

import DataAccessObjects.TableBuilder;
import Handlers.*;
import com.sun.corba.se.spi.activation.Server;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

/**
 * Created by Jesse on 3/9/2017.
 */
public class ServerComm {
    //Constants
    public static int SERVER_PORT_NUM = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    /**
     * Internal server object
     */
    private static HttpServer server;

    /**
     * Private contructor
     */
    private ServerComm(){}

    /**
     * Runs the server on port 8080.
     */
    private void runServer(){
        setupServer(SERVER_PORT_NUM, MAX_WAITING_CONNECTIONS);
        createContexts();
        server.start();
    }

    /**
     * Runs the server on whichever port hte user wants.
     */
    private void runServer(String[] args){
        if(args != null && args.length > 0){
            SERVER_PORT_NUM = Integer.parseInt(args[0]);
        }
        setupServer(SERVER_PORT_NUM, MAX_WAITING_CONNECTIONS);
        createContexts();
        server.start();
    }

    /**
     * Sets up the server to receive requests.
     *
     * @param portNum the port to be used by the server.
     * @param maxConnections the max number of connections allowed on the server.
     */
    private void setupServer(int portNum, int maxConnections){
        try{
            server = HttpServer.create(new InetSocketAddress(portNum), maxConnections);
        }catch (Exception e){
            System.out.println("Server Setup failed:" + e);
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
    }

    /**
     * Creates the contexts for the server.
     */
    private void createContexts(){
        server.createContext("/", new DefaultHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
    }

    /**
     * Clears out the database and makes sure that the DAOs are all up and running. Then starts the server
     * on the specified port number
     *
     * @param args These can be empty, tags don't do anything in this function.
     */
    public static void main(String args[]){
        try{
            TableBuilder.connectToDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }
        new ServerComm().runServer(args);
    }

    /**
     * Clears out the database and makes sure that the DAOs are all up and running.
     * Then starts the server on portNum 8080
     *
     */
    public static void testRunner(){
        try{
            TableBuilder.buildThemAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        new ServerComm().runServer();
    }

    public static void stopServer(){
        if(server !=null){
            server.stop(0);
        }
    }
}
