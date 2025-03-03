package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    public QueenMovesCalculator() {
    }

    public Collection<ChessMove> queenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        int start = 0;
        int i = 0;

        while(true){
            if(start == 0){
                row = myPosition.getRow();
                column = myPosition.getColumn();
                start = 1;
            }

            if(i == 0){
                row++;
                column++;
            }
            else if(i == 1){
                row++;
                column--;
            }
            else if(i == 2){
                row--;
                column++;
            }
            else if (i == 3){
                row--;
                column--;
            }
            else if(i == 4){
                row++;
            }
            else if(i == 5){
                row--;
            }
            else if(i == 6){
                column++;
            }
            else if(i == 7){
                column--;
            }
            else{
                break;
            }
            if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                start = 0;
                i++;
                continue;
            }
            ChessPosition checkPosition = new ChessPosition(row, column);
            if (board.getPiece(checkPosition) != null) {
                if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, checkPosition, null);
                    possibleMoves.add(move);
                    start = 0;
                    i++;
                    continue;

                }
                else {
                    start = 0;
                    i++;
                    continue;
                }
            }
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessMove move = new ChessMove(myPosition, newPosition, null);
            possibleMoves.add(move);

        }
        return possibleMoves;
    }
}
