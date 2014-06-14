package com.mdp.othello.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.screens.GameScreen;
/**
 * @author Alex
 */
public class Board{
    public enum BoardState{
        EMPTY,
        BLACK,
        WHITE
    }
    private Texture whitePiece;
    private Texture blackPiece;
    private TiledMap map;
    private BoardState[][] boardData = new BoardState[8][8];
    private int lastX, lastY;
    private PerformTurn performTurn;
    private boolean[] validTurns;
    public static int TOTAL_WHITE = 0, TOTAL_BLACK = 0;

    public Board(TiledMap map){
        this.map = map;
        this.performTurn = new PerformTurn();
        this.whitePiece = new Texture(Gdx.files.internal("map/pieceWhite_single10.png"));
        this.blackPiece = new Texture(Gdx.files.internal("map/pieceBlack_single10.png"));
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardData[i][j] = BoardState.EMPTY;
            }
        }
        boardData[3][3] = boardData[4][4] = BoardState.BLACK;
        boardData[3][4] = boardData[4][3] = BoardState.WHITE;
    }

    public TiledMap getMap(){ return map;}

    public void draw(SpriteBatch batch) {
        float piecesize = 32 * GameScreen.UNITSCALE;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(boardData[i][j] == BoardState.WHITE) batch.draw(whitePiece, ((i + 1) * 32 * GameScreen.UNITSCALE)
                                + GameScreen.OFFSET, ((j + 1) * 32 * GameScreen. UNITSCALE)
                                , piecesize, piecesize);
                if(boardData[i][j] == BoardState.BLACK) batch.draw(blackPiece, ((i + 1) * 32 * GameScreen.UNITSCALE)
                                + GameScreen.OFFSET, ((j + 1) * 32 * GameScreen.UNITSCALE)
                                , piecesize, piecesize);
            }
        }
    }

    public boolean setPiece(BoardState color, int x, int y){
        int transformedX, transformedY;
        transformedX = (int) (x / (32 * GameScreen.UNITSCALE)) - 1;
        transformedY = 9 - (int) (y / (32 * GameScreen.UNITSCALE)) - 1;
        if(transformedX < 0 || transformedX > 7 || transformedY < 0 || transformedY > 7) return false;
        if(boardData[transformedX][transformedY] == BoardState.EMPTY){
            boardData[transformedX][transformedY] = color;
            lastX = transformedX;
            lastY = transformedY;
            return true;
        }
        return false;
    }

    public boolean checkTurn(BoardState myColor){
        CheckTurn checkTurn = new CheckTurn(this.boardData, myColor);
        validTurns = checkTurn.check(lastX, lastY);
        for(boolean value : validTurns){
            if(value) return true;
        }
        return false;
    }


    public void performTurn(BoardState myColor){
        this.boardData = performTurn.perform(this.boardData, lastX, lastY, validTurns, myColor);
    }

    public void unsetLastPiece(){
        boardData[lastX][lastY] = BoardState.EMPTY;
    }

    public String getLastPiece(){
        return lastX+"-"+lastY;
    }

    public void setLastPiece(String data){
        String[] strings = data.split("-");
        lastX = Integer.parseInt(strings[0]);
        lastY = Integer.parseInt(strings[1]);
    }

    public void updateTotals(){
        TOTAL_BLACK = TOTAL_WHITE = 0;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(boardData[i][j] == BoardState.BLACK) TOTAL_BLACK++;
                if(boardData[i][j] == BoardState.WHITE) TOTAL_WHITE++;
            }
        }
    }

}
