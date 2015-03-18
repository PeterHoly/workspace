package com.example.androidApp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bclib.Car;
import com.example.bclib.Client;
import com.example.bclib.Game;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {

	private boolean gameStarted = false;
	private MyThread mythread;
	private Client myClient;
	private Game myGame;
	private GameUI gameUI;
	private Render r;
	private String packageName;
	private boolean nitroPressed = false;
	private AssetManager am;
	private Context context;
	private int crashCar = 0;
	private boolean endGame = false;

	public SurfacePanel(Context context, Client myClient, Game myGame,
			String packageName, AssetManager am) {
		super(context);

		this.context = context;
		this.myClient = myClient;
		this.myGame = myGame;
		this.am = am;
		this.r = new Render(context, this.myGame.getDisplay(), this.am);
		this.gameUI = new GameUI(this.myGame.getDisplay(), context);
		this.packageName = packageName;

		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("vypis", "created");
		this.mythread = new MyThread(holder, myGame, this, myClient);

		if (gameStarted) {
			this.mythread.setRunning(true);
			this.mythread.start();
		}
	}

	public void Start() {
		if (this.mythread != null) {
			this.mythread.setRunning(true);
			this.mythread.start();
		} else {
			gameStarted = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.mythread.setRunning(false);

		boolean retry = true;

		while (retry) {
			try {
				this.mythread.join();
				retry = false;
			}

			catch (Exception e) {
				Log.v("Exception Occured", e.getMessage());
			}

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (gameUI.getButtonLeft().contains((int) event.getX(),
					(int) event.getY())) {
				mythread.getClient().leftPush();
			} else if (gameUI.getButtonRight().contains((int) event.getX(),
					(int) event.getY())) {
				mythread.getClient().rightPush();
			} else {
				mythread.getClient().release();
			}
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (gameUI.getButtonLeft().contains((int) event.getX(),
					(int) event.getY())) {
				mythread.getClient().leftPush();
			} else if (gameUI.getButtonRight().contains((int) event.getX(),
					(int) event.getY())) {
				mythread.getClient().rightPush();
			} else if (gameUI.getButtonNitro().contains((int) event.getX(),
					(int) event.getY())) {
				mythread.getClient().nitroPush();
				nitroPressed = true;
			} else if (gameUI.getButtonBackToMenu().contains(
					(int) event.getX(), (int) event.getY())
					&& endGame) {
				this.mythread.setRunning(false);

				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						mythread.getClient().closeSocket();
						Intent intent = new Intent(context, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(intent);
					}
				});
				t.start();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			mythread.getClient().release();
		}

		return true;
	}

	public void doDraw(Canvas canvas) throws InterruptedException {

		r.draw(null, canvas, getResources(), this.packageName, 0,
				myGame.getMapName());

		for (Car c : myGame.getMap().getCars()) {
			r.draw(c, canvas, getResources(), this.packageName, crashCar,
					myGame.getMapName());
		}

		r.drawButton(gameUI.getButtonLeft(), canvas, getResources(),
				this.packageName, "left");
		r.drawButton(gameUI.getButtonRight(), canvas, getResources(),
				this.packageName, "right");

		if (nitroPressed) {
			r.drawButton(gameUI.getButtonNitro(), canvas, getResources(),
					this.packageName, "nitropressed");
		} else {
			r.drawButton(gameUI.getButtonNitro(), canvas, getResources(),
					this.packageName, "nitro");
		}

		r.drawHp(gameUI, canvas,
				myGame.getMap().getCars().get(myGame.getIDplayer()).getHp());

		if (myGame.getMap().getCars().get(myGame.getIDplayer()).getWin() != -1) {
			r.drawWin(gameUI, canvas,
					myGame.getMap().getCars().get(myGame.getIDplayer())
							.getWin());
			r.drawMenuButton(gameUI.getButtonBackToMenu(), canvas,
					getResources(), this.packageName);
			endGame = true;
		}
		crashCar++;
	}
}
