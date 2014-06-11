package com.mdp.othello.screens;

import java.util.Locale;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mdp.othello.OthelloGame;
import com.mdp.othello.services.MusicManager.OurMusic;
import com.mdp.othello.services.SoundManager.OurSound;
import com.mdp.othello.utils.ActionResolver;
import com.mdp.othello.utils.DefaultInputListener;

/**
 * A simple options screen.
 */
public class OptionsScreen extends AbstractScreen {
    private Label volumeValue;
    private ActionResolver actionResolver;
    public OptionsScreen( OthelloGame game , ActionResolver actionResolver){
        super( game );
        this.actionResolver = actionResolver;
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.defaults().spaceBottom( 30 * UNITSCALE);
        table.columnDefaults( 0 ).padRight( 20 );
        Label options = new Label("Options", getSkin());
        options.setFontScale(UNITSCALE);
        table.add(options).colspan( 3 );

        // create the labels widgets
        final CheckBox soundEffectsCheckbox = new CheckBox( "", getSkin() );
        soundEffectsCheckbox.setScale(UNITSCALE);
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
                game.getSoundManager().play(OurSound.CLICK );
            }
        } );
        table.row();
        Label soundEffects = new Label("Sound Effects" , getSkin());
        soundEffects.setFontScale(UNITSCALE);
        table.add( soundEffects);
        soundEffectsCheckbox.getStyle().font.setScale(UNITSCALE);
        table.add( soundEffectsCheckbox ).colspan( 2 ).left();

        final CheckBox musicCheckbox = new CheckBox( "", getSkin() );
        musicCheckbox.setScale(UNITSCALE);
        musicCheckbox.getStyle().font.setScale(UNITSCALE);
        musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferencesManager().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );
                game.getSoundManager().play( OurSound.CLICK );

                // if the music is now enabled, start playing the menu music
                if( enabled ) game.getMusicManager().play( OurMusic.MENU );
            }
        } );
        table.row();
        Label musicLabel = new Label("Music" , getSkin());
        musicLabel.setFontScale(UNITSCALE);
        table.add( musicLabel);
        table.add( musicCheckbox ).colspan( 2 ).left();

        // range is [0.0,1.0]; step is 0.1f
        Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, getSkin() );
        volumeSlider.setScale(UNITSCALE);
        volumeSlider.setValue( game.getPreferencesManager().getVolume() );
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                float value = ( (Slider) actor ).getValue();
                game.getPreferencesManager().setVolume( value );
                game.getMusicManager().setVolume( value );
                game.getSoundManager().setVolume( value );
                updateVolumeLabel();
            }
        } );

        // create the volume label
        volumeValue = new Label( "", getSkin() );
        volumeValue.setFontScale(UNITSCALE);
        updateVolumeLabel();

        // add the volume row
        table.row();
        Label volumeValueLabel = new Label("Volume" , getSkin());
        volumeValueLabel.setFontScale(UNITSCALE);
        table.add( volumeValueLabel);
        table.add( volumeSlider );
        table.add( volumeValue ).width( 40 * UNITSCALE);

        // register the back button
        TextButton backButton = new TextButton( "Back to main menu", getSkin() );
        backButton.getStyle().font.setScale(UNITSCALE);
        backButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( OurSound.CLICK );
                game.setScreen( new MenuScreen(game, actionResolver) );
            }
        } );
        table.row();
        table.add( backButton ).size( 200 * UNITSCALE, 30 *UNITSCALE).colspan( 3 );
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
        float volume = ( game.getPreferencesManager().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
}
