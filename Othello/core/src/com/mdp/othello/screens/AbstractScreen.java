package com.mdp.othello.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mdp.othello.OthelloGame;

/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen implements Screen {
    // the fixed viewport dimensions (ratio: 1.6)
    public static float UNITSCALE;
    protected final OthelloGame game;
    protected final Stage stage;
    private Label debugLabel;

    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private int width,height;

	public AbstractScreen( OthelloGame game ){
        this.game = game;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        UNITSCALE = getHeight() / 320f;
        this.stage = new Stage();
    }
	
	protected int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}

    protected String getName()
    {
        return super.getClass().getSimpleName();
    }

    protected boolean isGameScreen()
    {
        return false;
    }

    // Lazily loaded collaborators

    public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont(Gdx.files.internal("fonts/arial.fnt"),
                    Gdx.files.internal("fonts/arial.png"), false);
        }
        return font;
    }

    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    public TextureAtlas getAtlas()
    {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
        }
        return atlas;
    }

    public Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }

    protected Table getTable()
    {
        if( table == null ) {
            table = new Table( getSkin() );
            table.setFillParent( true );
            if( OthelloGame.DEV_MODE ) {
                table.debug();
            }
            stage.addActor( table );
        }
        return table;
    }

    protected void setDebugLabel(String string){
        if(debugLabel == null){
            debugLabel = new Label(string, getSkin());
            debugLabel.setPosition(0, 10);
            stage.addActor(debugLabel);
        }
        else{
            debugLabel.setText(string);
        }
    }

    public Stage getStage(){
        return stage;
    }

    // Screen implementation

    @Override
    public void show()
    {
        Gdx.app.log( OthelloGame.LOG, "Showing screen: " + getName() );

        // set the stage as the input processor
        Gdx.input.setInputProcessor( stage );
    }

    @Override
    public void resize(
            int width,
            int height )
    {
        Gdx.app.log( OthelloGame.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
    }

    @Override
    public void render(
            float delta )
    {
        // (1) process the game logic

        // update the actors
        stage.act( delta );

        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();

        // draw the table debug lines
        //Table.drawDebug( stage );
    }

    @Override
    public void hide()
    {
        Gdx.app.log( OthelloGame.LOG, "Hiding screen: " + getName() );

        // dispose the screen when leaving the screen;
        // note that the dipose() method is not called automatically by the
        // framework, so we must figure out when it's appropriate to call it
        dispose();
    }

    @Override
    public void pause()
    {
        game.getMusicManager().pause();
        Gdx.app.log( OthelloGame.LOG, "Pausing screen: " + getName() );
    }

    @Override
    public void resume()
    {
        game.getMusicManager().resume();
        Gdx.app.log( OthelloGame.LOG, "Resuming screen: " + getName() );
    }

    @Override
    public void dispose()
    {
        Gdx.app.log( OthelloGame.LOG, "Disposing screen: " + getName() );

        // the following call disposes the screen's stage, but on my computer it
        // crashes the game so I commented it out; more info can be found at:
        // http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
         stage.dispose();

        // as the collaborators are lazily loaded, they may be null
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
    }
}