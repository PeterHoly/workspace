package desktopApp;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.example.bclib.Car;
import com.example.bclib.Client;
import com.example.bclib.Game;
import com.example.bclib.Obstacle;
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
    Text writeCountPlayers;
    Text chooseGame;
   
    Text engine;
    Text exhaust;
    Text filter;
    Text absorber;
    Text wheel;
    Text nitro;
    Text loadingText;
    
    Combo engineC;
    Combo exhaustC;
    Combo filterC;
    Combo absorberC;
    Combo wheelC;
    Combo nitroC;
    
    Text bodywork;
    Text glass;
    
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
     
    Label myLabel;
    
    public DesktopMenu(final Display display, Shell shell, final Client client, final com.example.bclib.Display myDisplay, final Game game, final SurfacePanel sp){
 
        mainMenuComposite = new Composite(shell, SWT.NULL);
        newGameComposite = new Composite(shell, SWT.NULL);
        joinGameComposite = new Composite(shell, SWT.NULL);
        buildCarComposite = new Composite(shell, SWT.NULL);
        performanceComposite = new Composite(buildCarComposite, SWT.NULL);
        appearanceComposite = new Composite(buildCarComposite, SWT.NULL);
        loadingComposite = new Composite(shell, SWT.NULL);
        
        final org.eclipse.swt.graphics.Color color1 = display.getSystemColor(SWT.COLOR_WHITE);
        final org.eclipse.swt.graphics.Color color2 = display.getSystemColor(SWT.COLOR_BLACK);
        
        //Menu---------
        mainMenuComposite.setBackground(color1);  
        
        buildCar = new Button(mainMenuComposite,SWT.PUSH);
        buildCar.setSize(200, 50);
        buildCar.setText("Build Car");
        buildCar.setLocation(60, 320);
        //buildCar.setBackground(color1);
        
        
        joinGame = new Button(mainMenuComposite,SWT.PUSH);
        joinGame.setSize(200, 50);
        joinGame.setText("Join Game");
        joinGame.setLocation(60, 270);
        //joinGame.setBackground(color1);
        
        newGame = new Button(mainMenuComposite,SWT.PUSH);
        newGame.setSize(200, 50);
        newGame.setText("New Game!");
        newGame.setLocation(60, 220);
        newGame.setFocus();
        //newGame.setBackground(color1);
        
        
        //New Game---------
        newGameComposite.setBackground(color1);
        
        countPlayers = new Spinner(newGameComposite, SWT.NULL);
  	  	countPlayers.setMinimum(1);
  	  	countPlayers.setMaximum(10);
  	  	countPlayers.setSize(50, 40);
  	  	countPlayers.setLocation(115, 200);
  	  	//countPlayers.setBackground(color2);
  	  	
	  	play = new Button(newGameComposite,SWT.PUSH);
	  	play.setSize(200, 50);
	  	play.setText("Play!");
	  	play.setLocation(60, 250);
	  	
	  	writeCountPlayers = new Text(newGameComposite,SWT.NULL);
	  	writeCountPlayers.setSize(140, 20);
	  	writeCountPlayers.setText("Write count players!");
	  	writeCountPlayers.setLocation(85, 130);
	  	
	  	
	  	Image image = new Image(display, "images/back.png");
	  	backNewGame = new Button(newGameComposite,SWT.PUSH);
	  	backNewGame.setSize(60, 40);
	  	backNewGame.setLocation(5, 362);
	  	backNewGame.setImage(image);
        
	  	//Join Game---------
        joinGameComposite.setBackground(color1);
        
        chooseGame = new Text(joinGameComposite,SWT.NULL);
        chooseGame.setSize(125, 20);
        chooseGame.setText("Choose one game!");
        chooseGame.setLocation(80, 110);
        
        createdGames = new List(joinGameComposite,SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        createdGames.setSize(200, 150);
        createdGames.setLocation(62, 190);
        
	  	backJoinGame = new Button(joinGameComposite,SWT.PUSH);
	  	backJoinGame.setSize(60, 40);
	  	backJoinGame.setLocation(5, 362);
	  	backJoinGame.setImage(image);
	  	
	  	connect = new Button(joinGameComposite,SWT.PUSH);
	  	connect.setSize(70, 45);
	  	connect.setText("Connect!");
	  	connect.setLocation(245, 357);
        
	  	
	  	//Build car---------

	  	buildCarComposite.setBackground(color1);
	  	performanceComposite.setBackground(color1);
	  	appearanceComposite.setBackground(color1);
	  	
	  	
	  	backBuildCar = new Button(buildCarComposite,SWT.PUSH);
	  	backBuildCar.setSize(60, 40);
	  	backBuildCar.setLocation(5, 362);
	  	backBuildCar.setImage(image);
	  	
	  	save = new Button(buildCarComposite,SWT.PUSH);
	  	save.setSize(70, 45);
	  	save.setText("Save");
	  	save.setLocation(245, 357);
	  	
	  	//Vykon auta------
	  	
	  	performance = new Button(buildCarComposite,SWT.NULL);
	  	performance.setSize(125, 40);
	  	performance.setText("Performance");
	  	performance.setLocation(30, 310);
	  	
	  	engine = new Text(performanceComposite, SWT.NULL);
	  	engine.setSize(75, 20);
	  	engine.setText("Engine");
	  	engine.setLocation(25, 140);
	  	
	  	engineC = new Combo(performanceComposite, SWT.NULL);
	  	engineC.setSize(200, 20);
	  	engineC.setLocation(101, 140);
	  	
	  	exhaust = new Text(performanceComposite, SWT.NULL);
	  	exhaust.setSize(75, 20);
	  	exhaust.setText("Exhaust");
	  	exhaust.setLocation(25, 165);
	  	
	  	exhaustC = new Combo(performanceComposite, SWT.NULL);
	  	exhaustC.setSize(200, 20);
	  	exhaustC.setLocation(101, 165);
	  	
	  	filter = new Text(performanceComposite, SWT.NULL);
	  	filter.setSize(75, 20);
	  	filter.setText("Filter");
	  	filter.setLocation(25, 190);
	  	
	  	filterC = new Combo(performanceComposite, SWT.NULL);
	  	filterC.setSize(200, 20);
	  	filterC.setLocation(101, 190);
	  	
	  	absorber = new Text(performanceComposite, SWT.NULL);
	  	absorber.setSize(75, 20);
	  	absorber.setText("Absorber");
	  	absorber.setLocation(25, 215);
	  	
	  	absorberC = new Combo(performanceComposite, SWT.NULL);
	  	absorberC.setSize(200, 20);
	  	absorberC.setLocation(101, 215);
	  	
	  	wheel = new Text(performanceComposite, SWT.NULL);
	  	wheel.setSize(75, 20);
	  	wheel.setText("Wheel");
	  	wheel.setLocation(25, 240);
	  	
	  	wheelC = new Combo(performanceComposite, SWT.NULL);
	  	wheelC.setSize(200, 20);
	  	wheelC.setLocation(101, 240);
	  	
	  	nitro = new Text(performanceComposite, SWT.NULL);
	  	nitro.setSize(75, 20);
	  	nitro.setText("Nitro");
	  	nitro.setLocation(25, 265);
	  	
	  	nitroC = new Combo(performanceComposite, SWT.NULL);
	  	nitroC.setSize(200, 20);
	  	nitroC.setLocation(101, 265);
	  	
	  	//Vzhled auta------
	  	
	  	appearance = new Button(buildCarComposite,SWT.NULL);
	  	appearance.setSize(125, 40);
	  	appearance.setText("Appearance");
	  	appearance.setLocation(165, 310);
	  	
	  	bodywork = new Text(appearanceComposite, SWT.NULL);
	  	bodywork.setSize(75, 20);
	  	bodywork.setText("Bodywork");
	  	bodywork.setLocation(25, 200);
	  	
	  	bodyworkC = new Combo(appearanceComposite, SWT.NULL);
	  	bodyworkC.setSize(200, 20);
	  	bodyworkC.setLocation(101, 200);
	  	
	  	glass = new Text(appearanceComposite, SWT.NULL);
	  	glass.setSize(75, 20);
	  	glass.setText("Glass");
	  	glass.setLocation(25, 225);
	  	
	  	glassC = new Combo(appearanceComposite, SWT.NULL);
	  	glassC.setSize(200, 20);
	  	glassC.setLocation(101, 225);
	  	
	  	myLabel = new Label(appearanceComposite, SWT.NONE);
	  	myLabel.setBackground(color1);
	  	myLabel.setLocation(110, 110);
	  	
	  	addPerformanceComponents();
	  	addAppearanceComponents();
	  	
	  	//Loading----------
	  	
	  	loadingComposite.setBackground(color1);
	  	loadingComposite.setSize(320, 400);
	  	
	  	loadingText = new Text(loadingComposite, SWT.NULL);
	  	loadingText.setText("Waiting for opponents, please wait...");
	  	loadingText.setSize(240, 40);
	  	loadingText.setLocation(40, 200);
	  	
	  	loading = new ProgressBar(loadingComposite, SWT.HORIZONTAL | SWT.INDETERMINATE);
	  	loading.setSize(250, 20);
	  	loading.setLocation(35, 270);
	  	
        
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
		    	  System.out.println("Create game for " + u + " players.");
		    	  game.createCars(u);
		    	  client.createGame(myDisplay, u, game, bodyworkComponent, glassComponent);
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
		    	  
		    	  int cars = client.joinGame(u, myDisplay, game, bodyworkComponent, glassComponent);
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
		    	  
		    	  Image myImage = new Image(display, "images/"+karosery+glass+".png");
		    	  myLabel.setSize(myImage.getBounds().width, myImage.getBounds().height);
		    	  myLabel.setImage(myImage);
		      }
		 };
		 
		 SelectionListener bodyworkSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				  String karosery = Bodywork.bodyworks[bodyworkC.getSelectionIndex()].getCode();
		    	  String glass = Glass.glasses[glassC.getSelectionIndex()].getCode();
		    	  
		    	  Image myImage = new Image(display, "images/"+karosery+glass+".png");
		    	  myLabel.setSize(myImage.getBounds().width, myImage.getBounds().height);
		    	  myLabel.setImage(myImage);
				
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
			    	  
			    	  Image myImage = new Image(display, "images/"+karosery+glass+".png");
			    	  myLabel.setSize(myImage.getBounds().width, myImage.getBounds().height);
			    	  myLabel.setImage(myImage);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					glassC.select(0);
				}
			 };
		 
		 
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
		buildCarComposite.pack();
		buildCarComposite.setVisible(false);
		joinGameComposite.pack();
		joinGameComposite.setVisible(false);
		newGameComposite.pack();
		newGameComposite.setVisible(false);
		mainMenuComposite.pack();
		
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
	   
	   return true;
   }
   
   private boolean saveAllCarComponents(){
	   engineComponent = engineC.getSelectionIndex();
	   exhaustComponent = exhaustC.getSelectionIndex();
	   filterComponent = filterC.getSelectionIndex();
	   absorbersComponent = absorberC.getSelectionIndex();
	   wheelComponent = filterC.getSelectionIndex();
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
