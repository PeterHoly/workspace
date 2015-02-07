package desktopApp;

import java.awt.image.RGBImageFilter;
import java.util.concurrent.locks.Lock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.css.RGBColor;

import com.example.bclib.Car;
import com.example.bclib.MapObject;
import com.example.bclib.Obstacle;

public class Render {
	
	private com.example.bclib.Display myDisplay;
	private String[] imagesCarCrash = {"explosion1", "explosion2", "explosion3", "explosion4", "explosion5", "explosion6", "explosion7"};
	public int numberImgCrash;
	
	public Render(com.example.bclib.Display d)
	{
		myDisplay = d;
	}

	public void draw(MapObject mo, PaintEvent e, Shell s, int crashCar){
		
		Color p = new Color(s.getDisplay(), 0,0,0);
		
		if(mo instanceof Obstacle)
		{
			e.gc.setForeground(p);
			e.gc.drawLine((int)mo.getX(), (int)myDisplay.conversionY(mo.getY()), (int)((Obstacle)mo).getX2(), (int)myDisplay.conversionY(((Obstacle)mo).getY2()));
			
		}
		else
		{
			synchronized (mo) {
				synchronized (myDisplay) {
					
					if(((Car)mo).getHp() == 0){
						
						numberImgCrash = ((Car)mo).getCrashImg(crashCar);
						
						if(numberImgCrash != -1){
							
							Image myImage = new Image(e.display, "images/"+imagesCarCrash[numberImgCrash]+".png");
							e.gc.drawImage(myImage, 0, 0, myImage.getBounds().width, myImage.getBounds().height,(int)Math.round(((Car)mo).getxCrash()-mo.getWidth()/2), (int)Math.round(myDisplay.conversionY(((Car)mo).getyCrash()+mo.getHeight()/2)), myImage.getBounds().width, myImage.getBounds().height);
						}
					}
					else{
						
						Image myImage = new Image(e.display, "images/"+((Car)mo).getImgCode()+".png");
				    	  
						Transform t = new Transform(e.display);
						t.translate((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()));
						t.rotate((float) -Math.toDegrees(mo.getAngle()));
						t.translate(-(float)mo.getX(), -(float)myDisplay.conversionY(mo.getY()));
						
						e.gc.setTransform(t);
						e.gc.setBackground(p);
						
						//e.gc.drawRectangle((int)Math.round(mo.getLeft()), (int)Math.round(myDisplay.conversionY(mo.getTop())), (int)Math.round(mo.getWidth()), (int)Math.round(mo.getHeight()));
						
						e.gc.drawImage(myImage, 0, 0, myImage.getBounds().width, myImage.getBounds().height,(int)Math.round(mo.getX()-mo.getWidth()/2), (int)Math.round(myDisplay.conversionY(mo.getY()+mo.getHeight()/2)), (int)Math.round(mo.getWidth()), (int)Math.round(mo.getHeight()));
						
						e.gc.setTransform(null);
						
						t.dispose();
					}
				}
			}
		}
	}
	
	public void drawImg(PaintEvent e, Shell s, boolean nitroPressed, int hp){
		Image myImage;
		if(nitroPressed){
			myImage = new Image(e.display, "images/nitroPressed.png");
		}
		else{
			myImage = new Image(e.display, "images/nitro.png");	
		}
		
		
		Font myfont = new Font(e.display, new FontData("Capture it", 15, SWT.NORMAL));
		Color p1 = new Color(e.display, 255, 255, 255);
		Color p2 = new Color(e.display, 128, 128, 255);
		
		e.gc.setFont(myfont);
		e.gc.setForeground(p2);
		e.gc.setBackground(p1);
		e.gc.drawText("HP "+hp, (int)myDisplay.getWidth()-60, (int)myDisplay.getHeight()-390);
		
		e.gc.drawImage(myImage, 0, 0, myImage.getBounds().width, myImage.getBounds().height, (int)myDisplay.getWidth()-30, (int)myDisplay.getHeight()-90,myImage.getBounds().width/3, myImage.getBounds().height/3);
	}
}
