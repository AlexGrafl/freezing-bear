package com.mdp.othello.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.game.Board;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.DefaultInputListener;

import javax.xml.soap.Text;
import java.awt.*;


public class GameScreen extends AbstractScreen{
    public static float UNITSCALE;
    public static int OFFSET;

    private ActionResolver actionResolver;
    private Board.BoardState myColor = Board.BoardState.WHITE;
    private String gameData;
    private boolean myTurn = false;
    private Board gameBoard;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TextButton endTurn;

    public GameScreen(final OthelloGame game, final ActionResolver actionResolver, String data){
        super(game);
        this.actionResolver = actionResolver;
        if(data == null) {
            myTurn = true;
            myColor = Board.BoardState.BLACK;
        }
        gameData = data;
        UNITSCALE = getHeight() / 320f;
        OFFSET = (int) (getWidth() - (320 * UNITSCALE)) / 2;
        gameBoard = new Board(new TmxMapLoader().load("map/board.tmx"), myColor);
        renderer = new OrthogonalTiledMapRenderer(gameBoard.getMap(), UNITSCALE);
        endTurn = new TextButton("End turn", getSkin());
        endTurn.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(gameBoard.checkTurn()) {
                    gameBoard.performTurn();
                    gameData = gameBoard.getLastPiece();
                    actionResolver.takeTurn(gameData);
                }else {
                    gameBoard.unsetLastPiece();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        endTurn.setWidth(endTurn.getWidth());
        endTurn.setHeight(endTurn.getWidth());
        endTurn.setX((getWidth() - OFFSET + 3));
        endTurn.setY(10 * UNITSCALE);
        getStage().addActor(endTurn);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, getWidth(), getHeight());
        camera.translate(- OFFSET, 0);
        camera.update();
        Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        getBatch().begin();
        gameBoard.draw(getBatch());
        if(myTurn){
            //do stuff on my turn
            if(Gdx.input.isTouched()){
                boolean good = gameBoard.setPiece(myColor, Gdx.input.getX() - OFFSET, Gdx.input.getY());

            }
        }
        else{
            //disable everything
        }
        getBatch().end();
        getStage().act();
        getStage().draw();
    }

    public boolean isMyTurn(){return myTurn;}
    public void setMyTurn(boolean myTurn){this.myTurn = myTurn;}

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
}
