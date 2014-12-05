package com.example.bclib;

import java.io.ObjectInputStream.GetField;

public class Car extends Vehicle {

	private double HP;
	private double angle2;
	
	public Car(double x, double y, double width, double height) {
		super(x , y, width, height);
		angle = Math.PI/2;
		HP = 3;
		angle2 = 0;
	}
	
	public void reductionHP(){
		this.HP -= 1;
	}
	
	public boolean setPositionAndAngle(double angle2){
		if(angle2 != 0)
		{
			this.setNewPosition(angle2);
			this.setNewAngle();
			return true;
		}	
		else{
			this.setNewPosition();
			this.setNewAngle();
			return false;
		}
	}
	
	public double getAngle2() {
		return angle2;
	}
	
	public void setAngle2(double angle2) {
		this.angle2 = angle2;
	}
	
}
