package com.mdp.othello.screens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
        Label welcome = new Label("Welcome to Othello!", getSkin());
        welcome.setFontScale(UNITSCALE);
        table.add( welcome).spaceBottom( 50 );
        table.row();

        TextButton quickPlay = new TextButton( "Play", getSkin() );
        quickPlay.addListener(new DefaultInputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play( SoundManager.OurSound.CLICK );
                if(actionResolver.isSignedIn()) actionResolver.startGame();
                else actionResolver.signIn();

            }
        });
        quickPlay.getStyle().font.setScale(UNITSCALE);
        table.add(quickPlay).size( 200 * UNITSCALE, 30 *UNITSCALE).uniform().spaceBottom( 10 *UNITSCALE);
        table.row();

        TextButton achievementsButton = new TextButton( "Achievements", getSkin() );
        achievementsButton.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(actionResolver.isSignedIn()) {
                    actionResolver.showAchievements();
                }else{
                    actionResolver.signIn();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        achievementsButton.getStyle().font.setScale(UNITSCALE);
        table.add( achievementsButton ).uniform().fill().spaceBottom( 10 *UNITSCALE);
        table.row();

        TextButton highScoresButton = new TextButton( "High Scores", getSkin() );
        highScoresButton.addListener(new DefaultInputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(actionResolver.isSignedIn()) actionResolver.showLeaderboard();
                else actionResolver.signIn();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        highScoresButton.getStyle().font.setScale(UNITSCALE);
        table.add( highScoresButton ).uniform().fill().spaceBottom( 10 * UNITSCALE);
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
        optionsButton.getStyle().font.setScale(UNITSCALE);
        table.add( optionsButton ).uniform().fill().spaceBottom(10* UNITSCALE);

    }
}