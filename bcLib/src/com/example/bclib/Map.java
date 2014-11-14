package com.example.bclib;
import java.util.ArrayList;
import java.util.List;


public class Map {
	
	public List <Obstacle> obstacles;
	public List <Obstacle> visibleObstacles;
	public List <Car> cars;
	public Car car;
	private Display display;
	
	public Map(Display display){
		
		this.display = display;
		
		visibleObstacles = new ArrayList<Obstacle>();
		obstacles = new ArrayList <Obstacle>();
		cars = new ArrayList<Car>();
		
		car = new Car(150, 100, 40, 20);		
		
		//obstacles = 320, 430;
			
		//vkladat serazene podle Y
		//y - niz
		//y2 - vys
		
		obstacles.add(new Obstacle(15, 30, 40, 170, 1));
		obstacles.add(new Obstacle(215, 30, 240, 170, 1));
		
		obstacles.add(new Obstacle(40, 170, 30, 370, 2));
		obstacles.add(new Obstacle(240, 170, 230, 370, 2));
		
		obstacles.add(new Obstacle(30, 370, 15, 570, 3));
		obstacles.add(new Obstacle(230, 370, 215, 570, 3));
		
		obstacles.add(new Obstacle(15, 570, 40, 770, 4));
		obstacles.add(new Obstacle(215, 570, 240, 770, 4));

	}
	
	private List<Obstacle> getVisibleObstacle(){
		
		if(obstacles.get(obstacles.size()-1).getY2() > this.display.getBottom()){
			visibleObstacles.clear();

			for(Obstacle obs : obstacles){
				if(obs.getY() > this.display.getBottom() || obs.getY2() > this.display.getBottom()
						|| obs.getY() < this.display.getTop() || obs.getY2() < this.display.getTop()){
					visibleObstacles.add(obs);
				}
			}
		}
		return visibleObstacles;
	}
	
	private void addAndRemoveObstacle(){
		if(visibleObstacles.size() > 0){
			for(int i=0; i<visibleObstacles.size(); i++){
				if(visibleObstacles.get(i).getY2() < this.display.getBottom()){
					visibleObstacles.remove(i--);
				}
			}
			
			if(visibleObstacles.size() > 0){
				
				int indexLast = obstacles.indexOf(visibleObstacles.get(visibleObstacles.size()-1));
				
				for(int i = 0; i<obstacles.size()-indexLast-1; i++){
					if(obstacles.get(indexLast+1+i).getY() < this.display.getTop()){
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
	public Obstacle testVisibleObstacle(){

		if(visibleObstacles.size() == 0){
			visibleObstacles = getVisibleObstacle();
		}
		else{
			addAndRemoveObstacle();
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
}
