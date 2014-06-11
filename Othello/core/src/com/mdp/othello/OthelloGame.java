package com.mdp.othello;

import com.badlogic.gdx.*;
import com.mdp.othello.screens.GameScreen;
import com.mdp.othello.screens.MenuScreen;
import com.mdp.othello.services.MusicManager;
import com.mdp.othello.services.PreferencesManager;
import com.mdp.othello.services.SoundManager;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.DataCallback;


public class OthelloGame extends Game {
    public static final String LOG = OthelloGame.class.getSimpleName();
    public static final boolean DEV_MODE = false;
    private PreferencesManager preferencesManager;
    private MusicManager musicManager;
    private SoundManager soundManager;
    private ActionResolver actionResolver;

    public OthelloGame(ActionResolver actionResolver){
        this.actionResolver = actionResolver;
        this.actionResolver.addCallback(new DataCallback(this, this.actionResolver));
    }

    @Override
    public void create(){
        Gdx.app.log(OthelloGame.LOG, "Creating game on " + Gdx.app.getType() );
        soundManager = new SoundManager();
        musicManager = new MusicManager();
        preferencesManager = new PreferencesManager();
        if(DEV_MODE) setScreen(new GameScreen(this, actionResolver, null));
        else {
            proceedToMenuScreen();
        }
    }

    public void proceedToMenuScreen(){
            setScreen(new MenuScreen(this, actionResolver));
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose(){
        super.dispose();
        Gdx.app.log(OthelloGame.LOG, "Disposing game");
    }

    public PreferencesManager getPreferencesManager()
    {
        return preferencesManager;
    }
    @Override
    public void pause(){
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void setScreen(Screen screen){
        super.setScreen(screen);
        Gdx.app.log(OthelloGame.LOG, "Setting screen: " + screen.getClass().getSimpleName());
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        Gdx.app.log(OthelloGame.LOG, "Resizing game to: " + width + " x " + height);
    }

    public MusicManager getMusicManager(){
        return musicManager;
    }

    public SoundManager getSoundManager(){
        return soundManager;
    }
}
