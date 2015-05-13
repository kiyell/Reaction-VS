package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class RocketPlayScreen extends TrafficPlayScreen {
	
	Texture baseT, shipT, flameT, clock0T, clock1T, clock2T, clock3T, clock4T, clock5T;
	Sprite baseS, shipS, flameS, flameS2, clock0S, clock1S, clock2S, clock3S, clock4S, clock5S;
	float shakeX,shakeY;
	private float flameAlpha;
	boolean startedTakeOff;
	boolean animFired;
	
	TweenManager manager;
	TweenGraphic rocketAnim;
	boolean finishedTakeOff;
	
	long startTime2;
	String countdown2;
	boolean secondCountdown2;
	
	Sound sound1 = game.manager.get("sound1.wav");
	Sound sound2 = game.manager.get("sound2.wav");
	Sound sound3 = game.manager.get("sound3.wav");
	Sound sound4 = game.manager.get("sound4.wav");
	Sound sound5 = game.manager.get("sound5.wav");
	Sound rocketRumble = game.manager.get("rocketRumble.mp3");
	boolean played5,played4,played3,played2,played1,played0;
	private boolean setMode2;
	
	public RocketPlayScreen(MyGdxGame gam) {
		super(gam);
		level = 7;
		
		titleBound = font.getBounds("Tap when ROCKET LAUNCHES");
		gameName = "Tap when ROCKET LAUNCHES";
		tB = titleBound.width/2;
		
		baseT = game.manager.get("base.png"); baseS = new Sprite(baseT);
		shipT = game.manager.get("ship.png"); shipS = new Sprite(shipT);
		flameT = game.manager.get("flame.png"); flameS = new Sprite(flameT); flameS2 = new Sprite(flameT);
		
		clock0T = game.manager.get("0.jpg"); clock0S = new Sprite(clock0T);
		clock1T = game.manager.get("1.jpg"); clock1S = new Sprite(clock1T);
		clock2T = game.manager.get("2.jpg"); clock2S = new Sprite(clock2T);
		clock3T = game.manager.get("3.jpg"); clock3S = new Sprite(clock3T);
		clock4T = game.manager.get("4.jpg"); clock4S = new Sprite(clock4T);
		clock5T = game.manager.get("5.jpg"); clock5S = new Sprite(clock5T);
		
		baseS.setOrigin(0, baseS.getWidth()/2);
		baseS.setScale(.7f);
		shipS.setOrigin(0, shipS.getWidth()/2);
		shipS.setScale(.7f);
		
		baseS.setOrigin(baseS.getWidth()/2, 0);
		shipS.setOrigin(shipS.getWidth()/2, 0);
		
		baseS.setBounds(1280/2-baseS.getWidth()/2, 0, baseS.getWidth(),  baseS.getHeight());
		shipS.setBounds(1280/2-shipS.getWidth()/2, 0, shipS.getWidth(),  shipS.getHeight());
		flameS.setBounds(1280/2-flameS.getWidth()/2, -50f, flameS.getWidth(),  flameS.getHeight());
		
		
		clock0S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock0S.getWidth()/2,  clock0S.getHeight()/2);
		clock1S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock1S.getWidth()/2,  clock1S.getHeight()/2);
		clock2S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock2S.getWidth()/2,  clock2S.getHeight()/2);
		clock3S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock3S.getWidth()/2,  clock3S.getHeight()/2);
		clock4S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock4S.getWidth()/2,  clock4S.getHeight()/2);
		clock5S.setBounds(1280/2-flameS.getWidth()/2+flameS.getWidth()+50, 0,clock5S.getWidth()/2,  clock5S.getHeight()/2);
		
		flameAlpha = 0;
		flameS.setColor(flameAlpha, flameAlpha, flameAlpha, flameAlpha);
		flameS2.flip(false, true);
		startedTakeOff = false;
		finishedTakeOff = false;
		
		flameS2.setBounds(0-shipS.getWidth(), 0-shipS.getHeight(), shipS.getWidth(),  shipS.getHeight());
		
        rocketAnim = new TweenGraphic();
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		secondCountdown2 = false;
		played1 = false; played2 = false; played3 = false; played4 = false; played5 = false; played0 = false;
		animFired = false;
		setMode2 = false;
		// TODO Auto-generated constructor stub
	}
	
	
	public void doGameMode(int m, float d,Batch batch) {
		
		
		
		if (!fail && !win && m == 1) {
			
			if (startedTakeOff)
				startTakeOff();
			else {
				shipS.setBounds(1280/2-shipS.getWidth()/2, 0+rocketAnim.getY(), shipS.getWidth(),  shipS.getHeight());	
			}
				
			
			if (finishedTakeOff)
				finishTakeOff();
			
			manager.update(d);			
			baseS.draw(batch);			
			flameS.draw(batch);
			flameS2.draw(batch);			
			shipS.draw(batch);
			
			
			
			if (!secondCountdown2) {
				startTime2 = TimeUtils.millis();
				secondCountdown2 = true;
			}
			
			
			countdown2 = String.valueOf(5000 - (TimeUtils.millis() - startTime2));
			
			if((5000 - (TimeUtils.millis() - startTime2)) < 4000 && (5000 - (TimeUtils.millis() - startTime2)) >= 3000){
				clock5S.draw(batch);
				playSound(5);
			}
			
			if((5000 - (TimeUtils.millis() - startTime2)) < 3000 && (5000 - (TimeUtils.millis() - startTime2)) >= 2000){
				clock4S.draw(batch);
				playSound(4);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 2000 && (5000 - (TimeUtils.millis() - startTime2)) >= 1000){
				clock3S.draw(batch);
				playSound(3);
				startedTakeOff = true;
				playSound(0);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 1000 && (5000 - (TimeUtils.millis() - startTime2)) >= 0){
				clock2S.draw(batch);
				playSound(2);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 0 && (5000 - (TimeUtils.millis() - startTime2)) >= -1000){
				clock1S.draw(batch);
				playSound(1);
			}
					
		//	if(countdown2.equalsIgnoreCase("0") || Integer.parseInt(countdown2) <= -1000){
			if(Integer.parseInt(countdown2) <= -1000){
				clock0S.draw(batch);
				React = true;				
				startedTakeOff = false;
				
				if (!animFired) {
					doAnimation();
					animFired = true;
				}
					
				
				
				finishedTakeOff = true;
			}
									
			
// REACTION CODE			
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
// END REACTION CODE	
		}
		
		if (!fail && !win && m == 2) {
			setMode2();
		}
		
if (!fail && !win && m == 3) {
			
			
			if (startedTakeOff)
				startTakeOff();
			else {
				shipS.setBounds(1280/2-shipS.getWidth()/2, 0+rocketAnim.getY(), shipS.getWidth(),  shipS.getHeight());	
			}
				
			
			if (finishedTakeOff)
				finishTakeOff();
			
			manager.update(d);			
			baseS.draw(batch);			
			flameS.draw(batch);
			flameS2.draw(batch);			
			shipS.draw(batch);
			
			
			
			if (!secondCountdown2) {
				startTime2 = TimeUtils.millis();
				secondCountdown2 = true;
			}
			
			
			countdown2 = String.valueOf(5000 - (TimeUtils.millis() - startTime2));
			
			if((5000 - (TimeUtils.millis() - startTime2)) < 4000 && (5000 - (TimeUtils.millis() - startTime2)) >= 3000){
		//		clock5S.draw(batch);
		//		playSound(5);
			}
			
			if((5000 - (TimeUtils.millis() - startTime2)) < 3000 && (5000 - (TimeUtils.millis() - startTime2)) >= 2000){
	//			clock4S.draw(batch);
	//			playSound(4);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 2000 && (5000 - (TimeUtils.millis() - startTime2)) >= 1000){
		//		clock3S.draw(batch);
		//		playSound(3);
				startedTakeOff = true;
				playSound(0);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 1000 && (5000 - (TimeUtils.millis() - startTime2)) >= 0){
		//		clock2S.draw(batch);
		//		playSound(2);
			}			
			if((5000 - (TimeUtils.millis() - startTime2)) < 0 && (5000 - (TimeUtils.millis() - startTime2)) >= -1000){
		//		clock1S.draw(batch);
		//		playSound(1);
			}
					
		//	if(countdown2.equalsIgnoreCase("0") || Integer.parseInt(countdown2) <= -1000){
			if(Integer.parseInt(countdown2) <= -1000){
		//		clock0S.draw(batch);
				React = true;				
				startedTakeOff = false;
				
				if (!animFired) {
					doAnimation();
					animFired = true;
				}
					
				
				
				finishedTakeOff = true;
			}
									
			
// REACTION CODE			
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
// END REACTION CODE	
		}
		
		if (!fail && !win && m == 4) {
			game.nextGame(level);
		}
		}
	
	public void setMode2() {
		
		if (!setMode2) {
			flameAlpha = 0;
			flameS.setColor(flameAlpha, flameAlpha, flameAlpha, flameAlpha);
			flameS2.flip(false, true);
			startedTakeOff = false;
			finishedTakeOff = false;
			
			flameS2.setBounds(0-shipS.getWidth(), 0-shipS.getHeight(), shipS.getWidth(),  shipS.getHeight());
			
	        rocketAnim = new TweenGraphic();
			manager = new TweenManager();
			animFired = false;
			
			secondCountdown2 = false;
			setMode2 = true;
			
			gameMode = 0;
			titleFired = false;
			colorCustomMode = true;
			gameName = "Tap when ROCKET LAUNCHES (2)";
			titleBound = font.getBounds(gameName);
			tB = titleBound.width/2;
			played1 = false; played2 = false; played3 = false; played4 = false; played5 = false; played0 = false;
		}
	
	}
	
	public void startTakeOff() {
		shakeX = MathUtils.random(3f);
		shakeY = MathUtils.random(3f);
		
			if (flameAlpha < 1 && !(flameAlpha + .007f > 1)) {
				flameAlpha = flameAlpha + .007f;		
			}				
			else {
				flameAlpha = 1;
			}
			
			flameS.setColor(flameAlpha, flameAlpha, flameAlpha, flameAlpha);	
		
		shipS.setBounds(1280/2-shipS.getWidth()/2+shakeX, 0+shakeY, shipS.getWidth(),  shipS.getHeight());
		
	}
	
	public void finishTakeOff() {
		if (!(flameAlpha - .007f < 0)) {
			flameAlpha = flameAlpha - .007f;		
		}				
		else {
			flameAlpha = 0;
		}
		flameS2.setBounds(1280/2-shipS.getWidth()/2, (0-shipS.getHeight()+150)+0+rocketAnim.getY(), shipS.getWidth(),  shipS.getHeight());
		
		flameS.setColor(flameAlpha, flameAlpha, flameAlpha, flameAlpha);
	}
	
	public void doAnimation() {
		manager.add(Timeline.createSequence()
			    .push(Tween.to(rocketAnim, GraphicAnimator.POS_XY, .3f).target(0, 1700))
			    .repeat(0,0f)
			    .start(manager));
	}
	
	public void playSound(int i) {
		if (i == 5 && played5 == false) {
			if (game.soundEnabled)
			sound5.play();
			played5 = true;
		}
		else if (i == 4 && played4 == false) {
			if (game.soundEnabled)
			sound4.play();
			played4 = true;
		}
		else if (i == 3 && played3 == false) {
			if (game.soundEnabled)
			sound3.play();
			played3 = true;
		}
		else if (i == 2 && played2 == false) {
			if (game.soundEnabled)
			sound2.play();
			played2 = true;
		}
		else if (i == 1 && played1 == false) {
			if (game.soundEnabled)
			sound1.play();
			played1 = true;
		}
		else if (i == 0 && played0 == false) {
			if (game.soundEnabled)
			rocketRumble.play();
			played0 = true;
		}
	}

}
