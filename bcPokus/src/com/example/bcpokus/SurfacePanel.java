package com.example.bcpokus;

import java.awt.font.TextAttribute;

import com.example.bclib.Display;
import com.example.bclib.Game;
import com.example.bclib.Map;
import com.example.bclib.Obstacle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback{

	private MyThread mythread;
	private Game myGame = new Game(new Display(0,0,320,430));
	private GameUI gameUI = new GameUI();
	
	public SurfacePanel(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		Log.i("bc", "baf");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		mythread = new MyThread(holder, myGame, this);

		mythread.setRunning(true);

		mythread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mythread.setRunning(false);

		boolean retry = true;

		while(retry)
		{
			try
			{
				mythread.join();
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
				m.car.setIncrement(0.09f, 0.79f);
			}
			else if(gameUI.getButtonRight().contains((int)event.getX(), (int)event.getY()))
			{		
				m.car.setIncrement(-0.09f, 0.79f);
			}
			else{
				m.car.setIncrement(0.09f, 0f);
			}
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
				if(gameUI.getButtonLeft().contains((int)event.getX(), (int)event.getY()))
				{			
					m.car.setIncrement(0.09f, 0.79f);
				}
				else if(gameUI.getButtonRight().contains((int)event.getX(), (int)event.getY()))
				{					
					m.car.setIncrement(-0.09f, 0.79f);
				}
		}
		else if(event.getAction() == MotionEvent.ACTION_UP) {
			m.car.setIncrement(0.09f, 0f);
		}
		
		return true;
	}
	
	Map m = myGame.getMap();
	Display d = myGame.getDisplay();
	Render r = new Render(d);
	
	void doDraw(Canvas canvas) throws InterruptedException
	{
		canvas.drawColor(Color.WHITE);
		
		r.draw(m.car, canvas);
		
		for (Obstacle o : m.obstacles)
		{
			r.draw(o, canvas);
		}
		
		r.drawButton(gameUI.getButtonLeft(), canvas);
		r.drawButton(gameUI.getButtonRight(), canvas);
		r.drawText(gameUI.getText(), canvas);
		gameUI.setText("");
	}

}
