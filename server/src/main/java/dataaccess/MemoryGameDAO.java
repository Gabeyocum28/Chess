package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    public final static HashMap<String, GameData> games = new HashMap<>();

    public void clear(){
        games.clear();
    }

    public void createGame(GameData gameData){
        games.put(gameData.gameName(), gameData);
    }
    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> allGames = games.values();
        return allGames;
    }
}
