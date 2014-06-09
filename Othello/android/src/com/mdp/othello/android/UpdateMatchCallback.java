package com.mdp.othello.android;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.mdp.othello.utils.IDataCallback;

/**
 * @author Alex
 */
public class UpdateMatchCallback implements ResultCallback<TurnBasedMultiplayer.UpdateMatchResult> {
    private IDataCallback dataCallback;

    public UpdateMatchCallback(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
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
