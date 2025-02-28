package dataaccess;

import model.GameData;
import model.UserData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    public final static HashMap<String, GameData> games = new HashMap<>();

    public void clear(){
        games.clear();
    }

    public void createGame(GameData gameData){
        games.put(gameData.gameName(), gameData);
    }
}
