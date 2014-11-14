package com.example.bclib;

public class Game {

	private Display display;
	private Map map;
	
	public Game(Display display){
		this.display = display;
		this.map = new Map(this.display);
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public Map getMap(){
		return map;
	}
	
	public void createCars(int count){
		for(int i=0; i<count; i++){
			this.map.cars.add(new Car(150, 100, 40, 20));
		}
	}
}
