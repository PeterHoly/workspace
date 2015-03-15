package com.example.bclib;

import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

public class Game {

	private Display display;
	private Map map;
	private int IDplayer;
	private String mapNameImg;
	
	public Game(Display display){
		this.display = display;
		this.map = new Map();
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public Map getMap(){
		return map;
	}
	
	public void createCars(int count){
		for(int i=0; i<count; i++){
			map.cars.add(new Car(100, 100, 40, 20));
		}
	}
	
	public int getIDplayer() {
		return IDplayer;
	}
	
	public void setIDplayer(int iDplayer) {
		IDplayer = iDplayer;
	}
	
	public void setMapName(String mapNameImg) {
		this.mapNameImg = mapNameImg;
	}
	
	public String getMapName() {
		return this.mapNameImg;
	}
	
	public void setMapObstacleAndStart(String map) {
		
		String  size = map.split("/")[0];
		String startLines = map.split("/")[1];
		String obstacles = map.split("/")[2];
		
		double addRow = Double.valueOf(size.split(",")[0]);
		double width = Double.valueOf(size.split(",")[1]);
		double height = Double.valueOf(size.split(",")[2]);
		
		String start = startLines.split("=")[0];
		String cil = startLines.split("=")[1];
		
		this.map.startObs = new Obstacle(Double.valueOf(start.split(",")[0]), Double.valueOf(start.split(",")[1]), Double.valueOf(start.split(",")[2]), Double.valueOf(start.split(",")[3]));
		this.map.cilObs = new Obstacle(Double.valueOf(cil.split(",")[0]), Double.valueOf(cil.split(",")[1]), Double.valueOf(cil.split(",")[2]), Double.valueOf(cil.split(",")[3]));
		
		double displayWidthDouble = display.getWidth();

		for(String o : obstacles.split("=")){
			
			double x1 = (Double.valueOf(o.split(",")[0])/width)*displayWidthDouble;
			double y1 = (Double.valueOf(o.split(",")[1])/width)*displayWidthDouble;
			double x2 = (Double.valueOf(o.split(",")[2])/width)*displayWidthDouble;
			double y2 = (Double.valueOf(o.split(",")[3])/width)*displayWidthDouble;
			
			double y = (y2-y1)/2+y1;
			
			Obstacle obs = new Obstacle(x1, y2, x2, y1);
			obs.setAngle(Double.valueOf(o.split(",")[4]));
			obs.rotate(-Math.toRadians(Double.valueOf(o.split(",")[4])), x1, y);
			this.map.obstacles.add(obs);
		}
		
		Collections.sort(this.map.obstacles, new Comparator<Obstacle>() {
	        @Override public int compare(Obstacle o1, Obstacle o2) {
	            return (int)o1.getY() - (int)o2.getY();
	        }
	    });
	}
}
