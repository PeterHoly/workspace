package desktopApp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Car;
import com.example.bclib.Game;
import com.example.bclib.Map;
import com.example.bclib.Obstacle;

public class SurfacePanel implements PaintListener {
	int i;
	boolean pressed = false;
	private MyThread myThread;
	private Shell shell;
	private Display display;
	private DesktopMenu menu;
	private boolean nitroPressed = false;
	private Game myGame = new Game(new com.example.bclib.Display(0,0,314,429-31));
	
	public SurfacePanel() {
		
		display = new Display();
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
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
		myThread.setRunning(false);
	}
	
	private void OnCreate(){
		this.myThread = new MyThread(this,myGame,shell,display);
		this.menu = new DesktopMenu(display, shell, this.myThread.myClient, d, myGame, this);
	}
	
	public void Start(){
		myThread.setRunning(true);
		myThread.start();
	}
	
	KeyAdapter keyAdapter = new KeyAdapter()
	{	
		public void keyPressed(KeyEvent e)
		{
			synchronized (myThread.myClient) {
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
						else if(e.keyCode == SWT.SPACE){
							//myGame.getMap().car.setIncrement(-0.09f, 0.79f);
							myThread.myClient.nitroPush();
							nitroPressed = true;
						}
					}
				
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			synchronized (myThread.myClient) {
				pressed = false;
				synchronized (myGame) {
					//myGame.getMap().car.setIncrement(0.09f, 0f);
					myThread.myClient.release();
				}
			}
		}
	};
	
	Map m = myGame.getMap();
	com.example.bclib.Display d = myGame.getDisplay();
	Render r = new Render(d);
	int crashCar = 0;
	
	public void paintControl(PaintEvent e) {

		e.gc.setLineAttributes(new LineAttributes(1,SWT.CAP_FLAT,SWT.JOIN_MITER));
		
		for(Car c : m.cars){
			r.draw(c, e, shell, crashCar);
		}
		
		for (Obstacle o : m.obstacles){
			r.draw(o, e, shell, crashCar);
		}
		
		int idPlayer = myGame.getIDplayer();
		r.drawImg(e, shell, nitroPressed, myGame.getMap().cars.get(idPlayer).getHp());
		
		
		crashCar++;
	}
}

