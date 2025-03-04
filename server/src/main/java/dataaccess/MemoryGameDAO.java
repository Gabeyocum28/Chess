package dataaccess;

import exceptions.AlreadyTakenException;
import model.*;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    public final static HashMap<Integer, GameData> GAMES = new HashMap<>();
    public final static HashMap<Integer, GameList> GAMES_OUTPUT = new HashMap<>();

    public void clear(){
        GAMES.clear();
        GAMES_OUTPUT.clear();
    }

    public void createGame(GameData gameData){
        GAMES.put(gameData.gameID(), gameData);
        GameList game = new GameList(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName());
        GAMES_OUTPUT.put(game.gameID(), game);
    }
    public Collection<GameList> listGames() {
        return GAMES_OUTPUT.values();
    }
    public void updateGame(JoinRequest joinRequest, AuthData authData) {
        GameData gameData = GAMES.get(joinRequest.gameID());
        if(joinRequest.playerColor().equals("WHITE")){
            if(gameData.whiteUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(),
                    gameData.gameName(), gameData.game());
            GAMES.remove(joinRequest.gameID());
            GAMES_OUTPUT.remove(joinRequest.gameID());
            createGame(updateGame);
        }else{
            if(gameData.blackUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(),
                    gameData.gameName(), gameData.game());
            GAMES.remove(joinRequest.gameID());
            GAMES_OUTPUT.remove(joinRequest.gameID());
            createGame(updateGame);

        }


    }
}
