package com.example.bclib.components;

public class Engine {
	private String name;
	private int id;
	private double value;

	public static Engine[] engines = { new Engine("EJ22", 0, 0.8),
			new Engine("EJ22Turbo", 1, 1), new Engine("EJ23", 2, 1.2),
			new Engine("EJ23Turbo", 3, 1.4) };

	public Engine(String name, int id, double value) {
		this.name = name;
		this.id = id;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
