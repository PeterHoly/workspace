package desktopApp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.example.bclib.Client;
import com.example.bclib.Game;
import com.example.bclib.components.Absorbers;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Engine;
import com.example.bclib.components.Exhaust;
import com.example.bclib.components.Filter;
import com.example.bclib.components.Glass;
import com.example.bclib.components.Nitro;
import com.example.bclib.components.Wheel;

public class DesktopMenu {
	
	public int idGame; 
	public boolean saveAllCarComponents = false;
	
	int engineComponent;
	int exhaustComponent;
	int wheelComponent;
	int absorbersComponent;
	int filterComponent;
	int nitroComponent;
	int bodyworkComponent;
	int glassComponent;

    Button newGame;
    Button joinGame;
    Button buildCar;
    Button play;
    Button backNewGame;
    Button backJoinGame;
    Button backBuildCar;
    Button connect;
    Button save;
    Button performance;
    Button appearance;
    
    List createdGames;
    
    Spinner countPlayers;
    Label writeCountPlayers;
    Label chooseGame;
   
    Label engine;
    Label exhaust;
    Label filter;
    Label absorber;
    Label wheel;
    Label nitro;
    Label loadingText;
    
    Combo engineC;
    Combo exhaustC;
    Combo filterC;
    Combo absorberC;
    Combo wheelC;
    Combo nitroC;
    
    Label bodywork;
    Label glass;
    
    Combo bodyworkC;
    Combo glassC;
    
    ProgressBar loading;
    
    Composite mainMenuComposite;
    Composite newGameComposite;
    Composite joinGameComposite;
    Composite buildCarComposite;
    Composite performanceComposite;
    Composite appearanceComposite;
    Composite loadingComposite;
     
    Label carLabel;
    
