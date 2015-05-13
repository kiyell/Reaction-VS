package com.kiyell.game.reactionvs;

import com.badlogic.gdx.graphics.Color;

public class TweenGraphic {
	
	private float x = 0;
	private float y;
	private Color color;
	private float scale;
	
	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
	public float getScale() {
		// TODO Auto-generated method stub
		return scale;
	}
	public Object getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	public void setColor(Color c) {
		
		this.color = c;
		// TODO Auto-generated method stub
		
	}
	public void setPosition(float f, float g) {
		this.x = f;
		this.y = g;
		
	}
	public void setScale(float f) {
		scale = f;
		
	}

}
