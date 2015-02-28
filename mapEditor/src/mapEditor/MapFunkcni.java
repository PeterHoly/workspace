package mapEditor;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.example.bclib.Obstacle;

public class MapFunkcni {
	
	public Display display = new Display();
	Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
	
	final org.eclipse.swt.graphics.Color colorBlack = display.getSystemColor(SWT.COLOR_BLACK);
    final org.eclipse.swt.graphics.Color colorWhite = display.getSystemColor(SWT.COLOR_WHITE);
    final org.eclipse.swt.graphics.Color colorGray = display.getSystemColor(SWT.COLOR_GRAY);
    final org.eclipse.swt.graphics.Color colorGreen = display.getSystemColor(SWT.COLOR_GREEN);
    final org.eclipse.swt.graphics.Color colorYellow = display.getSystemColor(SWT.COLOR_YELLOW);

	TabFolder mapFolder;
	Composite areaComposite;
	Composite barrierComposite;
	Composite rowComposite;
	Composite startComposite;
	
	TabItem bArea;
	TabItem bBarrier;
	TabItem bRows;
	TabItem bStart;
	
	Label barrierLabel;
	Label barrierLabel1;
	Label barrierLabel2;
	Label barrierLabel3;
	
	Image barriersImage;
	Image barriersImage1;
	Image barriersImage2;
	Image barriersImage3;
	
	Label desertBlokLabel;
	Label desert1BlokLabel;
	Label greenBlokLabel;
	Label roadBlokLabel;
	Label road1BlokLabel;
	Label road2BlokLabel;
	Label roadLabel;
	Label road1Label;
	Label road2Label;
	Label roadHighLabel;
	Label road1HighLabel;
	Label road2HighLabel;
	Label startLabel;
	Label finishLabel;
	
	Image desertBlokImage;
	Image desert1BlokImage;
	Image greenBlokImage;
	Image roadBlokImage;
	Image road1BlokImage;
	Image road2BlokImage;
	Image roadImage;
	Image road1Image;
	Image road2Image;
	Image roadHighImage;
	Image road1HighImage;
	Image road2HighImage;
	Image startImage;
	Image finishImage;
	
	Button hideGrid1;
	Button hideGrid2;
	Button saveMap;
	Button saveMap2;
	Button clearMap;
	Button clearMap2;
	Button addRow;
	
	Combo activateArea;
	String [] areasSmall = {"desertBlokSmall","desert1BlokSmall","greenBlokSmall","roadBlokSmall","road1BlokSmall","road2BlokSmall","roadSmall","road1Small","road2Small","roadHighSmall","road1HighSmall","road2HighSmall"};
	Image [] imagesAreaSmall = new Image[12];
	
	Combo activateBarrier;
	String [] barriersSmall = {"barriersSmall","barriers1Small","barriers2Small","barriers3Small"};
	Image [] imagesBarriersSmall = new Image[4];
	
	Combo activateStart;
	String [] startSmall = {"start","finish"};
	Image [] imagesStartSmall = new Image[2];
	
	Composite sc;
	Canvas child;
	
	int width = 362/2;
	int height = 641;
	int rect = 20;
	int barrierWidth = 10;
	int barrierHeight = 60;
	int startWidth = 183;
	int startHeight = 30;
	int row = height/rect;
	int col = width/rect;
	
	Obstacle[] obstacleStart = new Obstacle[2];
	
	int[][] imgGrid = new int[row][col];
	
	List<Obstacle> obstacleList = new ArrayList<Obstacle>();
	List<Image> barriersList = new ArrayList<Image>();
	
	boolean mouseClick = false;
	boolean changePosition = false;
	boolean changeAngle = false;
	boolean grid = true;
	boolean testClick = false;
	boolean test = false;
	
	int xChange,yChange;
	int indexArea;
	int indexBarrier;
	int indexStart;
	int idBarrier;
	int idStart;
	int idBarrierAngle = -1;
	int actualX;
	double angle;
	double xStr, yStr;
	
