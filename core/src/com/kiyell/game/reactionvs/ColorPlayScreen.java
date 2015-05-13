package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class ColorPlayScreen extends TrafficPlayScreen {
	
	String colorSelected;
	String colorDisplay;
	String gameNameScore;
	public Array<String> colorGroupNames = new Array<String>();
	public Array<Color> colorGroupValues = new Array<Color>();
	TextBounds gameBound;
	Color red,orange,yellow,green,blue,purple;
	private Color currentColor;
	Boolean slept;
	Boolean startManager;
	
	public TweenManager manager;
	TweenGraphic colorAnim;
	TweenGraphic colorAnim2;
	private boolean flipFired;
	private boolean setMode2;
	
	TweenCallback myCallBack = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			newColor();
			
			if (currentColor == targetColor)
				React = true;
			else
				React = false;

			return;
		}	
	};
	
	TweenCallback myCallBack2 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			newColor();
			
			if (colorDisplay.equals(colorSelected))
				React = true;
			else
				React = false;

			return;
		}	
	};
	private Color targetColor;
	

	public ColorPlayScreen(MyGdxGame gam) {
		super(gam);
		slept = false;
		startManager = false;
		level = 4;		
		colorGroupNames.add("RED");
		colorGroupNames.add("ORANGE");
		colorGroupNames.add("YELLOW");
		colorGroupNames.add("GREEN");
		colorGroupNames.add("BLUE");
		colorGroupNames.add("PURPLE");
		red = new Color(Color.RED);
		orange = new Color(Color.ORANGE);
		yellow = new Color(Color.YELLOW);
		green = new Color(Color.GREEN);
		blue = new Color(Color.BLUE);
		purple = new Color(Color.PURPLE);
		colorGroupValues.add(red);
		colorGroupValues.add(orange);
		colorGroupValues.add(yellow);
		colorGroupValues.add(green);
		colorGroupValues.add(blue);
		colorGroupValues.add(purple);
		setMode2 = false;
		
		colorSelected = colorGroupNames.random();
		
		newColor();
		setColor();
		
		while (currentColor == targetColor)					// Game can not begin with current color being the target
			currentColor = colorGroupValues.random();
		
		
		colorAnim = new TweenGraphic();
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		colorAnim.setPosition(0, -200);
		
		

		gameBound = fontGame.getBounds(colorSelected);
		
		gameName = "Tap when COLOR is "+colorSelected;
		gameNameScore = "Tap when COLOR is COLOR";
		titleBound = font.getBounds(gameName);
		tB = titleBound.width/2;
		
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//		fontGame.setScale(2f);
		flipFired = false;
	}
	

	public void doGameMode(int m, float d,Batch batch) {
		

			
		
		if(!slept) {
			

			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			        startManager = true;
			        slept = true;
			    }
			}, 1);
		}
			
		
		if (!fail && !win && m == 1 && m != 0) {
			
			fontGame.setColor(currentColor);
			fontGame.draw(batch, colorDisplay, 1280/2-gameBound.width/2, colorAnim.getY());
			manager.update(d);
			
			if (!counterStarted && React == true) {
				counterStartTime = TimeUtils.nanoTime();
				counterStarted = true;
			}
			
			if (React == true) {
				reactCounter = TimeUtils.timeSinceNanos(counterStartTime);
			}
			else {
				React = false;
				counterStarted = false;
			}
			
			if (!flipFired && startManager) {
				doColorAnimation();
				flipFired = true;
			}
								
		}
		
		if (!fail && !win && m == 2) {
			
			
			
			setMode2();
			
			
			fontGame.setColor(currentColor);
			fontGame.draw(batch, colorDisplay, 1280/2-gameBound.width/2, colorAnim2.getY());
			manager.update(d);
			
			if (!counterStarted && React == true) {
				counterStartTime = TimeUtils.nanoTime();
				counterStarted = true;
			}
			
			if (React == true) {
				reactCounter = TimeUtils.timeSinceNanos(counterStartTime);
			}
			else {
				React = false;
				counterStarted = false;
			}
			
			if (!flipFired && startManager) {
				doColorAnimation2();
				flipFired = true;
			}
			
			
		}
		
		if (fail || win) {
			colorAnim = new TweenGraphic();
			manager = new TweenManager();
			colorAnim.setPosition(0, -200);
			setColor();
			newColor();
			flipFired = false;
		}
		
		if (m == 3) {
			game.nextGame(level);
		}
	
	}
	
	private void doColorAnimation() {
		
		manager.add(Timeline.createSequence() 
				.push(Tween.to(colorAnim, GraphicAnimator.POS_XY, .3f).target(0, (800/2)+45).ease(TweenEquations.easeInOutElastic))
				.pushPause(.4f)
				.push(Tween.to(colorAnim, GraphicAnimator.POS_XY, .2f).target(0, 200+800))
			    .repeat(100,0f)
			    .setCallback(myCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
	}
	
	private void doColorAnimation2() {
		
		manager.add(Timeline.createSequence() 
				.push(Tween.to(colorAnim2, GraphicAnimator.POS_XY, .3f).target(0, (800/2)+45).ease(TweenEquations.easeInOutElastic))
				.pushPause(.4f)
				.push(Tween.to(colorAnim2, GraphicAnimator.POS_XY, .2f).target(0, 200+800))
			    .repeat(100,0f)
			    .setCallback(myCallBack2)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
	}

	public void setColor() {
		if(colorSelected.equals("RED"))
			targetColor = red;
		if(colorSelected.equals("ORANGE"))
			targetColor = orange;
		if(colorSelected.equals("YELLOW"))
			targetColor = yellow;
		if(colorSelected.equals("GREEN"))
			targetColor = green;
		if(colorSelected.equals("BLUE"))
			targetColor = blue;
		if(colorSelected.equals("PURPLE"))
			targetColor = purple;
		
		
		
		while (colorDisplay.equals(colorSelected) && gameMode == 2)
			colorDisplay = colorGroupNames.random();
	}
	
	public void newColor() {
		currentColor = colorGroupValues.random();
		colorDisplay = colorGroupNames.random();
		

	}
	
	public void setMode2() {
		if (!setMode2) {
			manager.killTarget(colorAnim);
			React = false;
			counterStarted = false;
			
			colorSelected = colorGroupNames.random();
			gameBound = fontGame.getBounds(colorSelected);
			gameName = "Tap when WORD is "+colorSelected;
			gameNameScore = "Tap when WORD is COLOR";
			
			titleBound = font.getBounds(gameName);
			tB = titleBound.width/2;
			
			colorAnim2 = new TweenGraphic();
			colorAnim2.setPosition(0, -200);
			
			manager = new TweenManager();
			
			newColor();
			setColor();
			
			flipFired = false;
			setMode2 = true;
			startManager = false;
			slept = false;
			gameMode = 0;
			titleFired = false;
			colorCustomMode = true;
		}
	}

public void do1Score() {
		
		if (!scoreAdded && !spoonsMode) { // Add high score to counter
			if (fail) {
				game.p1Score.add(new Score(reactCounter,false,gameNameScore,playerActivated));

			}
			if (win) {
				game.p1Score.add(new Score(reactCounter,true,gameNameScore,playerActivated));		
				if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
					game.scorePref.putFloat(gameNameScore, game.p1Score.peek().returnReactionTime());
					newScoreAlert = true;
				}
				else
					newScoreAlert = false;
			}
			
		}
		
		if (game.spoonsMode) {
			if (playerActivated == 1) {
				if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
					game.scorePref.putFloat(gameNameScore, game.p2Score.peek().returnReactionTime());
					newScoreAlert = true;
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 2) {
				if (game.p2Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
					game.scorePref.putFloat(gameNameScore, game.p3Score.peek().returnReactionTime());
					newScoreAlert = true;
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 3) {
				if (game.p3Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
					game.scorePref.putFloat(gameNameScore, game.p3Score.peek().returnReactionTime());
					newScoreAlert = true;
				}
				else
					newScoreAlert = false;
			}
			if (playerActivated == 4) {
				if (game.p4Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
					game.scorePref.putFloat(gameNameScore, game.p4Score.peek().returnReactionTime());
					newScoreAlert = true;
				}
				else
					newScoreAlert = false;
			}
			
		}
		scoreAdded = true;
	}
@Override
public void doScore() {
	
	if (!scoreAdded && !spoonsMode) { // Add high score to counter
		if (fail) {
			//game.p1Score.add(new Score(reactCounter,false,gameNameScore,playerActivated));

		}
		if (win) {
			game.p1Score.add(new Score(reactCounter,true,gameNameScore,playerActivated));		
			if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
				game.scorePref.putFloat(gameNameScore, game.p1Score.peek().returnReactionTime());
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
			if (game.p1Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
				game.scorePref.putFloat(gameNameScore, game.p1Score.peek().returnReactionTime());
				newScoreAlert = true;
				if (game.soundEnabled)
					highscoreSound.play();
			}
			else
				newScoreAlert = false;
		}
		if (playerActivated == 2) {
			if (game.p2Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
				game.scorePref.putFloat(gameNameScore, game.p2Score.peek().returnReactionTime());
				newScoreAlert = true;
				if (game.soundEnabled)
					highscoreSound.play();
			}
			else
				newScoreAlert = false;
		}
		if (playerActivated == 3) {
			if (game.p3Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
				game.scorePref.putFloat(gameNameScore, game.p3Score.peek().returnReactionTime());
				newScoreAlert = true;
				if (game.soundEnabled)
					highscoreSound.play();
			}
			else
				newScoreAlert = false;
		}
		if (playerActivated == 4) {
			if (game.p4Score.peek().returnReactionTime() < game.scorePref.getFloat(gameNameScore, 10000f)) {
				game.scorePref.putFloat(gameNameScore, game.p4Score.peek().returnReactionTime());
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
}
