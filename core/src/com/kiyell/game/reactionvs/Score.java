package com.kiyell.game.reactionvs;

public class Score {
	
	long reactionTime;
	Boolean Win;
	String gameType;
	String gameDifficulty;
	int Player;
	
	public Score() {
		reactionTime = 0;
		Win = false;
		gameType = "none selected";
		gameDifficulty = "no difficulty setting";
	}
	
	public Score(long t, boolean w, String gt, int p) {
		reactionTime = t;
//		reactionTime = reactionTime * 1000;
		Win = w;
		gameType = gt;
		Player = p;
	}
	
	public void reactionTimeSet(long t) {
		reactionTime = t;
		return;
	}
	
	public void winSet(boolean b) {
		Win = b;
		return;
	}
	
	public void gameTypeSet(String s) {
		gameType = s;
		return;
	}
	
	public void gameDifficultySet(String s) {
		gameDifficulty = s;
		return;
	}
	
	public float returnReactionTime() {
//		return String.format("%.4f",(float)reactionTime/1000000f);
		return ((float)reactionTime)/1000000;
	}
	
	public String returnAllStats() {
		return " "+String.format("%.4f",(float)reactionTime/1000000f)+" "+gameType+" Player "+String.valueOf(Player)+" Win: "+Boolean.valueOf(Win);
//		return String.valueOf(reactionTime);
	}
	
	public String returnGameType(){
		return gameType;
	}
	
	

}
