package com.mdp.othello.utils;

import com.mdp.othello.OthelloGame;
import com.mdp.othello.screens.GameScreen;

/**
 * @author Alex
 */
public class DataCallback implements IDataCallback {

    private OthelloGame game;
    private ActionResolver actionResolver;

    public DataCallback(OthelloGame game, ActionResolver actionResolver) {
        this.game = game;
        this.actionResolver = actionResolver;
    }

    @Override
    public void proceedToMenuScreen(){
        game.proceedToMenuScreen();
    }

    @Override
    public void initializeGame(String data) {
        game.setScreen(new GameScreen(game, actionResolver, data));
    }

    @Override
    public void processGameData(String data) {
        ((GameScreen) game.getScreen()).setGameData(data);
    }

    @Override
    public void showIdleScreen() {
        ((GameScreen) game.getScreen()).setMyTurn(false);
    }
}
