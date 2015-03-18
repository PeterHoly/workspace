package com.example.bclib;

public class Display {

	private double width, height;
	private double x, y;

	public Display(double x, double y, double width, double height) {
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

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public double getTop() {
		return this.height + this.y;
	}

	public double getBottom() {
		return this.y;
	}

	public double conversionY(double y) {
		return height - y + this.y;
	}

	public void update(double y) {
		this.y += y;
	}
}
