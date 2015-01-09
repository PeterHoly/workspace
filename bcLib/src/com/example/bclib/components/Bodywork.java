package com.example.bclib.components;

public class Bodywork {
	private String name;
	private int id;
	private int type;
	private String color;
	
	public static Bodywork[] bodyworks = {new Bodywork("Demon",1,1,"black"), new Bodywork("Elemental",2,1,"blue"), new Bodywork("Dragon",3,1,"red"), new Bodywork("Shark",4,2,"yellow"), 
		new Bodywork("Lion",5,2,"gold"), new Bodywork("Stone",6,2,"silver"), new Bodywork("Golem",7,3,"brown"), new Bodywork("Beast",8,3,"green"), new Bodywork("Snake",9,3,"purple")};	
	
	public Bodywork(String name, int id, int type, String color){
		this.name = name;
		this.id = id;
		this.type = type;
		this.color = color;
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
}
