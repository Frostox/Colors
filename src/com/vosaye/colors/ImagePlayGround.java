package com.vosaye.colors;



import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path.FillType;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.view.View.OnClickListener;

import java.util.*;

public class ImagePlayGround extends ImageView implements OnTouchListener, OnClickListener{
	int mins;
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	ImageView board;
	int score = 0;
	TextView scoretext,timetext,accuracytet,highscore;
    float eachRadius = 0;
    int remainSecs = 5;
    int steps = 0, correctSteps = 0;
	float width, height;
	boolean paused = true;
	Toast toast;
	int offset = 0;
	boolean switcher = true;
	Random r = new Random();
	int colors[] = {Color.parseColor("#1abc9c"),Color.parseColor("#16a085"),Color.parseColor("#2ecc71"),Color.parseColor("#27ae60"),Color.parseColor("#3498db"),Color.parseColor("#2980b9"),Color.parseColor("#2c3e50"),Color.parseColor("#e74c3c")};
	
	int colorsMatrix[][] = {{Color.parseColor("#1abc9c"),Color.parseColor("#16a085"),Color.parseColor("#2ecc71"),Color.parseColor("#27ae60"),Color.parseColor("#3498db"),Color.parseColor("#2980b9"),Color.parseColor("#2c3e50"),Color.parseColor("#e74c3c")},
							{Color.parseColor("#2accac"),Color.parseColor("#26b095"),Color.parseColor("#3edc81"),Color.parseColor("#37be70"),Color.parseColor("#44a8eb"),Color.parseColor("#3990c9"),Color.parseColor("#3c4e60"),Color.parseColor("#f75c4c")},
							{Color.parseColor("#3adcbc"),Color.parseColor("#36c0a5"),Color.parseColor("#4eec91"),Color.parseColor("#47ce80"),Color.parseColor("#54b8fb"),Color.parseColor("#49a0d9"),Color.parseColor("#4c5e70"),Color.parseColor("#d76c5c")},
							{Color.parseColor("#4aeccc"),Color.parseColor("#46d0b5"),Color.parseColor("#5efca1"),Color.parseColor("#57de90"),Color.parseColor("#64c8cb"),Color.parseColor("#59b0e9"),Color.parseColor("#5c6e80"),Color.parseColor("#c77c6c")},
							{Color.parseColor("#5afcdc"),Color.parseColor("#56e0c5"),Color.parseColor("#6ebcb1"),Color.parseColor("#67eea0"),Color.parseColor("#74d8bb"),Color.parseColor("#69c0f9"),Color.parseColor("#6c7e90"),Color.parseColor("#b78c7c")}};
	
	
	Rect bounds = new Rect();

