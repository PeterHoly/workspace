package com.example.bclib;


public class Trajectory {
		
	private double x,y;
	
	public Trajectory(){
		this.x = 7;
		this.y = 6;
	}
	
	public double getX(double angle){
		return this.x * Math.cos(angle);
	}
	
	public double getY(double angle){
		return this.y * Math.sin(angle);
	}
	
}
