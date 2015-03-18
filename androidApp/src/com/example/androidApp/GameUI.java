package com.example.androidApp;

import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;

import com.example.bclib.Display;

public class GameUI {
	private Rect buttonLeft;
	private Rect buttonRight;
	private Rect buttonNitro;
	private Rect buttonBackToMenu;
	private String[] HP = { "HP 0", "HP 1", "HP 2", "HP 3", "HP 4", "HP 5",
			"HP 6", "HP 7", "HP 8", "HP 9", "HP 10" };
	private String[] win = { "WINNER", "GAME OVER" };

	public GameUI(Display di, Context context) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		android.view.Display d = wm.getDefaultDisplay();
		float dp = d.getWidth() / 320.0f;

		buttonLeft = new Rect((int) (2 * dp), (int) (d.getHeight() - 60 * dp),
				(int) (d.getWidth() / 2 - 1 * dp),
				(int) (d.getHeight() - 2 * dp));
		buttonRight = new Rect((int) (d.getWidth() / 2 + 1 * dp),
				(int) (d.getHeight() - 60 * dp), (int) (d.getWidth() - 2 * dp),
				(int) (d.getHeight() - 2 * dp));
		buttonNitro = new Rect((int) (d.getWidth() - 54 * dp),
				(int) (d.getHeight() - 225 * dp),
				(int) (d.getWidth() - 4 * dp), (int) (d.getHeight() - 75 * dp));
		buttonBackToMenu = new Rect((int) (10 * dp), (int) (10 * dp),
				(int) (160 * dp), (int) (60 * dp));
	}

	public Rect getButtonLeft() {
		return this.buttonLeft;
	}

	public Rect getButtonRight() {
		return this.buttonRight;
	}

	public Rect getButtonNitro() {
		return this.buttonNitro;
	}

	public Rect getButtonBackToMenu() {
		return this.buttonBackToMenu;
	}

	public String[] getHP() {
		return this.HP;
	}

	public String[] getWin() {
		return this.win;
	}
}
