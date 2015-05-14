package com.kiyell.game.reactionvs;

import java.util.ArrayList;
import java.util.Collections;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input;

/* Logs:
 * 10/22/2014 -- Just added nine patch buttons on corner, planning on adding labels to button and player indicator along with score tracking
 * 10/23/2014 -- Added player labels and logic to determine player that clicked
 * 10/28/2014 -- Added player count configuration
 * 10/29/2014 -- Added 2nd game mode
 * 10/30/2014 -- Added resolution non-specific buttons + titles
 * 11/4/2014 -- Added button press transition plus next game method
 * 11/5/2014 -- Added car animations + second arrival game mode
 * 11/6/2014 -- Edited taxi on screen detection, Added screen manager logic in MyGdxGame
 * 11/11/2014 -- Adjusted code for positioning title, added 5 card positions
 * 11/12/2014 -- fixed card bug, card mode is working now -- Added resolution test for card play title setting and added stage title
 * 11/13/2014 -- converted MyGdxGame to stage2d, converted other game modes to stage2d
 * 11/18/2014 - made buttons bigger, fixed buttons activating after win/fail bug, changed fail and win button colors, Added first game mode of color play level
 * 11/19/2014 -- created second color play level and transition to next game, began coding shape game
 * 11/20/2014 -- implemented spoons mode, added sound effects, added score subtraction when incorrect, edited player 1 after text to be p1bound.width/2 instead of p1w
 * 12/2/2014 -- Adjusted shape game to activate for rectangles, Added 6th game screen, run game. Looking to add second game mode
 * 12/3/2014 -- Retrieved assets for rocket game, added rocket class. Edited game titles to capitalize game goal. Worked on rocket animation flame
 * 12/4/2014 -- Edited Rocket animation and added sound effects. Transfered start game class to its own screen. Added game over screen and button to reset.
 * 12/9/2014 -- I'm 24 today. I added some in house graphics to the start screen, Edited score placement to center of buttons, added loading screen for loading music and textures
 * 12/10/2014 -- Attempted to fix a few bugs, finished added all assets to asset manager, adding variables to adjust button sizes, added pop up animation
 * 			 -- If game has one person, adjusted button to fill bottom of screen, adjusted games to display title in center first and removed initial get ready
 * 12/11/2014 -- Added second game mode to shape game. Added second game mode to run game. Added second rocket game mode. Added timings to spoon mode.
 * 			  -- Changed starting screen to use game manager, Added high scores button function and font area to display
 * 12/16/2014 -- Changed some assets to new ones. Changed single player to tap whole screen, fixed bug with detecting touches during title fade
 * 			  -- Added sounds from game loader. Added code to prevent close on back key. Close now goes to home.
 * 				Implemented high score loading and saving. Made modify x and y 100 from 73
 * 12/17/2014 -- Edited how bgMusic is played. fixed color mode high score reporting. Made buttons bigger. Changed win and fail images to new font.
 * 			  -- Changed next count down code to be more accurate. Added score grading and average score display. Changed name.
 * 12/18/2014 -- Changed x point of run game and game name. Changed speed of card game and random positions in mode 2. Changed bgMusic to MUSIC
 * 			  -- Implemented tie code. added more grade tiers 
 * 12/18/2014B -- Project migrated to home station. modified bgMusic playing and volume
 * 5/14/2015 -- github update file test
 *
 * 5/14/2015 -- github test branch created
 * 
 *           -- Looking to add sound and animation for fail/win -- MUSIC STILL STARTS WHEN GOING TO NEW SCREEN?
 *           -- Looking to add air push ads and public to play store
 *           -- 
 *    
 *          
 *   
 *		Bugs: [FIXED?]MEMORY ISSUE after replaying a couple of times, [FIXED?]Sometimes rocket blasts off before count down,
 *				[FIXED?]Shape reaction timing is off, [FIXED?]player 1 wins message not centered on mobile, buttons+font too small on mobile,
 *				[FIXED?]score doesn't reset after a play through,[FIXED]When everyone wins run Mode there is no win message,[FIXED?]keep back light on in android,
 *				 [FIXED]no sound on non spoons mode,[FIXED?]first color set doesn't register, need to center horizontally next count down,
 *				need to center winning upper scores on end game screen
 *				
 *           
 *          
 */

public class TrafficPlayScreen implements Screen {
	
	 public class titleActor extends Actor {
		 
