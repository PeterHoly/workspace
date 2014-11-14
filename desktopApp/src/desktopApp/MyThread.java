package desktopApp;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Game;
import com.example.bclib.Logic;
import com.example.bclib.Client;

public class MyThread extends Thread{
	private SurfacePanel surfacePanel;
	private PaintEvent paintEvent;
	private boolean mRun = false;
	private Game myGame;
	private Shell myShell;
	private Display myDisplay;
	private Logic myLogic = new Logic();
	private Object o = new Object();
	public Client myClient = new Client();
	
	
	public MyThread(SurfacePanel sp, Game myGame, Shell shell, Display display) {
		this.surfacePanel = sp;
		this.myGame = myGame;
		this.mRun = false;
		this.myShell = shell;
		this.myDisplay = display;
	}
	
	void setRunning(boolean bRun)
	{
		mRun = bRun;
	}
	
	@Override
	public void run()
	{
		while(mRun)
		{				
			try {
				
				if(myDisplay != null){
					myDisplay.asyncExec(new Runnable() {
						
						@Override
						public void run() {
							synchronized (myGame) {
								//long cas = System.currentTimeMillis();
								myLogic.increaseValue(myGame, myClient);
								
								myShell.redraw();	
								myShell.update();
								//System.out.println(System.currentTimeMillis()-cas);
							}
						}
					});
				}else{
					System.out.println("null");
				}
							
				synchronized (o) {
					o.wait(40);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
