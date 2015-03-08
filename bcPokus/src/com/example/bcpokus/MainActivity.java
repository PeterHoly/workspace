package com.example.bcpokus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.bclib.Client;
import com.example.bclib.Display;
import com.example.bclib.Game;
import com.example.bclib.components.Absorbers;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Engine;
import com.example.bclib.components.Exhaust;
import com.example.bclib.components.Filter;
import com.example.bclib.components.Glass;
import com.example.bclib.components.Nitro;
import com.example.bclib.components.Wheel;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private RelativeLayout r;
	private LayoutParams layoutParams;
	
	public int idGame; 
	public boolean saveAllCarPerformanceComponents = false;
	public boolean saveAllCarAppearanceComponents = false;
	public String[] textComponents = {"increase the speed of the car","increase control car","increase acceleration car","instant acceleration car"};
	
	int engineComponent;
	int exhaustComponent;
	int wheelComponent;
	int absorbersComponent;
	int filterComponent;
	int nitroComponent;
	int bodyworkComponent;
	int glassComponent;
	
	int engineCom;
	int exhaustCom;
	int wheelCom;
	int absorbersCom;
	int filterCom;
	int nitroCom;
	int bodyworkCom;
	int glassCom;
	
	public Handler mHandler;
	
	public String[] arraySpinner = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	private final Client myClient = new Client("192.168.0.21", 8096);
	//private final Client myClient = new Client("192.168.43.37", 8096);
	
	private final Game myGame = new Game(new Display(0,0,320,430));
	private SurfacePanel sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		menu();
	}
	
	public void menu(){
		setContentView(R.layout.activity_main);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		Button newGameButton = (Button) findViewById(R.id.menu_button_new);
		newGameButton.setTypeface(tf);
		
		Button joinGameButton = (Button) findViewById(R.id.menu_button_join);
		joinGameButton.setTypeface(tf);
		
		Button buildCarButton = (Button) findViewById(R.id.menu_button_build);
		buildCarButton.setTypeface(tf);
	}
	
	public TableLayout tableLayout2;
	public String mapName;
	public void newGame(View v){
		setContentView(R.layout.new_game);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		Button playButton = (Button) findViewById(R.id.play_button);
		playButton.setTypeface(tf);
		
		TextView tw = (TextView) findViewById(R.id.write_count_players);
		tw.setTypeface(tf);
		
		TextView tws = (TextView) findViewById(R.id.choose_one_map);
		tws.setTypeface(tf);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    
	    ScrollView sw = (ScrollView) findViewById(R.id.created_maps_scroll);
		sw.setBackgroundColor(Color.GRAY);
		
		String maps = myClient.getMaps();
		
  	  	tableLayout2 = (TableLayout) findViewById(R.id.created_maps_table);
  	  	
  	  	for(String a: maps.split(",")){
  	  		if(a.length() > 0){
  	  			
	  	  		TextView item = new TextView(getApplicationContext());
	  			item.setText("Map: "+a);
	  			item.setTextColor(Color.BLACK);
	  			item.setTextSize(20);
	  			
	  			final String map = a;
	  			
	  			final TableRow trow = new TableRow(getApplicationContext());
	  			trow.addView(item);
	  			
	  			trow.setOnClickListener(new OnClickListener() {
	  				
	  				@Override
	  				public void onClick(View v) {
	  					
	  					for(int i=0; i<tableLayout2.getChildCount(); i++){
	  						tableLayout2.getChildAt(i).setBackgroundColor(Color.GRAY);
	  					}
	  					
	  					trow.setBackgroundColor(Color.DKGRAY);
	  					mapName = map;
	  				}
	  			});
	  			tableLayout2.addView(trow);
  	  		}
  	  	}
	}
	
	public void playGame(View v){

		boolean mapIsLoaded = false;
		String a = null;
		String[] directoryListing = this.getDir("maps", MODE_PRIVATE).list();
		if (directoryListing != null) {
			for (String child : directoryListing) {
				if(child.equals(mapName)){
					mapIsLoaded = true;
					break;
				}
			}
		}
  
		if(!mapIsLoaded){
			byte[] map = myClient.loadMap(mapName);
			Render.createImg(map, mapName, this);
		}
  	  
		Spinner countPlayers = (Spinner) findViewById(R.id.spinner);
		final int u = Integer.parseInt(arraySpinner[countPlayers.getSelectedItemPosition()]);
		myGame.createCars(u);
		
		Thread th = new Thread(new Runnable(){
			@Override
			public void run() {
				myClient.createGame(mapName, myGame.getDisplay(), u, myGame, bodyworkComponent, glassComponent, ((Engine.engines[engineComponent].getValue()+Exhaust.exhausts[exhaustComponent].getValue())/2), ((Absorbers.absorbers[absorbersComponent].getValue()+Wheel.wheels[wheelComponent].getValue())/2),nitroComponent, filterComponent);
			}
		});
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		setComponentsToCar(myGame);
			  
  	  	waitingForOpponents();
  	  	
  	  	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				myClient.syncStart();
				myClient.getImgs(myGame.getMap().cars);
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						setContentView(R.layout.game_layout);
						r = (RelativeLayout) findViewById(R.id.layout_game);
						SurfacePanel sp = new  SurfacePanel(MainActivity.this, myClient, myGame, getPackageName(), getAssets());
						r.addView(sp);
						sp.Start();
					}
				});
			}
  	  	});
  	  	t.start();
	}
	
	public TableLayout tableLayout;
	public String gameId;
	public void joinGame(View v){
		setContentView(R.layout.join_game);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		TextView tw = (TextView) findViewById(R.id.choose_one_game);
		tw.setTypeface(tf);
		
		ScrollView sw = (ScrollView) findViewById(R.id.created_games_scroll);
		sw.setBackgroundColor(Color.GRAY);
		
		String games = myClient.getGames();

  	  	tableLayout = (TableLayout) findViewById(R.id.created_games_table);
  	  	
  	  	for(String a: games.split(",")){
  	  		if(a.length() > 0){
  	  			
	  	  		TextView item = new TextView(getApplicationContext());
	  			item.setText("Game id: "+a);
	  			item.setTextColor(Color.BLACK);
	  			item.setTextSize(20);
	  			
	  			final String idGame = a;
	  			
	  			final TableRow trow = new TableRow(getApplicationContext());
	  			trow.addView(item);
	  			
	  			trow.setOnClickListener(new OnClickListener() {
	  				@Override
	  				public void onClick(View v) {
	  					
	  					for(int i=0; i<tableLayout.getChildCount(); i++){
	  						tableLayout.getChildAt(i).setBackgroundColor(Color.GRAY);
	  					}
	  					
	  					trow.setBackgroundColor(Color.DKGRAY);
	  					gameId = idGame;
	  				}
	  			});

	  			tableLayout.addView(trow);
  	  		}
  	  	}	
	}
	
	public void connect(View v){
		String m = myClient.getMapName(Integer.parseInt(gameId));
		boolean mapIsLoaded = false;
		String a = null;
		String[] directoryListing = this.getDir("maps", MODE_PRIVATE).list();
		if (directoryListing != null) {
			for (String child : directoryListing) {
				if(child.equals(m)){
					mapIsLoaded = true;
					break;
				}
			}
		}
  
		if(!mapIsLoaded){
			byte[] map = myClient.loadMap(m);
			Render.createImg(map, m, this);
		}
		myGame.setMapName(m);
		
  	  	int cars = myClient.joinGame(Integer.parseInt(gameId), myGame.getDisplay(), myGame, bodyworkComponent, glassComponent, ((Engine.engines[engineComponent].getValue()+Exhaust.exhausts[exhaustComponent].getValue())/2), ((Absorbers.absorbers[absorbersComponent].getValue()+Wheel.wheels[wheelComponent].getValue())/2),nitroComponent, filterComponent);
  	  	myGame.createCars(cars);
  	  	setComponentsToCar(myGame);
		  
  	  	waitingForOpponents();
  	  	
  	  	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				myClient.syncStart();
				myClient.getImgs(myGame.getMap().cars);
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						setContentView(R.layout.game_layout);
						r = (RelativeLayout) findViewById(R.id.layout_game);
						SurfacePanel sp = new  SurfacePanel(MainActivity.this, myClient, myGame, getPackageName(), getAssets());
						r.addView(sp);
						sp.Start();
					}
				});
			}
  	  	});
  	  	t.start();
	}

	
	public void waitingForOpponents(){
		setContentView(R.layout.waiting_for_opponents);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		TextView tw = (TextView) findViewById(R.id.waiting_for_opponents);
		tw.setTypeface(tf);
	}
	
	public void buildCar(View v){
		if(!saveAllCarPerformanceComponents)
		{
			nullComboPerformance();
		}
		if(!saveAllCarAppearanceComponents){
			nullComboAppearance();
		}
		performance(v);
	}
	
	public void performance(View v){
		setContentView(R.layout.build_car_performance);
		addListenerChangePerformance();
		addPerformanceComponents();
		setAllCarPerformanceComponents(saveAllCarPerformanceComponents);
		
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		Button per = (Button) findViewById(R.id.button_performance);
		per.setTypeface(tf);
		Button app = (Button) findViewById(R.id.button_appearance);
		app.setTypeface(tf);
		
		TextView ab = (TextView) findViewById(R.id.textAbsorber);
		ab.setTypeface(tf);		
		TextView en = (TextView) findViewById(R.id.textEngine);
		en.setTypeface(tf);		
		TextView ex = (TextView) findViewById(R.id.textExhaust);
		ex.setTypeface(tf);		
		TextView ni = (TextView) findViewById(R.id.textNitro);
		ni.setTypeface(tf);		
		TextView wh = (TextView) findViewById(R.id.textWheel);
		wh.setTypeface(tf);		
		TextView fi = (TextView) findViewById(R.id.textFilter);
		fi.setTypeface(tf);

	}
	
	public void appearance(View v){
		setContentView(R.layout.build_car_appearance);
		addListenerChangeAppearance();
		addAppearanceComponents();
		setAllCarAppearanceComponents(saveAllCarAppearanceComponents);
		
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		Button per = (Button) findViewById(R.id.button_performance2);
		per.setTypeface(tf);
		Button app = (Button) findViewById(R.id.button_appearance2);
		app.setTypeface(tf);
		
		TextView bo = (TextView) findViewById(R.id.textBodywork);
		bo.setTypeface(tf);		
		TextView gl = (TextView) findViewById(R.id.textGlass);
		gl.setTypeface(tf);	
	}
	
	public void save(View v){
		saveAllCarPerformanceComponents = saveAllCarPerformanceComponents();
		saveAllCarAppearanceComponents = saveAllCarAppearanceComponents();
		back(v);
	}
	
	public void back(View v){
		menu();
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
    	
    	Spinner spinner = (Spinner) findViewById(R.id.spinnerEngine);
		String[] arraySpinner = new String[Engine.engines.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Engine.engines.length; i++){
    		arraySpinner[i] = Engine.engines[i].getName()+", "+Engine.engines[i].getValue();
    	}
    	spinner.setAdapter(adapter);
    }
    
    private void addExhaust(){
    	Spinner spinner = (Spinner) findViewById(R.id.spinnerExhaust);
		String[] arraySpinner = new String[Exhaust.exhausts.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Exhaust.exhausts.length; i++){
    		arraySpinner[i] = Exhaust.exhausts[i].getName()+", "+Exhaust.exhausts[i].getValue();
    	}
    	spinner.setAdapter(adapter);
    }
    
    private void addFilter(){
    	Spinner spinner = (Spinner) findViewById(R.id.spinnerFilter);
		String[] arraySpinner = new String[Filter.filters.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Filter.filters.length; i++){
    		arraySpinner[i] = Filter.filters[i].getName()+", "+Filter.filters[i].getValue();
    	}
    	spinner.setAdapter(adapter);
    }
    
    private void addAbsorber(){
    	Spinner spinner = (Spinner) findViewById(R.id.spinnerAbsorber);
		String[] arraySpinner = new String[Absorbers.absorbers.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Absorbers.absorbers.length; i++){
    		arraySpinner[i] = Absorbers.absorbers[i].getName()+", "+Absorbers.absorbers[i].getValue();
    	}
    	spinner.setAdapter(adapter);
    }

	private void addWheel(){
		Spinner spinner = (Spinner) findViewById(R.id.spinnerWheel);
		String[] arraySpinner = new String[Wheel.wheels.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Wheel.wheels.length; i++){
    		arraySpinner[i] = Wheel.wheels[i].getName()+", "+Wheel.wheels[i].getValue();
    	}
    	spinner.setAdapter(adapter);
	}

	private void addNitro(){
		Spinner spinner = (Spinner) findViewById(R.id.spinnerNitro);
		String[] arraySpinner = new String[Nitro.nitrous.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Nitro.nitrous.length; i++){
    		arraySpinner[i] = Nitro.nitrous[i].getName()+", "+Nitro.nitrous[i].getValue();
    	}
    	spinner.setAdapter(adapter);
	}
	
	private void addBodywork(){
		Spinner spinner = (Spinner) findViewById(R.id.spinnerBodywork);
		String[] arraySpinner = new String[Bodywork.bodyworks.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Bodywork.bodyworks.length; i++){
    		arraySpinner[i] = Bodywork.bodyworks[i].getName()+", "+Bodywork.bodyworks[i].getColor();
    	}
    	spinner.setAdapter(adapter);
	}
	
	private void addGlass(){
		Spinner spinner = (Spinner) findViewById(R.id.spinnerGlass);
		String[] arraySpinner = new String[Glass.glasses.length];
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
    	for(int i = 0; i<Glass.glasses.length; i++){
    		arraySpinner[i] = Glass.glasses[i].getName()+", "+Glass.glasses[i].getColor();
    	}
    	spinner.setAdapter(adapter);
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
	   
	   myGame.getMap().cars.get(idPlayer).setTrajectory();
	   
	   return true;
   }
   
   private boolean saveAllCarPerformanceComponents(){
	   engineComponent = engineCom;
	   exhaustComponent = exhaustCom;
	   filterComponent = filterCom;
	   absorbersComponent = absorbersCom;
	   wheelComponent = wheelCom;
	   nitroComponent = nitroCom;

	   return true;
   }
   
   private boolean saveAllCarAppearanceComponents(){
	   bodyworkComponent = bodyworkCom;
	   glassComponent = glassCom;
	   
	   return true;
   }
   
   private void setAllCarPerformanceComponents(boolean com){
	   
	   Spinner spinnerEn = (Spinner) findViewById(R.id.spinnerEngine);
	   Spinner spinnerEx = (Spinner) findViewById(R.id.spinnerExhaust);
	   Spinner spinnerFi = (Spinner) findViewById(R.id.spinnerFilter);
	   Spinner spinnerAb = (Spinner) findViewById(R.id.spinnerAbsorber);
	   Spinner spinnerWh = (Spinner) findViewById(R.id.spinnerWheel);
	   Spinner spinnerNi = (Spinner) findViewById(R.id.spinnerNitro);
	   
	   if(com){
		   spinnerEn.setSelection(engineComponent);
		   spinnerEx.setSelection(exhaustComponent);
		   spinnerFi.setSelection(filterComponent);
		   spinnerAb.setSelection(absorbersComponent);
		   spinnerWh.setSelection(wheelComponent);
		   spinnerNi.setSelection(nitroComponent);
	   }
	   else{
		   spinnerEn.setSelection(engineCom);
		   spinnerEx.setSelection(exhaustCom);
		   spinnerFi.setSelection(filterCom);
		   spinnerAb.setSelection(absorbersCom);
		   spinnerWh.setSelection(wheelCom);
		   spinnerNi.setSelection(nitroCom);
	   }
   }
   
   private void setAllCarAppearanceComponents(boolean com){
	   
	   Spinner spinnerBo = (Spinner) findViewById(R.id.spinnerBodywork);
	   Spinner spinnerGl = (Spinner) findViewById(R.id.spinnerGlass);
	   ImageView imageCar = (ImageView) findViewById(R.id.imageCar);
	   
	   if(com){
		   spinnerBo.setSelection(bodyworkComponent);
		   spinnerGl.setSelection(glassComponent);
		   
		   String codeB = Bodywork.bodyworks[bodyworkComponent].getCode();
		   String codeG = Glass.glasses[glassComponent].getCode();
		   String code = codeB+codeG;
			
		   int resourceID = getResources().getIdentifier(code, "drawable", getPackageName());
		   imageCar.setImageResource(resourceID);
	   }
	   else{
		   spinnerBo.setSelection(bodyworkCom);
		   spinnerGl.setSelection(glassCom);
		   
		   String codeB = Bodywork.bodyworks[bodyworkCom].getCode();
		   String codeG = Glass.glasses[glassCom].getCode();
		   String code = codeB+codeG;
			
		   int resourceID = getResources().getIdentifier(code, "drawable", getPackageName());
		   imageCar.setImageResource(resourceID);
	   }		
   }
	
	private void nullComboPerformance(){
		engineCom = 0;
		exhaustCom = 0;
		wheelCom = 0;
		absorbersCom = 0;
		filterCom = 0;
		nitroCom = 0;
	}
	
	private void nullComboAppearance(){
		bodyworkCom = 0;
		glassCom = 0;
	}
	
	private void addListenerChangePerformance(){
		
	   final Spinner spinnerEn = (Spinner) findViewById(R.id.spinnerEngine);
	   spinnerEn.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				engineCom = spinnerEn.getSelectedItemPosition();
						
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[0]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	   
	   final Spinner spinnerEx = (Spinner) findViewById(R.id.spinnerExhaust);
	   spinnerEx.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				exhaustCom = spinnerEx.getSelectedItemPosition();	
				
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[0]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	   
	   final Spinner spinnerFi = (Spinner) findViewById(R.id.spinnerFilter);
	   spinnerFi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				filterCom = spinnerFi.getSelectedItemPosition();	
				
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[2]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	   
	   final Spinner spinnerAb = (Spinner) findViewById(R.id.spinnerAbsorber);
	   spinnerAb.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				absorbersCom = spinnerAb.getSelectedItemPosition();
				
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[1]);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	   
	   final Spinner spinnerWh = (Spinner) findViewById(R.id.spinnerWheel);
	   spinnerWh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				wheelCom = spinnerWh.getSelectedItemPosition();
				
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[1]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	   
	   final Spinner spinnerNi = (Spinner) findViewById(R.id.spinnerNitro);
	   spinnerNi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				nitroCom = spinnerNi.getSelectedItemPosition();
				
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
				TextView textCompo = (TextView) findViewById(R.id.textComponent);
				textCompo.setTypeface(tf, Typeface.ITALIC);
				textCompo.setTextSize(16);
				textCompo.setText(textComponents[3]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	}
	
	private void addListenerChangeAppearance(){
		
		final Spinner spinnerBo = (Spinner) findViewById(R.id.spinnerBodywork);
		spinnerBo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				bodyworkCom = spinnerBo.getSelectedItemPosition();
				
				ImageView car = (ImageView) findViewById(R.id.imageCar);
				
				String codeB = Bodywork.bodyworks[bodyworkCom].getCode();
				String codeG = Glass.glasses[glassCom].getCode();
				String code = codeB+codeG;
				
				int resourceID = getResources().getIdentifier(code, "drawable", getPackageName());
				
				car.setImageResource(resourceID);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
		   
	   final Spinner spinnerGl = (Spinner) findViewById(R.id.spinnerGlass);
	   spinnerGl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				glassCom = spinnerGl.getSelectedItemPosition();
				
				ImageView car = (ImageView) findViewById(R.id.imageCar);
				
				String codeB = Bodywork.bodyworks[bodyworkCom].getCode();
				String codeG = Glass.glasses[glassCom].getCode();
				String code = codeB+codeG;
				
				int resourceID = getResources().getIdentifier(code, "drawable", getPackageName());
				
				car.setImageResource(resourceID);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
	   });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
