package com.example.bclib.components;

public class Filter {
	private String name;
	private int id;
	private int value;
	
	public static Filter[] filters = {new Filter("MANN C2201",1,10), new Filter("MANN C2203",2,12), new Filter("MANN C2301",3,14), new Filter("MANN C2303",4,16)};	
	
	public Filter(String name, int id, int value){
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
