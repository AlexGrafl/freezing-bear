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
    private BoardState myColor;
    private boolean[] validTurns;

    public Board(TiledMap map, BoardState myColor){
        this.map = map;
        this.myColor = myColor;
        this.performTurn = new PerformTurn(myColor);
        this.whitePiece = new Texture(Gdx.files.internal("map/pieceWhite_single10.png"));
        this.blackPiece = new Texture(Gdx.files.internal("map/pieceBlack_single10.png"));
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardData[i][j] = BoardState.EMPTY;
            }
        }
        boardData[3][3] = boardData[4][4] = BoardState.BLACK;
        boardData[3][4] = boardData[4][3] = BoardState.WHITE;
        boardData[3][5] = boardData[4][3] = BoardState.WHITE;
        boardData[3][6] = boardData[4][1] = BoardState.WHITE;
        boardData[4][5] = boardData[4][6] = BoardState.WHITE;
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
        Gdx.app.log(OthelloGame.LOG, "X: "+x+" transformed: " + transformedX + "\nY: "+y+" tranformed: "+transformedY);
        if(transformedX < 0 || transformedX > 7 || transformedY < 0 || transformedY > 7) return false;
        if(boardData[transformedX][transformedY] == BoardState.EMPTY){
            boardData[transformedX][transformedY] = color;
            lastX = transformedX;
            lastY = transformedY;
            return true;
        }
        return false;
    }

    public boolean checkTurn(){
        CheckTurn checkTurn = new CheckTurn(this.boardData, myColor);
        validTurns = checkTurn.check(lastX, lastY);
        for(boolean value : validTurns){
            if(value) return true;
        }
        return false;
    }


    public void performTurn(){
        this.boardData = performTurn.perform(this.boardData, lastX, lastY, validTurns);
    }

    public void unsetLastPiece(){
        boardData[lastX][lastY] = BoardState.EMPTY;
    }

    public String getLastPiece(){
        return lastX+"$"+lastY;
    }

    public void setLastPiece(String data){
        String[] strings = data.split("$");
        lastX = Integer.parseInt(strings[0]);
        lastY = Integer.parseInt(strings[1]);

    }

}
