package server;


import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.Login;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler{
    public static String login(Request request, Response response) throws Exception {
        Gson gson =  new Gson();
        Login login = gson.fromJson(request.body(), Login.class);
        try {
            AuthData auth = LoginService.login(login);
            return gson.toJson(auth);
        }catch (UnauthorizedException ex){
            return new ErrorHandler().error(401, response, ex);
        }catch (Exception ex){
            return new ErrorHandler().error(500, response, ex);
        }
    }
}