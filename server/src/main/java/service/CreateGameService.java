package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

public class CreateGameService {
    static int ID = 1;
    public static GameData createGame(String authRequest, GameData gameName){
        AuthData authData = new MemoryAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        ID++;
        GameData newGame = new GameData(ID, gameName.whiteUsername(), gameName.blackUsername(), gameName.gameName(), new ChessGame());
        new MemoryGameDAO().createGame(newGame);

        return newGame;
    }
}
