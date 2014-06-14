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
    private TurnBasedMatch turnBasedMatch;

    public UpdateMatchCallback(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void onResult(TurnBasedMultiplayer.UpdateMatchResult updateMatchResult) {
        if (!updateMatchResult.getStatus().isSuccess()) {
            return;
        }

        turnBasedMatch = updateMatchResult.getMatch();
        if (turnBasedMatch.getTurnStatus() == TurnBasedMatch.MATCH_TURN_STATUS_THEIR_TURN) {
            dataCallback.showIdleScreen();
            return;
        }
        dataCallback.processGameData(new String(turnBasedMatch.getData()));
    }

    public TurnBasedMatch getTurnBasedMatch(){return turnBasedMatch;}
}
