package com.mdp.othello.screens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.services.SoundManager;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.DefaultInputListener;

public class MenuScreen extends AbstractScreen{
    private ActionResolver actionResolver;
    public MenuScreen( OthelloGame game, ActionResolver actionResolver) {
        super( game );
        this.actionResolver = actionResolver;
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.add( "Welcome to Othello!" ).spaceBottom( 50 );
        table.row();

        TextButton quickPlay = new TextButton( "Quick Play", getSkin() );
        quickPlay.addListener(new DefaultInputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play( SoundManager.OurSound.CLICK );

            }
        });
        table.add(quickPlay).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        TextButton achievementsButton = new TextButton( "Achievements", getSkin() );
        achievementsButton.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actionResolver.showAchievements();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add( achievementsButton ).uniform().fill().spaceBottom( 10 );
        table.row();

        TextButton highScoresButton = new TextButton( "High Scores", getSkin() );
        highScoresButton.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actionResolver.showLeaderboard();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add( highScoresButton ).uniform().fill().spaceBottom( 10 );
        table.row();
        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", getSkin() );
        optionsButton.addListener(new DefaultInputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(SoundManager.OurSound.CLICK);
                game.setScreen(new OptionsScreen(game, actionResolver));
            }
        });
        table.add( optionsButton ).uniform().fill().spaceBottom(10);



    }
}