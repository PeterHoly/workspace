package com.example.bclib;


public class Logic {
	
	double angle = 0;
	double angle2 = 0;
	
	public void increaseValue(Game myGame){
		
		if(this.angle2 != 0)
		{
			myGame.getMap().car.setNewPosition(this.angle2);
			this.angle2 = 0;
			this.angle = 0;
		}	
		else{
			myGame.getMap().car.setNewPosition();
		}
		
		myGame.getDisplay().update(myGame.getMap().car.getIncrementY());
		
		myGame.getMap().car.setNewAngle();
		
		Obstacle o = myGame.getMap().testVisibleObstacle();
		
		if(o != null){
			
			myGame.getMap().car.reductionHP();//snizeni HP
			
			Vector v = new Vector();
			
			Vector v1 = v.getVectorByObstacle(o);
			Vector v2 = v.getVectorByCar(myGame.getMap().car);
			
			this.angle = v1.getAngle(v2);
			
			// c3=a1b2-a2b1
			double smer = v1.getX()*v2.getY()-v1.getY()*v2.getX();
			
			if(smer < 0){
				this.angle2 = o.getAngle() + this.angle;
			}
			else{
				this.angle2 = o.getAngle() - this.angle;
			}
	
		}
	}
}
