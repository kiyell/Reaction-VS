package com.kiyell.game.reactionvs;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;


 public class GraphicAnimator implements TweenAccessor<TweenGraphic> {
	 
	 public static final int POS_XY = 1;
	 public static final int SCALE = 2;
	 public static final int ALPHA = 3;

	@Override
	public int getValues(TweenGraphic target, int tweenType, float[] returnValues) {
		// TODO Auto-generated method stub
		
		switch (tweenType) {
		case POS_XY:
			returnValues[0] = target.getX();
		    returnValues[1] = target.getY();
		    return 2;             
		case SCALE:                 
			returnValues[0] = target.getScale();                 
			return 1;             
		case ALPHA:
			returnValues[0] = 1f; 
			return 4;
		default: assert false; return -1; 
		}
		
	}

	@Override
	public void setValues(TweenGraphic target, int tweenType, float[] newValues) {
		switch (tweenType) {
		
        case POS_XY: target.setPosition(newValues[0], newValues[1]);
        	break;
        case SCALE: target.setScale(newValues[0]);
        	break;
        case ALPHA:                  
          break;     
   
        default:
        	assert false;
    }
		
	}
	
}