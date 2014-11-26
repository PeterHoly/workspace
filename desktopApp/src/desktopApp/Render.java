package desktopApp;

import java.util.concurrent.locks.Lock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Car;
import com.example.bclib.MapObject;
import com.example.bclib.Obstacle;

public class Render {
	
	private com.example.bclib.Display myDisplay;
	
	public Render(com.example.bclib.Display d)
	{
		myDisplay = d;
	}

	public void draw(MapObject mo, PaintEvent e, Shell s){
		
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
					Transform t = new Transform(e.display);
					t.translate((float)mo.getX(), (float)myDisplay.conversionY(mo.getY()));
					t.rotate((float) -Math.toDegrees(mo.getAngle()));
					t.translate(-(float)mo.getX(), -(float)myDisplay.conversionY(mo.getY()));
					
					e.gc.setTransform(t);
					e.gc.setBackground(p);
					e.gc.drawRectangle((int)Math.round(mo.getLeft()), (int)Math.round(myDisplay.conversionY(mo.getTop())), (int)Math.round(mo.getWidth()), (int)Math.round(mo.getHeight()));
					
					e.gc.setTransform(null);
					
					t.dispose();
					
				}
			}
		}
	}
}
