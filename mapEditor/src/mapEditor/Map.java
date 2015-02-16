package mapEditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.example.bclib.Obstacle;


public class Map {
	
	Display display = new Display();
	Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
	
	final org.eclipse.swt.graphics.Color colorBlack = display.getSystemColor(SWT.COLOR_BLACK);
    final org.eclipse.swt.graphics.Color colorWhite = display.getSystemColor(SWT.COLOR_WHITE);
    final org.eclipse.swt.graphics.Color colorGray = display.getSystemColor(SWT.COLOR_GRAY);
    final org.eclipse.swt.graphics.Color colorGreen = display.getSystemColor(SWT.COLOR_GREEN);
    final org.eclipse.swt.graphics.Color colorYellow = display.getSystemColor(SWT.COLOR_YELLOW);

	TabFolder mapFolder;
	Composite areaComposite;
	Composite barrierComposite;
	
	TabItem bArea;
	TabItem bBarrier;
	
	Label barrierLabel;
	Label barrierLabel1;
	Label barrierLabel2;
	
	Image barriersImage;
	Image barriersImage1;
	Image barriersImage2;
	
	Label desertBlokLabel;
	Label desert1BlokLabel;
	Label greenBlokLabel;
	Label roadLabel;
	Label road1Label;
	Label roadBlokLabel;
	Label road1BlokLabel;
	
	Image desertBlokImage;
	Image desert1BlokImage;
	Image greenBlokImage;
	Image roadImage;
	Image road1Image;
	Image roadBlokImage;
	Image road1BlokImage;
	
	Combo activateArea;
	String [] areas = {"desertBlok","desert1Blok","greenBlok","roadBlok","road1Blok"};
	Image [] imagesArea = new Image[5];
	
	Combo activateBarrier;
	String [] barriers = {"barriers","barriers1","barriers2"};
	String [] barriersSmall = {"barriersSmall","barriers1Small","barriers2Small"};
	Image [] imagesBarriers = new Image[3];
	Image [] imagesBarriersSmall = new Image[3];
	
	Composite sc;
	Canvas child;
	
	int width = 350/2+6;
	int height = 650-9;
	
	int rect = 20;
	int barrierWidth = 10;
	int barrierHeight = 60;
	
	int row = width/rect;
	int col = height/rect;
	
	int[][] imgGrid = new int[row][col];
	List<Obstacle> obstacleList = new ArrayList<Obstacle>();
	
	List<Label> barriersList = new ArrayList<Label>();
	
	boolean mouseClick = false;
	
