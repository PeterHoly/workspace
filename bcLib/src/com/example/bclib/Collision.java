package com.example.bclib;


public class Collision {
		
	public static boolean findingsCollision(Car car, Obstacle obs){
		
		double left = car.getLeft();
		double right = car.getRight();
		double top = car.getTop();
		double bottom = car.getBottom();
		
		final int CORNER_LT = 1;
		final int CORNER_RT = 2; 
		final int CORNER_LB = 4; 
		final int CORNER_RB = 8;
		
		int RESULT_P = 0;
		int RESULT_N = 0;
		
		if(obs.getFsegment(left, top) > 0){
			RESULT_P = RESULT_P | CORNER_LT;
		}
		else if(obs.getFsegment(left, top) < 0){
			RESULT_N = RESULT_N | CORNER_LT;
		}
		
		if(obs.getFsegment(right, top) > 0){
			RESULT_P = RESULT_P | CORNER_RT;
		}
		else if(obs.getFsegment(right, top) < 0){
			RESULT_N = RESULT_N | CORNER_RT;
		}
		
		if(obs.getFsegment(left, bottom) > 0){
			RESULT_P = RESULT_P | CORNER_LB;
		}
		else if(obs.getFsegment(left, bottom) < 0){
			RESULT_N = RESULT_N | CORNER_LB;
		}
		
		if(obs.getFsegment(right, bottom) > 0){
			RESULT_P = RESULT_P | CORNER_RB;
		}
		else if(obs.getFsegment(right, bottom) < 0){
			RESULT_N = RESULT_N | CORNER_RB;
		}
		
		
		if(RESULT_P == 15 || RESULT_N == 15){
			return false;
		}
		else{
			if(obs.getX() > right && obs.getX2() > right){
				return false;
			}
			if(obs.getX() < left && obs.getX2() < left){
				return false;
			}
			if(obs.getY() > top && obs.getY2() > top){
				return false;
			}
			if(obs.getY() < bottom && obs.getY2() < bottom){
				return false;
			}
			
			return true;
		}
	}
}
