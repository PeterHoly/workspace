package com.example.bcpokus;

import com.example.bclib.Game;
import com.example.bclib.Logic;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyThread extends Thread {
	private SurfacePanel mySurfacePanel;
	private Canvas mcanvas;
	private boolean mRun = false;
	private SurfaceHolder holder;
	private Game myGame;
	private Object o = new Object();
	private Logic myLogic = new Logic();
	
	public MyThread(SurfaceHolder holder, Game game, SurfacePanel panel) {
		this.mySurfacePanel = panel;
		this.mRun = false;
		this.holder = holder;
		this.myGame = game;
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
					
					myLogic.increaseValue(myGame);
					mySurfacePanel.doDraw(mcanvas);
					
					synchronized (o) {
						o.wait(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				holder.unlockCanvasAndPost(mcanvas);

			}	
		}
	}
			
			
}
