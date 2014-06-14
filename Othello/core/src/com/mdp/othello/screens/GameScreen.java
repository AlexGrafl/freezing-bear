package com.mdp.othello.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.game.Board;
import com.mdp.othello.game.CheckTurn;
import com.mdp.othello.game.PerformTurn;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.DefaultInputListener;




public class GameScreen extends AbstractScreen{
    public static int OFFSET;

    private ActionResolver actionResolver;
    private Board.BoardState myColor = Board.BoardState.WHITE, hisColor = Board.BoardState.BLACK;
    private String gameData;
    private boolean myTurn = false;
    private Board gameBoard;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TextButton endTurn;
    private boolean good = false;
    private Label invalidTurn;
    private Label blackScore;
    private Label whiteScore;

    public GameScreen(final OthelloGame game, final ActionResolver actionResolver, String data){
        super(game);
        this.actionResolver = actionResolver;
        if(data == null) {
            myTurn = true;
            myColor = Board.BoardState.BLACK;
        }
        if(myColor == Board.BoardState.BLACK) hisColor = Board.BoardState.WHITE;
        gameData = data;
        OFFSET = (int) (getWidth() - (320 * UNITSCALE)) / 2;
        gameBoard = new Board(new TmxMapLoader().load("map/board.tmx"));
        renderer = new OrthogonalTiledMapRenderer(gameBoard.getMap(), UNITSCALE);
        endTurn = new TextButton("End turn", getSkin());
        endTurn.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                invalidTurn.setText("");
                if(gameBoard.checkTurn(myColor)) {
                    gameBoard.performTurn(myColor);
                    gameData = gameBoard.getLastPiece();
                    myTurn = false;
                    endTurn.setDisabled(true);
                    updateLabels();
                    actionResolver.takeTurn(gameData);
                }else {
                    if(myTurn) {
                        gameBoard.unsetLastPiece();
                        invalidTurn.setText("Invalid Turn!");
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        endTurn.setWidth(OFFSET - 20);
        endTurn.setHeight(endTurn.getWidth());
        endTurn.setX((getWidth() - OFFSET + 3));
        endTurn.setY(10 * UNITSCALE);
        endTurn.getStyle().font.setScale(UNITSCALE);
        invalidTurn = new Label("", getSkin());
        invalidTurn.setColor(Color.RED);
        invalidTurn.setFontScale(UNITSCALE);
        invalidTurn.setPosition(getWidth() - OFFSET + 3, getHeight() - 30 * UNITSCALE);
        whiteScore = new Label("", getSkin());
        blackScore = new Label("", getSkin());
        whiteScore.setPosition(10 * UNITSCALE, getHeight() - 30 * UNITSCALE);
        blackScore.setPosition(10 * UNITSCALE, getHeight() - 60 * UNITSCALE);
        whiteScore.setFontScale(UNITSCALE);
        blackScore.setFontScale(UNITSCALE);
       // if(myColor == Board.BoardState.BLACK) blackScore
        getStage().addActor(endTurn);
        getStage().addActor(invalidTurn);
        getStage().addActor(blackScore);
        getStage().addActor(whiteScore);
        updateLabels();
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
        Gdx.gl.glUseProgram(0);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        getBatch().begin();
        gameBoard.draw(getBatch());
        if(isMyTurn()){
            //do stuff on my turn
            if(Gdx.input.isTouched()){
                if(good) gameBoard.unsetLastPiece();
                good = gameBoard.setPiece(myColor, Gdx.input.getX() - OFFSET, Gdx.input.getY());
            }
        }
        else{
            invalidTurn.setText("Wait ...");
        }
        getBatch().end();
        getStage().act();
        getStage().draw();
    }

    public boolean isMyTurn(){return myTurn;}
    public void setMyTurn(boolean myTurn){
        this.myTurn = myTurn;
        endTurn.setDisabled(false);
        invalidTurn.setText("");
    }

    public String getGameData() {
        return gameData;
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
        gameBoard.setLastPiece(gameData);
        gameBoard.checkTurn(hisColor);
        gameBoard.performTurn(hisColor);
        updateLabels();
    }

    public void updateLabels(){
        gameBoard.updateTotals();
        whiteScore.setText("White: " + Board.TOTAL_WHITE);
        blackScore.setText("Black: " + Board.TOTAL_BLACK);
    }
}
