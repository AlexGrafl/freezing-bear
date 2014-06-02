package com.mdp.othello.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.ResultCallback;
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
        OnTurnBasedMatchUpdateReceivedListener,
        ActionResolver, GameHelper.GameHelperListener, ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>{
    final static int RC_SELECT_PLAYERS = 1337;
    final static int RC_INVITATION_ACCEPTED = 1234;
    private GameHelper gameHelper;
    private IDataCallback dataCallback;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setup(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        initialize(new OthelloGame(this), config);
    }

    @Override
    public void onSignInFailed() {
        gameHelper.showFailureDialog();
    }

    @Override
    public void onSignInSucceeded() {
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
        //https://developers.google.com/games/services/android/turnbasedMultiplayer#taking_a_turn
        Games.TurnBasedMultiplayer.takeTurn(gameHelper.getApiClient(),
                gameHelper.getTurnBasedMatch().getMatchId(), data.getBytes(Charset.forName("UTF-16")),
                getNextParticipantId()).setResultCallback(this);
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
                    .setResultCallback(new MatchInitiatedCallback(dataCallback));
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
        startActivityForResult(Games.Invitations.getInvitationInboxIntent(gameHelper.getApiClient()), RC_INVITATION_ACCEPTED);
    }

    @Override
    public void onInvitationRemoved(String s) {

    }

    @Override
    public void onTurnBasedMatchReceived(TurnBasedMatch turnBasedMatch) {
        if(turnBasedMatch.getStatus() == TurnBasedMatch.MATCH_STATUS_ACTIVE){
            if(turnBasedMatch.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN){
                dataCallback.processGameData(new String(turnBasedMatch.getData()));
            }
        }
    }

    @Override
    public void onTurnBasedMatchRemoved(String s) {

    }

    public String getNextParticipantId() {

        String playerId = Games.Players.getCurrentPlayerId(gameHelper.getApiClient());
        String myParticipantId = gameHelper.getTurnBasedMatch().getParticipantId(playerId);

        ArrayList<String> participantIds = gameHelper.getTurnBasedMatch().getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (gameHelper.getTurnBasedMatch().getAvailableAutoMatchSlots() <= 0) {
            // You've run out of automatch slots, so we start over.
            return participantIds.get(0);
        } else {
            // You have not yet fully automatched, so null will find a new
            // person to play against.
            return null;
        }
    }

    @Override
    public void onResult(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
        if (!updateMatchResult.getStatus().isSuccess()) {
            return;
        }

        TurnBasedMatch match = updateMatchResult.getMatch();
        if (match.getData() != null) {
            dataCallback.showIdleScreen();
            return;
        }
        dataCallback.initializeGame(new String(match.getData()));
        dataCallback.processGameData(new String(match.getData()));
    }
}
