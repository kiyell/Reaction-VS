package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class RunPlayScreen extends TrafficPlayScreen {
	
	Texture road;
	Texture flag;
	Texture black;
	
	TextureAtlas runcycleAtlas;
	Animation runAnimation;
	float runElapsedTime;
	
	TweenManager manager;
	TweenGraphic runAnim;
	Boolean animFired;
	
	TweenCallback myCallBack = new TweenCallback() {

		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			return;
		}
		
	};
	private boolean setMode2;
	private float runSpeed;
	float suddenRandom;

	public RunPlayScreen(MyGdxGame gam) {
			super(gam);
			level = 6;
		
			titleBound = font.getBounds("Tap when man CROSSES LINE");
			gameName = "Tap when man CROSSES LINE";
			tB = titleBound.width/2;
			
			road = game.manager.get("road.png");
			flag = game.manager.get("finishFlag.png");
			black = game.manager.get("black.png");
			
			runcycleAtlas = game.manager.get("runcycle.pack");
	        runAnimation = new Animation(1/15f, runcycleAtlas.getRegions());
	        
	        runAnim = new TweenGraphic();
			manager = new TweenManager();
			Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
			
			animFired = false;
			runAnim.setPosition(-200, 210);
			setMode2 = false;
			runSpeed = 1f;
			suddenRandom = MathUtils.random(100f, 700f);
			
	      
		// TODO Auto-generated constructor stub
	}
	

	public void doGameMode(int m, float d,Batch batch) {
		

		
		if (!fail && !win && m == 1 && m != 0) {
		
		runElapsedTime += d;
		
		if (!animFired) {
			doAnimation();
			animFired = true;
		}
		
		
		
		manager.update(d);
		
		batch.draw(road, 0, 300, 1280, 300);		
//		batch.draw(black, 875, 280, 5, 200);
		batch.draw(black, 875, 300, 6, 198);
		
		batch.draw(runAnimation.getKeyFrame(runElapsedTime, true), runAnim.getX(), 120+runAnim.getY(), 170, 211);
		
		if(runAnim.getX()+161 >= 875) {
			React = true;
		}
		else
			React = false;
		
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
		
		
	//	batch.draw(flag, 850, 480, 160, 160);	
	//	batch.draw(flag, 850, 280, 160, 160);
		
		}
		
		if (!fail && !win && m == 2) {
			setMode2();
		}
		
		if (!fail && !win && m == 3) {
			setMode2();
			runElapsedTime += d*runSpeed;
			
//			if (runAnim.getX()+170 >= suddenRandom)
//				runSpeed = 1.3f;
			
			if (!animFired) {
				doAnimation();
				animFired = true;
			}
			
			
			manager.update(d);
			
			batch.draw(road, 0, 300, 1280, 300);		
			batch.draw(black, 875, 300, 6, 198);
			
			batch.draw(runAnimation.getKeyFrame(runElapsedTime, true), runAnim.getX(), 120+runAnim.getY(), 170, 211);
			
			if(runAnim.getX()+161 >= 875) {
				React = true;
			}
			else
				React = false;
			
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
			
			
			
			
		//	batch.draw(flag, 850, 480, 160, 160);	
		//	batch.draw(flag, 850, 280, 160, 160);
			
			}
		
		if (!fail && !win && m == 4) {
			game.nextGame(level);
		}
		}

		
	public void setMode2() {
		if (!setMode2) {
			gameMode = 0;
			runSpeed = 1.4f;
			manager = new TweenManager();			
			animFired = false;
			runAnim.setPosition(-200, 210);
			
			runElapsedTime = 0;
			setMode2 = true;
			
			gameName = "Tap when man CROSSES LINE (2)";
			titleBound = font.getBounds(gameName);
			tB = titleBound.width/2;
			
			titleFired = false;
			colorCustomMode = true;
		}
	}
		
		
	
	
	public void doAnimation() {
		manager.add(Timeline.createSequence()
			    .push(Tween.to(runAnim, GraphicAnimator.POS_XY, 5f/runSpeed).target(1400, 210))
			    .repeat(0,.2f)
			    .setCallback(myCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
	}
	

}
