package com.mdp.othello.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Handles the game preferences.
 */
public class PreferencesManager
{
    // constants
    private static final String PREF_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String USER_NAME = "user.name";
    private static final String USER_PASSWORD = "user.password";
    private static final String PREFS_NAME = "DeFence";

    public PreferencesManager(){ }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences( PREFS_NAME );
    }

    public boolean isSoundEnabled() {
        return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
    }

    public void setSoundEnabled(boolean soundEffectsEnabled) {
        Preferences preferences = getPrefs();
        preferences.putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
        preferences.flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
    }

    public void setMusicEnabled(boolean musicEnabled ){
        Preferences preferences = getPrefs();
        preferences.putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        preferences.flush();
    }

    public String getUserName() {
        return getPrefs().getString(USER_NAME);
    }

    public boolean isUserLoggedIn(){
        return getPrefs().contains(USER_NAME);
    }

    public String getUserPassword() {
        return getPrefs().getString(USER_PASSWORD);
    }

    public void setUserInformation( String userName, String userPassword )
    {
        Preferences preferences = getPrefs();
        preferences.putString(USER_NAME, userName);
        preferences.putString(USER_PASSWORD, userPassword);
        preferences.flush();
    }

    public void clearUserInformation(){
        Preferences preferences = getPrefs();
        preferences.remove(USER_PASSWORD);
        preferences.remove(USER_NAME);
        preferences.flush();
    }

    public float getVolume()
    {
        return getPrefs().getFloat( PREF_VOLUME, 0.5f );
    }

    public void setVolume(float volume){
        Preferences preferences = getPrefs();
        preferences.putFloat( PREF_VOLUME, volume );
        preferences.flush();
    }
}