		 public titleActor() {
			 
			 addListener(new InputListener(){
	                

					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {												// Reaction code
	                	
	                	if (Playing || level >= 2 ) {
	                		
	                		if (game.playerCount == 1 && !game.endGame && !fail && !win && gameMode != 0 ) {  // Single player reaction code for whole screen
	            				playerActivated = 1;
	            				touched = true;
	            				
	            				if(touched) {
	            					
	    	            			if (!React && playerActivated != 0) {
	    	            				if (game.soundEnabled)
	    	            					badSound.play();
	    	            				fail = true;
	    	            			}
	    	            				
	    	            			if (React && playerActivated != 0) {
	    	            				if (game.soundEnabled)
	    	            					goodSound.play();
	    	            				win = true;
	    	            			}
	    	            				
	    	            			
	    	            			touched = false;
	    	            			}
	                		}
	                		
	                	if ( !fail && !win && !spoonsMode && gameMode != 0 && game.playerCount != 1) {
	                		
	                	
	            			if(x >= 0 && x <= 200+buttonModifyX && y >= 0 && y <= 200+buttonModifyY) { 								// Player 1 clicked
	            				playerActivated = 1;
	            				touched = true;
	            			//	gameMode = 3;
	            			}
	            			if(x >=  1280-200-buttonModifyX && x <= 1280 &&
	            					y >=  0 && y <= 200+buttonModifyY && playerCount > 1) { 									// Player 2 clicked
	            				playerActivated = 2;
	            				touched = true;
	            			}
	            			if(x >=  1280-200-buttonModifyX && x <= 1280 &&
	            					y >=  800-200-buttonModifyY && playerCount > 2) {	 						// Player 3 clicked
	            				playerActivated = 3;
	            				touched = true;
	            			}
	            			if(x >= 0 && x <= 200+buttonModifyX && y >= 800-200-buttonModifyY && playerCount > 3) { 	 // Player 4 clicked
	            				playerActivated = 4;
	            				touched = true;
	            			}
	            			
	            			if(touched) {
	            					
	            			if (!React && playerActivated != 0) {
	            				if (game.soundEnabled)
	            					badSound.play();
	            				fail = true;
	            			}
	            				
	            			if (React && playerActivated != 0) {
	            				if (game.soundEnabled)
	            					goodSound.play();
	            				win = true;
	            			}
	            				
	            			
	            			touched = false;
	            			}

	            			// select player + win or fail flag
	                	}
	                	
	                	if (spoonsMode && gameMode != 0) {
	                		
		                	
	            			if(x >= 0 && x <= 200+buttonModifyX && y >= 0 && y <= 200+buttonModifyY && !p1touch && !win && !fail) { 								// Player 1 clicked
	            				if (React == true) {
	            					p1win = true;
	            //					p1score = reactCounter;
	            					game.p1Score.add(new Score(reactCounter,true,gameName,1));
	            					p1score = TimeUtils.nanoTime();
	            					if (game.soundEnabled)
	            					goodSound.play();
	            				}
	            					
	            				else {
	            					p1lose = true;
	            					if (game.soundEnabled)
	            					badSound.play();
	            				}
	            					
	            				
	            				p1touch = true;
	            				spoonsCount++;
	            			}
	            			if(x >=  1280-200-buttonModifyX && x <= 1280 &&
	            					y >=  0 && y <= 200+buttonModifyY && playerCount > 1 && !p2touch && !win && !fail) { 									// Player 2 clicked
	            				if (React == true) {
	            					p2win = true;
	            		//			p2score = reactCounter;
	            					game.p2Score.add(new Score(reactCounter,true,gameName,2));
	            					p2score = TimeUtils.nanoTime();
	            					if (game.soundEnabled)
	            					goodSound.play();
	            				}
	            				else {
	            					p2lose = true;
	            					if (game.soundEnabled)
	            					badSound.play();	
	            				}
	            					
	            				
	            				p2touch = true;
	            				spoonsCount++;
	            			}
	            			if(x >=  1280-200-buttonModifyX && x <= 1280 &&
	            					y >=  800-200-buttonModifyY && playerCount > 2 && !p3touch && !win && !fail) {	 						// Player 3 clicked
	            				if (React == true) {
	            					p3win = true;
	            		//			p3score = reactCounter;
	            					game.p3Score.add(new Score(reactCounter,true,gameName,3));
	            					p3score = TimeUtils.nanoTime();
	            					if (game.soundEnabled)
	            					goodSound.play();
	            				}
	            				else {
	            					p3lose = true;
	            					if (game.soundEnabled)
	            					badSound.play();
	            				}
	            					
	            				
	            				p3touch = true;
	            				spoonsCount++;
	            			}
	            			if(x >= 0 && x <= 200+buttonModifyX && y >= 800-200-buttonModifyY && playerCount > 3 && !p4touch && !win && !fail) { 	 // Player 4 clicked
	            				if (React == true) {
	            					p4win = true;
	            		//			p4score = reactCounter;
	            					game.p4Score.add(new Score(reactCounter,true,gameName,4));
	            					p4score = TimeUtils.nanoTime();
	            					if (game.soundEnabled)
	            					goodSound.play();
	            				}
	            				else {
	            					p4lose = true;
	            					if (game.soundEnabled)
	            					badSound.play();
	            				}
	            					
	            				
	            				p4touch = true;
	            				spoonsCount++;
	            			}
	    //        			
	     //       			if (!spoonsCompute)
	     //       				p1score = 0; // (spoonsCount == playerCount - 1) && !spoonsCompute && 
	            			
	            			if((spoonsCount == playerCount - 1)) {
	            				if ((p1win || p2win || p3win || p4win)){
	            					win = true;
	            				}
	            				else
	            					fail = true;
	            				
	            				
	            				
	            				if (!spoonsCompute) {
	            		//			spoonsScores = new ArrayList<Long>();
	            					spoonsScores.clear();
	            					
	            						if (p1score >= 1)
	            					spoonsScores.add(p1score);
	            						if (p2score >= 1)
	            					spoonsScores.add(p2score);
	            						if (p3score >= 1)
	            					spoonsScores.add(p3score);
	            						if (p4score >= 1)
	            					spoonsScores.add(p4score);
	            					
	            						if (!fail)
	            					lowest = Collections.min(spoonsScores);
	            						
	            				
	            				
	    //        				if (TimeUtils.millis() / 100 - (startTime)*10 <= 100)
	            					
	            					
	            					if (!fail) {
	            						if (lowest == p1score) {						// Add points if you were the fastest, indicated fastest player
		            						game.p1total = game.p1total + scoreBonus;
		            						playerActivated = 1;
	            						}
		            					if (lowest == p2score) {
		            						game.p2total = game.p2total + scoreBonus;
		            						playerActivated = 2;
		            					}	
		            					if (lowest == p3score) {
		            						game.p3total = game.p3total + scoreBonus;
		            						playerActivated = 3;
		            					}	
		            					if (lowest == p4score) {
		            						game.p4total = game.p4total + scoreBonus;
		            						playerActivated = 4;
		            					}
		            					
		            					if (p1score > 0)							// Add points if you reacted at all, correctly
		            						game.p1total = game.p1total + scoreReg;
		            					if (p2score > 0)
		            						game.p2total = game.p2total + scoreReg;
		            					if (p3score > 0)
		            						game.p3total = game.p3total + scoreReg;
		            					if (p4score > 0)
		            						game.p4total = game.p4total + scoreReg;
		            					
		            					if (p1lose)									// Subtract points if you reacted incorrectly
		            						game.p1total = game.p1total - scoreLose;
		            					if (p2lose)
		            						game.p2total = game.p2total - scoreLose;
		            					if (p3lose)
		            						game.p3total = game.p3total - scoreLose;
		            					if (p4lose)
		            						game.p4total = game.p4total - scoreLose;

	            					}
	            					
	            					if (fail) {
	            						if (p1lose)									// Subtract points if you reacted incorrectly
		            						game.p1total = game.p1total - scoreLose;
		            					if (p2lose)
		            						game.p2total = game.p2total - scoreLose;
		            					if (p3lose)
		            						game.p3total = game.p3total - scoreLose;
		            					if (p4lose)
		            						game.p4total = game.p4total - scoreLose;	            						
	            					}
	            					
	            					p1Bound = fontPlayers.getBounds(String.valueOf(game.p1total));
	            					p1Bound.width = s1w;
	            					p2Bound = fontPlayers.getBounds(String.valueOf(game.p2total));
	            					p2Bound.width = s2w;
	            					p3Bound = fontPlayers.getBounds(String.valueOf(game.p3total));
	            					p3Bound.width = s3w;
	            					p4Bound = fontPlayers.getBounds(String.valueOf(game.p4total));
	            					p4Bound.width = s4w;
	            					spoonsCompute = true;
	            				}
	            						
	            					
	            					
	            					

	            			}

	            			// select player + win or fail flag
	                	}
	                	}
	                	
	                	// END GAME CODE HERE
	                	
	//                	if() { 								// Goes to home screen
	                		if(x >= 1280/2-buttonExitG.getWidth()/2 && x <= (1280/2-buttonExitG.getWidth()/2)+buttonExitG.getWidth()  && y >= 0 && y <= buttonExitG.getHeight() && game.endGame) {            				
	                		activateResetGame = true;
            			}
	                										// Goes to first game screen
	                		if (x >= 1280/2-buttonReplay.getWidth()/2 && x <= (1280/2-buttonReplay.getWidth()/2)+buttonReplay.getWidth() && y >= 20+buttonExitR.getHeight() && y <= 20+buttonExitR.getHeight()+buttonReplay.getHeight() && game.endGame) {
	                			
	                			if (game.soundEnabled)
	    	    					goodSound.play();
	                			
	                			game.p1total = 0; game.p2total = 0; game.p3total = 0; game.p4total = 0;	                			
	                			game.p1Score = new Array<Score>();
	                			game.p2Score = new Array<Score>();
	                			game.p3Score = new Array<Score>();
	                			game.p4Score = new Array<Score>();
	                			
	    	    		        game.setScreen(new TrafficPlayScreen(game));
	    	    		        game.endGame = false;
	                		}
	            		
	            		
	            		
	                	
	                	
	                	
	                    return true;
	                }
	            });
			 
		 }
	        @Override
	        public void draw(Batch batch, float alpha){
	        	batch.draw(bg, 0, 0, 1280, 800);
	    		if (!fail && !win) {
	   	 // 		font.draw(batch, gameName, 1280/2 - tB, (800/8)*6f);
	    			font.setColor(1, 1, 1, titleAnim.getScale());
	    			font.draw(batch, gameName, 1280/2 - tB, (800/2)+50);
	    	//		font.draw(batch, String.valueOf(titleAnim.getScale()), 1280/2 - tB, (800/8)*6f);
	   //         batch.draw(TLnone, 1280/2, 100);
	    		}

	    		
	    		if (!game.endGame)
	    			drawButtons(batch);
	    		else {
	    			if (!activateResetGame) {
	    				batch.draw(buttonExitG, 1280/2-buttonExitG.getWidth()/2, 0);								//buttonP.draw(batch, 1280/2-200, 0, 400, 100);
	    				batch.draw(buttonReplay, 1280/2-buttonReplay.getWidth()/2, 20+buttonExitR.getHeight());
	    			}
	    			else {
	    				if (game.soundEnabled)
	    					goodSound.play();
	    				
	    				batch.draw(buttonExitR, 1280/2-buttonExitG.getWidth()/2, 0);				//buttonPW.draw(batch, 1280/2-200, 0, 400, 100);
	    																// RESET ENTIRE GAME CODE
	    			//	game.startGameScreen = new StartGameScreen(game);
	    		        game.setScreen(game.startGameScreen);
	    		        game.endGame = false;
	    			
	    			}
	    			
	    //			fontPlayers.draw(batch, "RESET", 1280/2-200+110, 70);
	    		}
	        	
	        	if (!Playing && level == 1) {   		
	      //  		startCountdown(batch);
	        	}
	        	if (Playing || level >= 2) {
	        		doGameMode(gameMode,Gdx.graphics.getDeltaTime(),batch);
	        		monitorGame(batch);
	        	}
	        	if (fail && !finish) { 																					// failure message when improper click, resets settings
		//			batch.draw(failure, 1280/2 - failure.getWidth()/2, 800/2 - failure.getHeight()/2);
	        		fontFancy.drawWrapped(batch, "[RED]FAILURE![]", 100, 470, 1080, BitmapFont.HAlignment.CENTER);
					nextCountdown(batch);
				}
	        	if (finish) {
	        //		nextCountdown(batch);
	        		doGameMode(gameMode,Gdx.graphics.getDeltaTime(),batch);
	        	}
	        	
//	        	
	    		
	    		
	        		
	        	
	        	
	        }
	 }
	
