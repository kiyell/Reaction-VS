package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
/*
 * 
 *  Sprite 2,4,11 are symmetrical
 *  shape 6 is triangle, sprite s4
 *  
 *  Change to tap when rectangle appears?
 */
public class ShapePlayScreen extends TrafficPlayScreen {
	
	Sprite s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
	Sprite t1,t2,t3;
	Sprite currentShape;
	private TextureAtlas shapeAtlas;
	TweenGraphic shapeAnim,shapeAnim2;
	public TweenManager manager;
	Boolean animFired;
	private boolean setMode2;
	
	public Array<Sprite> shapeSet = new Array<Sprite>();
	
	TweenCallback myCallBack = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			if (gameMode == 1) {
				currentShape = shapeSet.random();
				setPosition();
				animFired = false;
				if(currentShape == t1 ||currentShape == t2 || currentShape == t3) {		
					React = true;
				}
				else React = false;
			}
			if (gameMode == 3) {
				currentShape = shapeSet.random();
				setPosition();
				animFired = false;
				if(currentShape == s4) {		
					React = true;
				}
				else React = false;
			}
			
		}	
	};
	


	public ShapePlayScreen(MyGdxGame gam) {
		super(gam);
		level = 5;
		animFired = false;
		
		titleBound = font.getBounds("Tap when SHAPE is a RECTANGLE");
		gameName = "Tap when SHAPE is a RECTANGLE";
		tB = titleBound.width/2;
		// TODO Auto-generated constructor stub
		
		shapeAtlas = game.manager.get("Shapes.pack");
//		s1 = shapeAtlas.createSprite("shape1");
		s2 = shapeAtlas.createSprite("shape3");
//		s3 = shapeAtlas.createSprite("shape5");
		s4 = shapeAtlas.createSprite("shape6");
		s5 = shapeAtlas.createSprite("shape7");
		s6 = shapeAtlas.createSprite("shape8");
//		s7 = shapeAtlas.createSprite("shape9");
//		s8 = shapeAtlas.createSprite("shape10");
//		s9 = shapeAtlas.createSprite("shape12");
//		s10 = shapeAtlas.createSprite("shape11");
		
		
		t1 = shapeAtlas.createSprite("shape2");
		t2 = shapeAtlas.createSprite("shape4");
//		t3 = shapeAtlas.createSprite("shape11");
		
	//	shapeSet.add(s1);
		shapeSet.add(s2);
	//	shapeSet.add(s3);
		shapeSet.add(s4);
		shapeSet.add(s5); shapeSet.add(s6);// shapeSet.add(s7); shapeSet.add(s8);
	//	shapeSet.add(s9); 
		shapeSet.add(t1); shapeSet.add(t2); //shapeSet.add(t3); shapeSet.add(s10);
		
		shapeAnim = new TweenGraphic();
		shapeAnim2 = new TweenGraphic();
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());

		
		setPosition();
		
		currentShape = shapeSet.random();
		if(currentShape == t1 ||currentShape == t2 || currentShape == t3) {		
			React = true;
		}
		
		
		setMode2 = false;
	}

	
	public void doGameMode(int m, float d,Batch batch) {
		
		if (!fail && !win && m == 1 && m != 0) {
			
		
		currentShape.setRotation(shapeAnim.getScale());
		currentShape.setBounds(shapeAnim.getX(), shapeAnim.getY(), 200, 200);
		currentShape.draw(batch);
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
		
		if (!animFired) {
			doAnimation();
			animFired = true;
		}
		}
		if (!fail && !win && m == 2 && m != 0) {
			setMode2();
		}
		
		
		if (!fail && !win && m == 3 && m != 0) {
			
			
			currentShape.setRotation(shapeAnim2.getScale());
			currentShape.setBounds(shapeAnim2.getX(), shapeAnim2.getY(), 200, 200);
			currentShape.draw(batch);
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
			
			if (!animFired) {
				doAnimation2();
				animFired = true;
			}
			}
		
		if (m == 4) {
			game.nextGame(level);
		}
	}
	
	public void doAnimation() {
		
		manager.add(Timeline.createSequence() // Card 1 Position
				.beginParallel()
				.push(Tween.to(shapeAnim, GraphicAnimator.POS_XY, .4f).target(1280/2-s2.getWidth()/2, 350))
				.push(Tween.to(shapeAnim, GraphicAnimator.SCALE, 1f).target(360))
				.pushPause(.5f)
				.end()
				.beginParallel()
				.push(Tween.to(shapeAnim, GraphicAnimator.POS_XY, 1f).target(720, 1000))
				.push(Tween.to(shapeAnim, GraphicAnimator.SCALE, 1f).target(720))
				.end()
			    .repeat(0,.1f)
			    .setCallback(myCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
	}
	
public void doAnimation2() {
		
		manager.add(Timeline.createSequence() // Card 1 Position
				.beginParallel()
				.push(Tween.to(shapeAnim2, GraphicAnimator.POS_XY, .4f).target(1280/2-s2.getWidth()/2, 350))
				.push(Tween.to(shapeAnim2, GraphicAnimator.SCALE, 1f).target(360))
				.pushPause(.5f)
				.end()
				.beginParallel()
				.push(Tween.to(shapeAnim2, GraphicAnimator.POS_XY, 1f).target(720, 1000))
				.push(Tween.to(shapeAnim2, GraphicAnimator.SCALE, 1f).target(720))
				.end()
			    .repeat(0,.1f)
			    .setCallback(myCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
	}
	
	
	public void setPosition() {
		int random = MathUtils.random(11);
		
		if (random >= 0)
			shapeAnim.setPosition(-100, 900);
		if (random >= 1)
			shapeAnim.setPosition(-100, 400);
		if (random >= 2)
			shapeAnim.setPosition(-100, 0);
		
		if (random >= 3)
			shapeAnim.setPosition(0, -100);
		if (random >= 4)
			shapeAnim.setPosition(720, -100);
		if (random >= 5)
			shapeAnim.setPosition(1280, -100);
		
		if (random >= 6)
			shapeAnim.setPosition(1280+100, 900);
		if (random >= 7)
			shapeAnim.setPosition(1280+100, 400);
		if (random >= 8)
			shapeAnim.setPosition(1280+100, -200);
		
		if (random >= 9)
			shapeAnim.setPosition(0, 1280+100);
		if (random >= 10)
			shapeAnim.setPosition(720, 1280+100);
		if (random >= 11)
			shapeAnim.setPosition(1280, 1280+100);
		
		if (random >= 0)
			shapeAnim2.setPosition(-100, 900);
		if (random >= 1)
			shapeAnim2.setPosition(-100, 400);
		if (random >= 2)
			shapeAnim2.setPosition(-100, 0);
		
		if (random >= 3)
			shapeAnim2.setPosition(0, -100);
		if (random >= 4)
			shapeAnim2.setPosition(720, -100);
		if (random >= 5)
			shapeAnim2.setPosition(1280, -100);
		
		if (random >= 6)
			shapeAnim2.setPosition(1280+100, 900);
		if (random >= 7)
			shapeAnim2.setPosition(1280+100, 400);
		if (random >= 8)
			shapeAnim2.setPosition(1280+100, -200);
		
		if (random >= 9)
			shapeAnim2.setPosition(0, 1280+100);
		if (random >= 10)
			shapeAnim2.setPosition(720, 1280+100);
		if (random >= 11)
			shapeAnim2.setPosition(1280, 1280+100);
		
	}
	
	public void setMode2() {
		
		if (!setMode2) {
			shapeAnim2.setScale(0);
			manager = new TweenManager();
			animFired = false;
			setPosition();
			currentShape = shapeSet.random();
			while (currentShape == s4) {		
				currentShape = shapeSet.random();
			}
			gameName = "Tap when SHAPE is a TRIANGLE";
			titleBound = font.getBounds(gameName);
			tB = titleBound.width/2;
			setMode2 = true;
			gameMode = 0;
			titleFired = false;
			colorCustomMode = true;
		}
		
	}

}
