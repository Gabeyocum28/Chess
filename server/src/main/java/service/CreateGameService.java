package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

public class CreateGameService {
    static int id = 1;
    public static GameData createGame(String authRequest, GameData gameName){
        AuthData authData = new MemoryAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        id++;
        GameData newGame = new GameData(id, gameName.whiteUsername(), gameName.blackUsername(), gameName.gameName(), new ChessGame());
        new MemoryGameDAO().createGame(newGame);

        return newGame;
    }
}
