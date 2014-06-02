package com.mdp.othello.screens;

import com.mdp.othello.OthelloGame;
import com.mdp.othello.utils.ActionResolver;


public class GameScreen extends AbstractScreen{

    private ActionResolver actionResolver;
    private boolean isBlack = false;
    private String gameData;
    private boolean myTurn = false;

    public GameScreen(OthelloGame game, ActionResolver actionResolver, String data){
        super(game);
        this.actionResolver = actionResolver;
        isBlack = myTurn = data == null;
        gameData = data;
    }

    @Override
    public void render(float delta){
        super.render(delta);
        if(myTurn){
            //do stuff on my turn
        }
        else{
            //disable everything
        }

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