	Vector<Blocks> blocks = new Vector<Blocks>();
	Activity context;
	Colors clrs;
	public ImagePlayGround(Context context) {
		super(context);
		this.isInEditMode();
		Blocks block;
		
		if(blocks.isEmpty())
			for(int i=0;i<12;i++){
				blocks.add(block = new Blocks());
	    		int y = r.nextInt(colorsMatrix[0].length);
	    		block.colorIndexX = 0;
	    		block.colorIndexY = y;
	    		
			}
		try {
			//xagon = new Xagon(minBoxes, row);

			board = (ImageView) this.findViewById(R.id.board);
			board.setOnTouchListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.onFinishInflate();
		this.isInEditMode();

		saveGame();
	}
	
	public ImagePlayGround(Context context, AttributeSet attrs){
		super(context,attrs);

		this.isInEditMode();
		Blocks block;
		if(blocks.isEmpty())
			for(int i=0;i<12;i++){
				blocks.add(block = new Blocks());
	    		int y = r.nextInt(colorsMatrix[0].length);
	    		block.colorIndexX = 0;
	    		block.colorIndexY = y;
	    		
			}
		try {
			//xagon = new Xagon(minBoxes, row);

				board = (ImageView) this.findViewById(R.id.board);
				board.setOnTouchListener(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		saveGame();
	}
	
	public void resumeGame(){
		
	}
	
	public ImagePlayGround(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		Blocks block;
		if(blocks.isEmpty())
			for(int i=0;i<12;i++){
				blocks.add(block = new Blocks());
	    		int y = r.nextInt(colorsMatrix[0].length);
	    		block.colorIndexX = 0;
	    		block.colorIndexY = y;
	    		
			}
		try {
			//xagon = new Xagon(minBoxes, row);
			board = (ImageView) this.findViewById(R.id.board);
			board.setOnTouchListener(this);
			board.setOnClickListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveGame();
	}
	
	
	public void setMetrics(){
		
		try {
			//xagon = new Xagon(minBoxes, row, isTut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		
	}
	
	public void saveGame(){
		
	}
	
	TextView v;
	public void setActivity(Activity act){
		context = act;
		clrs = (Colors) act.getApplication();
		toast = Toast.makeText(act, "", Toast.LENGTH_SHORT);
		v = (TextView) act.getLayoutInflater().inflate(R.layout.toast_layout, null);
		toast.setView(v);
		toast.setGravity(Gravity.FILL, 0, 15);
		saveGame();
		Blocks block;
		if(blocks.isEmpty())
		for(int i=0;i<12;i++){
			blocks.add(block = new Blocks());
    		int y = r.nextInt(colorsMatrix[0].length);
    		block.colorIndexX = 0;
    		block.colorIndexY = y;
    		
		}
		
		
		scoretext = (TextView) act.findViewById(R.id.score);
		scoretext.setText("Score : 0");
		timetext = (TextView) act.findViewById(R.id.time);
		timetext.setText(""+String.format("%02d", remainSecs/60)+":"+String.format("%02d", remainSecs%60));
		accuracytet = (TextView) act.findViewById(R.id.accuracy);
		this.accuracytet.setText("Accuracy : 0%");
		
		
		highscore = (TextView) act.findViewById(R.id.textView1);
		Cursor c = clrs.colorsDB.rawQuery("select * from highscore;");
		if(c.moveToFirst()){
			highscore.setText("Highest Score : "+c.getInt(0)+" with "+c.getInt(1)+"% accuracy");
		}else highscore.setText("Highest Score : 0 with 0% accuracy");
		
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(blocks.isEmpty()) return;
        //blocks.clear();
        width = this.getWidth();
        width-=40;
        height = this.getHeight();
        height-=40;
        p.setColor(Color.parseColor("#c0392b"));
        p.setStrokeWidth(2);
        int counter = 0;
        Blocks blocks;
        for(int i=0;i<=2;i++){
        	for(int j=0;j<=3;j++){
        		blocks = this.blocks.elementAt(counter);
        		
        		//canvas.drawRect((i*(width/3)) + 30, (j*(height/4)) + 30, (i*(width/3)+(width/3)-15)+20, (j*(height/4)+(height/4)-15)+20, p);
        		float cx = 20+(i*(width/3))  +  ((((i+1)*(width/3)) - (i*(width/3)))/2);
        		float cy = 20 + (j*(height/4))  +  ((((j+1)*(height/4)) - (j*(height/4)))/2);
        		
        		float r = (height>width?width/7:height/9);
        		if(eachRadius==0) eachRadius = r;
        		if(true){
        			
        			p.setColor(Color.parseColor("#ecf0f1"));
        			p.setStyle(Paint.Style.STROKE);
        			p.setStrokeWidth(11-offset/1.1f); 
        			canvas.drawCircle(cx, cy, r, p);
        			
        			p.setColor(colorsMatrix[blocks.colorIndexX][blocks.colorIndexY]);
        			p.setStyle(Style.FILL);
        			p.setStrokeWidth(2);
        			if(true){
        			if(counter!=current){
        				if(blocks.inComing&&blocks.r<r&&counter!=previous){
        					p.setAlpha(blocks.alpha);
        					canvas.drawCircle(cx, cy, blocks.r, p);
        					p.setAlpha(255);
        					
        				}
        				else if(blocks.outGoing&&counter!=previous){
        					p.setAlpha(blocks.alpha);
        					canvas.drawCircle(cx, cy, blocks.r, p);
        					p.setAlpha(255);
        				}
        				else
        					canvas.drawCircle(cx, cy, r+offset, p);
        			}
        			else canvas.drawCircle(cx, cy, r+20, p);}
        			
        		}
        		
        		
        		
        		
        		float left = cx-r/1.5f;
        		float right = cx+r/1.5f;
        		float top = cy-r/1.5f;
        		float bottom = cy+r/1.5f;
        		
        		
        		blocks.left = left;
        		blocks.right = right;
        		blocks.top = top;
        		blocks.bottom = bottom;
        		
        		
        		//this.blocks.add(blocks);
        		counter++;
        	}
        	
        }
        
    }
	int current = -1,previous = -1;
	@Override
	public boolean onTouch(View vx, MotionEvent event) { 
		
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			paused = false;
			for(int i=0;i<blocks.size();i++){
				Blocks blocks = this.blocks.elementAt(i);
				if(x>blocks.left&&x<blocks.right&&y>blocks.top&&y<blocks.bottom&&i!=current){
					//Toast.makeText(this.getContext(), "clicked "+i, Toast.LENGTH_SHORT).show();
					previous = current;
					current = i;
					score = 0;
					scoretext.setText("Score : 0");
					remainSecs = 5;
					timetext.setText(""+String.format("%02d", remainSecs/60)+":"+String.format("%02d", remainSecs%60));
					steps = 0;
					correctSteps = 0;
					accuracytet.setText("Accuracy : 0%");
					for(int j=0;j<this.blocks.size();j++){
						if(this.blocks.elementAt(j).inRange) {this.blocks.elementAt(j).inComing = false; this.blocks.elementAt(j).outGoing = true; this.blocks.elementAt(j).alpha=255;}
						if(j!=i) {this.blocks.elementAt(j).inRange = false; 
							/*
 							int ycolor = r.nextInt(colorsMatrix[0].length);
							this.blocks.elementAt(j).colorIndexX = 0;
							this.blocks.elementAt(j).colorIndexY = ycolor;
							*/
						}
					}
					
					//find valid neighbours
					//get random number and one unique match
					//inflate
					int r = i%4;
					int c = i/4;
					Vector<Integer> temp = new Vector<Integer>();
					if(r-1>=0){
						if(!this.blocks.elementAt((c)*4+(r-1)).inRange) {this.blocks.elementAt((c)*4+(r-1)).inComing=true; this.blocks.elementAt((c)*4+(r-1)).outGoing=false; this.blocks.elementAt((c)*4+(r-1)).alpha = 0;}
						this.blocks.elementAt(c*4+(r-1)).inRange=true;
						temp.add(c*4+(r-1));
						if(c-1>=0){
							if(!this.blocks.elementAt((c-1)*4+(r-1)).inRange) {this.blocks.elementAt((c-1)*4+(r-1)).inComing=true; this.blocks.elementAt((c-1)*4+(r-1)).outGoing=false; this.blocks.elementAt((c-1)*4+(r-1)).alpha = 0;}
							this.blocks.elementAt((c-1)*4+(r-1)).inRange=true;
							temp.add((c-1)*4+(r-1));
						}
						if(c+1<=2){
							if(!this.blocks.elementAt((c+1)*4+(r-1)).inRange) {this.blocks.elementAt((c+1)*4+(r-1)).inComing=true; this.blocks.elementAt((c+1)*4+(r-1)).outGoing=false; this.blocks.elementAt((c+1)*4+(r-1)).alpha = 0;}
							this.blocks.elementAt((c+1)*4+(r-1)).inRange=true;
							temp.add((c+1)*4+(r-1));
						}
					}
					if(r+1<=3){
						if(!this.blocks.elementAt((c)*4+(r+1)).inRange) {this.blocks.elementAt((c)*4+(r+1)).inComing=true; this.blocks.elementAt((c)*4+(r+1)).outGoing=false; this.blocks.elementAt((c)*4+(r+1)).alpha=0;}
						this.blocks.elementAt(c*4+(r+1)).inRange=true;

						temp.add((c)*4+(r+1));
						if(c-1>=0){
							if(!this.blocks.elementAt((c-1)*4+(r+1)).inRange) {this.blocks.elementAt((c-1)*4+(r+1)).inComing=true; this.blocks.elementAt((c-1)*4+(r+1)).outGoing=false; this.blocks.elementAt((c-1)*4+(r+1)).alpha=0;}
							this.blocks.elementAt((c-1)*4+(r+1)).inRange=true;

							temp.add((c-1)*4+(r+1));
						}
						if(c+1<=2){
							if(!this.blocks.elementAt((c+1)*4+(r+1)).inRange) {this.blocks.elementAt((c+1)*4+(r+1)).inComing=true; this.blocks.elementAt((c+1)*4+(r+1)).outGoing=false; this.blocks.elementAt((c+1)*4+(r+1)).alpha=0;}
							this.blocks.elementAt((c+1)*4+(r+1)).inRange=true;
							temp.add((c+1)*4+(r+1));
						}
					}
					if(c-1>=0){
						if(!this.blocks.elementAt((c-1)*4+(r)).inRange) {this.blocks.elementAt((c-1)*4+(r)).inComing=true; this.blocks.elementAt((c-1)*4+(r)).outGoing=false; this.blocks.elementAt((c-1)*4+(r)).alpha=0;}
						this.blocks.elementAt((c-1)*4+r).inRange=true;

						temp.add((c-1)*4+(r));
					}
					if(c+1<=2){
						if(!this.blocks.elementAt((c+1)*4+(r)).inRange) {this.blocks.elementAt((c+1)*4+(r)).inComing=true; this.blocks.elementAt((c+1)*4+(r)).outGoing=false; this.blocks.elementAt((c+1)*4+(r)).alpha=0;}
						this.blocks.elementAt((c+1)*4+r).inRange=true;
						temp.add((c+1)*4+(r));
					}
					
					
					int random = 0;
					do{
						random = this.r.nextInt(temp.size());}
					while(random==previous);
					this.blocks.elementAt(temp.elementAt(random)).colorIndexX = this.blocks.elementAt(current).colorIndexX;
					this.blocks.elementAt(temp.elementAt(random)).colorIndexY = this.blocks.elementAt(current).colorIndexY;
					
					for(int clr=0;clr<temp.size();clr++){
						if(clr!=random){
							int ycolor = this.r.nextInt(colorsMatrix[0].length);
							this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = 0;
							this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = ycolor;
						}
					}
					
					
					this.invalidate();
					return true;
				}
			}
		    return false;
		case MotionEvent.ACTION_MOVE:
			if(paused) {
				
				if(clrs.colorsDB.isScoreHigher(score)){
					//Toast.makeText(context, "new high score", Toast.LENGTH_LONG).show();
					int acc = ((int)(((float)correctSteps/(float)steps)*100));
					clrs.colorsDB.updateHighScore(score, acc);
					highscore.setText("Highest Score : "+score+" with "+acc+"% accuracy");
				}
				
				
				for(int i=0;i<blocks.size();i++){
					blocks.elementAt(i).inRange = true;
					blocks.elementAt(i).inComing = true;
					blocks.elementAt(i).outGoing = false;
				}
				this.invalidate();
				current = -1;
				previous = -1;
				score = 0;
				steps = 0;
				correctSteps=0;
				
				remainSecs = 5;
				
				return false;
				
			}
			
			for(int i=0;i<blocks.size();i++){
				Blocks blocks = this.blocks.elementAt(i);
				if(x>blocks.left&&x<blocks.right&&y>blocks.top&&y<blocks.bottom&&i!=current&&this.blocks.elementAt(i).inRange){
					//Toast.makeText(this.getContext(), "Moved to "+i, Toast.LENGTH_SHORT).show();
					previous = current;
					if(current!=i) steps++;
					current = i;
					if(this.blocks.elementAt(previous).colorIndexX==this.blocks.elementAt(current).colorIndexX&&this.blocks.elementAt(previous).colorIndexY==this.blocks.elementAt(current).colorIndexY){
						score++;
						score = score + (int)((remainSecs)*((((float)correctSteps/(float)steps))));
						if(remainSecs<20)
						remainSecs+=1;

						timetext.setText(""+String.format("%02d", remainSecs/60)+":"+String.format("%02d", remainSecs%60));
						if(scoretext!=null) scoretext.setText("Score : "+score);
						
						correctSteps++;
						//Toast.makeText(this.getContext(), ""+correctSteps+" "+steps, Toast.LENGTH_LONG).show();
						accuracytet.setText("Accuracy : "+(int)(((float)correctSteps/(float)steps)*100)+"%");
					}else{
						Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
						 // Vibrate for 500 milliseconds
						 v.vibrate(500);
						 accuracytet.setText("Accuracy : "+(int)(((float)correctSteps/(float)steps)*100)+"%");
						 score = score - (int)((remainSecs)*((100-(((float)correctSteps/(float)steps)*100)))/100);
						 if(scoretext!=null) scoretext.setText("Score : "+score);
						 
					}
					for(int j=0;j<this.blocks.size();j++){
						if(this.blocks.elementAt(j).inRange) {this.blocks.elementAt(j).inComing = false; this.blocks.elementAt(j).outGoing = true;}
						if(j!=i) {this.blocks.elementAt(j).inRange = false;
						/*
 						int ycolor = r.nextInt(colorsMatrix[0].length);
						this.blocks.elementAt(j).colorIndexX = 0;
						this.blocks.elementAt(j).colorIndexY = ycolor;
						*/
					}
					}
					
					//find valid neighbours
					//get random number and one unique match
					//inflate
					int r = i%4;
					int c = i/4;
					
					Vector<Integer> temp = new Vector<Integer>();
					if(r-1>=0){
						if(!this.blocks.elementAt((c)*4+(r-1)).inRange) {this.blocks.elementAt((c)*4+(r-1)).inComing=true; this.blocks.elementAt((c)*4+(r-1)).outGoing=false; this.blocks.elementAt((c)*4+(r-1)).alpha = 0;}
						this.blocks.elementAt(c*4+(r-1)).inRange=true;
						temp.add(c*4+(r-1));
						if(c-1>=0){
							if(!this.blocks.elementAt((c-1)*4+(r-1)).inRange) {this.blocks.elementAt((c-1)*4+(r-1)).inComing=true; this.blocks.elementAt((c-1)*4+(r-1)).outGoing=false; this.blocks.elementAt((c-1)*4+(r-1)).alpha = 0;}
							this.blocks.elementAt((c-1)*4+(r-1)).inRange=true;
							temp.add((c-1)*4+(r-1));
						}
						if(c+1<=2){
							if(!this.blocks.elementAt((c+1)*4+(r-1)).inRange) {this.blocks.elementAt((c+1)*4+(r-1)).inComing=true; this.blocks.elementAt((c+1)*4+(r-1)).outGoing=false; this.blocks.elementAt((c+1)*4+(r-1)).alpha = 0;}
							this.blocks.elementAt((c+1)*4+(r-1)).inRange=true;
							temp.add((c+1)*4+(r-1));
						}
					}
					if(r+1<=3){
						if(!this.blocks.elementAt((c)*4+(r+1)).inRange) {this.blocks.elementAt((c)*4+(r+1)).inComing=true; this.blocks.elementAt((c)*4+(r+1)).outGoing=false; this.blocks.elementAt((c)*4+(r+1)).alpha=0;}
						this.blocks.elementAt(c*4+(r+1)).inRange=true;

						temp.add((c)*4+(r+1));
						if(c-1>=0){
							if(!this.blocks.elementAt((c-1)*4+(r+1)).inRange) {this.blocks.elementAt((c-1)*4+(r+1)).inComing=true; this.blocks.elementAt((c-1)*4+(r+1)).outGoing=false; this.blocks.elementAt((c-1)*4+(r+1)).alpha=0;}
							this.blocks.elementAt((c-1)*4+(r+1)).inRange=true;

							temp.add((c-1)*4+(r+1));
						}
						if(c+1<=2){
							if(!this.blocks.elementAt((c+1)*4+(r+1)).inRange) {this.blocks.elementAt((c+1)*4+(r+1)).inComing=true; this.blocks.elementAt((c+1)*4+(r+1)).outGoing=false; this.blocks.elementAt((c+1)*4+(r+1)).alpha=0;}
							this.blocks.elementAt((c+1)*4+(r+1)).inRange=true;
							temp.add((c+1)*4+(r+1));
						}
					}
					if(c-1>=0){
						if(!this.blocks.elementAt((c-1)*4+(r)).inRange) {this.blocks.elementAt((c-1)*4+(r)).inComing=true; this.blocks.elementAt((c-1)*4+(r)).outGoing=false; this.blocks.elementAt((c-1)*4+(r)).alpha=0;}
						this.blocks.elementAt((c-1)*4+r).inRange=true;

						temp.add((c-1)*4+(r));
					}
					if(c+1<=2){
						if(!this.blocks.elementAt((c+1)*4+(r)).inRange) {this.blocks.elementAt((c+1)*4+(r)).inComing=true; this.blocks.elementAt((c+1)*4+(r)).outGoing=false; this.blocks.elementAt((c+1)*4+(r)).alpha=0;}
						this.blocks.elementAt((c+1)*4+r).inRange=true;
						temp.add((c+1)*4+(r));
					}
					
					int random = 0;
					do{
						random = this.r.nextInt(temp.size());}
					while(random==previous);
					this.blocks.elementAt(temp.elementAt(random)).colorIndexX = this.blocks.elementAt(current).colorIndexX;
					this.blocks.elementAt(temp.elementAt(random)).colorIndexY = this.blocks.elementAt(current).colorIndexY;
					
					for(int clr=0;clr<temp.size();clr++){
						if(clr!=random){
							if(steps<=3){
								int ycolor = this.r.nextInt(colorsMatrix[0].length);
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = 0;
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = ycolor;
							}else if(steps>=4&&steps<=6){
								boolean value = this.r.nextBoolean();
								int ycolor = this.r.nextInt(colorsMatrix[0].length);
								if(value)
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = 0;
								else
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = colorsMatrix.length-1;
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = ycolor;
							}else if(steps>=7&&steps<=12){
								int ycolor = this.r.nextInt(colorsMatrix[0].length);
								
								boolean value = this.r.nextBoolean();
								if(value)
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = 0;
								else
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = colorsMatrix.length-2;
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = ycolor;
							}else if(steps>=13&&steps<=16){
								int ycolor;
								
								boolean value = this.r.nextBoolean();
								boolean value2 = this.r.nextBoolean();
								if(value)
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = 0;
								else
									this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = colorsMatrix.length-3;
								if(value2) ycolor = this.r.nextInt(colorsMatrix[0].length);
								else ycolor = this.blocks.elementAt(current).colorIndexY;
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = ycolor;
							} else{
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexX = this.r.nextInt(this.colorsMatrix.length);
								this.blocks.elementAt(temp.elementAt(clr)).colorIndexY = this.blocks.elementAt(current).colorIndexY;
							}
						}
					}
					this.blocks.elementAt(i).inRange = true;
					this.invalidate();
					return true;
				}
			}
		    return true;
		case MotionEvent.ACTION_UP:
			
			if(paused) return false;
			//Toast.makeText(this.getContext(), "You gave up! Lets see you try again!!", Toast.LENGTH_LONG).show();
			if(clrs.colorsDB.isScoreHigher(score)){

				v.setText("Excellent, new high score!!");
			}
			else
			v.setText("All you got is one touch!\nYou gave up! Lets see you try again!!");
			toast.show();
			paused = true;
			for(int i=0;i<blocks.size();i++){
				blocks.elementAt(i).inRange = true;
				blocks.elementAt(i).inComing = true;
				blocks.elementAt(i).outGoing = false;
			}
			this.invalidate();
			current = -1;
			previous = -1;

			//Toast.makeText(context, "can see", Toast.LENGTH_LONG).show();
			if(clrs.colorsDB.isScoreHigher(score)){
				//Toast.makeText(context, "new high score", Toast.LENGTH_LONG).show();
				int acc = ((int)(((float)correctSteps/(float)steps)*100));
				clrs.colorsDB.updateHighScore(score, acc);
				highscore.setText("Highest Score : "+score+" with "+acc+"% accuracy");
			}
			score = 0;
			steps = 0;
			correctSteps=0;
			
			remainSecs = 5;
			
			
			return true;
		    
		default:
		    return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
	
	class Blocks{
		float left,top,bottom,right;
		int colorIndexX,colorIndexY;
		boolean inRange = true;
		float r=0;
		boolean inComing = true;
		boolean outGoing = false;
		int alpha = 0;
	}
	
	
	

}

