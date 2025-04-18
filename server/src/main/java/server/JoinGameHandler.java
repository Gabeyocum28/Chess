package server;

import com.google.gson.Gson;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.JoinRequest;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public static String joinGame(Request request, Response response){
        Gson gson =  new Gson();
        String authToken = request.headers("authorization");
        JoinRequest joinRequest = gson.fromJson(request.body(), JoinRequest.class);

        try {
            new JoinGameService().joinGame(authToken, joinRequest);
            return "{}";
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch(BadRequestException ex){
            return new ErrorHandler().error(400, response, ex);
        }catch(AlreadyTakenException ex){
            return new ErrorHandler().error(403, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }
    }
}
