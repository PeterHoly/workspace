package com.example.androidApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.example.bclib.Car;
import com.example.bclib.Display;
import com.example.bclib.MapObject;
import com.example.bclib.Obstacle;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Render {
	
	private Display myDisplay;
	private Typeface tf;
	private Paint paint;
	private Paint grayPaint;
	private String[] imagesCarCrash = {"explosion1", "explosion2", "explosion3", "explosion4", "explosion5", "explosion6", "explosion7"};
	private String[] imagesCarNitro = {"redfire1", "redfire2", "redfire3", "redfire4", "redfire5", "redfire6", "redfire7", "redfire8", 
			"redfire9", "redfire10", "redfire11", "redfire12", "redfire13", "redfire14", "redfire15", "redfire16", "redfire17", 
			"redfire18", "redfire19", "redfire20", "redfire21", "redfire22", "redfire23", "redfire24"};
	
	public int numberImgCrash;
	private double widthFire = 17;
	private double heightFire = 10;
	private Drawable mapImg = null;
	private Bitmap mapImgBitMap = null;
	private Context context;
	
	private double widthAndheightExplosion = 32;
	
	public Render(Context context, Display d, AssetManager am)
	{
		myDisplay = d;
		this.context = context;
		
		tf = Typeface.createFromAsset(am, "fonts/capture_it.ttf");
		
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTypeface(tf);
		paint.setTextSize(30);
		
		grayPaint = new Paint();
		grayPaint.setColor(Color.DKGRAY);
	}

	public void draw(MapObject mo, Canvas myCanvas, Resources res, String packageName, int crashCar, String mapName){
		
		if(mo instanceof Obstacle)
		{
			Paint p = new Paint();
			p.setColor(Color.WHITE);
			myCanvas.drawLine((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()), (float)((Obstacle)mo).getX2(), (float)myDisplay.conversionY(((Obstacle)mo).getY2()), p);
		}
		else if(mo instanceof Car){	
			if(((Car)mo).getHp() <= 0){
				
				numberImgCrash = ((Car)mo).getCrashImg(crashCar);
				
				if(numberImgCrash != -1){
											
					int resourceID = res.getIdentifier(imagesCarCrash[numberImgCrash], "drawable", packageName);
					   
					Drawable drawable = res.getDrawable(resourceID);
					
					drawable.setBounds((int)(mo.getRight()-mo.getWidth()/2-widthAndheightExplosion/2), (int)(myDisplay.conversionY(mo.getBottom() + mo.getHeight()/2 + widthAndheightExplosion/2)),(int)(mo.getRight()-mo.getWidth()/2+widthAndheightExplosion/2),(int)(myDisplay.conversionY(mo.getBottom() + mo.getHeight()/2 - widthAndheightExplosion/2)));
					
					myCanvas.save();
					drawable.draw(myCanvas);
					myCanvas.restore();
				}
			}
			else{
				
				if(((Car)mo).getnitroActived()){
					
					Random rand = new Random();
					int  n = rand.nextInt(23);
					int resourceID = res.getIdentifier(imagesCarNitro[n], "drawable", packageName);
					   
					Drawable drawable = res.getDrawable(resourceID);
					drawable.setBounds((int)(mo.getLeft()-widthFire), (int)myDisplay.conversionY(mo.getTop()-mo.getHeight()/2+heightFire/2), (int)mo.getLeft(), (int)myDisplay.conversionY(mo.getTop()-mo.getHeight()/2-heightFire/2));
					myCanvas.save();
					myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),(float)mo.getX(),(float)myDisplay.conversionY(mo.getY()));
					drawable.draw(myCanvas);
					myCanvas.restore();
				}
	
				String codeB = ((Car)mo).getBodywork().getCode();
				String codeG = ((Car)mo).getGlass().getCode();
				String code = codeB+codeG;
					
				int resourceID = res.getIdentifier(code, "drawable", packageName);
				
				Drawable drawable = res.getDrawable(resourceID);
				drawable.setBounds((int)mo.getLeft(), (int)myDisplay.conversionY(mo.getTop()), (int)mo.getRight(), (int)myDisplay.conversionY(mo.getBottom()));
				myCanvas.save();
				myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),(float)mo.getX(),(float)myDisplay.conversionY(mo.getY()));
				drawable.draw(myCanvas);
				myCanvas.restore();	
			}
		}
		else{
			
			if(this.mapImg == null)
			{
				File dir = context.getDir("maps", Context.MODE_PRIVATE);
				InputStream io = null;
				
				try {
					io = new FileInputStream(new File(dir, mapName));
					this.mapImg = Drawable.createFromStream(io, mapName);
					this.mapImgBitMap = ((BitmapDrawable)mapImg).getBitmap();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			int srcTop = (int)((myDisplay.getY() + myDisplay.getHeight()) * (mapImgBitMap.getWidth()/myDisplay.getWidth()));
			srcTop = mapImgBitMap.getHeight() - srcTop;
			
			int srcLeft = 0;
		    int srcRight = (int)mapImgBitMap.getWidth();
		    int srcBottom = srcTop + (int)(myDisplay.getHeight()*(mapImgBitMap.getWidth()/myDisplay.getWidth()));
		    
		    int destLeft = 0;
		    int destTop = 0;
		    int destRight = (int)myDisplay.getWidth();
		    int destBottom = (int)myDisplay.getHeight();
			
			myCanvas.drawBitmap(mapImgBitMap, new Rect(srcLeft,srcTop,srcRight,srcBottom), new Rect(destLeft,destTop,destRight,destBottom), null);
		}
	}
	
	public void drawButton(Rect rect, Canvas myCanvas, Resources res, String packageName, String fileName){
		if(fileName.equals("right")){
			myCanvas.drawRect(rect.left, rect.top-3, rect.right+5, rect.bottom+5, grayPaint);
		}
		else if(fileName.equals("left")){
			myCanvas.drawRect(rect.left-5, rect.top-3, rect.right+2, rect.bottom+5, grayPaint);
		}
		
		int resourceID = res.getIdentifier(fileName, "drawable", packageName);
		Drawable d = res.getDrawable(resourceID);
		d.setBounds(rect.left, rect.top, rect.right, rect.bottom);
		d.draw(myCanvas);	
	}
	
	public void drawHp(GameUI gameUI, Canvas myCanvas, int hp){
		myCanvas.drawText(gameUI.HP[hp], (float)myDisplay.getWidth()-80, (float)myDisplay.getHeight()-400, paint);
	}
	
	public static void createImg(byte[] map, String name, Context context){
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
