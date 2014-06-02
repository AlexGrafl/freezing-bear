package com.mdp.othello.utils;

/**
 * @author Alex
 */
public interface ActionResolver {
    public void signIn();
    public void signOut();
    public boolean isSignedIn();
    public void startGame();
    public void takeTurn(String data);
    public void submitScore(int score);
    public void unlockAchievement(String achievementId);
    public void showLeaderboard();
    public void showAchievements();
    public void addCallback(IDataCallback dataCallback);
}