	public MyGdxGame game;
	SpriteBatch batch;
	Texture tapwhen;
	Texture TLnone;
	Texture TLgreen;
	Texture TLyellow;
	Texture TLred;
	Texture failure;
	Texture bg;
	Texture nice;
	Texture buttonExitG, buttonExitR;
	Texture buttonReplay, buttonReplayG;
	Texture left,leftS,right,rightS;
	
	Boolean Debug = false;
	Boolean Playing;
	Boolean React;
	Boolean timeset = false;
	Boolean fail;
	Boolean win;
	Boolean scoreAdded;
	Boolean touched;
	Boolean Reset;
	Boolean counterStarted;
	Boolean firstCountdown;
	Boolean secondCountdown;
	Boolean spoonsCompute;
	Boolean finish;
	boolean activateResetGame;
	boolean beginGame;
	boolean titleFired;
	boolean colorCustomMode;
	boolean newScoreAlert;
	boolean playBG;
	

	Boolean p1touch,p2touch,p3touch,p4touch;
	Boolean p1win,p2win,p3win,p4win;
	Boolean p1lose,p2lose,p3lose,p4lose;
	Boolean spoonsMode;
	
	
	
	
	BitmapFont font;
	BitmapFont fontScore;
	BitmapFont fontPlayers;
	BitmapFont fontGame, fontFancy;

