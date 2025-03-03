package dataaccess;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import model.*;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    public final static HashMap<Integer, GameData> games = new HashMap<>();
    public final static HashMap<Integer, GameList> gamesOutput = new HashMap<>();

    public void clear(){
        games.clear();
        gamesOutput.clear();
    }

    public void createGame(GameData gameData){
        games.put(gameData.gameID(), gameData);
        GameList game = new GameList(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName());
        gamesOutput.put(game.gameID(), game);
    }
    public Collection<GameList> listGames() {
        return gamesOutput.values();
    }
    public void updateGame(JoinRequest joinRequest, AuthData authData) {
        GameData gameData = games.get(joinRequest.gameID());
        if(joinRequest.playerColor().equals("WHITE")){
            if(gameData.whiteUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
            games.remove(joinRequest.gameID());
            gamesOutput.remove(joinRequest.gameID());
            createGame(updateGame);
        }else{
            if(gameData.blackUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
            games.remove(joinRequest.gameID());
            gamesOutput.remove(joinRequest.gameID());
            createGame(updateGame);

        }


    }
}
