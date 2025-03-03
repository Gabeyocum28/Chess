package dataaccess;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import model.*;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    public final static HashMap<Integer, GameData> Games = new HashMap<>();
    public final static HashMap<Integer, GameList> GamesOutput = new HashMap<>();

    public void clear(){
        Games.clear();
        GamesOutput.clear();
    }

    public void createGame(GameData gameData){
        Games.put(gameData.gameID(), gameData);
        GameList game = new GameList(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName());
        GamesOutput.put(game.gameID(), game);
    }
    public Collection<GameList> listGames() {
        return GamesOutput.values();
    }
    public void updateGame(JoinRequest joinRequest, AuthData authData) {
        GameData gameData = Games.get(joinRequest.gameID());
        if(joinRequest.playerColor().equals("WHITE")){
            if(gameData.whiteUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
            Games.remove(joinRequest.gameID());
            GamesOutput.remove(joinRequest.gameID());
            createGame(updateGame);
        }else{
            if(gameData.blackUsername() != null){
                throw new AlreadyTakenException("Error: already taken");
            }
            GameData updateGame = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
            Games.remove(joinRequest.gameID());
            GamesOutput.remove(joinRequest.gameID());
            createGame(updateGame);

        }


    }
}
