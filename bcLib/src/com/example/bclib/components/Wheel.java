package com.example.bclib.components;

public class Wheel {
	private String name;
	private int id;
	private int value;
	
	public static Wheel[] wheels = {new Wheel("Platin",1,17), new Wheel("Gepard",2,19), new Wheel("Ronal",3,21), new Wheel("Enzo",4,23)};	
	
	public Wheel(String name, int id, int value){
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
