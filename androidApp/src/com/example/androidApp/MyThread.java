package com.example.androidApp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.bclib.Client;
import com.example.bclib.Game;
import com.example.bclib.Logic;

public class MyThread extends Thread {

	private SurfacePanel mySurfacePanel;
	private boolean mRun = false;
	private SurfaceHolder holder;
	private Game myGame;
	private Object o = new Object();
	private Logic myLogic = new Logic();
	private Canvas mcanvas;
	private Client myClient;

	public MyThread(SurfaceHolder holder, Game game, SurfacePanel panel,
			Client myClient) {
		this.mySurfacePanel = panel;
		this.mRun = false;
		this.holder = holder;
		this.myGame = game;
		this.myClient = myClient;
	}

	public void setRunning(boolean bRun) {
		mRun = bRun;
	}

	public Client getClient() {
		return this.myClient;
	}

	@Override
	public void run() {
		while (mRun) {
			mcanvas = holder.lockCanvas();

			if (mcanvas != null) {
				try {
					myLogic.increaseValue(myGame, myClient);
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
