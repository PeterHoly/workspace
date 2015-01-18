package com.example.bclib.components;

public class Glass {
	private String name;
	private String code;
	private int id;
	private String color;
	
	public static Glass[] glasses = {new Glass("Forester","Gbl",0,"black"), new Glass("Impreza","Gbr",1,"brown"), new Glass("Legacy","Gsi",2,"silver")};	
	
	public Glass(String name, String code, int id, String color){
		this.name = name;
		this.code = code;
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
