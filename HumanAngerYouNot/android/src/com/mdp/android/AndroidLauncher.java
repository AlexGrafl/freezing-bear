package com.mdp.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.mdp.HumanAngerYouNot;
import com.mdp.utils.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, GameHelper.GameHelperListener{

    private GameHelper gameHelper;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		gameHelper = new GameHelper(this);
        gameHelper.setup(this, GameHelper.CLIENT_GAMES);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new HumanAngerYouNot(), config);
	}

    @Override
    public void onSignInFailed() {
        gameHelper.showAlert("SignIn-Error", "SignIn Failed");
    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable(){
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ex) {
            log("AndroidLauncher", "SignIn Error");
        }
    }

    @Override
    public void signOut() {
        gameHelper.signOut();
    }

    @Override
    public boolean isSignedIn(){
        return gameHelper.isSignedIn();
    }

    @Override
    public void startQuickGame() {
     //auto matchmaking
    }

    @Override
    public void takeTurn() {
        //https://developers.google.com/games/services/android/turnbasedMultiplayer#taking_a_turn
    }

    @Override
    public void submitScore(int score) {
        //add score to leaderboard
        //TODO: UPDATE KEY
        gameHelper.getGamesClient().submitScore("CgkI6574wJUXEAIQBw", score);
    }

    @Override
    public void unlockAchievement(String achievementId) {
        //unlock achviements
        gameHelper.getGamesClient().unlockAchievement(achievementId);
    }

    @Override
    public void getLeaderboard() {
        //retrieve leaderboard and display shit
        startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent("CgkI6574wJUXEAIQBw"), 100);
    }

    @Override
    public void getAchievements() {
        //retireve unlocked achievements
        startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
    }

    @Override
    public void onStart(){
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        gameHelper.onActivityResult(request, response, data);
    }
}
