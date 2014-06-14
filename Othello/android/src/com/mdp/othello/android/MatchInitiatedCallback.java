package com.mdp.othello.android;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.mdp.othello.utils.IDataCallback;

/**
 * @author Alex
 */
public class MatchInitiatedCallback implements ResultCallback<InitiateMatchResult> {
    private IDataCallback dataCallback;
    private TurnBasedMatch turnBasedMatch;
    public MatchInitiatedCallback(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void onResult(InitiateMatchResult initiateMatchResult) {
        if (initiateMatchResult.getStatus().getStatusCode() != GamesStatusCodes.STATUS_OK) {
            return;
        }

        turnBasedMatch = initiateMatchResult.getMatch();
        byte[] data = turnBasedMatch.getData();
        if (data == null) {

            dataCallback.initializeGame();
            return;
        }
        dataCallback.processGameData(new String(data));
    }

    public TurnBasedMatch getTurnBasedMatch(){
       return turnBasedMatch;
    }
}
