package com.example.bclib;
import java.util.ArrayList;
import java.util.List;


public class Map {
	
	public List <Obstacle> obstacles;
	public List <Obstacle> visibleObstacles;
	public List <Car> cars;
	public Car car;
	
	public Map(){
		
		visibleObstacles = new ArrayList<Obstacle>();
		obstacles = new ArrayList <Obstacle>();
		cars = new ArrayList<Car>();
		
		//obstacles = 320, 430;
			
		//vkladat serazene podle Y
		//y - niz
		//y2 - vys
		
		obstacles.add(new Obstacle(15, 30, 40, 170));
		obstacles.add(new Obstacle(215, 30, 240, 170));
		
		obstacles.add(new Obstacle(40, 170, 30, 370));
		obstacles.add(new Obstacle(240, 170, 230, 370));
		
		obstacles.add(new Obstacle(30, 370, 15, 570));
		obstacles.add(new Obstacle(230, 370, 215, 570));
		
		obstacles.add(new Obstacle(15, 570, 40, 770));
		obstacles.add(new Obstacle(215, 570, 240, 770));
		
		obstacles.add(new Obstacle(40, 770, 40, 970));
		obstacles.add(new Obstacle(240, 770, 240, 970));
		
		obstacles.add(new Obstacle(40, 970, 30, 1270));
		obstacles.add(new Obstacle(240, 970, 230, 1270));
		
		obstacles.add(new Obstacle(30, 1270, 15, 1470));
		obstacles.add(new Obstacle(230, 1270, 215, 1470));
		
		obstacles.add(new Obstacle(15, 1470, 40, 1670));
		obstacles.add(new Obstacle(215, 1470, 240, 1670));

	}
	
	public int getCountCars(){
		return cars.size();
	}
	
	private List<Obstacle> getVisibleObstacle(Display display){
		
		if(obstacles.get(obstacles.size()-1).getY2() > display.getBottom()){
			visibleObstacles.clear();

			for(Obstacle obs : obstacles){
				if(obs.getY() > display.getBottom() || obs.getY2() > display.getBottom()
						|| obs.getY() < display.getTop() || obs.getY2() < display.getTop()){
					visibleObstacles.add(obs);
				}
			}
		}
		return visibleObstacles;
	}
	
	private void addAndRemoveObstacle(Display display){
		if(visibleObstacles.size() > 0){
			for(int i=0; i<visibleObstacles.size(); i++){
				if(visibleObstacles.get(i).getY2() < display.getBottom()){
					visibleObstacles.remove(i--);
				}
			}
			
			if(visibleObstacles.size() > 0){
				
				int indexLast = obstacles.indexOf(visibleObstacles.get(visibleObstacles.size()-1));
				
				for(int i = 0; i<obstacles.size()-indexLast-1; i++){
					if(obstacles.get(indexLast+1+i).getY() < display.getTop()){
						visibleObstacles.add(obstacles.get(indexLast+1+i));
					}
					else{
						break;
					}
				}	
			}
		}
	}
	
	//boolean
	public Obstacle testVisibleObstacle(Display display, Car car){

		if(visibleObstacles.size() == 0){
			visibleObstacles = getVisibleObstacle(display);
		}
		else{
			addAndRemoveObstacle(display);
		}
			
		
		if(obstacles.size() > 0){
			for(Obstacle obs : obstacles){
				obs.rotate(-car.getAngle(), car.getX(), car.getY());
				boolean test = Collision.findingsCollision(car, obs);
				obs.rotate(car.getAngle(), car.getX(), car.getY());
				if(test) {
					return obs; // return true;
				}
			}
		}
		
		//return false
		return null;
	}
	
	public void setAllCars(Game myGame, Client client){
		synchronized (client) {
			client.getPosesAndHp(myGame.getMap().cars);
		}
	}
}
