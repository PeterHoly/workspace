package com.example.bclib.components;

public class Glass {
	private String name;
	private int id;
	private String color;
	
	public static Glass[] glasses = {new Glass("Forester",1,"blue"), new Glass("Forester",2,"dark blue"), new Glass("Forester",3,"brown"), new Glass("Forester",4,"dark brown"),
		new Glass("Impreza",5,"black"), new Glass("Impreza",6,"dark black")};	
	
	public Glass(String name, int id, String color){
		this.name = name;
		this.id = id;
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
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
}
