package com.mdp.othello.game;

import com.badlogic.gdx.Gdx;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.game.Board.BoardState;


public class CheckTurn {

    private BoardState[][] board;
    private BoardState yourColor;

    public CheckTurn(BoardState[][] playBoard, BoardState yourcolor){
        this.board = playBoard;
        this.yourColor = yourcolor;
    }

    public boolean[] check(int posX, int posY){
        boolean[] validTurns;
        validTurns = new boolean[] {
            checkRight(posX + 1,posY),
            checkLeft(posX - 1, posY),
            checkUp(posX, posY + 1),
            checkDown(posX, posY - 1),
            checkUpRight(posX + 1, posY + 1),
            checkDownRight(posX + 1, posY - 1),
            checkUpLeft(posX - 1, posY + 1),
            checkDownLeft(posX - 1, posY - 1)
        };
        return validTurns;
    }

    public boolean checkRight(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX; i < 8; i++){
            if(board[i][posY] == BoardState.EMPTY) return false;
            if(isValid && board[i][posY] == yourColor) return true;
            if(board[i][posY] != yourColor ) isValid = true;
        }
        return false;
    }
    public boolean checkLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i >= 0;i--){
            if(board[i][posY] == BoardState.EMPTY) return false;
            if(isValid && board[i][posY] == yourColor) return true;
            if(board[i][posY] != yourColor) isValid = true;
        }
        return false;
    }
    public boolean checkDown(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int j = posY ; j >= 0;j--){
            if(board[posX][j] == BoardState.EMPTY) return false;
            if(isValid && board[posX][j] == yourColor) return true;
            if(board[posX][j] != yourColor) isValid = true;
        }
        return false;
    }
    public boolean checkUp(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int j = posY;j < 8;j++){
            if(board[posX][j] == BoardState.EMPTY) return false;
            if(isValid && board[posX][j] == yourColor) return true;
            if(board[posX][j] != yourColor) isValid = true;
        }
        return false;
    }

    //diagonal right
    public boolean checkUpRight(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX, j = posY;i < 8 && j < 8;i++, j++){
            if(board[i][j] == BoardState.EMPTY) return false;
            if (isValid && board[i][j] == yourColor) return true;
            if (board[i][j] != yourColor ) isValid = true;
        }
        return false;
    }
    public boolean checkDownRight(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX, j = posY;i < 8 && j >= 0;i++, j--){
            if(board[i][j] == BoardState.EMPTY) return false;
            if (isValid && board[i][j] == yourColor) return true;
            if (board[i][j] != yourColor) isValid = true;
        }
        return false;
    }

    //diagonal left
    public boolean checkUpLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX, j = posY;i >= 0 && j < 8;i--, j++){
            if(board[i][j] == BoardState.EMPTY) return false;
            if (isValid && board[i][j] == yourColor) return true;
            if (board[i][j] != yourColor) isValid = true;
        }
        return false;
    }
    public boolean checkDownLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX, j = posY;i >= 0 && j >= 0;i--, j--){
            if(board[i][j] == BoardState.EMPTY) return false;
            if (isValid && board[i][j] == yourColor) return true;
            if (board[i][j] != yourColor) isValid = true;
        }
        return false;
    }

}
