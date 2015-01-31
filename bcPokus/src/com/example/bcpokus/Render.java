package com.example.bcpokus;

import com.example.bclib.Car;
import com.example.bclib.Display;
import com.example.bclib.MapObject;
import com.example.bclib.Obstacle;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Glass;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class Render {
	
	private Display myDisplay;
	
	public Render(Display d)
	{
		myDisplay = d;
	}

	Car car;
	public void draw(MapObject mo, Canvas myCanvas, Resources res, String packageName){
		
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		
		Paint p2 = new Paint();
		p2.setColor(Color.YELLOW);
		
		if(mo instanceof Obstacle)
		{
			//((Obstacle) mo).rotate(-car.getAngle(), car.getX(), car.getY());
			//myCanvas.drawLine((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()), (float)((Obstacle)mo).getX2(), (float)myDisplay.conversionY(((Obstacle)mo).getY2()), p2);
			//((Obstacle) mo).rotate(car.getAngle(), car.getX(), car.getY());
			
			myCanvas.drawLine((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()), (float)((Obstacle)mo).getX2(), (float)myDisplay.conversionY(((Obstacle)mo).getY2()), p);
		}
		else
		{
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
	
	public void drawButton(Rect rect, Canvas myCanvas, Resources res, String packageName, String fileName){
		int resourceID = res.getIdentifier(fileName, "drawable", packageName);
		Drawable drawable = res.getDrawable(resourceID);
		drawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
		drawable.draw(myCanvas);
	}
	
	public void drawText(String text, Canvas myCanvas){
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		
		myCanvas.drawText(text, (float)150, (float)myDisplay.conversionY(myDisplay.getY() + 200), p);
	}
}
