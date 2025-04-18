package server;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import service.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ListGamesHandler {
    public static String listGames (Request request, Response response){
        String authToken = request.headers("authorization");

        response.type("application/json");


        try {
            var list = ListGamesService.listGames(authToken).toArray();
            return new Gson().toJson(Map.of("games", list));
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }
    }
}
