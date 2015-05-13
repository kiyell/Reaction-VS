package com.kiyell.game.reactionvs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Mats Svensson
 */
public class LoadingScreen implements Screen {

    private Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;
    private float percent;

    private Actor loadingBar;
    MyGdxGame game;

    public LoadingScreen(MyGdxGame gam) {
    	super();
    	game = gam;

        
    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        game.manager.load("loading.pack", TextureAtlas.class);
        // Wait until they are finished loading
        game.manager.finishLoading();

        // Initialize the stage where we will place everything
        stage = new Stage();

        // Get our texture atlas from the manager
        TextureAtlas atlas = game.manager.get("loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Add the loading bar animation
 //       Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim") );
 //       anim.setPlayMode(PlayMode.LOOP_REVERSED);
 //       loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
         loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
 //       stage.addActor(logo);

      
// Traffic game  

        game.manager.load("bg.jpg", Texture.class);
        game.manager.load("button_start.png", Texture.class);
		game.manager.load("arrowLeft.png", Texture.class);
		game.manager.load("arrowRight.png", Texture.class);
		game.manager.load("arrowLeft_s.png", Texture.class);
		game.manager.load("arrowRight_s.png", Texture.class);
		game.manager.load("title_reactionvs.png", Texture.class);
		game.manager.load("popup_spoons_prompt.png", Texture.class);
		game.manager.load("popup_reaction_prompt.png", Texture.class);
		game.manager.load("popup_blank.png", Texture.class);
		game.manager.load("button_x.png", Texture.class);
		game.manager.load("button_ready.png", Texture.class);
		game.manager.load("button_sound_on.png", Texture.class);
		game.manager.load("button_sound_off.png", Texture.class);
		game.manager.load("button_trophy_off.png", Texture.class);
		game.manager.load("button_trophy_on.png", Texture.class);
		game.manager.load("fontreg1.fnt", BitmapFont.class);
		game.manager.load("fontshadow0.fnt", BitmapFont.class);
		game.manager.load("fontshadow1.fnt", BitmapFont.class);
		game.manager.load("fontFancy1.fnt", BitmapFont.class);
		game.manager.load("bad.ogg", Sound.class);
		game.manager.load("bg2fadeout.ogg", Music.class);
		game.manager.load("click.ogg", Sound.class);
		game.manager.load("good.ogg", Sound.class);
		game.manager.load("highscore.ogg", Sound.class);
		game.manager.load("win.ogg", Sound.class);
		game.manager.finishLoading();
        
        
		

		game.manager.load("tapWhenGreen.png", Texture.class);
		game.manager.load("TLnone.png", Texture.class);
		game.manager.load("TLgreen.png", Texture.class);
		game.manager.load("TLyellow.png", Texture.class);
		game.manager.load("failure.png", Texture.class);
		game.manager.load("TLred.png", Texture.class);
	//	game.manager.load("bg.jpg", Texture.class);
		game.manager.load("nice.png", Texture.class);
		
		game.manager.load("buttons.pack", TextureAtlas.class);
		
		
		game.manager.load("comicsans.fnt", BitmapFont.class);
		game.manager.load("fontreg0.fnt", BitmapFont.class);
		game.manager.load("arialblack.fnt", BitmapFont.class);
		
		
		game.manager.load("greenC.png", Texture.class);
		game.manager.load("blueC.png", Texture.class);
		game.manager.load("yellowC.png", Texture.class);
		game.manager.load("taxi.png", Texture.class);
		game.manager.load("road.png", Texture.class);	
		
		game.manager.load("cards2_A.pack", TextureAtlas.class);
		game.manager.load("Shapes.pack", TextureAtlas.class);
		
		game.manager.load("finishFlag.png", Texture.class);
		game.manager.load("black.png", Texture.class);
		
		game.manager.load("runcycle.pack", TextureAtlas.class);
		
		game.manager.load("sound1.wav", Sound.class);
		game.manager.load("sound2.wav", Sound.class);
		game.manager.load("sound3.wav", Sound.class);
		game.manager.load("sound4.wav", Sound.class);
		game.manager.load("sound5.wav", Sound.class);
		game.manager.load("rocketRumble.mp3", Sound.class);
		
		game.manager.load("base.png", Texture.class); 
		game.manager.load("ship.png", Texture.class); 
		game.manager.load("flame.png", Texture.class);
		
		game.manager.load("0.jpg", Texture.class);
		game.manager.load("1.jpg", Texture.class); 
		game.manager.load("2.jpg", Texture.class); 
		game.manager.load("3.jpg", Texture.class);
		game.manager.load("4.jpg", Texture.class); 
		game.manager.load("5.jpg", Texture.class);
		
		game.manager.load("button_exit_green.png", Texture.class); 
		game.manager.load("button_exit_red.png", Texture.class);
		game.manager.load("button_replay.png", Texture.class); 
		game.manager.load("button_replay_green.png", Texture.class);
		
		
		
		
		
		
		
//		game.manager.finishLoading();
/*
 * 		
 * 
 * 
 * 
		game.manager.load("bg.jpg");
		game.manager.load("button_start.png");
		game.manager.load("arrowLeft.png");
		game.manager.load("arrowRight.png");
		game.manager.load("arrowLeft_s.png");
		game.manager.load("arrowRight_s.png");
		game.manager.load("title_reactionvs.png");
		game.manager.load("popup_spoons_prompt.png");
		game.manager.load("button_x.png");
		game.manager.load("button_ready.png");
		game.manager.load("button_sound_on.png");
		game.manager.load("button_sound_off.png");
		
 * 	
 */
		
		

		
      //    game.manager.load("deletelater.jpg", Texture.class);
       
        // Add everything to be loaded, for instance:
        // game.manager.load("data/assets1.pack", TextureAtlas.class);
        // game.manager.load("data/assets2.pack", TextureAtlas.class);
        // game.manager.load("data/assets3.pack", TextureAtlas.class);
    }

    @Override
    public void resize(int width, int height) {
        // Set our screen to always be XXX x 480 in size
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        stage.getViewport().update(width , height, false);

        // Make the background fill the screen
        screenBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((width - logo.getWidth()) / 2);
        logo.setY((height - logo.getHeight()) / 2 + 100);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.manager.update()) { // Load some, will return true if done loading
    //        if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the main menu screen
                game.setScreen(game.startGameScreen);
//            }
        }

        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        game.manager.unload("loading.pack");
    }

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}