package Handlers;

import Handlers.Result.EventResult;
import ServiceModels.EventService;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * <h1>EventHandler</h1>
 * <p>
 * The handler for the EventService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class EventHandler implements HttpHandler{
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the EventService
     *
     * @param exchange the HTTP Exchange handled by this function
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //snag the auth token
        Headers headers = exchange.getRequestHeaders();
        String authorizationCode  = headers.getFirst("Authorization");

        //Snag parameters out of the URI if the exist
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String[] pathSegments = path.split("/");

        //Call the service based on the parameters
        EventResult result;
        if(pathSegments.length > 2){ //[0]="", [1]="event", [2]="eventID"
            result = EventService.SINGLETON.event(authorizationCode, pathSegments[2]);
        }else{
            result = EventService.SINGLETON.event(authorizationCode);
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        try {
            gson.toJson(result, output);
        } catch (Exception e){
            e.printStackTrace();
        }
        output.close();
    }
}
