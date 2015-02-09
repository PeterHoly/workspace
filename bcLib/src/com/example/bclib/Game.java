package com.example.bclib;

public class Game {

	private Display display;
	private Map map;
	private int IDplayer;
	
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
			//pozice a velikosti auta nejsou podstatne
			map.cars.add(new Car(100, 100, 40, 20));
		}
	}
	
	public int getIDplayer() {
		return IDplayer;
	}
	
	public void setIDplayer(int iDplayer) {
		IDplayer = iDplayer;
	}
}
