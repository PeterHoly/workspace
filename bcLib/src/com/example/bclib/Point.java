package com.example.bclib;

public class Point {

	private double x, y;

	public Point() {
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void rotate(double angle, double x, double y) {
		double nX = (this.x - x) * Math.cos(angle) - (this.y - y)
				* Math.sin(angle) + x;
		double nY = (this.x - x) * Math.sin(angle) + (this.y - y)
				* Math.cos(angle) + y;

		this.x = nX;
		this.y = nY;
	}
}
