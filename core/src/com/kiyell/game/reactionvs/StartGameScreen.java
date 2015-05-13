package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/*
 *  Plan for highScores display:
 *  
 *  
 *  
 *  
Tap when GREEN....................123.1234 ms
Tap when GREEN (2)................123.1234 ms
Tap When TAXI COMES...............123.1234 ms
Tap When TAXI COMES (2)...........123.1234 ms
TAP when ACE APPEARS..............123.1234 ms
TAP when ACE APPEARS (2)..........123.1234 ms
TAP when WORD is COLOR............123.1234 ms
TAP when COLOR is WORD............123.1234 ms
TAP when SHAPE is a RECTANGLE.....123.1234 ms
TAP when SHAPE is a TRIANGLE......123.1234 ms
Tap when man FINISHES RACE........123.1234 ms
Tap when man FINISHES RACE (2)....123.1234 ms
Tap when ROCKET LAUNCHES..........123.1234 ms
Tap when ROCKET LAUNCHES (2)......123.1234 ms
 * 
 * 
 * 
 * 
 * 
 */
public class StartGameScreen implements Screen {
	MyGdxGame game;
	
	InputProcessor backProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {

            if (((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) && !showSmartWall) {
                // Maybe perform other operations before exiting
    						 	game.ar.startSmartWallAd();
    						 	showSmartWall = true;    			                
    			                
            }
            
            if (((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) && showSmartWall) {
            	Gdx.app.exit();
            }
            return true;
        }
    };
	
	public class PopupActor extends Actor {
		
		
		public PopupActor(MyGdxGame gam) {
			game = gam;
			

			
			
		}
		
		@Override
        public void draw(Batch batch, float alpha){
	//		Gdx.gl.glClearColor(1, 0, 0, 1);
    //		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    		
    		
    		
    		if (game.readyPopup) {
    			// draw animation for pop up
   // 			batch.draw(popup, 1280/2-popup.getWidth()/2, 800/2-popup.getHeight()/2);
   // 			batch.draw(popup, 1280/2-popup.getWidth()/2, 800/2-popup.getHeight()/2, popup.getWidth()*windowAnimOpen.getScale(), popup.getHeight()*windowAnimOpen.getScale());
    			popupS.setScale(windowAnimOpen.getScale());
    			popupS.setAlpha(.95f);
    			popupBlankS.setScale(windowAnimOpen.getScale());
    			popupBlankS.setAlpha(.95f);
    			popupReactionS.setScale(windowAnimOpen.getScale());
    			popupReactionS.setAlpha(.95f);
    			buttonReadyS.setScale(windowAnimOpen.getScale());
    			buttonXS.setScale(windowAnimOpen.getScale());
    			
    			if (!highScoreShow) {
    				
    				if (game.playerCount >= 2)
    					popupS.draw(batch);
    				else
    					popupReactionS.draw(batch);
    				
        			buttonReadyS.draw(batch);
        			buttonXS.draw(batch);
    			}
    			else if (highScoreShow) {
    				batch.draw(buttonTrophyOn, 1280-22*2-buttonSoundOff.getWidth()*.8f*2, 22, buttonSoundOff.getWidth()*.8f, buttonSoundOff.getHeight()*.8f);
    				popupBlankS.draw(batch);
        			buttonXS.draw(batch);
        			if (manager.getRunningTimelinesCount() == 0) {
        				
        				fontTitles.setColor(1,1, 1, windowAnimOpen.getScale());
        				fontScores.setColor(1,1, 1, windowAnimOpen.getScale());
        				fontHighScores.setColor(1,1, 1, windowAnimOpen.getScale());
        				
        				fontHighScores.drawMultiLine(batch, 
            					"High Scores",
            					180, 670, 920, BitmapFont.HAlignment.CENTER);
        				
        				if (windowAnimOpen.getScale() == 1)
        					fontScores.setMarkupEnabled(true);
        				else
        					fontScores.setMarkupEnabled(false);
        				
        				fontTitles.drawMultiLine(batch, 
            					"Tap when GREEN\nTap when GREEN (2)\nTap When TAXI COMES\nTap When TAXI COMES (2)\nTAP when ACE APPEARS\nTAP when ACE APPEARS (2)"
            					+ "\nTAP when WORD is COLOR\nTAP when COLOR is WORD\nTAP when SHAPE is a RECTANGLE\nTAP when SHAPE is a TRIANGLE\nTap when man CROSSES LINE"
            					+ "\nTap when man CROSSES LINE (2)\nTap when ROCKET LAUNCHES\nTap when ROCKET LAUNCHES (2)",
            					180, 600);
        				fontScores.drawMultiLine(batch, 
            					"............................................................................................ "  
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when GREEN", 000.0000f)))+" ms[]\n" // when green
            					+ "...................................................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when GREEN (2)", 000.0000f)))+" ms[]\n"
            					+ ".................................................................................. "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when TAXI COMES", 000.0000f)))+" ms[]\n" // taxi comes
            					+ ".......................................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when TAXI COMES (2)", 000.0000f)))+" ms[]\n"
            					+ "............................................................................. "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when ACE APPEARS", 000.0000f)))+" ms[]\n" // tap when ace
            					+ ".................................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when ACE APPEARS (2)", 000.0000f)))+" ms[]\n"
            					+ "........................................................................ "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when COLOR is COLOR", 000.0000f)))+" ms[]\n" // tap word is color
            					+ ".......................................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when WORD is COLOR", 000.0000f)))+" ms[]\n" // color is word
            					+ "............................................................ "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when SHAPE is a RECTANGLE", 000.0000f)))+" ms[]\n" // shape is rectangle
            					+ "............................................................ "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when SHAPE is a TRIANGLE", 000.0000f)))+" ms[]\n" // shape is triangle
            					+ "................................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when man CROSSES LINE", 000.0000f)))+" ms[]\n" // race1
            					+ "........................................................... "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when man CROSSES LINE (2)", 000.0000f)))+" ms[]\n" //race2
            					+ "................................................................. "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when ROCKET LAUNCHES", 000.0000f)))+" ms[]\n" //rocket1
            					+ "............................................................ "
            					+ "[GREEN]"+String.format("%.4f", (game.scorePref.getFloat("Tap when ROCKET LAUNCHES (2)", 000.0000f)))+" ms[]\n", //rocket2
            					180, 600, 870, BitmapFont.HAlignment.RIGHT);
        			}
        			
    			}
    			
    			
    			
    	//		batch.draw(buttonX, (1280/2-popup.getWidth()/2)+(popup.getWidth()-buttonX.getWidth()/2), (800/2-popup.getHeight()/2)+(popup.getHeight()-buttonX.getHeight()/2));
    	//		batch.draw(buttonReady, 1280/2-buttonReady.getWidth()/2, 125);
    		}
    		
		}
		
	}
	
	public class StartButtonActor extends Actor {
		
		Boolean stageStart;
		Sound clickSound = game.manager.get("click.ogg", Sound.class);
		
		
		public StartButtonActor(MyGdxGame gam) {
			game = gam;
			stageStart = false;
			
			addListener(new InputListener(){
                

				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                	stageStart = true;
                	
                	if(  			// START BUTTON IS HIT
            				x >= 1280/2 - start.getWidth()/2 && x <= 1280/2 - start.getWidth()/2+start.getWidth() &&
            				y >= 800/2 - start.getHeight()/2 && y <= 800/2 - start.getHeight()/2+start.getHeight() && !highScoreShow     				
            				){
            			
                		highScoreShow = false;	
            			reverse = 2;
            			game.readyPopup = true; // SHOW POPUP
            			animFired = true;
            			
            			if (game.soundEnabled)
            				clickSound.play();

            	    }
                	
                	// pop up X button is hit
                	
                	if (x >= (1280/2-popup.getWidth()/2)+(popup.getWidth()-buttonX.getWidth()/2) && x <= (1280/2-popup.getWidth()/2)+(popup.getWidth()-buttonX.getWidth()/2)+popup.getWidth() 
                			&& y >= (800/2-popup.getHeight()/2)+(popup.getHeight()-buttonX.getHeight()/2) && y <= (800/2-popup.getHeight()/2)+(popup.getHeight()-buttonX.getHeight()/2)+popup.getHeight() && game.readyPopup)
                	{
                		animFired = true;
                		reverse = 1;
           //     		game.readyPopup = false;
          //      		windowAnimOpen.setScale(0);
                		
                		if (game.soundEnabled)
            				clickSound.play();
                	}
                	
                	// pop up ready button is hit
                	
                	if (x >= 1280/2-buttonReady.getWidth()/2 && x <= 1280/2-buttonReady.getWidth()/2+buttonReady.getWidth()
                			&& y >= 125 && y <= 125+buttonReady.getHeight() && game.readyPopup && reverse == 2 && !highScoreShow)
                	{
                		
                		if (game.playerCount < 2) {
            				game.spoonsMode = false;
            			} else
            				game.spoonsMode = true;
                		

            			started = true;
            			windowAnimOpen.setScale(0);
            			
            			
            			
            			game.setScreen(new TrafficPlayScreen(game));           			
//            			game.setScreen(new ArrivalPlayScreen(game));
//	          			game.setScreen(new CardPlayScreen(game));
//            			game.setScreen(new ColorPlayScreen(game));
//                		game.setScreen(new ShapePlayScreen(game));
//            			game.setScreen(new RunPlayScreen(game));
            			
//            			game.setScreen(new RocketPlayScreen(game));
                		
//            			game.setScreen(new EndGameScreen(game));
            			
  //          			game.setScreen(new StartGameScreen(game));
            			

     //       			this.pause();
            			
                	}
                	

            			if( // Left button is hit
            				x >= 40 + playerFont.getBounds("Players: ").width && x <= 190 + playerFont.getBounds("Players: ").width &&
            				y >=  10 && y <=  160 &&
            				!clickedLeft && !game.readyPopup
            				)
            				{			
            				game.playerCount--;
            			clickedLeft = true;
            			
            			if (game.soundEnabled)
            				clickSound.play();
            		}
            		
            			if( // Right button is hit
            				x >= 40 + playerFont.getBounds("Players: ").width +180 && x <= 150 + playerFont.getBounds("Players: ").width +180 + 40 &&
            				y >= 10 && y <= 160 &&
            				!clickedRight && !game.readyPopup
            				)
            				{			
            //			batch.draw(rightS, 40 + playerFont.getBounds("Players: ").width + 130, 50, 100, 100);
            				game.playerCount++;
            			clickedRight = true;
            			
            			if (game.soundEnabled)
            				clickSound.play();
            			
            	//		batch.draw(start, 200, 500);
            		}
            			// IF sound button is hit
            			if (x >= 1280-22-buttonSoundOn.getWidth()*.8f && x <= 1280-22-buttonSoundOn.getWidth()*.8f+buttonSoundOn.getWidth()*.8f
            					&& y >= 22 && y <= 22+buttonSoundOn.getHeight()*.8f && !game.readyPopup) {
            				clickedSound = true;
            			}
            			
            			
            		
            			
            			if (x >= 1280-22*2-buttonSoundOff.getWidth()*.8f*2 && x <= (1280-22*2-buttonSoundOff.getWidth()*.8f*2)+buttonSoundOff.getWidth()*.8f
            					&& y >= 22 && y <= 22+buttonSoundOn.getHeight()*.8f && !game.readyPopup) {
            					reverse = 2;
                    			game.readyPopup = true; // SHOW POPUP
                    			highScoreShow = true;
                    			animFired = true;
                    			if (game.soundEnabled)
                    				clickSound.play();
            			}
            		
                	
                	
                	
                    return true;
                }
				

				
				
            });
			
		}

        @Override
        public void draw(Batch batch, float alpha){
        		
        		
        		Gdx.gl.glClearColor(1, 0, 0, 1);
        		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        		
        		if (!started) {
        		
        		if(stageStart) {
        			game.setScreen(new TrafficPlayScreen(game));
        			started = true;
        		}
        		
        			}
        		
        		
        		batch.draw(bg, 0, 0, 1280, 800);
        //		batch.draw(title, 1280/2 - title.getWidth()/2, 800-140);
        		batch.draw(title, 1280/2 - title.getWidth()/2, 800-280);
        		batch.draw(start, 1280/2 - start.getWidth()/2, 800/2 - start.getHeight()/2);
        		
        		playerFont.draw(batch, "Players: ", 40, 100);
        		
        		batch.draw(left, 40 + playerFont.getBounds("Players: ").width, 10, 150, 150); // 100 100
        		playerFont.draw(batch, String.valueOf(game.playerCount), 175 + playerFont.getBounds("Players: ").width, 100); // 140
        		batch.draw(right, 40 + playerFont.getBounds("Players: ").width + 180, 10, 150, 150); // 130
        		
        		if (game.soundEnabled) // SOUND ICON
        			batch.draw(buttonSoundOn, 1280-22-buttonSoundOn.getWidth()*.8f, 22, buttonSoundOn.getWidth()*.8f, buttonSoundOn.getHeight()*.8f);
        		else
        			batch.draw(buttonSoundOff, 1280-22-buttonSoundOff.getWidth()*.8f, 22, buttonSoundOff.getWidth()*.8f, buttonSoundOff.getHeight()*.8f);
        			
        		// HIGHSCORES ICON -- need to replace graphc
        		batch.draw(buttonTrophyOff, 1280-22*2-buttonSoundOff.getWidth()*.8f*2, 22, buttonSoundOff.getWidth()*.8f, buttonSoundOff.getHeight()*.8f);
        		
        		
        		if (clickedLeft == true) {
        			if(game.playerCount <= 0)
        				game.playerCount = 4;
        			if(game.playerCount >= 5)
        				game.playerCount = 1;
        			
        			batch.draw(leftS, 40 + playerFont.getBounds("Players: ").width, 10, 150, 150);

        			try {
        				Thread.sleep(70);
       			} catch (InterruptedException e) {        				// TODO Auto-generated catch block
        				e.printStackTrace();
      			}
        			clickedLeft = false;
        		}
        		
        		if (clickedRight == true) {
        			if(game.playerCount <= 0)
        				game.playerCount = 4;
        			if(game.playerCount >= 5)
        				game.playerCount = 1;
        			
        			batch.draw(rightS, 40 + playerFont.getBounds("Players: ").width + 180, 10, 150, 150);

        			try {
        				Thread.sleep(70);
       			} catch (InterruptedException e) {        				// TODO Auto-generated catch block
        				e.printStackTrace();
      			}
        			clickedRight = false;
        		}
        		
        		if (clickedSound == true) {
        			game.soundEnabled = !game.soundEnabled;       			
        			try {
        				Thread.sleep(70);
       			} catch (InterruptedException e) {        				// TODO Auto-generated catch block
        				e.printStackTrace();
      			}
        			clickedSound = false;
        		}
        		

        		
        	 }	
        		
        }
	SpriteBatch batch;
	Texture bg;
	Texture start;
	Texture left;
	Texture right;
	Texture leftS;
	Texture rightS;
	Texture title;
	Texture popup, popupReaction, popupBlank;
	Texture buttonX, buttonReady, buttonSoundOn, buttonSoundOff,
		buttonTrophyOff, buttonTrophyOn;
	Sprite buttonXS;
	Sprite buttonReadyS;
	Sprite popupS, popupReactionS, popupBlankS;
	
	
	Boolean started;
	Boolean clickedLeft, clickedRight, clickedSound;
	
	

	BitmapFont playerFont;
	BitmapFont fontScores,fontTitles,fontHighScores;
	
	float startXmod;
	float startYmod;
	
	private Viewport viewport;
	private Camera camera;
	Stage stage;
	Actor startButtonActor;
	Actor popupActor;
	Group group;
	
	
	ArrivalPlayScreen arrivalPlayScreen;
	CardPlayScreen cardPlayScreen;
	TrafficPlayScreen trafficPlayScreen;
	ColorPlayScreen colorPlayScreen;
	ShapePlayScreen shapePlayScreen;
	RunPlayScreen runPlayScreen;
	RocketPlayScreen rocketPlayScreen;
	EndGameScreen endGameScreen;
	
	TweenGraphic windowAnimOpen;
	TweenManager manager;
	Boolean animFired;
	private int reverse;
	private boolean highScoreShow;
	InputMultiplexer multiplexer;
	

	
	
	
	TweenCallback readyPopupCallBack = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			if (reverse == 1)
			game.readyPopup = false;
			
			if(highScoreShow && reverse == 1)
				highScoreShow = false;

			return;
		}	
	};
	private boolean showSmartWall;

	public StartGameScreen(MyGdxGame gam) {
		game = gam;
		batch = new SpriteBatch();
		
		bg = game.manager.get("bg.jpg", Texture.class);
		start = game.manager.get("button_start.png", Texture.class);
		left = game.manager.get("arrowLeft.png", Texture.class);
		right = game.manager.get("arrowRight.png", Texture.class);
		leftS = game.manager.get("arrowLeft_s.png", Texture.class);
		rightS = game.manager.get("arrowRight_s.png", Texture.class);
		title = game.manager.get("title_reactionvs.png", Texture.class);
		popup = game.manager.get("popup_spoons_prompt.png", Texture.class);
		popupReaction = game.manager.get("popup_reaction_prompt.png", Texture.class);
		popupBlank = game.manager.get("popup_blank.png", Texture.class);
		buttonX = game.manager.get("button_x.png", Texture.class);
		buttonReady = game.manager.get("button_ready.png", Texture.class);
		buttonSoundOn = game.manager.get("button_sound_on.png", Texture.class);
		buttonSoundOff = game.manager.get("button_sound_off.png", Texture.class);
		buttonTrophyOn = game.manager.get("button_trophy_on.png", Texture.class);
		buttonTrophyOff = game.manager.get("button_trophy_off.png", Texture.class);
		
		started = false;
		clickedLeft = false;
		clickedRight = false;
//		playerFont = new BitmapFont(Gdx.files.internal("comicsans.fnt"),Gdx.files.internal("comicsans_0.png"), false);
		
		
//		playerFont = new BitmapFont(Gdx.files.internal("fontreg1.fnt"),Gdx.files.internal("fontreg1.png"), false);
		playerFont = game.manager.get("fontreg1.fnt", BitmapFont.class);
		fontTitles = game.manager.get("fontshadow0.fnt", BitmapFont.class);
		fontScores = game.manager.get("fontshadow0.fnt", BitmapFont.class);
		fontHighScores = game.manager.get("fontshadow1.fnt", BitmapFont.class);
//		playerFont.setScale(0.8f);
		
		startXmod = Gdx.graphics.getWidth()/2 - start.getWidth()/2;
		startYmod = Gdx.graphics.getHeight()/2 - start.getHeight()/2;
		
		
		camera = new PerspectiveCamera();
	    viewport = new StretchViewport(800, 600, camera);
		
		stage = new Stage(new StretchViewport(1280, 800));
		
		startButtonActor = new StartButtonActor(game);
		startButtonActor.setWidth(1280);
		startButtonActor.setHeight(800);
		startButtonActor.setBounds(0, 0, 1280, 800);
		
		popupActor = new PopupActor(game);
//		popupActor.setWidth(1280);
//		popupActor.setHeight(800);
//		popupActor.setBounds(0, 0, popup.getWidth(), popup.getHeight());
		
		group = new Group();
		
		group.addActor(startButtonActor);
		group.addActor(popupActor);
		
		stage.addActor(group);
		
		game.p1total = 0; game.p2total = 0; game.p3total = 0; game.p4total = 0;
		clickedSound = false;
		game.readyPopup = false;
		
		windowAnimOpen = new TweenGraphic();
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		animFired = false;
		windowAnimOpen.setScale(0);
		
		popupS = new Sprite(popup);
		popupReactionS = new Sprite(popupReaction);
		popupBlankS = new Sprite(popupBlank);
		buttonXS = new Sprite(buttonX);
		buttonReadyS = new Sprite(buttonReady);
		
		popupS.setBounds(1280/2-popup.getWidth()/2,  800/2-popup.getHeight()/2, popup.getWidth(), popup.getHeight());
		popupReactionS.setBounds(1280/2-popup.getWidth()/2,  800/2-popup.getHeight()/2, popup.getWidth(), popup.getHeight());
		popupBlankS.setBounds(1280/2-popup.getWidth()/2,  800/2-popup.getHeight()/2, popup.getWidth(), popup.getHeight());
		buttonXS.setBounds((1280/2-popup.getWidth()/2)+(popup.getWidth()-buttonX.getWidth()/2), (800/2-popup.getHeight()/2)+(popup.getHeight()-buttonX.getHeight()/2), buttonX.getWidth(), buttonX.getHeight());
		buttonReadyS.setBounds(1280/2-buttonReady.getWidth()/2, 125, buttonReady.getWidth(), buttonReady.getHeight());
		reverse = 2;
		highScoreShow = false;
		fontTitles.setScale(.5f);
		fontScores.setScale(.5f);
		fontHighScores.setScale(.5f);
		
		showSmartWall = false;
		Gdx.input.setCatchBackKey(true);
		multiplexer = new InputMultiplexer(stage,
                backProcessor);
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
		Gdx.input.setInputProcessor(multiplexer);
		stage.draw();
		
		if (animFired) {
			doAnimation();
			animFired = false;
		
		
//		game.render();
		}
	}
	
	public void doAnimation() {
		
		manager.add(Timeline.createSequence() //  open window
				.push(Tween.to(windowAnimOpen, GraphicAnimator.SCALE,.4f).target(reverse-1))
			    .repeat(0,.1f)
			    .setCallback(readyPopupCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		game.readyPopup = false;
		game.p1total = 0; game.p2total = 0; game.p3total = 0; game.p4total = 0;
		reverse = 2;
		
		game.p1Score = new Array<Score>();
		game.p2Score = new Array<Score>();
		game.p3Score = new Array<Score>();
		game.p4Score = new Array<Score>();

		game.ar.showAds(true);
		
	}
	


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
