package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator {

    public Collection<ChessMove> PieceMovesCalculator (ChessBoard board, ChessPosition myPosition) {
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP) {
            BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
            return bishopMovesCalculator.pieceMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING) {
            KingMovesCalculator kingMovesCalculator = new KingMovesCalculator();
            return kingMovesCalculator.pieceMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN) {
            QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator();
            return queenMovesCalculator.pieceMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
            return knightMovesCalculator.pieceMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK) {
            RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
            return rookMovesCalculator.pieceMovesCalculator(board,myPosition);
        }
        return new ArrayList<>();
    }


}