    public DesktopMenu(final Display display, Shell shell, final Client client, final com.example.bclib.Display myDisplay, final Game game, final SurfacePanel sp){
    	
        mainMenuComposite = new Composite(shell, SWT.NULL);
        mainMenuComposite.setBackgroundImage(new Image(display, "images/menuWallpaper.png"));
        mainMenuComposite.setSize(320, 410);
        mainMenuComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        
        newGameComposite = new Composite(shell, SWT.NULL);
        newGameComposite.setBackgroundImage(new Image(display, "images/menuWallpaper.png"));
        newGameComposite.setSize(320, 410);
        newGameComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        
        joinGameComposite = new Composite(shell, SWT.NULL);
        joinGameComposite.setBackgroundImage(new Image(display, "images/menuWallpaper.png"));
        joinGameComposite.setSize(320, 410);
        joinGameComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        
        buildCarComposite = new Composite(shell, SWT.NULL);
        buildCarComposite.setSize(320, 410);
        buildCarComposite.setBackgroundImage(new Image(display, "images/buildWallpaper.png"));
        buildCarComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        
        performanceComposite = new Composite(buildCarComposite, SWT.NULL);
        performanceComposite.setBackgroundImage(new Image(display, "images/buildWallpaper.png"));
        performanceComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        performanceComposite.setSize(320, 410);
        
        appearanceComposite = new Composite(buildCarComposite, SWT.NULL);
        appearanceComposite.setBackgroundImage(new Image(display, "images/buildWallpaperWithCarPlace.png"));
        appearanceComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        appearanceComposite.setSize(320, 410);
        
        loadingComposite = new Composite(shell, SWT.NULL);
        loadingComposite.setBackgroundImage(new Image(display, "images/menuWallpaper.png"));
        loadingComposite.setSize(320, 410);
        loadingComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        
        final org.eclipse.swt.graphics.Color colorBlack = display.getSystemColor(SWT.COLOR_BLACK);
        final org.eclipse.swt.graphics.Color colorWhite = display.getSystemColor(SWT.COLOR_WHITE);
        final org.eclipse.swt.graphics.Color colorDarkGray = display.getSystemColor(SWT.COLOR_DARK_GRAY);
        final org.eclipse.swt.graphics.Color colorGray = new org.eclipse.swt.graphics.Color(display, 132, 144, 142);
        final org.eclipse.swt.graphics.Color colorSilver = new org.eclipse.swt.graphics.Color(display, 178,184,182);
        final org.eclipse.swt.graphics.Color colorSilver2 = new org.eclipse.swt.graphics.Color(display, 196,197,201);
        final org.eclipse.swt.graphics.Color colorBlack2 = new org.eclipse.swt.graphics.Color(display, 40, 45, 45);
        
        //Menu---------
        Font myfont = new Font(display, new FontData("Capture it", 13, SWT.NORMAL));
        
        buildCar = new Button(mainMenuComposite,SWT.PUSH);
        buildCar.setSize(200, 40);
        buildCar.setText("Build Car");
        buildCar.setLocation(60, 334);
        buildCar.setFont(myfont);
        
        joinGame = new Button(mainMenuComposite,SWT.PUSH);
        joinGame.setSize(200, 40);
        joinGame.setText("Join Game");
        joinGame.setLocation(60, 292);
        joinGame.setFont(myfont);
        
        newGame = new Button(mainMenuComposite,SWT.PUSH);
        newGame.setSize(200, 40);
        newGame.setText("New Game");
        newGame.setLocation(60, 250);
        newGame.setFont(myfont);
        
        //New Game---------
        
        countPlayers = new Spinner(newGameComposite, SWT.TRANSPARENT);
  	  	countPlayers.setMinimum(1);
  	  	countPlayers.setMaximum(10);
  	  	countPlayers.setSize(50, 40);
  	  	countPlayers.setLocation(125, 150);
  	  	countPlayers.setBackground(colorGray);
  	  	countPlayers.setForeground(colorBlack);
  	  	
	  	play = new Button(newGameComposite,SWT.PUSH);
	  	play.setSize(200, 40);
	  	play.setText("Play");
	  	play.setLocation(60, 250);
	  	play.setFont(myfont);
	  	
	  	writeCountPlayers = new Label(newGameComposite,SWT.NONE);
	  	writeCountPlayers.setSize(220, 20);
	  	writeCountPlayers.setText("Write count players !");
	  	writeCountPlayers.setLocation(68, 100);
	  	writeCountPlayers.setForeground(colorSilver2);
	  	writeCountPlayers.setFont(myfont);
	  	
	  	backNewGame = new Button(newGameComposite,SWT.PUSH );
	  	backNewGame.setSize(48, 48);
	  	backNewGame.setLocation(4, 358);
        
	  	//Join Game---------
        
        chooseGame = new Label(joinGameComposite,SWT.NULL);
        chooseGame.setSize(220, 20);
        chooseGame.setText("Choose one game !");
        chooseGame.setLocation(77, 100);
        chooseGame.setForeground(colorSilver2);
        chooseGame.setFont(myfont);
        
        createdGames = new List(joinGameComposite,SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.TRANSPARENT);
        createdGames.setBackground(colorGray);
        createdGames.setForeground(colorBlack);
        createdGames.setSize(200, 150);
        createdGames.setLocation(62, 190);
        
	  	backJoinGame = new Button(joinGameComposite,SWT.PUSH);
	  	backJoinGame.setSize(48, 48);
	  	backJoinGame.setLocation(4, 358);
	  	
	  	connect = new Button(joinGameComposite,SWT.PUSH);
	  	connect.setSize(75, 40);
	  	connect.setText("Connect");
	  	connect.setLocation(241, 366);
	  	Font italicFont = new Font(display, new FontData("Arial", 11, SWT.ITALIC ));
	  	connect.setFont(italicFont);
	  	
        
	  	
	  	//Build car---------	  	
	  	
	  	backBuildCar = new Button(buildCarComposite,SWT.PUSH);
	  	backBuildCar.setSize(48, 48);
	  	backBuildCar.setLocation(4, 358);
	  	
	  	save = new Button(buildCarComposite,SWT.PUSH);
	  	save.setSize(65, 40);
	  	save.setText("Save");
	  	save.setLocation(251, 366);
	  	save.setFont(italicFont);
	  	
	  	//Vykon auta------
	  	
	  	Font perfAndAppeFont = new Font(display, new FontData("Capture it", 11, SWT.NORMAL ));
	  	
	  	performance = new Button(buildCarComposite,SWT.NULL);
	  	performance.setSize(125, 40);
	  	performance.setText("Performance");
	  	performance.setLocation(30, 310);
	  	performance.setFont(perfAndAppeFont);
	  	
	  	engine = new Label(performanceComposite, SWT.NULL);
	  	engine.setSize(75, 20);
	  	engine.setText("Engine");
	  	engine.setLocation(35, 110);
	  	engine.setFont(perfAndAppeFont);
	  	engine.setForeground(colorSilver2);
	  	
	  	engineC = new Combo(performanceComposite, SWT.NULL);
	  	engineC.setSize(170, 20);
	  	engineC.setLocation(116, 105);
	  	
	  	exhaust = new Label(performanceComposite, SWT.NULL);
	  	exhaust.setSize(75, 20);
	  	exhaust.setText("Exhaust");
	  	exhaust.setLocation(35, 140);
	  	exhaust.setFont(perfAndAppeFont);
	  	exhaust.setForeground(colorSilver2);
	  	
	  	exhaustC = new Combo(performanceComposite, SWT.NULL);
	  	exhaustC.setSize(170, 20);
	  	exhaustC.setLocation(116, 135);
	  	
	  	filter = new Label(performanceComposite, SWT.NULL);
	  	filter.setSize(75, 20);
	  	filter.setText("Filter");
	  	filter.setLocation(35, 170);
	  	filter.setFont(perfAndAppeFont);
	  	filter.setForeground(colorSilver2);
	  	
	  	filterC = new Combo(performanceComposite, SWT.NULL);
	  	filterC.setSize(170, 20);
	  	filterC.setLocation(116, 165);
	  	
	  	absorber = new Label(performanceComposite, SWT.NULL);
	  	absorber.setSize(75, 20);
	  	absorber.setText("Absorber");
	  	absorber.setLocation(35, 200);
	  	absorber.setFont(perfAndAppeFont);
	  	absorber.setForeground(colorSilver2);
	  	
	  	absorberC = new Combo(performanceComposite, SWT.NULL);
	  	absorberC.setSize(170, 20);
	  	absorberC.setLocation(116, 195);
	  	
	  	wheel = new Label(performanceComposite, SWT.NULL);
	  	wheel.setSize(75, 20);
	  	wheel.setText("Wheel");
	  	wheel.setLocation(35, 230);
	  	wheel.setFont(perfAndAppeFont);
	  	wheel.setForeground(colorSilver2);
	  	
	  	wheelC = new Combo(performanceComposite, SWT.NULL);
	  	wheelC.setSize(170, 20);
	  	wheelC.setLocation(116, 225);
	  	
	  	nitro = new Label(performanceComposite, SWT.NULL);
	  	nitro.setSize(75, 20);
	  	nitro.setText("Nitro");
	  	nitro.setLocation(35, 260);
	  	nitro.setFont(perfAndAppeFont);
	  	nitro.setForeground(colorSilver2);
	  	
	  	nitroC = new Combo(performanceComposite, SWT.NULL);
	  	nitroC.setSize(170, 20);
	  	nitroC.setLocation(116, 255);
	  	
	  	//Vzhled auta------
	  	
	  	appearance = new Button(buildCarComposite,SWT.NULL);
	  	appearance.setSize(125, 40);
	  	appearance.setText("Appearance");
	  	appearance.setLocation(165, 310);
	  	appearance.setFont(perfAndAppeFont);
	  	
	  	bodywork = new Label(appearanceComposite, SWT.NULL);
	  	bodywork.setSize(75, 20);
	  	bodywork.setText("Bodywork");
	  	bodywork.setLocation(35, 200);
	  	bodywork.setFont(perfAndAppeFont);
	  	bodywork.setForeground(colorSilver2);
	  	
	  	bodyworkC = new Combo(appearanceComposite, SWT.NULL);
	  	bodyworkC.setSize(170, 20);
	  	bodyworkC.setLocation(116, 195);
	  	
	  	glass = new Label(appearanceComposite, SWT.NULL);
	  	glass.setSize(75, 20);
	  	glass.setText("Glass");
	  	glass.setLocation(35, 230);
	  	glass.setFont(perfAndAppeFont);
	  	glass.setForeground(colorSilver2);
	  	
	  	glassC = new Combo(appearanceComposite, SWT.NULL);
	  	glassC.setSize(170, 20);
	  	glassC.setLocation(116, 225);
	  	
	  	carLabel = new Label(appearanceComposite, SWT.TRANSPARENT);
	  	carLabel.setLocation(148, 67);
	  	
	  	
	  	addPerformanceComponents();
	  	addAppearanceComponents();
	  	
	  	//Loading----------
	  	
	  	loadingComposite.setSize(320, 410);
	  	
	  	loadingText = new Label(loadingComposite, SWT.NULL);
	  	loadingText.setText("Waiting for opponents, \n   please wait...");
	  	loadingText.setSize(260, 40);
	  	loadingText.setLocation(50, 110);
	  	loadingText.setForeground(colorSilver2);
	  	loadingText.setFont(myfont);
	  	
	  	
	  	loading = new ProgressBar(loadingComposite, SWT.HORIZONTAL | SWT.INDETERMINATE);
	  	loading.setSize(250, 20);
	  	loading.setLocation(35, 290);
	  	
	  	loading.setBackground(display.getSystemColor(SWT.TRANSPARENT));

        
    	shell.setSize(320,430);
		shell.setMinimumSize(320, 430);
    	shell.setText("Menu");
		
		 Listener newGameL = new Listener() {
		      public void handleEvent(Event event) {
		    	  mainMenuComposite.setVisible(false);
		    	  newGameComposite.setVisible(true);
		      }
		 };
		 
		 Listener joinGameL = new Listener() {
		      public void handleEvent(Event event) {
		    	  mainMenuComposite.setVisible(false);
		    	  joinGameComposite.setVisible(true);
		    	  
		    	  String games = client.getGames();
		    	  
		    	  createdGames.removeAll();
		    	  
		    	  for(String a: games.split(",")){
		    		  if(!a.isEmpty()){
		    			  createdGames.add("Game id: "+a);
		    		  }
		    	  }
		      }
		 };
		 
		 Listener buildCarL = new Listener() {
		      public void handleEvent(Event event) {
		    	  mainMenuComposite.setVisible(false);
		    	  if(!saveAllCarComponents){
		    		  nullCombo();
		    	  }
		    	  else{
		    		  setAllCarComponents();
		    	  }
		    	  
		    	  buildCarComposite.setVisible(true);
		    	  performance.forceFocus();
		    	  performanceComposite.setVisible(true);
		      }
		 };
		 
		 Listener backL = new Listener() {
		      public void handleEvent(Event event) {
		    	  newGameComposite.setVisible(false);
		    	  mainMenuComposite.setVisible(true);
		      }
		 };
		 
		 Listener playL = new Listener() {
		      public void handleEvent(Event event) {
		    	  
		    	  int u = Integer.parseInt(countPlayers.getText());
		    	  game.createCars(u);
		    	  client.createGame(myDisplay, u, game, bodyworkComponent, glassComponent, ((Engine.engines[engineComponent].getValue()+Exhaust.exhausts[exhaustComponent].getValue())/2), ((Absorbers.absorbers[absorbersComponent].getValue()+Wheel.wheels[wheelComponent].getValue())/2));
		    	  setComponentsToCar(game);
		   
		    	  newGameComposite.setVisible(false); 	  
		    	  loadingComposite.setVisible(true);
		    	  
		    	  Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						client.syncStart();
						client.getImgs(game.getMap().cars);
						display.syncExec(new Runnable() {
							
							@Override
							public void run() {
								loadingComposite.setVisible(false);
							}
						});
						sp.Start();
					}
		    	  });
		    	  t.start();
		      }
		 };
		 
		 Listener back2L = new Listener() {
		      public void handleEvent(Event event) {
		    	  joinGameComposite.setVisible(false);
		    	  mainMenuComposite.setVisible(true);
		      }
		 };
		 
		 Listener connectL = new Listener() {
		      public void handleEvent(Event event) {
		    	  int u = Integer.parseInt(createdGames.getItem(createdGames.getSelectionIndex()).split(" ")[2]);
		    	  System.out.println("Selected game: " + u);
		    	  
		    	  int cars = client.joinGame(u, myDisplay, game, bodyworkComponent, glassComponent, ((Engine.engines[engineComponent].getValue()+Exhaust.exhausts[exhaustComponent].getValue())/2), ((Absorbers.absorbers[absorbersComponent].getValue()+Wheel.wheels[wheelComponent].getValue())/2));
				  game.createCars(cars);
				  setComponentsToCar(game);
				  
				  joinGameComposite.setVisible(false);
				  
				  loadingComposite.setVisible(true);
		    	  Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						client.syncStart();
						client.getImgs(game.getMap().cars);
						display.syncExec(new Runnable() {
							
							@Override
							public void run() {
								 loadingComposite.setVisible(false);
							}
						});
						sp.Start();
					}
		    	  });
		    	  t.start();
		      }
		 };
		 
		 Listener back3L = new Listener() {
		      public void handleEvent(Event event) {
		    	  buildCarComposite.setVisible(false);
		    	  mainMenuComposite.setVisible(true);
		      }
		 };
		 
		 Listener saveL = new Listener() {
		      public void handleEvent(Event event) {		    	  
		    	  saveAllCarComponents = saveAllCarComponents();
		      }
		 };
		 
		 Listener performanceL = new Listener() {
		      public void handleEvent(Event event) {
		    	  appearanceComposite.setVisible(false);
		    	  performanceComposite.setVisible(true);
		      }
		 };
		 
		 Listener appearanceL = new Listener() {
		      public void handleEvent(Event event) {
		    	  performanceComposite.setVisible(false);
		    	  appearanceComposite.setVisible(true);
		    	  
		    	  String karosery = Bodywork.bodyworks[bodyworkC.getSelectionIndex()].getCode();
		    	  String glass = Glass.glasses[glassC.getSelectionIndex()].getCode();
		    	  
		    	  Image carImage = new Image(display, "images/"+karosery+glass+".png");
		    	  carLabel.setSize(carImage.getBounds().width, carImage.getBounds().height);
		    	  carLabel.setImage(carImage);
		      }
		 };
		 
		 SelectionListener bodyworkSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				  String karosery = Bodywork.bodyworks[bodyworkC.getSelectionIndex()].getCode();
		    	  String glass = Glass.glasses[glassC.getSelectionIndex()].getCode();
		    	  
		    	  Image carImage = new Image(display, "images/"+karosery+glass+".png");
		    	  carLabel.setSize(carImage.getBounds().width, carImage.getBounds().height);
		    	  carLabel.setImage(carImage);
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
		    	  bodyworkC.select(0);
			}
		 };
		 
		 SelectionListener glassSelectionL = new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					  String karosery = Bodywork.bodyworks[bodyworkC.getSelectionIndex()].getCode();
			    	  String glass = Glass.glasses[glassC.getSelectionIndex()].getCode();
			    	  
			    	  Image carImage = new Image(display, "images/"+karosery+glass+".png");
			    	  carLabel.setSize(carImage.getBounds().width, carImage.getBounds().height);
			    	  carLabel.setImage(carImage);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					glassC.select(0);
				}
			 };
		
		PaintListener backgroundButtonPaintL = new PaintListener() {
					
			@Override
			public void paintControl(PaintEvent e) {
				if(e.widget instanceof Button)
				{
					Button button = (Button)e.widget;
				
					e.gc.setBackground(colorSilver);
					e.gc.fillRoundRectangle(0, 0, button.getBounds().width, button.getBounds().height, 10, 10);
					
					e.gc.setBackground(colorBlack2);
					e.gc.fillRoundRectangle(5, 5, button.getBounds().width-10, button.getBounds().height-10, 10, 10);
					
					e.gc.setForeground(colorSilver);
					
					Point textSize = e.gc.stringExtent(button.getText());
					int x = (button.getBounds().width-textSize.x)/2;
					int y = (button.getBounds().height-textSize.y)/2;
					
					e.gc.drawText(button.getText(), x, y);
				}
			}
		};
				
		PaintListener backgroundButtonImgL = new PaintListener() {
					
			@Override
			public void paintControl(PaintEvent e) {
				if(e.widget instanceof Button)
				{
					Button button = (Button)e.widget;
				
					e.gc.setBackground(colorSilver);
					e.gc.fillRoundRectangle(0, 0, button.getBounds().width, button.getBounds().height, 10, 10);
					
					Image image = new Image(display, "images/back2.png");
					e.gc.drawImage(image, 0, 0);
				}
			}
		};
				
		 
		newGame.addPaintListener(backgroundButtonPaintL);
		joinGame.addPaintListener(backgroundButtonPaintL);
		buildCar.addPaintListener(backgroundButtonPaintL);
		play.addPaintListener(backgroundButtonPaintL);
		connect.addPaintListener(backgroundButtonPaintL);
		save.addPaintListener(backgroundButtonPaintL);
		performance.addPaintListener(backgroundButtonPaintL);
		appearance.addPaintListener(backgroundButtonPaintL);
		
		backNewGame.addPaintListener(backgroundButtonImgL);
		backJoinGame.addPaintListener(backgroundButtonImgL);
		backBuildCar.addPaintListener(backgroundButtonImgL);
		
				
		bodyworkC.addSelectionListener(bodyworkSelectionL);
		glassC.addSelectionListener(glassSelectionL);
		
		newGame.addListener(SWT.Selection, newGameL);
		joinGame.addListener(SWT.Selection, joinGameL);
		buildCar.addListener(SWT.Selection, buildCarL);
		backNewGame.addListener(SWT.Selection, backL);
		play.addListener(SWT.Selection, playL);
		backJoinGame.addListener(SWT.Selection, back2L);
		connect.addListener(SWT.Selection, connectL);
		backBuildCar.addListener(SWT.Selection, back3L);
		save.addListener(SWT.Selection, saveL);
		performance.addListener(SWT.Selection, performanceL);
		appearance.addListener(SWT.Selection, appearanceL);
		
		
		loadingComposite.setVisible(false);
		appearanceComposite.pack();
		appearanceComposite.setVisible(false);
		performanceComposite.pack();
		performanceComposite.setVisible(false);
		buildCarComposite.setVisible(false);
		joinGameComposite.setVisible(false);
		newGameComposite.setVisible(false);
    }
    
    private void addPerformanceComponents(){
    	addEngine();
    	addExhaust();
    	addFilter();
    	addAbsorber();
    	addWheel();
    	addNitro();
    }
    
    private void addAppearanceComponents(){
    	addBodywork();
    	addGlass();
    }
    
    private void addEngine(){
    	for(Engine e : Engine.engines){
    		engineC.add(e.getName()+", "+e.getValue());
    	}
    	engineC.select(0);
    }
    
    private void addExhaust(){
    	for(Exhaust e : Exhaust.exhausts){
    		exhaustC.add(e.getName()+", "+e.getValue());
    	}
    	exhaustC.select(0);
    }
    
    private void addFilter(){
    	for(Filter f : Filter.filters){
    		filterC.add(f.getName()+", "+f.getValue());
    	}
    	filterC.select(0);
    }
    
    private void addAbsorber(){
    	for(Absorbers a : Absorbers.absorbers){
    		absorberC.add(a.getName()+", "+a.getValue());
    	}
    	absorberC.select(0);
    }

	private void addWheel(){
		for(Wheel w : Wheel.wheels){
			wheelC.add(w.getName()+", "+w.getValue());
		}
		wheelC.select(0);
	}

	private void addNitro(){
		for(Nitro n : Nitro.nitrous){
			nitroC.add(n.getName()+", "+n.getValue());
		}
		nitroC.select(0);
	}
	
	private void addBodywork(){
		for(Bodywork b : Bodywork.bodyworks){
			bodyworkC.add(b.getName()+", "+b.getType()+", "+b.getColor());
		}
		bodyworkC.select(0);
	}
	
	private void addGlass(){
		for(Glass g : Glass.glasses){
			glassC.add(g.getName()+", "+g.getColor());
		}
		glassC.select(0);
	}
	
   private boolean setComponentsToCar(Game myGame){
	   int idPlayer = myGame.getIDplayer();
	   
	   myGame.getMap().cars.get(idPlayer).setEngine(Engine.engines[engineComponent]);
	   myGame.getMap().cars.get(idPlayer).setExhaust(Exhaust.exhausts[exhaustComponent]);
	   myGame.getMap().cars.get(idPlayer).setFilter(Filter.filters[filterComponent]);
	   myGame.getMap().cars.get(idPlayer).setAbsorbers(Absorbers.absorbers[absorbersComponent]);
	   myGame.getMap().cars.get(idPlayer).setWheel(Wheel.wheels[wheelComponent]);
	   myGame.getMap().cars.get(idPlayer).setNitro(Nitro.nitrous[nitroComponent]);
	   myGame.getMap().cars.get(idPlayer).setBodywork(Bodywork.bodyworks[bodyworkComponent]);
	   myGame.getMap().cars.get(idPlayer).setGlass(Glass.glasses[glassComponent]);
	   
	   System.out.println(Wheel.wheels[wheelComponent].getValue());
	   
	   myGame.getMap().cars.get(idPlayer).setTrajectory();
	   
	   return true;
   }
   
   private boolean saveAllCarComponents(){
	   engineComponent = engineC.getSelectionIndex();
	   exhaustComponent = exhaustC.getSelectionIndex();
	   filterComponent = filterC.getSelectionIndex();
	   absorbersComponent = absorberC.getSelectionIndex();
	   wheelComponent = wheelC.getSelectionIndex();
	   nitroComponent = nitroC.getSelectionIndex();
	   
	   bodyworkComponent = bodyworkC.getSelectionIndex();
	   glassComponent = glassC.getSelectionIndex();
	   
	   return true;
   }
   
   private void setAllCarComponents(){
	   engineC.select(engineComponent);
	   exhaustC.select(exhaustComponent);
	   filterC.select(filterComponent);
	   absorberC.select(absorbersComponent);
	   wheelC.select(wheelComponent);
	   nitroC.select(nitroComponent);
	   
	   bodyworkC.select(bodyworkComponent);
	   glassC.select(glassComponent);
   }
	
	private void nullCombo(){
		bodyworkC.select(0);
	    glassC.select(0);
	  
	    engineC.select(0);
	    exhaustC.select(0);
	    filterC.select(0);
	    absorberC.select(0);
	    wheelC.select(0);
	    nitroC.select(0); 
	}
}