	long startTime;
	float reactDuration;
	long reactCounter;
	long counterStartTime;
	float reactScore;
	float deltaCounter;
	long p1score; long p2score; long p3score; long p4score;
	long lowest;
	
	int playerActivated;	
	int touchX;
	int touchY;
	int playerCount;
	int gameMode;
	int level;
	int spoonsCount;
	
	public Array<Texture> threeSet = new Array<Texture>();
	public ArrayList<Long> spoonsScores = new ArrayList<Long>();
	
	NinePatch buttonP;	
	NinePatch buttonPF;
	NinePatch buttonPW;
	NinePatch buttonPR;
	TextureAtlas buttonsAtlas;
	TextBounds p1Bound;
	float p1w;
	float w1B; float w2B;
	float tB;
	float sB;
	float s1w,s2w,s3w,s4w;
	TextBounds p2Bound;
	TextBounds p3Bound;
	TextBounds p4Bound;
	TextBounds win1Bound;
	TextBounds win2Bound;
	TextBounds titleBound;
	TextBounds scoreBound;
	
	public Viewport viewport;
	public Camera camera;
	Stage stage;
	public boolean mode2Set;
	public int randomLight;
	
	float titleScale;
	
	String gameName;
	titleActor myActor;
	
	
	int scoreBonus = 25;
	int scoreReg = 100;
	int scoreLose = 25;
	
	float buttonModifyX = 170;
	float buttonModifyY = 170;

	
	Sound goodSound;
	Sound badSound, winSound;
	
	TweenGraphic titleAnim;
	TweenManager titleManager;
	
	Sound highscoreSound;
	
	Sound bgMusicold;
	Music bgMusic;
	
	
	
	TweenCallback titleCallBack = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			if(titleAnim.getScale() == 0) {
				gameMode = 1;
				Playing = true;
				
				if (colorCustomMode) {
					gameMode = 2;
					if (level == 1 || level == 2 || level == 5 || level == 6 || level == 7 || level == 3)
						gameMode = 3;
				}
					
			}
				

