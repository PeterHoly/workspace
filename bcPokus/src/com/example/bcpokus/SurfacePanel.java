package com.example.bcpokus;

import com.example.bclib.Car;
import com.example.bclib.Client;
import com.example.bclib.Display;
import com.example.bclib.Game;
import com.example.bclib.Map;
import com.example.bclib.Obstacle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback{
	
	private boolean gameStarted = false;
	private MyThread mythread;
	private Client myClient;
	private Game myGame;
	private GameUI gameUI;
	private Map m;
	private Display d;
	private Render r;
	private String packageName;
	
	public SurfacePanel(Context context, Client myClient, Game myGame, String packageName) {
		super(context);
		Log.i("vypis", "konstruktor");
		this.myClient = myClient;
		this.myGame = myGame;
		this.m = myGame.getMap();
		this.d = myGame.getDisplay();
		this.r = new Render(d);
		this.gameUI = new GameUI(d);
		this.packageName = packageName;
		
		getHolder().addCallback(this);
		
		Log.i("bc", "baf");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("vypis", "created");
		this.mythread = new MyThread(holder, myGame, this, myClient);
		
		if(gameStarted){
			this.mythread.setRunning(true);
			this.mythread.start();
		}
	}
	
	public void Start(){
		if(this.mythread != null){
			this.mythread.setRunning(true);
			this.mythread.start();
		}else{
			gameStarted = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.mythread.setRunning(false);

		boolean retry = true;

		while(retry)
		{
			try
			{
				this.mythread.join();
				retry = false;
			}

			catch(Exception e)
			{
				Log.v("Exception Occured", e.getMessage());
			}

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			if(gameUI.getButtonLeft().contains((int)event.getX(), (int)event.getY()))
			{		
				//m.car.setIncrement(0.09f, 0.79f);
				mythread.myClient.leftPush();
			}
			else if(gameUI.getButtonRight().contains((int)event.getX(), (int)event.getY()))
			{		
				//m.car.setIncrement(-0.09f, 0.79f);
				mythread.myClient.rightPush();
			}
			else{
				//m.car.setIncrement(0.09f, 0f);
				mythread.myClient.release();
			}
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
				if(gameUI.getButtonLeft().contains((int)event.getX(), (int)event.getY()))
				{			
					//m.car.setIncrement(0.09f, 0.79f);
					mythread.myClient.leftPush();
				}
				else if(gameUI.getButtonRight().contains((int)event.getX(), (int)event.getY()))
				{					
					//m.car.setIncrement(-0.09f, 0.79f);
					mythread.myClient.rightPush();
				}
				else if(gameUI.getButtonNitro().contains((int)event.getX(), (int)event.getY()))
				{
					mythread.myClient.nitroPush();
					Log.i("asd","nitro.");
				}
		}
		else if(event.getAction() == MotionEvent.ACTION_UP) {
			//m.car.setIncrement(0.09f, 0f);
			mythread.myClient.release();
		}
		
		return true;
	}
	
	void doDraw(Canvas canvas) throws InterruptedException
	{
		canvas.drawColor(Color.WHITE);
		
		for(Car c : m.cars){
			r.draw(c, canvas, getResources(), this.packageName);
		}
		
		for (Obstacle o : m.obstacles){
			r.draw(o, canvas, getResources(), this.packageName);
		}
		
		r.drawButton(gameUI.getButtonLeft(), canvas, getResources(), this.packageName, "left");
		r.drawButton(gameUI.getButtonRight(), canvas, getResources(), this.packageName, "right");
		r.drawButton(gameUI.getButtonNitro(), canvas, getResources(), this.packageName, "nitro");
		r.drawText(gameUI.getText(), canvas);
	}

}
