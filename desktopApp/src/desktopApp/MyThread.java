package desktopApp;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Game;
import com.example.bclib.Logic;

public class MyThread extends Thread{
	private SurfacePanel surfacePanel;
	private PaintEvent paintEvent;
	private boolean mRun = false;
	private Game myGame;
	private Shell myShell;
	private Display myDisplay;
	private Logic myLogic = new Logic();
	private Object o = new Object();
	
	
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
							
								myLogic.increaseValue(myGame);
								//myGame.getMap().car.setNewAngle();
								myShell.redraw();	
								myShell.update();
							}
						}
					});
				}else{
					System.out.println("null");
				}
							
				
				synchronized (o) {
					o.wait(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
