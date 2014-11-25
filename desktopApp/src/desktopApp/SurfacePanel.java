package desktopApp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Car;
import com.example.bclib.Game;
import com.example.bclib.Map;
import com.example.bclib.Menu;
import com.example.bclib.Obstacle;

public class SurfacePanel implements PaintListener {
	int i;
	boolean pressed = false;
	private MyThread myThread;
	private Shell shell;
	private Display display;
	private Menu menu;
	private Game myGame = new Game(new com.example.bclib.Display(0,0,314,429-31));
	
	public SurfacePanel() {
		
		display = new Display();
		shell = new Shell(display);
		shell.setSize(320,430);
		shell.setMinimumSize(320, 430);
		
		org.eclipse.swt.graphics.Color color = display.getSystemColor(SWT.COLOR_WHITE);
		shell.setBackground(color);
		 
		shell.addPaintListener(this);
		shell.addKeyListener(keyAdapter);
		
		
		this.OnCreate();
		shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		shell.dispose();
		myThread.setRunning(false);
	}
	
	private void OnCreate(){
		this.myThread = new MyThread(this,myGame,shell,display);
		this.menu = new Menu(this.myThread.myClient, d, myGame);
		
		myThread.setRunning(true);
		myThread.start();
	}
	
	KeyAdapter keyAdapter = new KeyAdapter()
	{	
		public void keyPressed(KeyEvent e)
		{
			if(!pressed) {
				pressed = true;
				synchronized (myGame) {
					if(e.keyCode == SWT.ARROW_LEFT){
						//myGame.getMap().car.setIncrement(0.09f, 0.79f);//0.79
						myThread.myClient.leftPush();
					}
					else if(e.keyCode == SWT.ARROW_RIGHT){
						//myGame.getMap().car.setIncrement(-0.09f, 0.79f);
						myThread.myClient.rightPush();
					}
				}
			
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			pressed = false;
			synchronized (myGame) {
				//myGame.getMap().car.setIncrement(0.09f, 0f);
				myThread.myClient.release();
			}
		}
	};
	
	Map m = myGame.getMap();
	com.example.bclib.Display d = myGame.getDisplay();
	Render r = new Render(d);
	
	public void paintControl(PaintEvent e) {

		e.gc.setLineAttributes(new LineAttributes(1,SWT.CAP_FLAT,SWT.JOIN_MITER));
		
		for(Car c : m.cars){
			r.draw(c, e,shell);
		}
		
		for (Obstacle o : m.obstacles){
			r.draw(o, e,shell);
		}
	}
}

