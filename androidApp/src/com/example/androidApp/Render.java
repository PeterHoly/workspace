package com.example.androidApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import com.example.bclib.Car;
import com.example.bclib.Display;
import com.example.bclib.MapObject;

public class Render {

	private Display myDisplay;
	private Typeface tf;
	private Paint paint;
	private Paint grayPaint;
	private String[] imagesCarCrash = { "explosion1", "explosion2",
			"explosion3", "explosion4", "explosion5", "explosion6",
			"explosion7" };
	private String[] imagesCarNitro = { "redfire1", "redfire2", "redfire3",
			"redfire4", "redfire5", "redfire6", "redfire7", "redfire8",
			"redfire9", "redfire10", "redfire11", "redfire12", "redfire13",
			"redfire14", "redfire15", "redfire16", "redfire17", "redfire18",
			"redfire19", "redfire20", "redfire21", "redfire22", "redfire23",
			"redfire24" };

	private int numberImgCrash;
	private double widthFire = 17;
	private double heightFire = 10;
	private double widthAndHeightExplosion = 32;
	private Drawable mapImg = null;
	private Bitmap mapImgBitMap = null;
	private Context context;
	private float dp;
	private float sp;
	private android.view.Display display;

	public Render(Context context, Display d, AssetManager am) {
		this.myDisplay = d;
		this.context = context;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		this.display = wm.getDefaultDisplay();
		this.dp = display.getWidth() / 320.0f;
		this.sp = this.context.getResources().getDisplayMetrics().scaledDensity;

		tf = Typeface.createFromAsset(am, "fonts/capture_it.ttf");

		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTypeface(tf);
		paint.setTextSize(30 * this.sp);

		grayPaint = new Paint();
		grayPaint.setColor(Color.DKGRAY);
	}

