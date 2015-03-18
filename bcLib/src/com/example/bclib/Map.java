package com.example.bclib;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private List<Obstacle> obstacles;
	private List<Obstacle> visibleObstacles;
	private Obstacle startObs;
	private Obstacle cilObs;
	private List<Car> cars;

	public Map() {

		visibleObstacles = new ArrayList<Obstacle>();
		obstacles = new ArrayList<Obstacle>();
		cars = new ArrayList<Car>();
	}

	public List<Car> getCars() {
		return cars;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public Obstacle getStartObs() {
		return startObs;
	}

	public Obstacle getCilObs() {
		return cilObs;
	}

	public void setStartObs(Obstacle o) {
		startObs = o;
	}

	public void setCilObs(Obstacle o) {
		cilObs = o;
	}

	public int getCountCars() {
		return cars.size();
	}

	private List<Obstacle> getVisibleObstacle(Display display) {

		if (obstacles.get(obstacles.size() - 1).getY2() > display.getBottom()) {
			visibleObstacles.clear();

			for (Obstacle obs : obstacles) {
				if (obs.getY() > display.getBottom()
						|| obs.getY2() > display.getBottom()
						|| obs.getY() < display.getTop()
						|| obs.getY2() < display.getTop()) {
					visibleObstacles.add(obs);
				}
			}
		}
		return visibleObstacles;
	}

	private void addAndRemoveObstacle(Display display) {
		if (visibleObstacles.size() > 0) {
			for (int i = 0; i < visibleObstacles.size(); i++) {
				if (visibleObstacles.get(i).getY2() < display.getBottom()) {
					visibleObstacles.remove(i--);
				}
			}

			if (visibleObstacles.size() > 0) {

				int indexLast = obstacles.indexOf(visibleObstacles
						.get(visibleObstacles.size() - 1));

				for (int i = 0; i < obstacles.size() - indexLast - 1; i++) {
					if (obstacles.get(indexLast + 1 + i).getY() < display
							.getTop()) {
						visibleObstacles.add(obstacles.get(indexLast + 1 + i));
					} else {
						break;
					}
				}
			}
		}
	}

	public Obstacle testVisibleObstacle(Display display, Car car) {

		if (visibleObstacles.size() == 0) {
			visibleObstacles = getVisibleObstacle(display);
		} else {
			addAndRemoveObstacle(display);
		}

		if (obstacles.size() > 0) {
			for (Obstacle obs : obstacles) {
				obs.rotate(-car.getAngle(), car.getX(), car.getY());
				boolean test = Collision.findingsCollision(car, obs);
				obs.rotate(car.getAngle(), car.getX(), car.getY());
				if (test) {
					return obs; // return true;
				}
			}
		}

		// return false
		return null;
	}

	public void setAllCars(Game myGame, Client client) {
		synchronized (client) {
			client.getPosesAndHp(myGame.getMap().cars);
		}
	}
}
