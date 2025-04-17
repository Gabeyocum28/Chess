package websocket.commands;

import chess.ChessMove;

public class MakeMoveHelper extends UserGameCommand{

    private ChessMove move;

    public MakeMoveHelper(CommandType commandType, String authToken, Integer gameID, ChessMove move) {
        super(commandType, authToken, gameID);
        this.move = move;
    }

    public ChessMove getMove(){
        return move;
    }
}