	public void draw(MapObject mo, Canvas myCanvas, Resources res,
			String packageName, int crashCar, String mapName) {

		if (mo instanceof Car) {
			if (((Car) mo).getHp() <= 0) {

				numberImgCrash = ((Car) mo).getCrashImg(crashCar);

				if (numberImgCrash != -1) {

					int resourceID = res.getIdentifier(
							imagesCarCrash[numberImgCrash], "drawable",
							packageName);

					Drawable drawable = res.getDrawable(resourceID);

					int left = (int) ((mo.getRight() - mo.getWidth() / 2 - widthAndHeightExplosion / 2) * dp);
					int top = (int) ((myDisplay.conversionY(mo.getBottom()
							+ mo.getHeight() / 2 + widthAndHeightExplosion / 2)) * dp);
					int right = (int) ((mo.getRight() - mo.getWidth() / 2 + widthAndHeightExplosion / 2) * dp);
					int bottom = (int) ((myDisplay.conversionY(mo.getBottom()
							+ mo.getHeight() / 2 - widthAndHeightExplosion / 2)) * dp);

					drawable.setBounds(left, top, right, bottom);

					myCanvas.save();
					drawable.draw(myCanvas);
					myCanvas.restore();
				}
			} else {

				if (((Car) mo).getnitroActived()) {

					Random rand = new Random();
					int n = rand.nextInt(23);
					int resourceID = res.getIdentifier(imagesCarNitro[n],
							"drawable", packageName);

					Drawable drawable = res.getDrawable(resourceID);

					int left = (int) ((mo.getLeft() - widthFire) * dp);
					int top = (int) (myDisplay.conversionY(mo.getTop()
							- mo.getHeight() / 2 + heightFire / 2) * dp);
					int right = (int) (mo.getLeft() * dp);
					int bottom = (int) (myDisplay.conversionY(mo.getTop()
							- mo.getHeight() / 2 - heightFire / 2) * dp);

					drawable.setBounds(left, top, right, bottom);
					myCanvas.save();
					myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),
							(float) mo.getX() * dp,
							(float) myDisplay.conversionY(mo.getY()) * dp);
					drawable.draw(myCanvas);
					myCanvas.restore();
				}

				String codeB = ((Car) mo).getBodywork().getCode();
				String codeG = ((Car) mo).getGlass().getCode();
				String code = codeB + codeG;

				int resourceID = res.getIdentifier(code, "drawable",
						packageName);

				Drawable drawable = res.getDrawable(resourceID);

				int left = (int) (mo.getLeft() * dp);
				int top = (int) (myDisplay.conversionY(mo.getTop()) * dp);
				int right = (int) (mo.getRight() * dp);
				int bottom = (int) (myDisplay.conversionY(mo.getBottom()) * dp);

				drawable.setBounds(left, top, right, bottom);
				myCanvas.save();
				myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),
						(float) mo.getX() * dp,
						(float) myDisplay.conversionY(mo.getY()) * dp);
				drawable.draw(myCanvas);
				myCanvas.restore();
			}
		} else {

			if (this.mapImg == null) {
				File dir = context.getDir("maps", Context.MODE_PRIVATE);
				InputStream io = null;

				try {
					io = new FileInputStream(new File(dir, mapName));
					this.mapImg = Drawable.createFromStream(io, mapName);
					this.mapImgBitMap = ((BitmapDrawable) mapImg).getBitmap();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			int srcTop = (int) ((myDisplay.getY() + myDisplay.getHeight()) * (mapImgBitMap
					.getWidth() / myDisplay.getWidth()));
			srcTop = mapImgBitMap.getHeight() - srcTop;

			int srcLeft = 0;
			int srcRight = (int) mapImgBitMap.getWidth();
			int srcBottom = srcTop
					+ (int) (myDisplay.getHeight() * (mapImgBitMap.getWidth() / myDisplay
							.getWidth()));

			int destLeft = 0;
			int destTop = 0;
			int destRight = (int) (myDisplay.getWidth() * dp);
			int destBottom = (int) (myDisplay.getHeight() * dp);

			myCanvas.drawBitmap(mapImgBitMap, new Rect(srcLeft, srcTop,
					srcRight, srcBottom), new Rect(destLeft, destTop,
					destRight, destBottom), null);
		}
	}

	public void drawMenuButton(Rect rect, Canvas myCanvas, Resources res,
			String packageName) {
		int resourceID = res.getIdentifier("buildperandapp", "drawable",
				packageName);
		Drawable d = res.getDrawable(resourceID);
		d.setBounds(rect.left, rect.top, rect.right, rect.bottom);
		d.draw(myCanvas);
		Paint backPaint = new Paint();
		backPaint.setColor(Color.GRAY);
		backPaint.setTypeface(tf);
		backPaint.setTextSize(18 * dp);
		myCanvas.drawText("BACK TO MENU", rect.left + 15 * dp, rect.top + 30
				* dp, backPaint);
	}

	public void drawWin(GameUI gameUI, Canvas myCanvas, int w) {
		Paint winPaint = new Paint();
		winPaint.setColor(Color.BLUE);
		winPaint.setTypeface(tf);
		winPaint.setTextSize(35 * dp);

		if (w == 0) {
			myCanvas.drawText(gameUI.getWin()[w],
					(float) (myDisplay.getWidth() / 3 - 5) * dp,
					(float) (myDisplay.getHeight() / 3) * dp, winPaint);
		} else if (w == 1) {
			myCanvas.drawText(gameUI.getWin()[w],
					(float) (myDisplay.getWidth() / 4 - 10) * dp,
					(float) (myDisplay.getHeight() / 3) * dp, winPaint);
		}
	}

	public void drawButton(Rect rect, Canvas myCanvas, Resources res,
			String packageName, String fileName) {
		if (fileName.equals("right")) {
			myCanvas.drawRect(rect.left, rect.top - 3 * dp,
					rect.right + 5 * dp, rect.bottom + 5 * dp, grayPaint);
		} else if (fileName.equals("left")) {
			myCanvas.drawRect(rect.left - 5 * dp, rect.top - 3 * dp, rect.right
					+ 2 * dp, rect.bottom + 5 * dp, grayPaint);
		}
		int resourceID = res.getIdentifier(fileName, "drawable", packageName);
		Drawable d = res.getDrawable(resourceID);
		d.setBounds(rect.left, rect.top, rect.right, rect.bottom);
		d.draw(myCanvas);
	}

	public void drawHp(GameUI gameUI, Canvas myCanvas, int hp) {
		myCanvas.drawText(gameUI.getHP()[hp],
				(float) (myDisplay.getWidth() - 80) * dp, 40 * dp, paint);
	}

	public static void createImg(byte[] map, String name, Context context) {
		File dir = context.getDir("maps", Context.MODE_PRIVATE);
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(dir, name));
			Bitmap b = BitmapFactory.decodeByteArray(map, 0, map.length);
			b.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
