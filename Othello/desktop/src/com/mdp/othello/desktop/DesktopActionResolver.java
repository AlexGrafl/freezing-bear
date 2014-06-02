package com.mdp.othello.desktop;

import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.IDataCallback;

/**
 * @author Alex
 */
public class DesktopActionResolver implements ActionResolver {
    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return true;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void takeTurn(String data) {

    }

    @Override
    public void submitScore(int score) {

    }

    @Override
    public void unlockAchievement(String achievementId) {

    }

    @Override
    public void showLeaderboard() {

    }

    @Override
    public void showAchievements() {

    }

    @Override
    public void addCallback(IDataCallback dataCallback) {

    }
}
