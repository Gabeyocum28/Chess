package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;

public class CreateGameService {
    public static GameData createGame(String authRequest, GameData gameName) throws DataAccessException, SQLException {
        AuthData authData = new SQLAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        GameData newGame = new GameData(0, gameName.whiteUsername(), gameName.blackUsername(), gameName.gameName(), new ChessGame());
        Integer id = new SQLGameDAO().createGame(newGame);
        GameData game = new GameData(id, newGame.whiteUsername(), newGame.blackUsername(), newGame.gameName(), newGame.game());

        return game;
    }
}
