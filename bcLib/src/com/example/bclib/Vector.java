package com.example.bclib;

public class Vector {

	private double x, y;

	public Vector() {
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector getVectorByObstacle(Obstacle obs) {
		return new Vector(obs.getX2() - obs.getX(), obs.getY2() - obs.getY());
	}

	public Vector getVectorByCar(Car car) {
		return new Vector(car.width * Math.cos(car.angle), car.width
				* Math.sin(car.angle));
	}

	public double getAngle(Vector v) {
		return Math
				.acos((x * v.x + y * v.y)
						/ (Math.sqrt(x * x + y * y) * Math.sqrt(v.x * v.x + v.y
								* v.y)));
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}
