package com.example.bclib.components;

public class Filter {
	private String name;
	private int id;
	private double value;
	
	public static Filter[] filters = {new Filter("MANN C2201",0,0.8), new Filter("MANN C2203",1,0.6), new Filter("MANN C2301",2,0.4), new Filter("MANN C2303",3,0.2)};	
	
	public Filter(String name, int id, double value){
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
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
}
