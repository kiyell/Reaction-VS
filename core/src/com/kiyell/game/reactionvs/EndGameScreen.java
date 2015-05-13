package com.kiyell.game.reactionvs;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class EndGameScreen extends TrafficPlayScreen {
	
	int largest;
	int playerSelected;
	boolean doOnce;
	boolean doOnce2;
	float p1average;
	String timeColor;
	String countColor;
	String gradeColor;
	String grade;

	public EndGameScreen(MyGdxGame gam) {
		super(gam);
		level = 100;
		
		game.endGame = true;
		gameMode = 1;
		finish = true;
		playBG = true;
		
		titleBound = font.getBounds("GAME OVER");
		tB = titleBound.width/2;
		gameName = "";

		
		// TODO Auto-generated constructor stub
		//fontPlayers.setScale(1f);
		playerSelected = 0;
		doOnce = false;
		titleAnim.setScale(1);
		if (game.playerCount == 1)
			font.setScale(.5f);
		font.setMarkupEnabled(true);
		
		timeColor = "[BLACK]";
		countColor = "[BLACK]";
		gradeColor = "[BLACK]";
		grade = "E";
		computeScore();
		doOnce2 = false;
		
	}
	
	
	public void doGameMode(int m, float d,Batch batch) {
//		fail = true;
		bgMusic.stop();
		if (game.playerCount != 1 && playerSelected != 0)
			font.drawWrapped(batch, "Player "+String.valueOf(playerSelected)+" wins the game!", 100, 500, 1080, BitmapFont.HAlignment.CENTER);
		else
		{
			if (playerSelected == 0 && game.playerCount != 1) {
				font.drawWrapped(batch, "There was a tie! \nTime for a rematch!", 100, 600, 1080, BitmapFont.HAlignment.CENTER);
			}
			if (game.playerCount == 1) {
				font.drawWrapped(batch, "You scored on "+countColor+game.p1Score.size+"[]/14 games\n"
						+ timeColor+String.format("%.4f",computeAverage())+"[] ms average\n"				
						, 100, 650, 1080, BitmapFont.HAlignment.CENTER);
				fontFancy.draw(batch, gradeColor+grade+"[]"		
						, 600, 500);
			}
				
		}
			
		
		if(playerSelected == 1) {
		buttonPW.draw(batch, 0, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 1 nine patch image	
		fontPlayers.drawWrapped(batch, String.valueOf(game.p1total), 0, 120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}		
		
		else if(playerSelected == 2) {
		buttonPW.draw(batch, 1280-200-buttonModifyX, 0, 200+buttonModifyX, 200+buttonModifyY); 										// Player 2 nine patch image
		fontPlayers.drawWrapped(batch, String.valueOf(game.p2total), 1280-200-buttonModifyX, 120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}
		
		else if(playerSelected == 3) {
		buttonPW.draw(batch, 1280-200-buttonModifyX, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY); 									// Player 3 nine patch image
		fontPlayers.drawWrapped(batch, String.valueOf(game.p3total), 1280-200-buttonModifyX, 800-200-buttonModifyY+120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}
		
		else if(playerSelected == 4) {
		buttonPW.draw(batch, 0, 800-200-buttonModifyY, 200+buttonModifyX, 200+buttonModifyY);																// Player 4 nine patch image
		fontPlayers.drawWrapped(batch, String.valueOf(game.p4total), 0, 800-200-buttonModifyY+120+buttonModifyY/2, 200+buttonModifyX, BitmapFont.HAlignment.CENTER);
		}

		
		
		finish = true;
	}
	
	public void computeScore() {
		
		if (!doOnce) {
			if (game.p1total > game.p2total && game.p1total > game.p3total && game.p1total > game.p4total)
				playerSelected = 1;
			if (game.p2total > game.p1total && game.p2total > game.p3total && game.p2total > game.p4total)
				playerSelected = 2;
			if (game.p3total > game.p2total && game.p3total > game.p1total && game.p3total > game.p4total)
				playerSelected = 3;
			if (game.p4total > game.p2total && game.p4total > game.p3total && game.p4total > game.p1total)
				playerSelected = 4;
			doOnce = true;
			

			
			if (game.soundEnabled) {
				winSound.play();
				bgMusic.stop();
			}
				
		}
		
		

	}
	
	public float computeAverage() {
		if (!doOnce2) {
			for (Score s : game.p1Score)
				p1average = p1average + s.returnReactionTime();
			
			p1average = p1average/game.p1Score.size;
			doOnce2 = true;
		}
		
		
		if (game.p1Score.size == 0)
			p1average = 0;
		
			
		
		if (game.p1Score.size == 0) {
			grade = "F";
			gradeColor = "[RED]";
			countColor = "[RED]";
			timeColor = "[RED]";
		}
		if (game.p1Score.size > 0 && game.p1Score.size < 7) {
			grade = "D";
			gradeColor = "[RED]";
			countColor = "[RED]";
			timeColor = "[RED]";
		}
		if (game.p1Score.size >= 7 && game.p1Score.size < 10) {
			grade = "C";
			gradeColor = "[ORANGE]";
			countColor = "[ORANGE]";
			timeColor = "[ORANGE]";
		}
		if ((game.p1Score.size >= 10 && game.p1Score.size < 14) ||game.p1Score.size >= 10 && p1average < 600 && p1average > 500) {
			grade = "B";
			gradeColor = "[ORANGE]";
			countColor = "[ORANGE]";
			timeColor = "[ORANGE]";
		}
		if ((game.p1Score.size >= 10 && game.p1Score.size < 14) || game.p1Score.size >= 10 &&  p1average < 500 && p1average > 400) {
			grade = "B-";
			gradeColor = "[ORANGE]";
			countColor = "[ORANGE]";
			timeColor = "[ORANGE]";
		}
		if (game.p1Score.size == 14) {
			grade = "B+";
			gradeColor = "[ORANGE]";
			countColor = "[GREEN]";
			timeColor = "[ORANGE]";
		}
		if (game.p1Score.size == 14 && p1average < 500 && p1average > 400 ) {
			grade = "A-";
			gradeColor = "[ORANGE]";
			countColor = "[GREEN]";
			timeColor = "[ORANGE]";
		}
		if (game.p1Score.size == 14 && p1average < 400 && p1average > 300 ) {
			grade = "A";
			gradeColor = "[GREEN]";
			countColor = "[GREEN]";
			timeColor = "[ORANGE]";
		}
		if (game.p1Score.size == 14 && p1average < 300 && p1average > 200 ) {
			grade = "A+";
			gradeColor = "[GREEN]";
			countColor = "[GREEN]";
			timeColor = "[GREEN]";
		}
		
		return p1average;
		
	}

}