	public MapFunkcni(){
		
		for(int i=0; i<row; i++){
    		for(int j=0; j<col; j++){
    			imgGrid[i][j] = -1;
    		}
    	}
		
		for(int i=0; i<areasSmall.length; i++){
			imagesAreaSmall[i] = new Image(display, "images/"+areasSmall[i]+".png");
		}
		
		for(int i=0; i<barriersSmall.length; i++){
			imagesBarriersSmall[i] = new Image(display, "images/"+barriersSmall[i]+".png");
		}
		
		for(int i=0; i<startSmall.length; i++){
			imagesStartSmall[i] = new Image(display, "images/"+startSmall[i]+".png");
		}
		
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setSize(700, 600);
		shell.setMinimumSize(700, 600);
    	shell.setText("Editor");
        shell.setVisible(true);
        
		sc = new Composite(shell, SWT.V_SCROLL);
		sc.setBackground(colorGray);
        
	    child = new Canvas(sc, SWT.NONE);
	    child.setBackground(colorGreen);
	    child.setSize(width,height);
	    child.setLocation(width/2, 0);
	    
	    final Point origin = new Point(0, 0);
        
        final ScrollBar vBar = sc.getVerticalBar();
        vBar.setMaximum(10);
        vBar.setMinimum(0);
        vBar.addListener(SWT.Selection , new Listener() {
            @Override
            public void handleEvent(Event event) {
                int vSelection = vBar.getSelection();
                int destY = -vSelection - origin.y;
                child.scroll(0, destY, 0, 0, 180, 640, false);
                origin.y = -vSelection;
            }
        });
	    
	    child.addPaintListener(new PaintListener(){
	        public void paintControl(PaintEvent e){
	        	
	            for(int i=0; i<row; i++){
	        		for(int j=0; j<col; j++){	
	        			if(imgGrid[i][j] != -1){
	        				e.gc.drawImage(imagesAreaSmall[imgGrid[i][j]], j * rect, origin.y + i * rect);
	        			}
	        		}
	        	}
	            
	            for(int i=0; i<obstacleStart.length; i++){
	            	if(obstacleStart[i] != null){
	            		e.gc.drawImage(imagesStartSmall[i], 0, 0, imagesStartSmall[i].getBounds().width, imagesStartSmall[i].getBounds().height, (int)obstacleStart[i].getX(), origin.y + (int)obstacleStart[i].getY()-(startHeight/2), startWidth, startHeight);
	            	}
	            }
	            
	            for(Obstacle o : obstacleList){
            		Transform transform = new Transform(display);
            		
            		transform.translate((float)o.getX(), (float)(origin.y+o.getY()+barrierHeight/2));
					transform.rotate((float)o.getAngle2());
					transform.translate(-(float)o.getX(), -(float)(origin.y+o.getY()+barrierHeight/2));
            		
            		e.gc.setTransform(transform);
            		e.gc.drawImage(barriersList.get(obstacleList.indexOf(o)), 0, 0, barriersList.get(obstacleList.indexOf(o)).getBounds().width, barriersList.get(obstacleList.indexOf(o)).getBounds().height, (int)o.getX()-barrierWidth/2, origin.y + (int)o.getY(), barrierWidth, barrierHeight);
            		e.gc.setTransform(null);
            		transform.dispose();
            	}
	            
	            if(grid){
		            for (int i = 0; i < row; i++) {
	                    for (int j = 0; j < col; j++) {
	                        e.gc.drawRectangle(j * rect, origin.y + i * rect, rect, rect);
	                    }
	                }
	            }
	        }
	    }); 
	   	
	    child.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				mouseClick = false;
				changePosition = false;
				test = false;
				changeAngle = false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(mapFolder.getSelectionIndex() == 0){
					if(e.x > 0 && e.x < width-1 && e.y < height-1 && e.y > 0){
						mouseClick = true;
						int indexX = ((-origin.y)+e.y)/rect;
						int indexY = e.x/rect;
						imgGrid[indexX][indexY] = indexArea;
						child.redraw();
					}
				}
				else if(mapFolder.getSelectionIndex() == 1){
					for(Obstacle o : obstacleList){
						if(e.x > o.getX()-barrierWidth/2 && e.x < o.getX()+barrierWidth/2 && (-origin.y)+e.y > o.getY() && (-origin.y)+e.y < o.getY2()){
							idBarrier = obstacleList.indexOf(o);
							xChange = e.x - (int)o.getX()+barrierWidth/2;
							yChange = e.y - (int)o.getY();
							changePosition = true;
							break;
						}
					}
					
					if(!changePosition){
						Obstacle o = new Obstacle(e.x+barrierWidth/2, (-origin.y)+e.y, e.x+barrierWidth/2, (-origin.y)+e.y+barrierHeight);
						o.setAngle(0);
						obstacleList.add(o);
						barriersList.add(imagesBarriersSmall[indexBarrier]);
						child.redraw();
					}
				}
				else if(mapFolder.getSelectionIndex() == 2){
					for(int i=0; i<obstacleStart.length; i++){
						if(obstacleStart[i] != null){
							if(e.x > obstacleStart[i].getX() && e.x < obstacleStart[i].getX2() && (-origin.y)+e.y > obstacleStart[i].getY()-(startHeight/2) && (-origin.y)+e.y < obstacleStart[i].getY2()+(startHeight/2)){
								idStart = i;
								xChange = e.x - (int)obstacleStart[i].getX();
								yChange = e.y - (int)obstacleStart[i].getY()+startHeight/2;
								changePosition = true;
								break;
							}
						}
					}
					
					if(!changePosition){
						Obstacle o = new Obstacle(e.x, (-origin.y)+e.y+startHeight/2, e.x+startWidth, (-origin.y)+e.y+startHeight/2);
						o.setAngle(0);
						obstacleStart[indexStart] = o;
						child.redraw();
					}
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(mapFolder.getSelectionIndex() == 1){
					for(Obstacle o : obstacleList){
						if(e.x > o.getX()-barrierWidth/2 && e.x < o.getX()+barrierWidth/2 && (-origin.y)+e.y > o.getY() && (-origin.y)+e.y < o.getY2()){
							idBarrierAngle = obstacleList.indexOf(o);
							changeAngle = true;
							break;
						}
					}
				}
			}
		});
	    
	    child.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				
				if(mouseClick){
					if(e.x > 0 && e.x < width-1 && e.y < height-1 && e.y > 0){
						int indexX = ((-origin.y)+e.y)/rect;
						int indexY = e.x/rect;
						imgGrid[indexX][indexY] = indexArea;
						child.redraw();
					}
				}
				else if(changeAngle){
					Obstacle o = obstacleList.get(idBarrierAngle);
					if(actualX > e.x && o.getY()+barrierHeight/2 > (-origin.y)+e.y){
						o.setAngle(o.getAngle2()-5);
					}
					else if(actualX < e.x && o.getY()+barrierHeight/2 > (-origin.y)+e.y){
						o.setAngle(o.getAngle2()+5);
					}
					else if(actualX > e.x && o.getY()+barrierHeight/2 < (-origin.y)+e.y){
						o.setAngle(o.getAngle2()+5);
					}
					else if(actualX < e.x && o.getY()+barrierHeight/2 < (-origin.y)+e.y){
						o.setAngle(o.getAngle2()-5);
					}
					
					actualX = e.x;
					child.redraw();
				}
				else if(changePosition){
					if(mapFolder.getSelectionIndex() == 1){
						obstacleList.get(idBarrier).setX(e.x+barrierWidth/2-xChange);
						obstacleList.get(idBarrier).setY(e.y-yChange);
						obstacleList.get(idBarrier).setX2(e.x+barrierWidth/2-xChange);
						obstacleList.get(idBarrier).setY2(e.y+barrierHeight-yChange);
					}
					else if(mapFolder.getSelectionIndex() == 2){
						obstacleStart[idStart].setX(e.x-xChange);
						obstacleStart[idStart].setY(e.y+startHeight/2-yChange);
						obstacleStart[idStart].setX2(e.x+startWidth-xChange);
						obstacleStart[idStart].setY2(e.y+startHeight/2-yChange);
					}
					child.redraw();
				}
			}
		});
	    
	    child.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.DEL){
					if(obstacleList.size() > 0){
						if(obstacleList.size() > idBarrier){
							obstacleList.remove(idBarrier);
							barriersList.remove(idBarrier);
						}
					}
					child.redraw();
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
		
		startComposite = new Composite(mapFolder, SWT.NULL);
		startComposite.setSize(340, 580);
		startComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		startComposite.setBackground(colorGray);
		startComposite.setLocation(5, 33);
		
		rowComposite = new Composite(mapFolder, SWT.NULL);
		rowComposite.setSize(340, 580);
		rowComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		rowComposite.setBackground(colorGray);
		rowComposite.setLocation(5, 33);
		
		bArea = new TabItem(mapFolder, SWT.PUSH);
		bArea.setText("Areas");
				
		bBarrier = new TabItem(mapFolder, SWT.PUSH);
		bBarrier.setText("Barrier");
		
		bStart = new TabItem(mapFolder, SWT.PUSH);
		bStart.setText("Start line");
		
		bRows = new TabItem(mapFolder, SWT.PUSH);
		bRows.setText("Rows");
		
		final Spinner spinner = new Spinner(rowComposite, SWT.NULL);
		spinner.setSize(60,30);
		spinner.setLocation(20, 20);
		spinner.setMaximum(100);
		
		addRow = new Button(rowComposite, SWT.PUSH);
		addRow.setSize(80, 30);
		addRow.setLocation(100, 20);
		addRow.setText("add row");
		addRow.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				
				int add = spinner.getSelection();
				row += add;
				
				int[][] imgPole = imgGrid;
				imgGrid = new int[row][col];
				
				for(int i=0; i<row; i++){
		    		for(int j=0; j<col; j++){
		    			imgGrid[i][j] = -1;
		    		}
		    	}
				for(int i=0; i<row-add; i++){
		    		for(int j=0; j<col; j++){
		    			imgGrid[i+add][j] = imgPole[i][j];
		    		}
		    	}
				for(Obstacle o : obstacleList){
					o.setY(o.getY()+rect*add);
					o.setY2(o.getY2()+rect*add);
				}
				for(int i=0; i<obstacleStart.length; i++){
					obstacleStart[i].setY(obstacleStart[i].getY()+rect*add);
					obstacleStart[i].setY2(obstacleStart[i].getY2()+rect*add);
				}
				
				
				vBar.setMaximum(vBar.getMaximum()+add*rect);
				vBar.setSelection(vBar.getMaximum());
				
				int vSelection = vBar.getSelection();
                int destY = -vSelection - origin.y;
                child.scroll(0, destY, 0, 0, 180, 640, false);
                origin.y = -vSelection;
				
				child.redraw();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		activateArea = new Combo(areaComposite, SWT.NULL);
		activateArea.setSize(170, 20);
		activateArea.setLocation(80, 30);
		activateArea.add("desert part yellow");
		activateArea.add("desert part");
		activateArea.add("green part");
		activateArea.add("road part gray");
		activateArea.add("road part dark gray");
		activateArea.add("road part light gray");
		activateArea.add("road big");
		activateArea.add("road big 1");
		activateArea.add("road big 2");
		activateArea.add("road high");
		activateArea.add("road 1 high");
		activateArea.add("road 2 high");
		activateArea.select(0);
		activateArea.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexArea = activateArea.getSelectionIndex();	
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		hideGrid1 = new Button(areaComposite, SWT.PUSH);
		hideGrid1.setSize(80, 30);
		hideGrid1.setLocation(260, 0);
		hideGrid1.setText("hide grid");
		hideGrid1.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				if(grid){
					grid = false;
					hideGrid1.setText("show grid");
					hideGrid2.setText("show grid");
				}
				else{
					grid = true;
					hideGrid1.setText("hide grid");
					hideGrid2.setText("hide grid");
				}
				child.redraw();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		saveMap = new Button(areaComposite, SWT.PUSH);
		saveMap.setSize(80, 30);
		saveMap.setLocation(260, 30);
		saveMap.setText("save map");
		saveMap.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				saveMap();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		clearMap = new Button(areaComposite, SWT.PUSH);
		clearMap.setSize(80, 30);
		clearMap.setLocation(260, 60);
		clearMap.setText("clear map");
		clearMap.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				obstacleList.clear();
				barriersList.clear();
				for(int i=0;i<obstacleStart.length; i++){
					obstacleStart[i] = null;
				}
				for(int i=0; i<row; i++){
		    		for(int j=0; j<col; j++){
		    			imgGrid[i][j] = -1;
		    		}
		    	}
				child.redraw();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		activateBarrier = new Combo(barrierComposite, SWT.NULL);
		activateBarrier.setSize(170, 20);
		activateBarrier.setLocation(80, 30);
		activateBarrier.add("red and white");
		activateBarrier.add("silver");
		activateBarrier.add("black and yellow");
		activateBarrier.add("silver2");
		activateBarrier.select(0);
		activateBarrier.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexBarrier = activateBarrier.getSelectionIndex();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
			}
		});
		
		hideGrid2 = new Button(barrierComposite, SWT.PUSH);
		hideGrid2.setSize(80, 30);
		hideGrid2.setLocation(260, 0);
		hideGrid2.setText("hide grid");
		hideGrid2.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				if(grid){
					grid = false;
					hideGrid2.setText("show grid");
					hideGrid1.setText("show grid");
				}
				else{
					grid = true;
					hideGrid2.setText("hide grid");
					hideGrid1.setText("hide grid");
				}
				child.redraw();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		saveMap2 = new Button(barrierComposite, SWT.PUSH);
		saveMap2.setSize(80, 30);
		saveMap2.setLocation(260, 30);
		saveMap2.setText("save map");
		saveMap2.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				saveMap();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		clearMap2 = new Button(barrierComposite, SWT.PUSH);
		clearMap2.setSize(80, 30);
		clearMap2.setLocation(260, 60);
		clearMap2.setText("clear map");
		clearMap2.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				obstacleList.clear();
				barriersList.clear();
				for(int i=0;i<obstacleStart.length; i++){
					obstacleStart[i] = null;
				}
				for(int i=0; i<row; i++){
		    		for(int j=0; j<col; j++){
		    			imgGrid[i][j] = -1;
		    		}
		    	}
				child.redraw();
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		barriersImage = new Image(display, "images/barriers.png");
		barrierLabel = new Label(barrierComposite, SWT.TRANSPARENT);
		barrierLabel.setLocation(100, 150);
		barrierLabel.setSize(barriersImage.getBounds().width, barriersImage.getBounds().height);
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
		barrierLabel1.setSize(barriersImage1.getBounds().width, barriersImage1.getBounds().height);
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
		barrierLabel2.setSize(barriersImage2.getBounds().width, barriersImage2.getBounds().height);
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
		
		barriersImage3 = new Image(display, "images/barriers3.png");
		barrierLabel3 = new Label(barrierComposite, SWT.TRANSPARENT);
		barrierLabel3.setLocation(220, 150);
		barrierLabel3.setSize(barriersImage3.getBounds().width, barriersImage3.getBounds().height);
		barrierLabel3.setImage(barriersImage3);
		barrierLabel3.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexBarrier = 3;
				activateBarrier.select(indexBarrier);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		activateStart = new Combo(startComposite, SWT.NULL);
		activateStart.setSize(170, 20);
		activateStart.setLocation(80, 30);
		activateStart.add("start");
		activateStart.add("finish");
		activateStart.select(0);
		activateStart.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexStart = activateStart.getSelectionIndex();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
			}
		});
		
		startImage = new Image(display, "images/start.png");
		startLabel = new Label(startComposite, SWT.TRANSPARENT);
		startLabel.setLocation(80,150);
		startLabel.setSize(startImage.getBounds().width, startImage.getBounds().height);
		startLabel.setImage(startImage);
		startLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexStart = 0;
				activateStart.select(indexStart);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		finishImage = new Image(display, "images/finish.png");
		finishLabel = new Label(startComposite, SWT.TRANSPARENT);
		finishLabel.setLocation(80,200);
		finishLabel.setSize(finishImage.getBounds().width, finishImage.getBounds().height);
		finishLabel.setImage(finishImage);
		finishLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexStart = 1;
				activateStart.select(indexStart);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		desertBlokImage = new Image(display, "images/desertBlok.png");
		desertBlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		desertBlokLabel.setLocation(10, 120);
		desertBlokLabel.setSize(desertBlokImage.getBounds().width, desertBlokImage.getBounds().height);
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
		desert1BlokLabel.setSize(desert1BlokImage.getBounds().width, desert1BlokImage.getBounds().height);
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
		greenBlokLabel.setSize(greenBlokImage.getBounds().width, greenBlokImage.getBounds().height);
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
		roadBlokLabel.setLocation(10, 240);
		roadBlokLabel.setSize(roadBlokImage.getBounds().width, roadBlokImage.getBounds().height);
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
		road1BlokLabel.setLocation(120, 240);
		road1BlokLabel.setSize(road1BlokImage.getBounds().width, road1BlokImage.getBounds().height);
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
		
		road2BlokImage = new Image(display, "images/road2Blok.png");
		road2BlokLabel = new Label(areaComposite, SWT.TRANSPARENT);
		road2BlokLabel.setLocation(230, 240);
		road2BlokLabel.setSize(road2BlokImage.getBounds().width, road2BlokImage.getBounds().height);
		road2BlokLabel.setImage(road2BlokImage);
		road2BlokLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 5;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		roadImage = new Image(display, "images/road.png");
		roadLabel = new Label(areaComposite, SWT.TRANSPARENT);
		roadLabel.setLocation(10, 360);
		roadLabel.setSize(roadImage.getBounds().width, roadImage.getBounds().height);
		roadLabel.setImage(roadImage);
		roadLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 6;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		road1Image = new Image(display, "images/road1.png");
		road1Label = new Label(areaComposite, SWT.TRANSPARENT);
		road1Label.setLocation(120, 360);
		road1Label.setSize(road1Image.getBounds().width, road1Image.getBounds().height);
		road1Label.setImage(road1Image);
		road1Label.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 7;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		road2Image = new Image(display, "images/road2.png");
		road2Label = new Label(areaComposite, SWT.TRANSPARENT);
		road2Label.setLocation(230, 360);
		road2Label.setSize(road2Image.getBounds().width, road2Image.getBounds().height);
		road2Label.setImage(road2Image);
		road2Label.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 8;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		roadHighImage = new Image(display, "images/roadHigh.png");
		roadHighLabel = new Label(areaComposite, SWT.TRANSPARENT);
		roadHighLabel.setLocation(10, 480);
		roadHighLabel.setSize(roadHighImage.getBounds().width, roadHighImage.getBounds().height);
		roadHighLabel.setImage(roadHighImage);
		roadHighLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 9;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		road1HighImage = new Image(display, "images/road1High.png");
		road1HighLabel = new Label(areaComposite, SWT.TRANSPARENT);
		road1HighLabel.setLocation(120, 480);
		road1HighLabel.setSize(road1HighImage.getBounds().width, road1HighImage.getBounds().height);
		road1HighLabel.setImage(road1HighImage);
		road1HighLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 10;
				activateArea.select(indexArea);
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		road2HighImage = new Image(display, "images/road2High.png");
		road2HighLabel = new Label(areaComposite, SWT.TRANSPARENT);
		road2HighLabel.setLocation(230, 480);
		road2HighLabel.setSize(road2HighImage.getBounds().width, road2HighImage.getBounds().height);
		road2HighLabel.setImage(road2HighImage);
		road2HighLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
				indexArea = 11;
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
					rowComposite.setVisible(false);
					barrierComposite.setVisible(false);
					startComposite.setVisible(false);
					areaComposite.setVisible(true);
				}
				else if(mapFolder.getSelectionIndex()==1){
					rowComposite.setVisible(false);
					areaComposite.setVisible(false);
					startComposite.setVisible(false);
					barrierComposite.setVisible(true);
				}
				else if(mapFolder.getSelectionIndex()==2){
					areaComposite.setVisible(false);
					barrierComposite.setVisible(false);
					rowComposite.setVisible(false);
					startComposite.setVisible(true);
				}
				else if(mapFolder.getSelectionIndex()==3){
					areaComposite.setVisible(false);
					barrierComposite.setVisible(false);
					startComposite.setVisible(false);
					rowComposite.setVisible(true);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		 };
		   	 
		mapFolder.addSelectionListener(folderSelectionL);
	  	
        shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	} 
	
	public void saveMap(){
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("map");
			doc.appendChild(rootElement);
			
	 
			Element lines = doc.createElement("Lines");
			rootElement.appendChild(lines);

			for(int i=0; i<obstacleStart.length; i++){
				if(obstacleStart[i] != null){

					Element line = doc.createElement("line");
					
					Attr x1 = doc.createAttribute("x1");
					x1.setValue(String.valueOf(obstacleStart[i].getX()));
					line.setAttributeNode(x1);
					
					Attr x2 = doc.createAttribute("x2");
					x2.setValue(String.valueOf(obstacleStart[i].getX2()));
					line.setAttributeNode(x2);
					
					Attr y1 = doc.createAttribute("y1");
					y1.setValue(String.valueOf(obstacleStart[i].getY()));
					line.setAttributeNode(y1);
					
					Attr y2 = doc.createAttribute("y2");
					y2.setValue(String.valueOf(obstacleStart[i].getY2()));
					line.setAttributeNode(y2);
					
					Attr img = doc.createAttribute("img");
					img.setValue(String.valueOf(startSmall[i]));
					line.setAttributeNode(img);
					
					lines.appendChild(line);
				}
			}	
				
			
			Element obstacles = doc.createElement("Obstacles");
			rootElement.appendChild(obstacles);

			for(Obstacle o : obstacleList){
				Element obstacle = doc.createElement("obstacle");
				
				Attr x1 = doc.createAttribute("x1");
				x1.setValue(String.valueOf(o.getX()));
				obstacle.setAttributeNode(x1);
				
				Attr y1 = doc.createAttribute("y1");
				y1.setValue(String.valueOf(o.getY()));
				obstacle.setAttributeNode(y1);
				
				Attr y2 = doc.createAttribute("y2");
				y2.setValue(String.valueOf(o.getY2()));
				obstacle.setAttributeNode(y2);
				
				Attr anlge = doc.createAttribute("angle");
				anlge.setValue(String.valueOf(o.getAngle2()));
				obstacle.setAttributeNode(anlge);
				
				Attr img = doc.createAttribute("img");
				for(int i=0; i<imagesBarriersSmall.length;i++){
					if(imagesBarriersSmall[i].equals(barriersList.get(obstacleList.indexOf(o)))){
						img.setValue(barriersSmall[i]);
					}
				}
				obstacle.setAttributeNode(img);
				
				obstacles.appendChild(obstacle);
			}
			
			
			Element roads = doc.createElement("Roads");
			rootElement.appendChild(roads);

			for(int i=0; i<row; i++){
	    		for(int j=0; j<col; j++){
	    			if(imgGrid[i][j] != -1){
		    			Element road = doc.createElement("road");
						
		    			Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(j));
						road.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(i));
						road.setAttributeNode(y);
						
						Attr img = doc.createAttribute("img");
						img.setValue(areasSmall[imgGrid[i][j]]);
						road.setAttributeNode(img);
						
						roads.appendChild(road);
	    			}
	    		}
	    	}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("maps/map.xml"));
			transformer.transform(source, result);
		} 
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} 
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
