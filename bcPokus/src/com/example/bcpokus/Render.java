package com.example.bcpokus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Render {
	
	private Display myDisplay;
	private Typeface tf;
	private Paint paint;
	private Paint p;
	private Paint p2;
	private String[] imagesCarCrash = {"explosion1", "explosion2", "explosion3", "explosion4", "explosion5", "explosion6", "explosion7"};
	private String[] imagesCarNitro = {"redfire1", "redfire2", "redfire3", "redfire4", "redfire5", "redfire6", "redfire7", "redfire8", 
			"redfire9", "redfire10", "redfire11", "redfire12", "redfire13", "redfire14", "redfire15", "redfire16", "redfire17", 
			"redfire18", "redfire19", "redfire20", "redfire21", "redfire22", "redfire23", "redfire24"};
	
	public int numberImgCrash;
	private double widthFire = 17;
	private double heightFire = 10;
	
	private double widthAndheightExplosion = 32;
	
	public Render(Display d, AssetManager am)
	{
		myDisplay = d;
		
		tf = Typeface.createFromAsset(am, "fonts/capture_it.ttf");
		
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTypeface(tf);
		paint.setTextSize(30);
		
		p = new Paint();
		p.setColor(Color.BLACK);
		
		p2 = new Paint();
		p2.setColor(Color.YELLOW);
	}

	Car car;
	public void draw(MapObject mo, Canvas myCanvas, Resources res, String packageName, int crashCar){
		
		if(mo instanceof Obstacle)
		{
			//((Obstacle) mo).rotate(-car.getAngle(), car.getX(), car.getY());
			//myCanvas.drawLine((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()), (float)((Obstacle)mo).getX2(), (float)myDisplay.conversionY(((Obstacle)mo).getY2()), p2);
			//((Obstacle) mo).rotate(car.getAngle(), car.getX(), car.getY());
			
			myCanvas.drawLine((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()), (float)((Obstacle)mo).getX2(), (float)myDisplay.conversionY(((Obstacle)mo).getY2()), p);
		}
		else
		{
			
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
				
				//car = (Car) mo;
				//myCanvas.drawRect((float)mo.getLeft(), (float)myDisplay.conversionY(mo.getTop()), (float)mo.getRight(), (float)myDisplay.conversionY(mo.getBottom()), p2);
	
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
				
				
				//myCanvas.save();
				//myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),(float)mo.getX(),(float)myDisplay.conversionY(mo.getY()));
				//myCanvas.drawRect((float)mo.getLeft(), (float)myDisplay.conversionY(mo.getTop()), (float)mo.getRight(), (float)myDisplay.conversionY(mo.getBottom()), p2);
				//myCanvas.restore();
			}
		}
	}
	
	public void drawButton(Rect rect, Canvas myCanvas, Resources res, String packageName, String fileName){
		int resourceID = res.getIdentifier(fileName, "drawable", packageName);
		Drawable drawable = res.getDrawable(resourceID);
		drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
		drawable.draw(myCanvas);
	}
	
	public void drawHp(GameUI gameUI, Canvas myCanvas, int hp){
		
		myCanvas.drawText(gameUI.hp[hp], (float)myDisplay.getWidth()-80, (float)myDisplay.getHeight()-400, paint);
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