				return;
		}	
	};

	public TrafficPlayScreen(MyGdxGame gam) {
		game = gam;
		batch = new SpriteBatch();
		
		tapwhen = game.manager.get("tapWhenGreen.png", Texture.class);
		
		TLnone = game.manager.get("TLnone.png", Texture.class);
		TLgreen = game.manager.get("TLgreen.png", Texture.class);
		TLyellow = game.manager.get("TLyellow.png", Texture.class);
		failure = game.manager.get("failure.png", Texture.class);
		TLred = game.manager.get("TLred.png", Texture.class);
		bg = game.manager.get("bg.jpg", Texture.class);
		nice = game.manager.get("nice.png", Texture.class);
		
		buttonExitG = game.manager.get("button_exit_green.png", Texture.class);
		buttonExitR = game.manager.get("button_exit_red.png", Texture.class);
		buttonReplay = game.manager.get("button_replay.png", Texture.class);
		buttonReplayG = game.manager.get("button_replay_green.png", Texture.class);
		left = game.manager.get("arrowLeft.png", Texture.class);
		right = game.manager.get("arrowRight.png", Texture.class);
		leftS = game.manager.get("arrowLeft_s.png", Texture.class);
		rightS = game.manager.get("arrowRight_s.png", Texture.class);
		
		
//		font = game.manager.get("fontshadow0.fnt", BitmapFont.class);
//		fontScore = game.manager.get("comicsans.fnt", BitmapFont.class);
//		fontPlayers = game.manager.get("fontreg0.fnt", BitmapFont.class);
		
		bgMusic = game.manager.get("bg2fadeout.ogg", Music.class);
		goodSound = game.manager.get("good.ogg", Sound.class);
		badSound = game.manager.get("bad.ogg", Sound.class);
		highscoreSound = game.manager.get("highscore.ogg", Sound.class);
		winSound = game.manager.get("win.ogg", Sound.class);
		

		fail = false;
		win = false;
		React = false;
		Playing = false;
		scoreAdded = false;
		
		
		font = game.manager.get("fontshadow1.fnt", BitmapFont.class);
		fontFancy = game.manager.get("fontFancy1.fnt", BitmapFont.class);
		fontScore = game.manager.get("comicsans.fnt", BitmapFont.class);
		fontPlayers = game.manager.get("fontreg0.fnt", BitmapFont.class);
		fontGame = game.manager.get("arialblack.fnt"); // USED FOR COLOR GAME

		
	//	fontScore = new BitmapFont(Gdx.files.internal("comicsans.fnt"),Gdx.files.internal("comicsans_0.png"), false); // for debug
	//	fontPlayers = new BitmapFont(Gdx.files.internal("fontreg0.fnt"),Gdx.files.internal("fontreg0.png"), false);
		
		

		fontScore.setScale(0.3f);
		font.setScale(0.7f);

		
		p1Bound = fontPlayers.getBounds("Player 1");
		p1w = p1Bound.width/2;
		
		p2Bound = fontPlayers.getBounds("Player 2");
		p3Bound = fontPlayers.getBounds("Player 3");
		p4Bound = fontPlayers.getBounds("Player 4");
		
		win1Bound = font.getBounds("Player 1 was the fastest!");
		w1B = win1Bound.width/2;
		
		win2Bound = font.getBounds("Player 1 wins the game!");
		w2B = win2Bound.width/2;
		
		titleBound = font.getBounds("Tap when GREEN");
		tB = titleBound.width/2;
		
		scoreBound = font.getBounds("250.0000 ms!");
		sB = scoreBound.width/2;
		
		
		
		
		
		playerCount = game.playerCount;
		reactCounter = 0;
		counterStartTime = 0;
		counterStarted = false;
		firstCountdown = false;
		secondCountdown = false;
		
		level = 1;
				
		
		startTime = TimeUtils.millis() / 1000;
		reactDuration = MathUtils.random(1f, 4f);
		buttonsAtlas = game.manager.get("buttons.pack");
		buttonP = buttonsAtlas.createPatch("buttonNormal");
		buttonPF = buttonsAtlas.createPatch("buttonPressed");
		buttonPW = buttonsAtlas.createPatch("buttonGreen");
		buttonPR =  buttonsAtlas.createPatch("buttonRed");
		playerActivated = 0;
		Reset = false;
		touched = false;
		gameMode = 0;
		threeSet.add(TLred);
		threeSet.add(TLyellow);
		threeSet.add(TLgreen);
		threeSet.add(TLnone);
		deltaCounter = 0;
		gameName = "Tap when GREEN";
		
		camera = new PerspectiveCamera();
	    viewport = new StretchViewport(800, 600, camera);
		
		stage = new Stage(new StretchViewport(1280, 800));
		
		myActor = new titleActor();		
		myActor.setWidth(1280);
		myActor.setHeight(800);
		myActor.setBounds(0, 0, 1280, 800);
		stage.addActor(myActor);
		
		//Spoons mode booleans
		spoonsMode = game.spoonsMode;
		p1touch = false; p2touch = false; p3touch = false; p4touch = false;
		p1win = false; p2win = false; p3win = false; p4win = false;
		p1lose = false; p2lose = false; p3lose = false; p4lose = false;
		spoonsCount = 0;
		
		p1score = 0; p2score = 0; p3score = 0; p4score = 0;
		lowest = 0;
		spoonsCompute = false;
		finish = false;
		beginGame = false;
		titleFired = false;

		if (game.playerCount < 2) {
			buttonModifyX = 1080;
			buttonModifyY = 0;
		}
		
		titleAnim = new TweenGraphic();
		titleManager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		
		titleAnim.setScale(0f);
		colorCustomMode = false;
		
		buttonP.setColor(new Color(1f, 1f, 1f, .3f));
		buttonPW.setColor(new Color(1f, 1f, 1f, .8f));
		buttonPR.setColor(new Color(1f, 1f, 1f, .8f));
		
		newScoreAlert = false;
		
		bgMusic.setLooping(true);
		bgMusic.setVolume(.1f);
		playBG = false;
		Gdx.input.setCatchBackKey(true);
		fontFancy.setMarkupEnabled(true);
		font.setMarkupEnabled(true);
	}

	@Override
	public void render(float delta) {
		titleManager.update(delta);
//		titleAnim.setScale(delta);
		

		if (!titleFired && !finish)
		{
			doTitleAnimation();
			titleFired = true;
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	//	batch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f); 																// Ready message fonts

		Gdx.input.setInputProcessor(stage);
		stage.draw();
		
		
		
		
	}
	
	public void drawButtons(Batch batch) {
		
if (game.playerCount != 1)		{
	
	if(playerActivated == 0 || spoonsMode) {
		buttonP.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY);																// Player 1 nine patch image
		
		if(playerCount > 1) {
		buttonP.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 2 nine patch image
		}
		
		if(playerCount > 2) {
		buttonP.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY); 									// Player 3 nine patch image

		}
		
		if(playerCount > 3) {
		buttonP.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);																// Player 4 nine patch image
		}
	}

		
	if (!spoonsMode) {
			
		
		if(playerActivated == 1) {
				if (win)
			buttonPW.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY);																// Player 1 nine patch image
				else
					buttonPR.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY);
			fontPlayers.draw(batch, "Player 1", (200+buttonModifyX)/2 - p1w,
					((200+buttonModifyY)/2 - p1Bound.height/2)+p1Bound.height);													// Player 1 text
		}
		if(playerActivated == 2) {
				if (win)
			buttonPW.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 2 nine patch image
				else
					buttonPR.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY);
			fontPlayers.draw(batch, "Player 2", ((200+buttonModifyX)/2 - p1w)+1280-200-buttonModifyX, 
					((200+buttonModifyY)/2 - p2Bound.height/2)+p2Bound.height+0); 												// Player 2 text
		}
		if(playerActivated == 3) {
				if (win)
			buttonPW.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY); 									// Player 3 nine patch image
				else
					buttonPR.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);
			fontPlayers.draw(batch, "Player 3", ((200+buttonModifyX)/2 - p1w)+1280-200-buttonModifyX, 
					((200+buttonModifyY)/2 - p3Bound.height/2)+p3Bound.height+800-200-buttonModifyY); 											// Player 3 text
		}
		if(playerActivated == 4) {
				if (win)
			buttonPW.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);																// Player 4 nine patch image
				else
					buttonPR.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);	
			fontPlayers.draw(batch, "Player 4", (200+buttonModifyX)/2 - p1w,
					((200+buttonModifyY)/2 - p4Bound.height/2)+p4Bound.height+800-200-buttonModifyY); 											// Player 4 text
		}
	}
	
}
	
