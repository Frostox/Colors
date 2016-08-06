package com.vosaye.colors;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PlayGround extends Activity {
	ImagePlayGround board;
	Handler mHandler = new Handler();
	Runnable updater;
	Toast toast;
	TextView v;
	int clock = 0;
	int speed = 80;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_ground);
		
		board = (ImagePlayGround) this.findViewById(R.id.board);
		board.setActivity(this);
		toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		v = (TextView) getLayoutInflater().inflate(R.layout.toast_layout, null);
		toast.setView(v);
		toast.setGravity(Gravity.TOP|Gravity.FILL , 0, 15);
		updater = new Runnable(){

			@Override
			public void run() {
				clock++;
				if(clock==speed) {
					
					clock = 0; 
					if(board.steps<10) speed = 100;
					else if(board.steps>=11&&board.steps<20) speed = 80;
					else if(board.steps>=21&&board.steps<40) speed = 70;
					else if(board.steps>=41&&board.steps<50) speed = 60;
					else if(board.steps>=51&&board.steps<60) speed = 50;
					else if(board.steps>=61) speed = 40;
					if(!board.paused){
						
						if(board.remainSecs<=0){
							board.paused = true;
							if(((Colors)PlayGround.this.getApplication()).colorsDB.isScoreHigher(board.score)){

								v.setText("Excellent, new high score!!\nTime's up!!");
							}
							else
							v.setText("Time's up!!");
							toast.show();
							Vibrator v = (Vibrator) PlayGround.this.getSystemService(Context.VIBRATOR_SERVICE);
							 // Vibrate for 500 milliseconds
							 v.vibrate(1000);
							board.remainSecs = 3;
						}else{
						board.remainSecs--; 
						board.timetext.setText(""+String.format("%02d", board.remainSecs/60)+":"+String.format("%02d", board.remainSecs%60));
						}
					}
				
				}
				if(board.offset==5) board.switcher = false;
				else if(board.offset==0) board.switcher = true;
				if(board.switcher){
					board.offset+=1;
				}else board.offset-=1;
				
				for(int i=0;i<board.blocks.size();i++){
					if(board.blocks.elementAt(i).inComing&&board.blocks.elementAt(i).r<board.eachRadius){
						board.blocks.elementAt(i).r+=10;
						if(board.blocks.elementAt(i).alpha<255)
						board.blocks.elementAt(i).alpha+=10;
						else board.blocks.elementAt(i).alpha = 255;
					}else if(board.blocks.elementAt(i).outGoing&&board.blocks.elementAt(i).r>=0){
						board.blocks.elementAt(i).r-=10;
						//if(board.blocks.elementAt(i).alpha>0)
						//board.blocks.elementAt(i).alpha-=10;
						//else board.blocks.elementAt(i).alpha = 0;
					}
				}
				mHandler.postDelayed(updater, 10);	
				board.invalidate();
				//Toast.makeText(PlayGround.this, "in now", Toast.LENGTH_SHORT).show();
			}
			
		};
		mHandler.postDelayed(updater, 10);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_ground, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.warning); 
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_play_ground);
            //setContentView(R.layout.only_landscape_message);         

        }
    }
}
