package com.mdp.othello.utils;

import com.mdp.othello.OthelloGame;
import com.mdp.othello.screens.GameScreen;

/**
 * @author Alex
 */
public class DataCallback implements IDataCallback {

    private OthelloGame game;
    private boolean isGameSetUp = false;
    private GameScreen gameScreen;

    public DataCallback(OthelloGame game) {
        this.game = game;
    }

    @Override
    public void initializeGame() {
        isGameSetUp = true;
        gameScreen = game.proceedToGame(null);
    }

    @Override
    public void processGameData(String data) {
        if(!isGameSetUp){
            isGameSetUp = true;
            gameScreen = game.proceedToGame(data);
        }
        gameScreen.setGameData(data);
        gameScreen.setMyTurn(true);

    }

    @Override
    public void showIdleScreen() {
        gameScreen.setMyTurn(false);
    }
}