if (spoonsMode) {
			
		if(p1win) {
			buttonPW.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY);																// Player 1 nine patch image SPOONS WIN
		}
		if(p1lose) {
			buttonPR.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY);																// Player 1 nine patch image SPOONS LOSE
		}
		if(p2win) {
			buttonPW.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 2 nine patch image SPOONS
		}
		if(p2lose) {
			buttonPR.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 2 nine patch image SPOONS
		}
		if(p3win) {
			buttonPW.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY); 									// Player 3 nine patch image SPOONS
		}
		if(p3lose) {
			buttonPR.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY); 									// Player 3 nine patch image SPOONS
		}
		if(p4win) {
			buttonPW.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);																// Player 4 nine patch image SPOONS
		}
		if(p4lose) {
			buttonPR.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);																// Player 4 nine patch image SPOONS
		}
	}

	if((playerActivated == 0 && !spoonsMode || (spoonsMode && !win && !fail)) && game.playerCount != 1) {
		fontPlayers.draw(batch, "Player 1", (200+buttonModifyX)/2 - p1w,
		((200+buttonModifyY)/2 - p1Bound.height/2)+p1Bound.height);													// Player 1 text

		if(playerCount > 1) {
			fontPlayers.draw(batch, "Player 2", ((200+buttonModifyX)/2 - p2Bound.width/2)+1280-200-buttonModifyX, 
		((200+buttonModifyY)/2 - p2Bound.height/2)+p2Bound.height+0); 												// Player 2 text
		}

		if(playerCount > 2) {
			fontPlayers.draw(batch, "Player 3", ((200+buttonModifyX)/2 - p3Bound.width/2)+1280-200-buttonModifyX, 				// Player 3 text
		((200+buttonModifyY)/2 - p3Bound.height/2)+p3Bound.height+800-200-buttonModifyY);

		}

		if(playerCount > 3) {
			fontPlayers.draw(batch, "Player 4", (200+buttonModifyX)/2 - p4Bound.width/2,
		((200+buttonModifyY)/2 - p4Bound.height/2)+p4Bound.height+800-200-buttonModifyY); 											// Player 4 text
		}
	}	


		
		if (Debug) {
			debugReaction(batch);
			debugScores(batch);	
		}
		
		return;
	}
	
	public void startCountdown(Batch batch) {
		if (!firstCountdown) {
			startTime = TimeUtils.millis() / 1000;
			firstCountdown = true;
		}
		
		font.draw(batch, "Get Ready!", 1280/2 - font.getBounds("Get Ready!").width/2, 800/2); //350

		String countdown = String.valueOf(3 - (TimeUtils.millis() / 1000 - startTime));
		if(!countdown.equalsIgnoreCase("0")){
			font.draw(batch, countdown, 1280/2 - font.getBounds("3").width/2, 800/2-80);		// 270	
		} else {
			Playing = true;
		}
				
//		drawTitle();
		return;
	}
	
	public void getReaction() {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){												// Reaction code

			touchX = Gdx.input.getX();
			touchY = Gdx.graphics.getHeight()-Gdx.input.getY();
			
			if(touchX >= 0 && touchX <= 200 && touchY >= 0 && touchY <= 100) { 								// Player 1 clicked
				playerActivated = 1;
				touched = true;
			}
			if(touchX >=  Gdx.graphics.getWidth()-200 && touchX <= Gdx.graphics.getWidth() &&
					touchY >=  0 && touchY <= 100 && playerCount > 1) { 									// Player 2 clicked
				playerActivated = 2;
				touched = true;
			}
			if(touchX >=  Gdx.graphics.getWidth()-200 && touchX <= Gdx.graphics.getWidth() &&
					touchY >=  Gdx.graphics.getHeight()-100 && playerCount > 2) {	 						// Player 3 clicked
				playerActivated = 3;
				touched = true;
			}
			if(touchX >= 0 && touchX <= 200 && touchY >= Gdx.graphics.getHeight()-100 && playerCount > 3) { 	 // Player 4 clicked
				playerActivated = 4;
				touched = true;
			}
			
			if(touched) {
					
			if (!React && playerActivated != 0)
				fail = true;
			if (React && playerActivated != 0)
				win = true;
			
			touched = false;
			}

			// select player + win or fail flag
		}
		return;
		
	}
	
	public void doGameMode(int m, float d, Batch batch) {
		
		float delta = d;
		int mode = m;
		
		
		if(mode == 1) {																						// Mode 1: Display blank traffic light then switch to green
			
			
			
			if (!fail && reactDuration >= 0) { 																// Display initial blank traffic light
//				batch.draw(tapwhen, Gdx.graphics.getWidth()/2 - tapwhen.getWidth()/2, (Gdx.graphics.getHeight()/4)*3);
//				drawTitle();
				
				
				batch.draw(TLnone, 1280/2 - TLnone.getWidth()/2, (800/2 - TLnone.getHeight()/2));
				
				
				reactDuration -= delta;
			}
			
			if (!fail && reactDuration <= 0 && !win) { 														// Display green traffic light and begin counting
//				drawTitle();
				
				if (!counterStarted) {
					counterStartTime = TimeUtils.nanoTime();
				}
				counterStarted = true;
				batch.draw(TLgreen, 1280/2 - TLgreen.getWidth()/2, (800/2 - TLnone.getHeight()/2));
//				reactCounter += delta;
//				reactCounter += ((TimeUtils.nanoTime() - counterStartTime));
				reactCounter = TimeUtils.timeSinceNanos(counterStartTime);
				React = true;
			}
			
		}
		if (mode == 2) {
			resetTraffic();
		}
		if(mode == 3) { // Flash a random traffic light for .3 seconds then go to blank, loop
			
			if(!mode2Set) {
				deltaCounter = 0;
				mode2Set = true;
			}
			
			if (!fail && !win ) {
//				drawTitle();
				if (deltaCounter <= .4) {
					batch.draw(threeSet.get(randomLight), 1280/2 - TLnone.getWidth()/2, (800/2 - TLnone.getHeight()/2)); //170 is old y
					
//debug					font.draw(batch, "R is "+String.valueOf(React)+" rL is "+String.valueOf(randomLight), 20, 150);
					
					if (randomLight == 2) {
						if (!counterStarted) {
							counterStartTime = TimeUtils.nanoTime();
							counterStarted = true;
					}
						React = true;											
						reactCounter = TimeUtils.timeSinceNanos(counterStartTime);			
					} else {
						React = false;
						counterStarted = false;
					}
						
//					font.draw(batch, "deltaCounter is "+String.valueOf(deltaCounter)+"!", 220, 350);
				} else {
					batch.draw(threeSet.get(randomLight), 1280/2 - TLnone.getWidth()/2, (800/2 - TLnone.getHeight()/2));
					randomLight = MathUtils.random(3);
//					batch.draw(failure, Gdx.graphics.getWidth()/2 - failure.getWidth()/2, 270);
					deltaCounter = 0;
				}
			
				
					
				deltaCounter += delta;
				
			}
		}
		
		if(mode == 4) {
			game.nextGame(level);
		}	
		
		
		
		
		
	}
	
	public void monitorGame(Batch batch) {
		if (win) { 																						// Display win message, resets settings
			
//			batch.draw(nice, 640 - nice.getWidth()/2, 600 - nice.getHeight()/2);
			fontFancy.drawWrapped(batch, "NICE JOB!", 100, 650, 1080, BitmapFont.HAlignment.CENTER);
//			font.draw(batch, "X is "+String.valueOf(touchX)+" Y is "+String.valueOf(touchY), 50, 250);
			
			
//			if (!scoreAdded) { // Add high score to counter
//				game.p1Score.add(new Score(reactCounter,true,"Traffic Light",playerActivated));
//				scoreAdded = true;
//			}
			
			doScore();
			
			
			
//			String stats = game.p1Score.first().returnAllStats();
				
			fontScore.setScale(0.3f);
			int decender = 0;
			
//			font.draw(batch, "Player "+String.valueOf(playerActivated)+" Wins!", Gdx.graphics.getWidth()/2- w1B, 350);
			if (game.playerCount != 1)
				font.draw(batch, "Player "+String.valueOf(playerActivated)+" was the fastest!", 1280/2- w1B, 450);
			else
				font.draw(batch, "Your reaction time was:", 1280/2- w1B, 450);
			
			if (newScoreAlert) {
				font.drawWrapped(batch, "[YELLOW]NEW HIGHSCORE!!!!![]", 100, 200, 1080, BitmapFont.HAlignment.CENTER);
			}
			
			if (!spoonsMode)
			font.draw(batch, "[GREEN]"+String.format("%.4f", game.p1Score.peek().returnReactionTime())+" ms![]", 1280/2 - sB, 450-nice.getHeight());
			
			if(spoonsMode) {
				if (playerActivated == 1)
					font.draw(batch, "[GREEN]"+String.format("%.4f", game.p1Score.peek().returnReactionTime())+" ms![]", 1280/2 - sB, 450-nice.getHeight());
				if (playerActivated == 2)
					font.draw(batch, "[GREEN]"+String.format("%.4f", game.p2Score.peek().returnReactionTime())+" ms![]", 1280/2 - sB, 450-nice.getHeight());
				if (playerActivated == 3)
					font.draw(batch, "[GREEN]"+String.format("%.4f", game.p3Score.peek().returnReactionTime())+" ms![]", 1280/2 - sB, 450-nice.getHeight());
				if (playerActivated == 4)
					font.draw(batch, "[GREEN]"+String.format("%.4f", game.p4Score.peek().returnReactionTime())+" ms![]", 1280/2 - sB, 450-nice.getHeight());
			}
			
			
			
			for (Score temp : game.p1Score) {
//				fontScore.draw(batch, temp.returnAllStats()+" "+String.valueOf(game.p1Score.size), 100, 350 - decender);
//				fontScore.draw(batch, temp.returnAllStats()+" ", 30, 100 - decender);
				decender += 50;
			}
			

//			fontFade.draw(batch, stats, 100, 350);
			

				nextCountdown(batch);
			
			
			
			
		
		}
		
		if(Reset) {
			resetGame();
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			if (game.soundEnabled)
				bgMusic.stop();
			
			game.setScreen(game.startGameScreen);
			}
		
		if (game.soundEnabled && !playBG) {
			bgMusic.play();
			
			playBG = true;
		}
		

		
		return;
		
	}
	
	public void resetGame(){
		fail = false;
		win = false;
		React = false;
		reactDuration = MathUtils.random(1f, 4f);
		reactCounter = 0;
		scoreAdded = false;
		Reset = false;
		playerActivated = 0;
		touched = false;
		counterStarted = false;
		counterStartTime = 0;
		randomLight = MathUtils.random(2);
		deltaCounter = 0;
		newScoreAlert = false;
//		playBG = false;
//		bgMusic.stop();
		if (spoonsMode) {
			p1touch = false; p2touch = false; p3touch = false; p4touch = false;
			p1win = false; p2win = false; p3win = false; p4win = false;
			p1lose = false; p2lose = false; p3lose = false; p4lose = false;
			
			p1score = 0; p2score = 0; p3score = 0; p4score = 0;
			
			spoonsCount = 0;
			spoonsCompute = false;
		}
		return;
	}
	
	public void nextCountdown(Batch batch){
		if (!secondCountdown) {
			doScore();
			startTime = TimeUtils.millis();
			secondCountdown = true;
		}
		bgMusic.stop();
		String countdown = String.valueOf(3000 - (TimeUtils.millis()- startTime)); // 3000 is going down by elapsed time
		if((3000 - (TimeUtils.millis() - startTime)) <= 3000 && (3000 - (TimeUtils.millis() - startTime)) >= 2000)
			font.draw(batch, "[YELLOW]3[]", 1280/2-15, 100);		
		if((3000 - (TimeUtils.millis() - startTime)) <= 2000 && (3000 - (TimeUtils.millis() - startTime)) >= 1000)
				font.draw(batch, "[YELLOW]2[]", 1280/2-15, 100);		
		if((3000 - (TimeUtils.millis() - startTime)) <= 1000 && (3000 - (TimeUtils.millis() - startTime)) >= -1000)
				font.draw(batch, "[YELLOW]1[]", 1280/2-15, 100);	
		if((3000 - (TimeUtils.millis() - startTime)) <= 0){
				Reset = true;
				gameMode++;
				secondCountdown = false;
				newScoreAlert = false;
				playBG = false;
//				if (game.soundEnabled && gameMode != 4 && !bgMusic.isPlaying())
	//				bgMusic.play();
		}
		
		if (spoonsMode)
			drawSpoonScore(batch);
		return;
	}
	
	public void OLDnextCountdown(Batch batch){
		if (!secondCountdown) {
			doScore();
			startTime = TimeUtils.millis() / 1000;
			secondCountdown = true;
		}
		bgMusic.stop();
		String countdown = String.valueOf(3 - (TimeUtils.millis() / 1000 - startTime));
		if(!countdown.equalsIgnoreCase("0") && (3 - (TimeUtils.millis() / 1000 - startTime)) >= 0){
			font.draw(batch, countdown, 1280/2, 100);			
		} else {
				Reset = true;
				gameMode++;
				secondCountdown = false;
				newScoreAlert = false;

		}
		
		if (spoonsMode)
			drawSpoonScore(batch);
		return;
	}
	
	public void drawToggles(Batch batch) {
		batch.draw(buttonReplay, (1280/2 - buttonReplay.getWidth()/2), 0);
		batch.draw(right, 1280-right.getWidth(), 0);
		batch.draw(left, 0, 0);
		
	}
	

	
	public void drawTitle() {
		font.draw(batch, "TAP WHEN GREEN", Gdx.graphics.getWidth()/2 - tB, (Gdx.graphics.getHeight()/8)*6);
		return;
	}
	
	public void doScore() {
		
		if (!scoreAdded && !spoonsMode) { // Add high score to counter
			if (fail) {
		//		game.p1Score.add(new Score(reactCounter,false,gameName,playerActivated));

			}
			if (win) {
				game.p1Score.add(new Score(reactCounter,true,gameName,playerActivated));		
				if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameName, 10000f)) {
					game.scorePref.putFloat(gameName, game.p1Score.peek().returnReactionTime());
					newScoreAlert = true;
					if (game.soundEnabled)
						highscoreSound.play();
				}
				else
					newScoreAlert = false;
			}
			game.scorePref.flush();
			
		}
		
		if (!scoreAdded && game.spoonsMode) {
			if (playerActivated == 1) {
				if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameName, 10000f)) {
					game.scorePref.putFloat(gameName, game.p1Score.peek().returnReactionTime());
					newScoreAlert = true;
					if (game.soundEnabled)
						highscoreSound.play();
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 2) {
				if (game.p2Score.peek().returnReactionTime() < game.scorePref.getFloat(gameName, 10000f)) {
					game.scorePref.putFloat(gameName, game.p2Score.peek().returnReactionTime());
					newScoreAlert = true;
					if (game.soundEnabled)
						highscoreSound.play();
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 3) {
				if (game.p3Score.peek().returnReactionTime() < game.scorePref.getFloat(gameName, 10000f)) {
					game.scorePref.putFloat(gameName, game.p3Score.peek().returnReactionTime());
					newScoreAlert = true;
					if (game.soundEnabled)
						highscoreSound.play();
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 4) {
				if (game.p4Score.peek().returnReactionTime() < game.scorePref.getFloat(gameName, 10000f)) {
					game.scorePref.putFloat(gameName, game.p4Score.peek().returnReactionTime());
					newScoreAlert = true;
					if (game.soundEnabled)
						highscoreSound.play();
				}
				else
					newScoreAlert = false;
			}
			game.scorePref.flush();
		}
		scoreAdded = true;
		
	}
	
	public void debugScores(Batch batch) {
		int decender = 0;
		
		for (Score temp : game.p1Score) {
	//		fontScore.draw(batch, temp.returnAllStats()+" "+String.valueOf(game.p1Score.size), 100, Gdx.graphics.getHeight()-100 - decender);
	//		fontScore.draw(batch, temp.returnAllStats()+" ", 30, Gdx.graphics.getHeight()-100 - decender);
			decender += 50;
		}
	}
	
	public void debugReaction(Batch batch) {
		fontScore.draw(batch, "React Duration is "+String.valueOf(reactDuration), 380, 50);
		fontScore.draw(batch, "React Counter is "+String.valueOf(reactCounter), 380, 30);
		
	//	fontScore.draw(batch, "SpoonScores size is "+String.valueOf(spoonsScores.size()), 380, 50);
	}
	
	public void drawSpoonScore(Batch batch) {
					
		
	//	fontPlayers.draw(batch, String.valueOf(game.p1total), 200/2 - p1Bound.width/2,
	//	(200/2 - p1Bound.height/2)+p1Bound.height);													// Player 1 text
		
		fontPlayers.drawWrapped(batch, String.valueOf(game.p1total), 0, 120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);

		if(playerCount > 1) {
			fontPlayers.drawWrapped(batch, String.valueOf(game.p2total), 1280-200-buttonModifyX, 120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}

		if(playerCount > 2) {			
			fontPlayers.drawWrapped(batch, String.valueOf(game.p3total), 1280-200-buttonModifyX, 800-200-buttonModifyY/2+120, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}

		if(playerCount > 3) {
			fontPlayers.drawWrapped(batch, String.valueOf(game.p4total), 0, 800-200-buttonModifyY/2+120, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
			
//			fontPlayers.draw(batch, String.valueOf(game.p4total), 200/2 - p4Bound.width/2,
//		(200/2 - p4Bound.height/2)+p4Bound.height+800-200); 											// Player 4 text
		}
	}
	
	public void doTitleAnimation() {
		titleManager.add(Timeline.createSequence() //  fade in title
				.push(Tween.to(titleAnim, GraphicAnimator.SCALE,1f).target(1))
			    .repeatYoyo(1,1f)
			    .setCallback(titleCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(titleManager));
		
//		titleAnim.setScale(4);
	}
	
	public void resetTraffic() {
		gameName = "Tap when GREEN (2)";
		titleBound = font.getBounds(gameName);
		tB = titleBound.width/2;
		gameMode = 0;
		titleFired = false;
		colorCustomMode = true;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
//		viewport.update(width, height);

		
	}

	@Override
	public void show() {
		
		// TODO Auto-generated method stub
		
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
		 buttonsAtlas.dispose();
		 stage.dispose();

	}

}
