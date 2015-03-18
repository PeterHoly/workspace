package com.example.bclib;

public class Trajectory {
		
	private double x,y;
	private double filter;
	private double accteleration;
	private double speed;
	private int i;
	
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
		this.accteleration = getYY(angle) / (this.filter * 100);
		
		if(i < (this.filter * 100) && this.speed < getYY(angle)){
			this.speed += this.accteleration;
			i++;
			return this.speed;
		}
		else{
			double s = getYY(angle);
			return s;
		}	
	}
	
	public double getYY(double angle){
		return this.y * Math.sin(angle);
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setYwithComponent(double a) {
		this.y *= a;
	}
	
	public void setFilter(double filter){
		this.filter = filter;
	}
	
	public void stop(){
		this.x = 0;
		this.y = 0;
	}
}
