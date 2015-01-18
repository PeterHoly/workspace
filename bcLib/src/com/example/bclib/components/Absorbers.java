package com.example.bclib.components;

public class Absorbers {
	private String name;
	private int id;
	private int value;
	
	public static Absorbers[] absorbers = {new Absorbers("Forester",0,160), new Absorbers("Legacy",1,162), new Absorbers("Svx",2,170), new Absorbers("Impreza",3,190)};	
	
	public Absorbers(String name, int id, int value){
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
