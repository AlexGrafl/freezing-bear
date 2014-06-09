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

    public MatchInitiatedCallback(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void onResult(InitiateMatchResult initiateMatchResult) {
        if (initiateMatchResult.getStatus().getStatusCode() != GamesStatusCodes.STATUS_OK) {
            return;
        }

        TurnBasedMatch match = initiateMatchResult.getMatch();
        if (match.getData() != null) {
            dataCallback.processGameData(new String(match.getData()));
            return;
        }
        dataCallback.initializeGame(new String(match.getData()));
        dataCallback.processGameData(new String(match.getData()));
    }
}