	int indexArea;
	int indexBarrier;
	
	
	public Map(){
		
		for(int i=0; i<row; i++){
    		for(int j=0; j<col; j++){
    			imgGrid[i][j] = -1;
    		}
    	}
		
		for(int i=0; i<areas.length; i++){
			imagesArea[i] = new Image(display, "images/"+areas[i]+".png");
		}
		
		for(int i=0; i<barriers.length; i++){
			imagesBarriers[i] = new Image(display, "images/"+barriers[i]+".png");
			imagesBarriersSmall[i] = new Image(display, "images/"+barriersSmall[i]+".png");
		}
		
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		sc = new Composite(shell, SWT.NONE);
		sc.setBackground(colorGray);
	    child = new Canvas(sc, SWT.NONE);
	    child.setBackground(colorGreen);
	    child.setSize(width,height);
	    child.setLocation(width/2, 0);
	    
	    child.addPaintListener(new PaintListener(){
	        public void paintControl(PaintEvent e){
	            Rectangle clientArea = child.getClientArea();
	            
	            if(e.width == rect){
		            e.gc.drawImage(imagesArea[indexArea], 0, 0, imagesArea[indexArea].getBounds().width, imagesArea[indexArea].getBounds().height, e.x, e.y, rect, rect);
	            }
	            else if(e.width == barrierWidth){
	            	//e.gc.drawImage(imagesBarriers[indexBarrier], 0, 0, imagesBarriers[indexBarrier].getBounds().width, imagesBarriers[indexBarrier].getBounds().height, e.x, e.y, barrierWidth, barrierHeight);
	            }
	            
            	for(int i=0	; i<clientArea.width;i++){
	            	e.gc.drawLine(i*rect,0,i*rect,clientArea.height);
	            }
	            for(int j=0; j<clientArea.height;j++){
	            	e.gc.drawLine(0,j*rect,clientArea.width,j*rect);
	            }
	        }
	    }); 
	   	
	    child.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				mouseClick = false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				if(mapFolder.getSelectionIndex() == 0){
					mouseClick = true;
					
					int indexX = e.x/rect;
					int indexY = e.y/rect;
					
					imgGrid[indexX][indexY] = indexArea;
					
					int positionX = indexX*rect;
					int positionY = indexY*rect;
					
					child.redraw(positionX, positionY, rect, rect, false);
				}
				else{
					//logika souradnice
					obstacleList.add(new Obstacle(e.x+barrierWidth/2, e.y, e.x+barrierWidth/2, e.y+barrierHeight));
					
					Label l = new Label(child, SWT.NONE);
					l.setBounds(e.x, e.y, barrierWidth, barrierHeight);
					l.setBackgroundImage(imagesBarriersSmall[indexBarrier]);
					
					l.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseUp(MouseEvent e) {
						}
						
						@Override
						public void mouseDown(MouseEvent e) {
						}
						
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							//zmena uhlu
							System.out.println("ok");
						}
					});
					
					l.addMouseMoveListener(new MouseMoveListener() {
						
						@Override
						public void mouseMove(MouseEvent e) {
							//pohyb do stran meni uhel
						}
					});
					
					barriersList.add(l);
					
					//child.redraw(e.x, e.y, barrierWidth, barrierHeight, false);
					
					/*
					Transform t = new Transform(e.display);
					t.translate();
					t.rotate();
					t.translate();
					
					e.gc.setTransform(t);
					*/
				}
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
	    
	    child.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				
				if(mouseClick){
					if(e.x > 0 && e.x < width-1 && e.y < height-1 && e.y > 0){
						int indexX = e.x/rect;
						int indexY = e.y/rect;
						
						imgGrid[indexX][indexY] = indexArea;
						
						int positionX = indexX*rect;
						int positionY = indexY*rect;
					
						child.redraw(positionX, positionY, rect, rect, false);
					}
				}
			}
		});
	    
		mapFolder = new TabFolder(shell, SWT.NULL);
		mapFolder.setSize(350, 590);
		mapFolder.setBackgroundMode(SWT.INHERIT_FORCE);
		mapFolder.setBackground(colorGray);
		mapFolder.setLocation(295,5);
		
		areaComposite = new Composite(mapFolder, SWT.NULL);
		areaComposite.setSize(340, 580);
		areaComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		areaComposite.setBackground(colorGray);
		areaComposite.setLocation(5, 33);
		
		barrierComposite = new Composite(mapFolder, SWT.NULL);
		barrierComposite.setSize(340, 580);
		barrierComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		barrierComposite.setBackground(colorGray);
		barrierComposite.setLocation(5, 33);
		
		bArea = new TabItem(mapFolder, SWT.PUSH);
		bArea.setText("Areas");
				
		bBarrier = new TabItem(mapFolder, SWT.PUSH);
		bBarrier.setText("Barrier");
		
		
		activateArea = new Combo(areaComposite, SWT.NULL);
		activateArea.setSize(170, 20);
		activateArea.setLocation(80, 40);
		activateArea.add("desert part yellow");
		activateArea.add("desert part");
		activateArea.add("green part");
		activateArea.add("road part gray");
		activateArea.add("road part dark");
		
		activateArea.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexArea = activateArea.getSelectionIndex();	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		
		activateBarrier = new Combo(barrierComposite, SWT.NULL);
		activateBarrier.setSize(170, 20);
		activateBarrier.setLocation(80, 40);
		activateBarrier.add("red and white");
		activateBarrier.add("silver");
		activateBarrier.add("black and yellow");
		
		activateBarrier.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexBarrier = activateBarrier.getSelectionIndex();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
			}
		});
		
		
		barriersImage = new Image(display, "images/barriers.png");
		barrierLabel = new Label(barrierComposite, SWT.TRANSPARENT);
		barrierLabel.setLocation(100, 150);
		barrierLabel.setSize(barriersImage.getBounds().width/2, barriersImage.getBounds().height/2);
		barrierLabel.setImage(barriersImage);
		
		barrierLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexBarrier = 0;
				activateBarrier.select(indexBarrier);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		barriersImage1 = new Image(display, "images/barriers1.png");
		barrierLabel1 = new Label(barrierComposite, SWT.TRANSPARENT);
		barrierLabel1.setLocation(140, 150);
		barrierLabel1.setSize(barriersImage1.getBounds().width/2, barriersImage1.getBounds().height/2);
		barrierLabel1.setImage(barriersImage1);
		
		barrierLabel1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexBarrier = 1;
				activateBarrier.select(indexBarrier);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {	
			}
		});
		
		barriersImage2 = new Image(display, "images/barriers2.png");
		barrierLabel2 = new Label(barrierComposite, SWT.TRANSPARENT);
		barrierLabel2.setLocation(180, 150);
		barrierLabel2.setSize(barriersImage2.getBounds().width/2, barriersImage2.getBounds().height/2);
		barrierLabel2.setImage(barriersImage2);
		
		barrierLabel2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexBarrier = 2;
				activateBarrier.select(indexBarrier);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		
		
		
		desertBlokImage = new Image(display, "images/desertBlok.png");
		desertBlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		desertBlokLabel.setLocation(10, 120);
		desertBlokLabel.setSize(desertBlokImage.getBounds().width/4, desertBlokImage.getBounds().height/4);
		desertBlokLabel.setImage(desertBlokImage);
		
		desertBlokLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 0;
				activateArea.select(indexArea);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		desert1BlokImage = new Image(display, "images/desert1Blok.png");
		desert1BlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		desert1BlokLabel.setLocation(120, 120);
		desert1BlokLabel.setSize(desert1BlokImage.getBounds().width/4, desert1BlokImage.getBounds().height/4);
		desert1BlokLabel.setImage(desert1BlokImage);
		
		desert1BlokLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 1;
				activateArea.select(indexArea);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		greenBlokImage = new Image(display, "images/greenBlok.png");
		greenBlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		greenBlokLabel.setLocation(230, 120);
		greenBlokLabel.setSize(greenBlokImage.getBounds().width/4, greenBlokImage.getBounds().height/4);
		greenBlokLabel.setImage(greenBlokImage);
		
		greenBlokLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 2;
				activateArea.select(indexArea);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		
		roadBlokImage = new Image(display, "images/roadBlok.png");
		roadBlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		roadBlokLabel.setLocation(10, 280);
		roadBlokLabel.setSize(roadBlokImage.getBounds().width/4, roadBlokImage.getBounds().height/4);
		roadBlokLabel.setImage(roadBlokImage);
		
		roadBlokLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 3;
				activateArea.select(indexArea);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		road1BlokImage = new Image(display, "images/road1Blok.png");
		road1BlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		road1BlokLabel.setLocation(120, 280);
		road1BlokLabel.setSize(road1BlokImage.getBounds().width/4, road1BlokImage.getBounds().height/4);
		road1BlokLabel.setImage(road1BlokImage);
		
		road1BlokLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 4;
				activateArea.select(indexArea);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
        
        SelectionListener folderSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(mapFolder.getSelectionIndex()==0){
					barrierComposite.setVisible(false);
					areaComposite.setVisible(true);
				}
				else{
					areaComposite.setVisible(false);
					barrierComposite.setVisible(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		 };
		   	 
		mapFolder.addSelectionListener(folderSelectionL);
	  	
        shell.setSize(700, 600);
		shell.setMinimumSize(700, 600);
    	shell.setText("Editor");
        shell.setVisible(true);
        
        shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	
	
	 
}
