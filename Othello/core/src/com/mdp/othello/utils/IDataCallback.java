package com.mdp.othello.utils;

/**
 * @author Alex
 */
public interface IDataCallback {

    public void proceedToMenuScreen();
    public void initializeGame(String data);
    public void processGameData(String data);
    public void showIdleScreen();

}
