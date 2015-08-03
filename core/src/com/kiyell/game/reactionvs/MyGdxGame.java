package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kiyell.game.reactionvs.TrafficPlayScreen.titleActor;

public class MyGdxGame extends Game {
	
	//removed actionResolver public ActionResolver ar;

	MyGdxGame game;
	
	Boolean started;
	public Array<Score> p1Score = new Array<Score>();
	public Array<Score> p2Score = new Array<Score>();
	public Array<Score> p3Score = new Array<Score>();
	public Array<Score> p4Score = new Array<Score>();
	public int p1total;
	public int p2total;
	public int p3total;
	public int p4total;
	int playersAmount;
	

	public int playerCount;
//	public int l1,l1b,l2,l2b,l3,l3b,l4,l4b,l5,l5b,l6,l6b,l7,l7b;
	
	
	
	ArrivalPlayScreen arrivalPlayScreen;
	CardPlayScreen cardPlayScreen;
	TrafficPlayScreen trafficPlayScreen;
	ColorPlayScreen colorPlayScreen;
	ShapePlayScreen shapePlayScreen;
	RunPlayScreen runPlayScreen;
	RocketPlayScreen rocketPlayScreen;
	EndGameScreen endGameScreen;
	StartGameScreen startGameScreen;
	
	
	Boolean spoonsMode;
	Boolean endGame;
	Boolean readyPopup;
	Boolean soundEnabled;
	AssetManager manager;
	Preferences scorePref;
	
	
//	public Game game; removed actionresolver reference

	public MyGdxGame() {
		game = this;
//		this.ar = ar;
	}
	 
	
	
	
	@Override
	public void create () {
		
		scorePref = Gdx.app.getPreferences("My Scores");
		
		manager = new AssetManager();
		
		started = false;

		
		playerCount = 1;
		

		spoonsMode = true;
		endGame = false;
		readyPopup = false;
		soundEnabled = true;
		
		setScreen(new LoadingScreen(this));
		startGameScreen = new StartGameScreen(this);
 //       setScreen(startGameScreen); 
        
	}

	@Override
	public void render () {
		super.render();					// SUPER IMPORTANT, AVOIDS HAVING A BLACK SCREEN AND MAKING YOU NERVOUS
	}
	
	public void doNothing() {
		return;
	}
	
	public void nextGame(int l) {
		if (l == 1) {
			arrivalPlayScreen = new ArrivalPlayScreen(this);
			setScreen(arrivalPlayScreen);
		}
		else if (l == 2) {
			cardPlayScreen = new CardPlayScreen(this);
			setScreen(cardPlayScreen);
		}
		else if (l == 3) {			
			colorPlayScreen = new ColorPlayScreen(this);
			setScreen(colorPlayScreen);			
		}
		else if (l == 4) {
//			trafficPlayScreen = new TrafficPlayScreen(this);
//			setScreen(trafficPlayScreen);
			shapePlayScreen = new ShapePlayScreen(this);
			setScreen(shapePlayScreen);
		}
		else if (l == 5) {
			runPlayScreen = new RunPlayScreen(this);
			setScreen(runPlayScreen);			
		}
		
		else if (l == 6) {
			rocketPlayScreen = new RocketPlayScreen(this);
			setScreen(rocketPlayScreen);
		}
		else if (l == 7) {
			endGameScreen = new EndGameScreen(this);
			setScreen(endGameScreen);
		}
		
//		this.hide();
	}
	
	public void resize(int width, int height) {
//	    viewport.update(width, height);
//		this.resize(1290, 800);
	}
	
}
