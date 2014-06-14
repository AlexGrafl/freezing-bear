package com.mdp.othello.android;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.GameHelper;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.IDataCallback;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements OnInvitationReceivedListener,
        OnTurnBasedMatchUpdateReceivedListener,ActionResolver,
        GameHelper.GameHelperListener{
    final static int RC_SELECT_PLAYERS = 1337;
    final static int RC_INVITATION_ACCEPTED = 1234;
    private GameHelper gameHelper;
    private IDataCallback dataCallback;
    private TurnBasedMatch turnBasedMatch = null;
    private MatchInitiatedCallback matchInitiatedCallback;
    private UpdateMatchCallback updateMatchCallback;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        config.useGLSurfaceView20API18 = true;
        int error = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getContext());
        if(error != ConnectionResult.SUCCESS ) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(error, this, 11110);
            dialog.show();
        }else{
            initialize(new OthelloGame(this), config);
        }
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setConnectOnStart(true);
        gameHelper.setShowErrorDialogs(true);
        gameHelper.setup(this);
    }

    @Override
    public void onSignInFailed() {
        log(OthelloGame.LOG, "Sign in failed!");
        gameHelper.showFailureDialog();
    }

    @Override
    public void onSignInSucceeded() {
        log(OthelloGame.LOG, "Sign in succeeded");
        this.turnBasedMatch = gameHelper.getTurnBasedMatch();
        Games.Invitations.registerInvitationListener(gameHelper.getApiClient(), this);
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
    public void startGame() {
        Intent intent =
                Games.TurnBasedMultiplayer.getSelectOpponentsIntent(gameHelper.getApiClient(), 1, 1, true);
        startActivityForResult(intent, RC_SELECT_PLAYERS);

    }

    @Override
    public void takeTurn(String data) {
        Games.TurnBasedMultiplayer.takeTurn(gameHelper.getApiClient(),
                getTurnBasedMatch().getMatchId(), data.getBytes(Charset.forName("UTF-16")),
                getNextParticipantId()).setResultCallback(updateMatchCallback);
    }

    @Override
    public void submitScore(int score) {
        //add score to leaderboard
        Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                getString(R.string.leaderboard_total_pieces_difference), score);
    }

    @Override
    public void unlockAchievement(String achievementId) {
        //unlock achviements
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
    }

    @Override
    public void showLeaderboard() {
        //retrieve leaderboard and display shit
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                getString(R.string.leaderboard_total_pieces_difference)), 100);
    }

    @Override
    public void showAchievements() {
        //retireve unlocked achievements
        startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
    }

    @Override
    public void addCallback(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
        updateMatchCallback = new UpdateMatchCallback(dataCallback);
        matchInitiatedCallback = new MatchInitiatedCallback(dataCallback);
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
        if (request == RC_SELECT_PLAYERS) {
            if (response != RESULT_OK) {
                // user canceled
                return;
            }

            // get the invitee list
            final ArrayList<String> invitees =
                    data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // get auto-match criteria
            Bundle autoMatchCriteria = null;
            int minAutoMatchPlayers = data.getIntExtra(
                    Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers
                    = data.getIntExtra(
                    Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria
                        = RoomConfig.createAutoMatchCriteria(
                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(autoMatchCriteria).build();

            // kick the match off
            Games.TurnBasedMultiplayer
                    .createMatch(gameHelper.getApiClient(), tbmc)
                    .setResultCallback(matchInitiatedCallback);
        }

        if(request == RC_INVITATION_ACCEPTED){
            if (response != RESULT_OK) {
                // user canceled
                return;
            }
        }
    }

    @Override
    public void onInvitationReceived(Invitation invitation) {
        startActivityForResult(
                Games.Invitations.getInvitationInboxIntent(gameHelper.getApiClient()), RC_INVITATION_ACCEPTED);
    }

    @Override
    public void onInvitationRemoved(String s) {

    }

    @Override
    public void onTurnBasedMatchReceived(TurnBasedMatch turnBasedMatch) {
        this.turnBasedMatch = turnBasedMatch;
        if(turnBasedMatch.getStatus() == TurnBasedMatch.MATCH_STATUS_ACTIVE){
            if(turnBasedMatch.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN){
                dataCallback.processGameData(new String(turnBasedMatch.getData()));
            }
        }
    }

    @Override
    public void onTurnBasedMatchRemoved(String s) {

    }


    private TurnBasedMatch getTurnBasedMatch(){
        if(turnBasedMatch == null){
            turnBasedMatch = gameHelper.getTurnBasedMatch();
        }
        if(turnBasedMatch == null){
            turnBasedMatch = matchInitiatedCallback.getTurnBasedMatch();
        }
        if(turnBasedMatch == null){
            turnBasedMatch = updateMatchCallback.getTurnBasedMatch();
        }
        if(turnBasedMatch == null){
           // turnBasedMatch =
        }
        return turnBasedMatch;
    }

    public String getNextParticipantId() {

        String playerId = Games.Players.getCurrentPlayerId(gameHelper.getApiClient());
        String myParticipantId = getTurnBasedMatch().getParticipantId(playerId);

        ArrayList<String> participantIds = getTurnBasedMatch().getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (turnBasedMatch.getAvailableAutoMatchSlots() <= 0) {
            // You've run out of automatch slots, so we start over.
            return participantIds.get(0);
        } else {
            // You have not yet fully automatched, so null will find a new
            // person to play against.
            return null;
        }
    }


}
