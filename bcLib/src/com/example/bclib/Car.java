package com.example.bclib;

public class Car extends Vehicle {

	private double HP;
	
	public Car(double x, double y, double width, double height) {
		super(x , y, width, height);
		angle = Math.PI/2;
		HP = 3;
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
	
}
