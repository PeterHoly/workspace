package com.example.bclib;


public class MovableObject extends MapObject {

	protected Trajectory trajectory;
	
	private double incrementY;
	private double increment;
	private double stop;
	private double limit;
	
	public MovableObject(double x, double y, double width, double height) {
		super(x, y, width, height);
		
		this.trajectory = new Trajectory();
		this.increment = 0;
		this.stop = 0;
		this.limit = 0.79f;
	}
	
	public void setNewPosition(){
		
		this.x += trajectory.getX(this.angle);
		this.incrementY = trajectory.getY(this.angle);
		this.y += incrementY;
	}
		
	public void setNewPosition(double angle){
		double a = Math.abs(angle);
		
		if(a < (Math.PI/2)+this.limit && a > (Math.PI/2)-this.limit){
			this.angle = angle;
		}
		else{
			if(a > (Math.PI/2)+this.limit){
				this.angle = ((Math.PI/2)+this.limit)-1;
			}
			else{
				this.angle = ((Math.PI/2)-this.limit)+1;
			}
		}
		
		this.x += trajectory.getX(this.angle);
		this.incrementY = trajectory.getY(this.angle);
		this.y += incrementY;
	}
	
	public double getIncrementY(){
		return incrementY;
	}
	
	public void setIncrement(double increment, double stop){
		if(stop == 0 && this.increment > 0){
			this.increment = increment * -1;
		}
		else{
			this.increment = increment;
		}
		this.stop = stop;
	}
	
	public void setNewAngle(){
		if(this.stop == 0 && this.angle != Math.PI/2){
			if(this.angle > Math.PI/2 && this.increment > 0 || this.angle < Math.PI/2 && this.increment < 0)
			{
				this.increment *= -1;
			}
			this.angle += this.increment;
			if(Math.abs(this.angle - Math.PI/2) < Math.abs(increment)){
				this.increment = 0;
				this.angle = Math.PI/2;
			}
		}
		else if(this.angle + this.increment <= ((Math.PI/2)+this.stop) && this.angle + this.increment >= ((Math.PI/2)-this.stop) && this.stop != 0){
			this.angle += this.increment;
		}
	}
	
	public Trajectory getTrajectory(){
		return this.trajectory;
	}
}
