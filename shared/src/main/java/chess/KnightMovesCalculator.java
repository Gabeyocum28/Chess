package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {
    public KnightMovesCalculator() {
    }

    public Collection<ChessMove> knightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
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
                row++;
                column++;
            }
            else if(i == 1){
                row++;
                row++;
                column--;
            }
            else if(i == 2){
                row--;
                column++;
                column++;
            }
            else if (i == 3){
                row++;
                column++;
                column++;
            }
            else if(i == 4){
                row--;
                row--;
                column++;
            }
            else if(i == 5){
                row--;
                row--;
                column--;
            }
            else if(i == 6){
                row--;
                column--;
                column--;
            }
            else if(i == 7){
                row++;
                column--;
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
            i++;
            start = 0;

        }
        return possibleMoves;
    }
}
