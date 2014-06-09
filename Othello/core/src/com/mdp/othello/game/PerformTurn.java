package com.mdp.othello.game;

import com.mdp.othello.game.Board.BoardState;

public class PerformTurn {

    private BoardState yourColor;
    private BoardState[][] playBoard;

    public PerformTurn(BoardState yourcolor) {
        this.yourColor = yourcolor;
    }

    public BoardState[][] perform(BoardState[][] playboard, int posX, int posY, boolean[] validTurns){
        this.playBoard = playboard;

        if(validTurns[0]) right(posX + 1, posY);
        if(validTurns[1]) left(posX - 1, posY);
        if(validTurns[2]) up(posX, posY + 1);
        if(validTurns[3]) down(posX, posY - 1);
        if(validTurns[4]) upRight(posX + 1, posY + 1);
        if(validTurns[5]) downRight(posX + 1, posY - 1);
        if(validTurns[6]) upLeft(posX - 1, posY + 1);
        if(validTurns[7]) downLeft(posX - 1, posY - 1);

        return this.playBoard;

    }

    private void right(int posX, int posY){
        for(int i = posX;i < 8;i++){
            if(playBoard[i][posY] != yourColor && playBoard[i][posY] != BoardState.EMPTY) playBoard[i][posY] = yourColor;
            if(playBoard[i][posY] == yourColor) return;
        }
    }
    private void left(int posX, int posY){
        for(int i = posX;i >= 0;i--){
            if(playBoard[i][posY] != yourColor && playBoard[i][posY] != BoardState.EMPTY) playBoard[i][posY] = yourColor;
            if(playBoard[i][posY] == yourColor) return;
        }
    }
    private void up(int posX, int posY){
        for(int j = posY;j >= 0; j--){
            if(playBoard[posX][j] != yourColor && playBoard[posX][j] != BoardState.EMPTY) playBoard[posX][j] = yourColor;
            if(playBoard[posX][j] == yourColor) return;
        }
    }
    private void down(int posX, int posY){
        for(int j = posY;j < 8; j++){
            if(playBoard[posX][j] != yourColor && playBoard[posX][j] != BoardState.EMPTY) playBoard[posX][j] = yourColor;
            if(playBoard[posX][j] == yourColor) return;
        }
    }

    //diagonalRight
    private void upRight(int posX, int posY){
        for(int i = posX;i < 8; i++){
            for(int j = posY;j >= 0; j--) {
                if (playBoard[i][j] != yourColor && playBoard[i][j] != BoardState.EMPTY) playBoard[i][j] = yourColor;
                if (playBoard[i][j] == yourColor) return;
            }
        }
    }

    private void downRight(int posX, int posY){
        for(int i = posX;i < 8; i++){
            for(int j = posY;j < 8; j++) {
                if (playBoard[i][j] != yourColor && playBoard[i][j] != BoardState.EMPTY) playBoard[i][j] = yourColor;
                if (playBoard[i][j] == yourColor) return;
            }
        }
    }

    //diagonalLeft
    private void upLeft(int posX, int posY){
        for(int i = posX;i >= 0; i--){
            for(int j = posY;j >= 0; j--) {
                if (playBoard[i][j] != yourColor && playBoard[i][j] != BoardState.EMPTY) playBoard[i][j] = yourColor;
                if (playBoard[i][j] == yourColor) return;
            }
        }
    }
    private void downLeft(int posX, int posY){
        for(int i = posX;i >= 0; i--){
            for(int j = posY;j < 8; j++) {
                if (playBoard[i][j] != yourColor && playBoard[i][j] != BoardState.EMPTY) playBoard[i][j] = yourColor;
                if (playBoard[i][j] == yourColor) return;
            }
        }
    }

}
