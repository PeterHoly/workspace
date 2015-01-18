package com.example.bclib.components;

import java.util.jar.Attributes.Name;

public class Engine {
	private String name;
	private int id;
	private int value;
	
	public static Engine[] engines = {new Engine("EJ22",0,106), new Engine("EJ22Turbo",1,206), new Engine("EJ23",2,224), new Engine("EJ23Turbo",3,324)};	
	
	public Engine(String name, int id, int value){
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
