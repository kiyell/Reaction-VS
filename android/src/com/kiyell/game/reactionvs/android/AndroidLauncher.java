package com.kiyell.game.reactionvs.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kiyell.game.reactionvs.ActionResolver;
import com.kiyell.game.reactionvs.MyGdxGame;
import com.mwfvdhbw.axqnjxec214139.AdListener;
import com.mwfvdhbw.axqnjxec214139.AdView;
import com.mwfvdhbw.axqnjxec214139.Prm;

//remove interface for ads > public class AndroidLauncher extends AndroidApplication implements ActionResolver
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useWakelock = true;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        
        // removed code for ads
        // if(ma==null) ma=new Prm(this, adCallbackListener, false);
        RelativeLayout layout = new RelativeLayout(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		View gameView = initializeForView(new MyGdxGame(), cfg);
		//View gameView = initializeForView(new MyGdxGame(this), cfg); <-- removed actionresolver pass-in

        RelativeLayout.LayoutParams adParams = new
        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        RelativeLayout.LayoutParams adParams2 = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                adParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                adParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
  // Removed adView setup
  //      adView = new AdView(this, AdView.BANNER_TYPE_IN_APP_AD, AdView.PLACEMENT_TYPE_INTERSTITIAL, false, false,
  //              AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);
  //      adView.setAdListener(adlistener);
  //     adView.setLayoutParams(adParams2);
        layout.addView(gameView);
// Removed ads from view        layout.addView(adView);
        setContentView(layout);
// Remove catching backKey        Gdx.input.setCatchBackKey(true);
		
		
//		initialize(new MyGdxGame(this), cfg);
	}

	/* Removed code for ads

	 AdListener adCallbackListener=new AdListener(){
	        @Override
	        public void onSDKIntegrationError(String message){
	            //Here you will receive message from SDK if it detects any integration issue.
	            Log.w("Airpush", "onSDKIntegrationError() "+message);
	        }
	        public void onSmartWallAdShowing(){
	            // This will be called by SDK when it's showing any of the SmartWall ad.
	            Log.w("Airpush", "onSmartWallAdShowing()");
	        }
	        @Override
	        public void onSmartWallAdClosed(){
	            // This will be called by SDK when the SmartWall ad is closed.
	            Log.w("Airpush", "onSmartWallAdClosed()");
	//            Gdx.app.exit();
	        }
	        @Override
	        public void onAdError(String message){
	            //This will get called if any error occurred during ad serving.
	            Log.w("Airpush", "onAdError() "+message);
	//            Gdx.app.exit();
	            
	        }
	        @Override
	        public void onAdCached(AdType arg0){
	            //This will get called when an ad is cached. 
	            Log.w("Airpush", "onAdCached() "+arg0.toString());
	        }
	        @Override
	        public void noAdAvailableListener(){ 
	            //this will get called when ad is not available 
	            Log.w("Airpush", "noAdAvailableListener()");
	        }
	        
	        
	     };

	     AdListener.BannerAdListener adlistener = new AdListener.BannerAdListener(){
	        @Override
	        public void onAdClickListener(){
	            //This will get called when ad is clicked.
	            Log.w("Airpush", "onAdClickListener()");
	        }
	        @Override
	        public void onAdLoadedListener(){
	            //This will get called when an ad has loaded.
	            Log.w("Airpush", "onAdLoadedListener()");
	        }
	        @Override
	        public void onAdLoadingListener(){
	            //This will get called when a rich media ad is loading.
	            Log.w("Airpush", "onAdLoadingListener()");
	        }
	        @Override
	        public void onAdExpandedListner(){
	            //This will get called when an ad is showing on a user's screen. This may cover the whole UI.
	            Log.w("Airpush", "onAdExpandedListner()");
	        }
	        @Override
	        public void onCloseListener(){
	            //This will get called when an ad is closing/resizing from an expanded state.
	            Log.w("Airpush", "onCloseListener()");
	        }
	        @Override
	        public void onErrorListener(String message){
	            //This will get called when any error has occurred. This will also get called if the SDK notices any integration mistakes.
	            Log.w("Airpush", message);
	        }
	        @Override
	        public void noAdAvailableListener(){
	            //this will get called when ad is not available 
	            Log.w("Airpush", "noAdAvailableListener()");
	        }
	        
	        
	    };

	    private final int SHOW_ADS = 1;
	    private final int HIDE_ADS = 0;

	    Prm ma = null;
	    AdView adView = null;

	    protected Handler handler = new Handler(Looper.getMainLooper()){
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case SHOW_ADS: {
	                if (adView != null) adView.setVisibility(View.VISIBLE);
	                break;
	            }
	            case HIDE_ADS: {
	                if (adView != null) adView.setVisibility(View.GONE);
	                break;
	            }
	            }
	        }
	    };

	    @Override
	    public void showAds(boolean show){
	        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	    }

	    @Override
	    public void startSmartWallAd(){
	        if (ma!=null)
	        	ma.runAppWall();
	    }
	    */

	}
