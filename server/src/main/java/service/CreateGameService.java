package service;

import dataaccess.MemoryGameDAO;
import model.GameData;

public class CreateGameService {
    public static GameData createGame(String authData, GameData gameID){

        GameData newGame = new MemoryGameDAO().createGame();

        return newGame;
    }
}
