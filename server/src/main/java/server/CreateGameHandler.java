package server;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import model.GameData;
import model.UserData;
import service.CreateGameService;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public static String createGame(Request request, Response response){
        Gson gson =  new Gson();
        String authToken = request.headers("authorization");
        GameData newGame = gson.fromJson(request.body(), GameData.class);
        try {
            GameData game = new CreateGameService().createGame(authToken, newGame);
            return gson.toJson(auth);
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }
    }
}
