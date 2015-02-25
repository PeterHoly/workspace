package mapEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
 
public class aaa {
	
    protected static final int Y_STEP = 20;
 
    public static void main(String[] args) {
        final Display display = new Display();
        final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        shell.setLayout(new FillLayout());
        shell.setSize(400, 300);
 
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setLayout(new GridLayout(1, false));
 
        final Canvas canvas = new Canvas(composite, SWT.V_SCROLL);
        canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        final Point origin = new Point(0, 0);
        
        final ScrollBar vBar = canvas.getVerticalBar();
        vBar.setMaximum(1800);
        vBar.setMinimum(0);
        vBar.addListener(SWT.Selection, new Listener() {
 
            @Override
            public void handleEvent(Event event) {
                int vSelection = vBar.getSelection();
                int destY = -vSelection - origin.y;
                canvas.scroll(0, destY, 0, 0, 2120, 2000, false);
                origin.y = -vSelection;
            }
        });
        
        canvas.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 100; j++) {
                        e.gc.drawRectangle(origin.x + j * Y_STEP, origin.y + i * Y_STEP, 20, 20);
                    }
                }
            }
        });
        
        
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}