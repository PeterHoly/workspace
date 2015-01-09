package com.example.bclib.components;

public class Nitro {
	private String name;
	private int id;
	private int value;
	
	public static Nitro[] nitrous = {new Nitro("EJ20",1,20), new Nitro("EJ30",2,30), new Nitro("ZEXi20",3,50), new Nitro("ZEXi30",4,60)};	
	
	public Nitro(String name, int id, int value){
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
