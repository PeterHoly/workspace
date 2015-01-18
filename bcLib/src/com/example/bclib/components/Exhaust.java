package com.example.bclib.components;

public class Exhaust {
	private String name;
	private int id;
	private int value;
	
	public static Exhaust[] exhausts = {new Exhaust("Izawit 004",0,40), new Exhaust("Izawit 006",1,45), new Exhaust("Izawit 008",2,50), new Exhaust("Izawit 010",3,55)};	
	
	public Exhaust(String name, int id, int value){
		this.name = name;
		this.id = id;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
