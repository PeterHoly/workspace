package com.example.bcpokus;

import com.example.bclib.Car;
import com.example.bclib.Game;
import com.example.bclib.Logic;
import com.example.bclib.Client;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyThread extends Thread {
	private SurfacePanel mySurfacePanel;
	private boolean mRun = false;
	private SurfaceHolder holder;
	private Game myGame;
	private Object o = new Object();
	private Logic myLogic = new Logic();
	public Canvas mcanvas;
	public Client myClient;
	
	
	public MyThread(SurfaceHolder holder, Game game, SurfacePanel panel, Client myClient) {
		this.mySurfacePanel = panel;
		this.mRun = false;
		this.holder = holder;
		this.myGame = game;
		this.myClient = myClient;
	}
	
	void setRunning(boolean bRun)
	{
		mRun = bRun;
	}

	@Override
	public void run()
	{	
		while(mRun)
		{
			mcanvas = holder.lockCanvas();
			
			if(mcanvas != null)
			{
				try {
					
					//myLogic.increaseValue(myGame, myClient);
					myLogic.increaseValue2(myGame, myClient);
					
					mySurfacePanel.doDraw(mcanvas);
					
					synchronized (o) {
						o.wait(40);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				holder.unlockCanvasAndPost(mcanvas);

			}	
		}
	}
			
			
}
