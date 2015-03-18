package desktopApp;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Client;
import com.example.bclib.Game;
import com.example.bclib.Logic;

public class MyThread extends Thread {

	private SurfacePanel surfacePanel;
	private boolean mRun = false;
	private Game myGame;
	private Shell myShell;
	private Display myDisplay;
	private Logic myLogic = new Logic();
	private Object o = new Object();

	private Client myClient = new Client("127.0.0.1", 8096);

	public MyThread(SurfacePanel sp, Game myGame, Shell shell, Display display) {
		this.surfacePanel = sp;
		this.myGame = myGame;
		this.mRun = false;
		this.myShell = shell;
		this.myDisplay = display;
	}

	public void setRunning(boolean bRun) {
		mRun = bRun;
	}

	public Client getClient() {
		return this.myClient;
	}

	@Override
	public void run() {
		while (mRun) {
			try {

				if (myDisplay != null) {
					myDisplay.asyncExec(new Runnable() {

						@Override
						public void run() {
							synchronized (myGame) {

								if (myShell.isDisposed()) {
									setRunning(false);
								} else {
									myLogic.increaseValue(myGame, myClient);

									myShell.redraw();
									myShell.update();
								}
							}
						}
					});
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
