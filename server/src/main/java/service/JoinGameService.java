package service;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.JoinRequest;
import model.UserData;

import java.util.Map;
import java.util.Objects;

public class JoinGameService {
    public void joinGame(String authToken, JoinRequest joinRequest){
        AuthData authData = new MemoryAuthDAO().getAuth(authToken);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        if(joinRequest.gameID() == 0 || joinRequest.playerColor() == null){
            throw new BadRequestException("Error: bad request");
        }
        if(Objects.equals(joinRequest.playerColor(), "WHITE") || Objects.equals(joinRequest.playerColor(), "BLACK")) {
            new MemoryGameDAO().updateGame(joinRequest, authData);
        }else{
            throw new BadRequestException("Error: bad request");
        }


    }
}
