package com.example.bclib;


public class Trajectory {
		
	public double x,y;
	
	public Trajectory(){
		this.x = 7;
		this.y = 6;
	}
	
	public double getX(double angle){
		return this.x * Math.cos(angle);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setXwithComponent(double a) {
		this.x *= a;
	}
	
	public double getY(double angle){
		return this.y * Math.sin(angle);
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setYwithComponent(double a) {
		this.y *= a;
	}
	
}
