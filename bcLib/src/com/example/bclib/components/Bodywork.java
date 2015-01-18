package com.example.bclib.components;

public class Bodywork {
	private String name;
	private String code;
	private int id;
	private int type;
	private String color;
	private double width;
	private double height;
	
	public static Bodywork[] bodyworks = {new Bodywork("Demon","t1b",0,1,"black",50 ,23.5), new Bodywork("Demon","t1g",1,1,"gray",50 ,23.5), new Bodywork("Demon","t1w",2,1,"white",50 ,23.5), new Bodywork("Shark","t2b",3,2,"blue",50 ,21.5), 
		new Bodywork("Shark","t2r",4,2,"red", 50 ,21.5), new Bodywork("Shark","t2g",5,2,"gold", 50 ,21.5), new Bodywork("Golem","t3b",6,3,"blue", 42 ,24), new Bodywork("Golem","t3w",7,3,"white", 42 ,24), new Bodywork("Golem","t3y",8,3,"yellow", 42 ,24), 
		new Bodywork("Snake","t4b",9,4,"black", 50 ,20), new Bodywork("Snake","t4g",10,4,"gold", 50 ,20), new Bodywork("Snake","t4gr",11,4,"green", 50 ,20)};	
	
	public Bodywork(String name, String code, int id, int type, String color, double width, double height){
		this.name = name;
		this.code = code;
		this.id = id;
		this.type = type;
		this.color = color;
		this.height = height;
		this.width = width;
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
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getCode() {
		return code;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
