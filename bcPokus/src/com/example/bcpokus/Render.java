package com.example.bcpokus;

import com.example.bclib.Car;
import com.example.bclib.Display;
import com.example.bclib.MapObject;
import com.example.bclib.Obstacle;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Render {
	
	private Display myDisplay;
	
	public Render(Display d)
	{
		myDisplay = d;
	}

	Car car;
	public void draw(MapObject mo, Canvas myCanvas){
		
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
			
			myCanvas.save();
			myCanvas.rotate((float) Math.toDegrees(-mo.getAngle()),(float)mo.getX(),(float)myDisplay.conversionY(mo.getY()));
			myCanvas.drawRect((float)mo.getLeft(), (float)myDisplay.conversionY(mo.getTop()), (float)mo.getRight(), (float)myDisplay.conversionY(mo.getBottom()), p);
			myCanvas.restore();
		}
	}
	
	public void drawButton(Rect rect, Canvas myCanvas){
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		
		myCanvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, p);
	}
	
	public void drawText(String text, Canvas myCanvas){
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		
		myCanvas.drawText(text, (float)150, (float)myDisplay.conversionY(myDisplay.getY() + 200), p);
	}
}
