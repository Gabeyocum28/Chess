package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

public class CreateGameService {
    static int id = 1;
    public static GameData createGame(String authRequest, GameData gameName) throws DataAccessException {
        AuthData authData = new SQLAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        id++;
        GameData newGame = new GameData(id, gameName.whiteUsername(), gameName.blackUsername(), gameName.gameName(), new ChessGame());
        new SQLGameDAO().createGame(newGame);

        return newGame;
    }
}
