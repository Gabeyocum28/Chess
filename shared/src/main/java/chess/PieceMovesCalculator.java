package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    public Collection<ChessMove> pieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP) {
            BishopMovesCalculator bishopMovesCalc = new BishopMovesCalculator();
            return bishopMovesCalc.bishopMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING) {
            KingMovesCalculator kingMovesCalc = new KingMovesCalculator();
            return kingMovesCalc.kingMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN) {
            QueenMovesCalculator queenMovesCalc = new QueenMovesCalculator();
            return queenMovesCalc.queenMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalculator knightMovesCalc = new KnightMovesCalculator();
            return knightMovesCalc.knightMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK) {
            RookMovesCalculator rookMovesCalc = new RookMovesCalculator();
            return rookMovesCalc.rookMovesCalculator(board,myPosition);
        }
        if(board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
            PawnMovesCalculator pawnMovesCalc = new PawnMovesCalculator();
            return pawnMovesCalc.pawnMovesCalculator(board, myPosition);
        }
        return new ArrayList<>();
    }


}