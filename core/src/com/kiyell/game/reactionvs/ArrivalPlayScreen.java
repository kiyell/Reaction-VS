package com.kiyell.game.reactionvs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Bounce;



public class ArrivalPlayScreen extends TrafficPlayScreen {
	
	Texture greenC;
	Texture blueC;
	Texture yellowC;
	Texture taxi;
	Texture road;
	Texture currentTex;
	
	TweenGraphic carAnim;
	TweenGraphic carAnim2;
	
	float Xpos;
	final int speed = 10;
	public TweenManager manager;
	
	
	
	
	TweenCallback myCallBack = new TweenCallback() {

		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			currentTex = threeSet.random();
			return;
		}
		
	};
	
	TweenCallback myCallBack2 = new TweenCallback() {

		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			currentTex = threeSet.random();
			
			if(!flipped)
				flipped = true;
			else 
				flipped = false;
			
			return;
		}
		
	};
	
	private boolean animFired;
	private boolean animFired2;
	private boolean flipped;

	public ArrivalPlayScreen(MyGdxGame gam) {
		super(gam);
		//game = gam;
		// TODO Auto-generated constructor stub
		titleBound = font.getBounds("Tap when TAXI COMES");
		tB = titleBound.width/2;
		
		greenC = game.manager.get("greenC.png");
		blueC = game.manager.get("blueC.png");
		yellowC = game.manager.get("yellowC.png");
		taxi = game.manager.get("taxi.png");
		road = game.manager.get("road.png");
		
		
		threeSet.clear();
		threeSet.add(greenC);
		threeSet.add(blueC);
		threeSet.add(taxi);
		threeSet.add(yellowC);
		currentTex = blueC;
		Xpos = 0;
		
		carAnim = new TweenGraphic();
		carAnim2 = new TweenGraphic();
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		animFired = false;
		animFired2 = false;
		flipped = false;
		gameName = "Tap when TAXI COMES";
		level = 2;
		

		}
	


	public void doGameMode(int m, float d,Batch batch) {
		float delta = d;
		int mode = m;
		
		
		if(mode == 1) {
			
			if (!animFired) {
				createAnimation1();
				animFired = true;
			}
			
			if (!fail && !win) {
				batch.draw(road, 0, 300, 1280, 243);
				manager.update(delta);
	//			drawTitle();
				batch.draw(currentTex, 1280-carAnim.getX(), 300);
				
				if (currentTex == taxi) {
					if (!counterStarted && carAnim.getX() > taxi.getWidth()) {
						counterStartTime = TimeUtils.nanoTime();
						counterStarted = true;
				}
					React = true;											
					reactCounter = TimeUtils.timeSinceNanos(counterStartTime);
					
				}
					
				else {
					React = false;
					counterStarted = false;
				}
								
			}
			
			
						
		}
		if(mode == 2) {
			setMode2();
		}
		
		if(mode == 3) {
			if (!animFired2) {
				manager.killAll();
				counterStarted = false;
				createAnimation2();
				currentTex = threeSet.random();
				animFired2 = true;
			}
			
			if (!fail && !win) {
				batch.draw(road, 0, 300, 1280, 243);
				manager.update(delta);
		//		drawTitle();
				batch.draw(currentTex, 1280-carAnim2.getX(), 300, taxi.getWidth(), taxi.getHeight(), 0, 0, taxi.getWidth(), taxi.getHeight(), flipped, false);
				
				if (currentTex == taxi && 1280-carAnim2.getX() < 1280) {
					if (!counterStarted) {
						counterStartTime = TimeUtils.nanoTime();
						counterStarted = true;
				}
					React = true;											
					reactCounter = TimeUtils.timeSinceNanos(counterStartTime);
					
				}
					
				else {
					React = false;
					counterStarted = false;
				}
								
			}
			
			
			
			
		}
		
		if(mode == 4) {
			game.nextGame(level);
		}	
		
		
		
		
		
	}
	
	public void setMode2() {
		gameName = "Tap when TAXI COMES (2)";
		
		titleBound = font.getBounds(gameName);
		tB = titleBound.width/2;
		
		gameMode = 0;
		titleFired = false;
		colorCustomMode = true;
	}
	
	private void createAnimation2() {
		manager.add(Timeline.createSequence()
			    .push(Tween.to(carAnim2, GraphicAnimator.POS_XY, 0.2f).target(1280/2 - taxi.getWidth()/2+taxi.getWidth(), 100))
			    .pushPause(.2f)
			    .push(Tween.to(carAnim2, GraphicAnimator.POS_XY, 0.2f).target(0+1280+taxi.getWidth(),50))
			    .repeatYoyo(200,.2f)
			    .setCallback(myCallBack2)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
	}

	public void drawTitle() {
		font.draw(batch, "TAP WHEN TAXI COMES",1280/2 - tB, (800/8)*6);
	}
	
	public void createAnimation1() {
		manager.add(Timeline.createSequence()
			    .push(Tween.to(carAnim, GraphicAnimator.POS_XY, 0.3f).target(1280/2 - taxi.getWidth()/2+taxi.getWidth(), 100))
			    .pushPause(.3f)
			    .push(Tween.to(carAnim, GraphicAnimator.POS_XY, 0.3f).target(0+1280+taxi.getWidth(),50))
			    .repeat(200,.2f)
			    .setCallback(myCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
	}

}
