package Handlers;

import Handlers.Result.PersonResult;
import ServiceModels.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * <h1>PersonHandler</h1>
 * <p>
 * The handler for the PersonService
 * </p>
 *
 * @author Jesse White
 * @version 0.1
 * @since 3/9/2017.
 */
public class PersonHandler implements HttpHandler {
    /**
     * Encoder / decoder for Json objects
     */
    private Gson gson = new Gson();

    /**
     * Handles the exchange regarding the PersonService
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

        //call the service based on the parameters
        PersonResult result;
        if(pathSegments.length > 2){ //[0]="", [1]="person", [2]="personID"
            result = PersonService.SINGLETON.person(authorizationCode, pathSegments[2]);
        }else{
            result = PersonService.SINGLETON.person(authorizationCode);
        }
        //0 means that there is something in the response body
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, output);
        output.close();
    }
}
