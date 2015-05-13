package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class CardPlayScreen extends TrafficPlayScreen {
	
	TweenGraphic flipAnim1,flipAnim2,flipAnim3,flipAnim4,flipAnim5,alphaAnim;
	public TweenManager manager;
	private boolean flipFired;
	private boolean f1,f2,f3,f4,f5;
	boolean cardShowing;
	private TextureAtlas cardAtlas;
	
	
	Sprite cBack;
	Sprite card1, card2, card3, card4, card5;
	
	Sprite cA, c2, c3, c4, c5, c6, c7, c8, c9, c10;
	
	public Array<Sprite> cardSet = new Array<Sprite>();
	public Array<Float> shuffleOrder = new Array<Float>();
	
	float cWidth;
	float cHeight;
	
	float cPadding;
	float cWidth2;
	float cHeight2;
	float cardPosY;
	
	Color clear;
	private float showSpeed;
	private float scaleSpeed;
	private float alphaPause;
	
	TweenCallback myCallBack1 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			if(!f1) {
				card1 = cardSet.random();
				f1 = true;
				
				if (card1 == cA)
					React = true;
			}
				return;
		}	
	};
	
	TweenCallback myCallBack2 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			if(!f2) {
				card2 = cardSet.random();
				f2 = true;
				
				if (card2 == cA)
					React = true;
			}
				return;
		}	
	};
	
	TweenCallback myCallBack3 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			if(!f3) {
				card3 = cardSet.random();
				f3 = true;
				
				if (card3 == cA)
					React = true;
			}
				return;
		}	
	};
	
	TweenCallback myCallBack4 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			if(!f4) {
				card4 = cardSet.random();
				f4 = true;
				
				if (card4 == cA)
					React = true;
			}
				return;
		}	
	};
	
	TweenCallback myCallBack5 = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			if(!f5) {
				card5 = cardSet.random();
				f5 = true;
				
				if (card5 == cA)
					React = true;
			}
				return;
		}	
	};
	
	TweenCallback resetCallBack = new TweenCallback() {
		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			
			if (gameMode == 3)
				shuffleOrder.shuffle();
			
			flipAnim1.setPosition(50+cPadding, Gdx.graphics.getHeight()-cA.getHeight()-800);
			flipAnim2.setPosition(50+cPadding*2+cWidth2, Gdx.graphics.getHeight()-cA.getHeight()-800);
			flipAnim3.setPosition(50+cPadding*3+cWidth2*2, Gdx.graphics.getHeight()-cA.getHeight()-800);
			flipAnim4.setPosition(50+cPadding*4+cWidth2*3, Gdx.graphics.getHeight()-cA.getHeight()-800);
			flipAnim5.setPosition(50+cPadding*5+cWidth2*4, Gdx.graphics.getHeight()-cA.getHeight()-800);
			alphaAnim.setPosition(1,0);

			flipFired = false;
			f1 = false;
			f2 = false;
			f3 = false;
			f4 = false;
			f5 = false;
			React = false;
			
			card1 = cBack;
			card2 = cBack;
			card3 = cBack;
			card4 = cBack;
			card5 = cBack;
			
			manager = new TweenManager();
			
			return;
		}	
	};
	


	public CardPlayScreen(MyGdxGame gam) {
		super(gam);
		// TODO Auto-generated constructor stub
		level = 3;
		titleBound = font.getBounds("Tap when ACE APPEARS");
		gameName = "Tap when ACE APPEARS";
	//	gameMode = 1;
		tB = titleBound.width/2;
		cardAtlas = game.manager.get("cards2_A.pack");
		cBack = cardAtlas.createSprite("card_back");	
		cA = cardAtlas.createSprite("a");
		c2 = cardAtlas.createSprite("2");
		c3 = cardAtlas.createSprite("3");
		c4 = cardAtlas.createSprite("4");
		c5 = cardAtlas.createSprite("5");
		c6 = cardAtlas.createSprite("6");
		c7 = cardAtlas.createSprite("7");
		c8 = cardAtlas.createSprite("8");
		c9 = cardAtlas.createSprite("9");
		c10 = cardAtlas.createSprite("10");
		
		cardSet.addAll(cA,c2,c3,c4,c5,c6,c7,c8,c9,c10);
	//	cardSet.shuffle();
		

		card1 = cBack;
		card2 = cBack;
		card3 = cBack;
		card4 = cBack;
		card5 = cBack;
		
		
		cWidth = cA.getWidth()/2;
		cHeight = cA.getHeight()/2;
		
		cPadding = 1280/40;
		cWidth2 = ((1280-100)/5)-cPadding;
		cHeight2 = cHeight/(cWidth/cWidth2);
		
		shuffleOrder.insert(0, 1f);
		shuffleOrder.insert(1, 2f);
		shuffleOrder.insert(2, 3f);
		shuffleOrder.insert(3, 4f);
		shuffleOrder.insert(4, 5f);

//		shuffleOrder.sort();
		
	//	shuffleOrder.shuffle();
		
	//	if (Gdx.graphics.getWidth() > 1600)
	//		cardPosY = ((800/8)*3-card1.getHeight()/2);
	//	else
			cardPosY = ((800/8)*4-card1.getHeight()/2);
		
		flipFired = false;
		
		flipAnim1 = new TweenGraphic();
		flipAnim2 = new TweenGraphic();
		flipAnim3 = new TweenGraphic();
		flipAnim4 = new TweenGraphic();
		flipAnim5 = new TweenGraphic();
		alphaAnim = new TweenGraphic();
		
		manager = new TweenManager();
		Tween.registerAccessor(TweenGraphic.class, new GraphicAnimator());
		
		flipAnim1.setPosition(50+cPadding, 800-cA.getHeight()-800);
		flipAnim2.setPosition(50+cPadding*2+cWidth2, 800-cA.getHeight()-800);
		flipAnim3.setPosition(50+cPadding*3+cWidth2*2, 800-cA.getHeight()-800);
		flipAnim4.setPosition(50+cPadding*4+cWidth2*3, 800-cA.getHeight()-800);
		flipAnim5.setPosition(50+cPadding*5+cWidth2*4, 800-cA.getHeight()-800);
		alphaAnim.setPosition(1,0);
		Tween.setCombinedAttributesLimit(5);
		
		f1 = f2 = f3 = f4 = f5 = false;
//		showSpeed = .5f;
//		scaleSpeed = .5f;
//		alphaPause = 8;
		
		showSpeed = .3f;
		scaleSpeed = .3f;
		alphaPause = 3;

	}
	

	
	public void drawTitle() {
		font.draw(batch, "TAP WHEN ACE APPEARS", Gdx.graphics.getWidth()/2 - tB, (Gdx.graphics.getHeight()/8)*6);
	}
	
	public void doGameMode(int m, float d,Batch batch) {
		
		
		if (!fail && !win && gameMode == 1 && gameMode != 0) {
			
			
			card1.setScale(1-flipAnim1.getScale(), 1);
			card1.setOriginCenter();
			card1.setBounds(flipAnim1.getX(), flipAnim1.getY(),cWidth2,cHeight2);
			card1.draw(batch,alphaAnim.getX());
			
			card2.setScale(1-flipAnim2.getScale(), 1);
			card2.setOriginCenter();
			card2.setBounds(flipAnim2.getX(), flipAnim2.getY(),cWidth2,cHeight2);
			card2.draw(batch,alphaAnim.getX());

			card3.setScale(1-flipAnim3.getScale(), 1);
			card3.setOriginCenter();
			card3.setBounds(flipAnim3.getX(), flipAnim3.getY(),cWidth2,cHeight2);
			card3.draw(batch,alphaAnim.getX());
			
			card4.setScale(1-flipAnim4.getScale(), 1);
			card4.setOriginCenter();
			card4.setBounds(flipAnim4.getX(), flipAnim4.getY(),cWidth2,cHeight2);
			card4.draw(batch,alphaAnim.getX());
			
			card5.setScale(1-flipAnim5.getScale(), 1);
			card5.setOriginCenter();
			card5.setBounds(flipAnim5.getX(), flipAnim5.getY(),cWidth2,cHeight2);
			card5.draw(batch,alphaAnim.getX());
			
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

			
			
			if (!flipFired) {
				doFlipAnimation1();
				flipFired = true;
			}
		}
		if (!fail && !win && gameMode == 2) {
			setMode2();
		}
		if (!fail && !win && gameMode == 3 && gameMode != 0) {
			
			
			card1.setScale(1-flipAnim1.getScale(), 1);
			card1.setOriginCenter();
			card1.setBounds(flipAnim1.getX(), flipAnim1.getY(),cWidth2,cHeight2);
			card1.draw(batch,alphaAnim.getX());
			
			card2.setScale(1-flipAnim2.getScale(), 1);
			card2.setOriginCenter();
			card2.setBounds(flipAnim2.getX(), flipAnim2.getY(),cWidth2,cHeight2);
			card2.draw(batch,alphaAnim.getX());

			card3.setScale(1-flipAnim3.getScale(), 1);
			card3.setOriginCenter();
			card3.setBounds(flipAnim3.getX(), flipAnim3.getY(),cWidth2,cHeight2);
			card3.draw(batch,alphaAnim.getX());
			
			card4.setScale(1-flipAnim4.getScale(), 1);
			card4.setOriginCenter();
			card4.setBounds(flipAnim4.getX(), flipAnim4.getY(),cWidth2,cHeight2);
			card4.draw(batch,alphaAnim.getX());
			
			card5.setScale(1-flipAnim5.getScale(), 1);
			card5.setOriginCenter();
			card5.setBounds(flipAnim5.getX(), flipAnim5.getY(),cWidth2,cHeight2);
			card5.draw(batch,alphaAnim.getX());
			
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

			
			
			if (!flipFired) {
				doFlipAnimation1();
				flipFired = true;
			}
		}
		else if (gameMode == 4) {
			game.nextGame(level);
		}

		
	}
	
	public void OLDdoFlipAnimation1() {

		manager.add(Timeline.createSequence() // Card 1 Position
				.push(Tween.to(flipAnim1, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 1 Scale
				.push(Tween.to(flipAnim1, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim1, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack1)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 2 Position
				.pushPause(showSpeed)
				.push(Tween.to(flipAnim2, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*2+cWidth2, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 2 Scale
				.pushPause(showSpeed)
				.push(Tween.to(flipAnim2, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim2, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack2)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 3 Position
				.pushPause(showSpeed*2)
				.push(Tween.to(flipAnim3, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*3+cWidth2*2, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 3 Scale
				.pushPause(showSpeed*2)
				.push(Tween.to(flipAnim3, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim3, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack3)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 4 Position
				.pushPause(showSpeed*3)
				.push(Tween.to(flipAnim4, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*4+cWidth2*3, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 4 Scale
				.pushPause(showSpeed*3)
				.push(Tween.to(flipAnim4, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim4, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack4)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 5 Position
				.pushPause(showSpeed*4)
				.push(Tween.to(flipAnim5, GraphicAnimator.POS_XY,showSpeed).target(50+cPadding*5+cWidth2*4, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 5 Scale
				.pushPause(showSpeed*4)
				.push(Tween.to(flipAnim5, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim5, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack5)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		
		manager.add(Timeline.createSequence() // Cards Alpha decrease
				.pushPause(alphaPause)
				.push(Tween.to(alphaAnim, GraphicAnimator.POS_XY, scaleSpeed).target(0,0))
			    .setCallback(resetCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));				
	}
	
	public void doFlipAnimation1() {

		manager.add(Timeline.createSequence() // Card 1 Position
				.pushPause(showSpeed*shuffleOrder.get(0))
				.push(Tween.to(flipAnim1, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 1 Scale
				.pushPause(showSpeed*shuffleOrder.get(0))
				.push(Tween.to(flipAnim1, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim1, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack1)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 2 Position
				.pushPause(showSpeed*shuffleOrder.get(1))
				.push(Tween.to(flipAnim2, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*2+cWidth2, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 2 Scale
				.pushPause(showSpeed*shuffleOrder.get(1))
				.push(Tween.to(flipAnim2, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim2, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack2)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 3 Position
				.pushPause(showSpeed*shuffleOrder.get(2))
				.push(Tween.to(flipAnim3, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*3+cWidth2*2, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 3 Scale
				.pushPause(showSpeed*shuffleOrder.get(2))
				.push(Tween.to(flipAnim3, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim3, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack3)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 4 Position
				.pushPause(showSpeed*shuffleOrder.get(3))
				.push(Tween.to(flipAnim4, GraphicAnimator.POS_XY, showSpeed).target(50+cPadding*4+cWidth2*3, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 4 Scale
				.pushPause(showSpeed*shuffleOrder.get(3))
				.push(Tween.to(flipAnim4, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim4, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack4)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 5 Position
				.pushPause(showSpeed*shuffleOrder.get(4))
				.push(Tween.to(flipAnim5, GraphicAnimator.POS_XY,showSpeed).target(50+cPadding*5+cWidth2*4, cardPosY))
				.pushPause(.5f)
			    .repeat(0,.1f)
			    .start(manager));
		
		manager.add(Timeline.createSequence() // Card 5 Scale
				.pushPause(showSpeed*shuffleOrder.get(4))
				.push(Tween.to(flipAnim5, GraphicAnimator.SCALE, scaleSpeed).target(0))
				.pushPause(.2f)
				.push(Tween.to(flipAnim5, GraphicAnimator.SCALE, scaleSpeed).target(1))
			    .repeatYoyo(1,.1f)
			    .setCallback(myCallBack5)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));
		
		
		manager.add(Timeline.createSequence() // Cards Alpha decrease
				.pushPause(alphaPause)
				.push(Tween.to(alphaAnim, GraphicAnimator.POS_XY, scaleSpeed).target(0,0))
			    .setCallback(resetCallBack)
			    .setCallbackTriggers(TweenCallback.END)
			    .start(manager));				
	}
	
	public void resetGame() {
		
		if (gameMode == 2) {
			showSpeed = .2f;
			scaleSpeed = .2f;
			alphaPause = 2;
			shuffleOrder.shuffle();
		}
		
		manager = new TweenManager();
		
		
		flipAnim1.setPosition(50+cPadding, Gdx.graphics.getHeight()-cA.getHeight()-800);
		flipAnim2.setPosition(50+cPadding*2+cWidth2, Gdx.graphics.getHeight()-cA.getHeight()-800);
		flipAnim3.setPosition(50+cPadding*3+cWidth2*2, Gdx.graphics.getHeight()-cA.getHeight()-800);
		flipAnim4.setPosition(50+cPadding*4+cWidth2*3, Gdx.graphics.getHeight()-cA.getHeight()-800);
		flipAnim5.setPosition(50+cPadding*5+cWidth2*4, Gdx.graphics.getHeight()-cA.getHeight()-800);
		
		flipAnim1.setScale(0f);
		flipAnim2.setScale(0f);
		flipAnim3.setScale(0f);
		flipAnim4.setScale(0f);
		flipAnim5.setScale(0f);
		
		alphaAnim.setPosition(1,0);
		

		flipFired = false;
		f1 = false;
		f2 = false;
		f3 = false;
		f4 = false;
		f5 = false;
		React = false;
		
		card1 = cBack;
		card2 = cBack;
		card3 = cBack;
		card4 = cBack;
		card5 = cBack;
		
		super.resetGame();
		return;
		
	}
	
	public void setMode2() {
		gameMode = 0;		

		gameName = "Tap when ACE APPEARS (2)";
		titleBound = font.getBounds(gameName);
		tB = titleBound.width/2;
		
		titleFired = false;
		colorCustomMode = true;
	}
}
