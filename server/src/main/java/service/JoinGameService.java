package service;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.JoinRequest;

import java.sql.SQLException;
import java.util.Objects;

public class JoinGameService {
    public static void joinGame(String authToken, JoinRequest joinRequest) throws DataAccessException, SQLException {
        AuthData authData = new SQLAuthDAO().getAuth(authToken);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        if(joinRequest.gameID() < 0 || joinRequest.playerColor() == null){
            throw new BadRequestException("Error: bad request");
        }
        if(Objects.equals(joinRequest.playerColor(), "white") || Objects.equals(joinRequest.playerColor(), "black")
                || Objects.equals(joinRequest.playerColor(), "WHITE") || Objects.equals(joinRequest.playerColor(), "BLACK")) {
            new SQLGameDAO().updateGame(joinRequest, authData);
        }else{
            throw new BadRequestException("Error: bad request");
        }


    }
}
