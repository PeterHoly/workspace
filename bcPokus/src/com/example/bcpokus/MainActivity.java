package com.example.bcpokus;

import java.util.ArrayList;

import org.w3c.dom.Text;

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
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private RelativeLayout r;
	private SurfacePanel sp;
	private LayoutParams layoutParams;
	
	public int idGame; 
	public boolean saveAllCarPerformanceComponents = false;
	public boolean saveAllCarAppearanceComponents = false;
	
	int engineComponent;
	int exhaustComponent;
	int wheelComponent;
	int absorbersComponent;
	int filterComponent;
	int nitroComponent;
	int bodyworkComponent;
	int glassComponent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		menu();
		
		//r = (RelativeLayout) findViewById(R.id.layout_panel);
		
		//sp = new  SurfacePanel(this);
				
		//r.addView(sp);
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
	
	public void newGame(View v){
		setContentView(R.layout.new_game);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		Button playButton = (Button) findViewById(R.id.play_button);
		playButton.setTypeface(tf);
		
		TextView tw = (TextView) findViewById(R.id.write_count_players);
		tw.setTypeface(tf);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		String[] arraySpinner = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	        
	    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arraySpinner);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}
	
	public TableLayout tableLayout;
	public int gameId;
	public void joinGame(View v){
		setContentView(R.layout.join_game);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		TextView tw = (TextView) findViewById(R.id.choose_one_game);
		tw.setTypeface(tf);
		
		// ve foru naplnit tableLayount - to budou vytvorene hry
		
		TextView item = new TextView(getApplicationContext());
		item.setText("ahoj");
		item.setTextColor(Color.BLACK);
		item.setTextSize(20);
		
		final int idGame = 0; // misto 0 bude i foru
		final TableRow trow = new TableRow(getApplicationContext());
		trow.addView(item);
		
		trow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				trow.setBackgroundColor(Color.GRAY);
				gameId = idGame;
				
			}
		});
		
		tableLayout = (TableLayout) findViewById(R.id.created_games_table);
		tableLayout.addView(trow);
	}
	
	public void connect(View v){
		Button connect = (Button) findViewById(R.id.connect_button);
		connect.setText(String.valueOf(gameId));
	}
	
	public void waitingForOpponents(){
		setContentView(R.layout.waiting_for_opponents);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/capture_it.ttf");
		
		TextView tw = (TextView) findViewById(R.id.waiting_for_opponents);
		tw.setTypeface(tf);		
	}
	
	public void buildCar(View v){
		setContentView(R.layout.build_car_performance);
		
		addPerformanceComponents();
		setAllCarPerformanceComponents();
		
		
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
	
	public void performance(View v){
		setContentView(R.layout.build_car_performance);
		
		addPerformanceComponents();
		setAllCarPerformanceComponents();
		
		
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
		
		addAppearanceComponents();
		setAllCarAppearanceComponents();
		
		
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
	
	public void savePerformance(View v){
		saveAllCarPerformanceComponents = saveAllCarPerformanceComponents();
	}
	
	public void saveAppearance(View v){
		saveAllCarAppearanceComponents = saveAllCarAppearanceComponents();
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
	   
	   Spinner spinnerEn = (Spinner) findViewById(R.id.spinnerEngine);
	   engineComponent = spinnerEn.getSelectedItemPosition();
	   
	   Spinner spinnerEx = (Spinner) findViewById(R.id.spinnerExhaust);
	   exhaustComponent = spinnerEx.getSelectedItemPosition();
	   
	   Spinner spinnerFi = (Spinner) findViewById(R.id.spinnerFilter);
	   filterComponent = spinnerFi.getSelectedItemPosition();
	   
	   Spinner spinnerAb = (Spinner) findViewById(R.id.spinnerAbsorber);
	   absorbersComponent = spinnerAb.getSelectedItemPosition();
	   
	   Spinner spinnerWh = (Spinner) findViewById(R.id.spinnerWheel);
	   wheelComponent = spinnerWh.getSelectedItemPosition();
	   
	   Spinner spinnerNi = (Spinner) findViewById(R.id.spinnerNitro);
	   nitroComponent = spinnerNi.getSelectedItemPosition();

	   return true;
   }
   
   private boolean saveAllCarAppearanceComponents(){
	   
	   Spinner spinnerBo = (Spinner) findViewById(R.id.spinnerBodywork);
	   bodyworkComponent = spinnerBo.getSelectedItemPosition();
	   
	   Spinner spinnerGl = (Spinner) findViewById(R.id.spinnerGlass);
	   glassComponent = spinnerGl.getSelectedItemPosition();
	   
	   return true;
   }
   
   private void setAllCarPerformanceComponents(){
	   
	   Spinner spinnerEn = (Spinner) findViewById(R.id.spinnerEngine);
	   spinnerEn.setSelection(engineComponent);
	   
	   Spinner spinnerEx = (Spinner) findViewById(R.id.spinnerExhaust);
	   spinnerEx.setSelection(exhaustComponent);
	   
	   Spinner spinnerFi = (Spinner) findViewById(R.id.spinnerFilter);
	   spinnerFi.setSelection(filterComponent);
	   
	   Spinner spinnerAb = (Spinner) findViewById(R.id.spinnerAbsorber);
	   spinnerAb.setSelection(absorbersComponent);
	   
	   Spinner spinnerWh = (Spinner) findViewById(R.id.spinnerWheel);
	   spinnerWh.setSelection(wheelComponent);
	   
	   Spinner spinnerNi = (Spinner) findViewById(R.id.spinnerNitro);
	   spinnerNi.setSelection(nitroComponent);
   }
   
   private void setAllCarAppearanceComponents(){
	   
	   Spinner spinnerBo = (Spinner) findViewById(R.id.spinnerBodywork);
	   spinnerBo.setSelection(bodyworkComponent);
	   
	   Spinner spinnerGl = (Spinner) findViewById(R.id.spinnerGlass);
	   spinnerGl.setSelection(glassComponent);
   }
	
	private void nullCombo(){
		
	   Spinner spinnerEn = (Spinner) findViewById(R.id.spinnerEngine);
	   spinnerEn.setSelection(0);
	   
	   Spinner spinnerEx = (Spinner) findViewById(R.id.spinnerExhaust);
	   spinnerEx.setSelection(0);
	   
	   Spinner spinnerFi = (Spinner) findViewById(R.id.spinnerFilter);
	   spinnerFi.setSelection(0);
	   
	   Spinner spinnerAb = (Spinner) findViewById(R.id.spinnerAbsorber);
	   spinnerAb.setSelection(0);
	   
	   Spinner spinnerWh = (Spinner) findViewById(R.id.spinnerWheel);
	   spinnerWh.setSelection(0);
	   
	   Spinner spinnerNi = (Spinner) findViewById(R.id.spinnerNitro);
	   spinnerNi.setSelection(0);
	   
	   Spinner spinnerBo = (Spinner) findViewById(R.id.spinnerBodywork);
	   spinnerBo.setSelection(0);
	   
	   Spinner spinnerGl = (Spinner) findViewById(R.id.spinnerGlass);
	   spinnerGl.setSelection(0);  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
