package com.kiyell.game.reactionvs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kiyell.game.reactionvs.ActionResolver;
import com.kiyell.game.reactionvs.MyGdxGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kiyell.game.reactionvs.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
//		config.height = 600;
//		config.width = 800;
//		config.height = 800;
//		config.width = 1280;
//		config.height = 1080;
//		config.width = 1920;
// droid 3
//		config.height = 540;
//		config.width = 960;
// droid incredible 2
		config.height = 480;
		config.width = 800;		
		
		
		
		new LwjglApplication(new MyGdxGame(new ActionResolver(){
            @Override public void startSmartWallAd(){}
            @Override public void showAds(boolean show){}
        }), config);
		
	}
}
