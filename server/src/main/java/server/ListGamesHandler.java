package server;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.Login;
import service.ListGamesService;
import service.LoginService;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    public static String listGames (Request request, Response response){
        Gson gson =  new Gson();
        String authToken = request.headers("authorization");
        try {
            new ListGamesService().listGames(authToken);
            return gson.toJson("");
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }
    }
}
