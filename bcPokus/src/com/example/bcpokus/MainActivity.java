package com.example.bcpokus;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

	private RelativeLayout r;
	private SurfacePanel sp;
	private LayoutParams layoutParams;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		r = (RelativeLayout) findViewById(R.id.layout_panel);
		
		
		//layoutParams = new LayoutParams(313, 420);
		
		sp = new  SurfacePanel(this);
		
		//sp.setLayoutParams(layoutParams);
		
		r.addView(sp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
