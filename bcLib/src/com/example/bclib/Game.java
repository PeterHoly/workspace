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
	
}
