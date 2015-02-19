package com.example.bclib;


public class Obstacle extends MapObject {
	
	private double x2,y2;

	public Obstacle(double x, double y, double x2, double y2) {
		super(x, y, 0, 0);
		
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public double getX2(){
		return this.x2;
	}
	
	public double getY2(){
		return this.y2;
	}
	
	public void setX2(double x2){
		this.x2 = x2;
	}
	
	public void setY2(double y2){
		this.y2 = y2;
	}
	
	@Override
	public void rotate(double angle, double x, double y){
		super.rotate(angle, x, y);
		
		double nX2 = (this.x2-x)*Math.cos(angle)-(this.y2-y)*Math.sin(angle)+x;
		double nY2 = (this.x2-x)*Math.sin(angle)+(this.y2-y)*Math.cos(angle)+y;
		
		this.x2 = nX2;
		this.y2 = nY2;
	}
	
	public double getFsegment(double xCorner, double yCorner){
		return (this.y2-this.y)*xCorner + (this.x-this.x2)*yCorner + (this.x2*this.y-this.x*this.y2);
	}
	
	@Override
	public double getAngle() {
		return Math.atan2(y2-y,x2-x);
	}
	
	public double getAngle2() {
		return angle;
	}
}
