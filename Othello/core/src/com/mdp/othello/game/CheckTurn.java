package com.mdp.othello.game;

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
        for(int i = posX;i < 8;i++){
            if(board[i][posY] != yourColor && board[i][posY] != BoardState.EMPTY) isValid = true;
            if(isValid && board[i][posY] == yourColor) return isValid;
        }
        return false;
    }
    public boolean checkLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i >= 0;i--){
            if(board[i][posY] != yourColor && board[i][posY] != BoardState.EMPTY) isValid = true;
            if(isValid && board[i][posY] == yourColor) return isValid;
        }
        return false;
    }
    public boolean checkUp(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int j = posY ; j >= 0;j--){
            if(board[posX][j] != yourColor && board[posX][j] != BoardState.EMPTY) isValid = true;
            if(isValid && board[posX][j] == yourColor) return isValid;
        }
        return false;
    }
    public boolean checkDown(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int j = posY;j < 8;j++){
            if(board[posX][j] != yourColor && board[posX][j] != BoardState.EMPTY) isValid = true;
            if(isValid && board[posX][j] == yourColor) return isValid;
        }
        return false;
    }

    //diagonal right
    public boolean checkUpRight(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i < 8;i++){
            for(int j = posY; j >= 0; j--) {
                if (board[i][j] != yourColor && board[i][j] != BoardState.EMPTY) isValid = true;
                if (isValid && board[i][j] == yourColor) return isValid;
            }
        }
        return false;
    }
    public boolean checkDownRight(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i < 8;i++){
            for(int j = posY; j < 8; j++) {
                if (board[i][j] != yourColor && board[i][j] != BoardState.EMPTY) isValid = true;
                if (isValid && board[i][j] == yourColor) return isValid;
            }
        }
        return false;
    }

    //diagonal left
    public boolean checkUpLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i >= 0;i--){
            for(int j = posY; j >= 0; j--) {
                if (board[i][j] != yourColor && board[i][j] != BoardState.EMPTY) isValid = true;
                if (isValid && board[i][j] == yourColor) return isValid;
            }
        }
        return false;
    }
    public boolean checkDownLeft(int posX, int posY){
        boolean isValid = false;
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7) return false;
        for(int i = posX;i >= 0;i--){
            for(int j = posY; j < 8; j++) {
                if (board[i][j] != yourColor && board[i][j] != BoardState.EMPTY) isValid = true;
                if (isValid && board[i][j] == yourColor) return isValid;
            }
        }
        return false;
    }

}
