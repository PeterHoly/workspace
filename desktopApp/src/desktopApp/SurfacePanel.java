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

public class SurfacePanel implements PaintListener {
	
	private boolean pressed = false;
	private MyThread myThread;
	private Shell shell;
	private Display display;
	private DesktopMenu menu;
	private boolean nitroPressed = false;
	private Game myGame = new Game(new com.example.bclib.Display(0,0,320,410));
	private String[] args;
	private Render r = new Render(myGame.getDisplay());
	private int crashCar = 0;
	
	public SurfacePanel(String[] args) {
		this.args = args;
		
		display = new Display();
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setSize(320,430);
		shell.setMinimumSize(320, 430);
		
		shell.setLocation((display.getClientArea().width-shell.getSize().x)/2, (display.getClientArea().height-shell.getSize().y)/2);
		
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
		this.menu = new DesktopMenu(display, shell, this.myThread.getClient(), myGame.getDisplay(), myGame, this);
	}
	
	public void Start(){
		myThread.setRunning(true);
		myThread.start();
	}
	
	private KeyAdapter keyAdapter = new KeyAdapter()
	{	
		public void keyPressed(KeyEvent e)
		{
			synchronized (myThread.getClient()) {
				if(!pressed) {
					pressed = true;
					synchronized (myGame) {
						if(e.keyCode == SWT.ARROW_LEFT){
							myThread.getClient().leftPush();
						}
						else if(e.keyCode == SWT.ARROW_RIGHT){
							myThread.getClient().rightPush();
						}
						else if(e.keyCode == SWT.SPACE){
							myThread.getClient().nitroPush();
							nitroPressed = true;
						}
					}
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			synchronized (myThread.getClient()) {
				pressed = false;
				synchronized (myGame) {
					myThread.getClient().release();
				}
			}
		}
	};

	public void paintControl(PaintEvent e) {
		e.gc.setLineAttributes(new LineAttributes(1,SWT.CAP_FLAT,SWT.JOIN_MITER));

		r.draw(null, e, shell, 0, myGame.getMapName());
		
		for(Car c : myGame.getMap().getCars()){
			r.draw(c, e, shell, crashCar, myGame.getMapName());
		}
		
		r.drawImg(e, shell, nitroPressed, myGame.getMap().getCars().get(myGame.getIDplayer()).getHp());
		
		if(myGame.getMap().getCars().get(myGame.getIDplayer()).getWin() != -1){
			r.drawWin(e, shell, myGame.getMap().getCars().get(myGame.getIDplayer()).getWin());

			if(r.drawMenuButton(e, shell)){
				this.myThread.setRunning(false);
				this.myThread.getClient().closeSocket();
				this.display.close();
				Main.main(this.args);
			}
		}
		crashCar++;
	}
}

