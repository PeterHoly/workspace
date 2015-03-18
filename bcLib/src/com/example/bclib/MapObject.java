package com.example.bclib;

public class MapObject {
	protected double x, y, width, height;
	protected double angle;

	public MapObject(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return this.width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return this.height;
	}

	public double getLeft() {
		return this.x - (this.width / 2);
	}

	public double getTop() {
		return this.y + (this.height / 2);
	}

	public double getRight() {
		return this.x + (this.width / 2);
	}

	public double getBottom() {
		return this.y - (this.height / 2);
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setX(double x) {
		this.x = x;
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
