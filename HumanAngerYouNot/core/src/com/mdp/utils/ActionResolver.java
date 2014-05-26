package com.mdp.utils;

/**
 * @author Alex
 */
public interface ActionResolver {
    public void signIn();
    public void signOut();
    public boolean isSignedIn();
    public void startQuickGame();
    public void takeTurn();
    public void submitScore(int score);
    public void unlockAchievement(String achievementId);
    public void getLeaderboard();
    public void getAchievements();
}
