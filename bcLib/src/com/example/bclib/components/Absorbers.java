package com.example.bclib.components;

public class Absorbers {
	private String name;
	private int id;
	private double value;

	public static Absorbers[] absorbers = { new Absorbers("Forester", 0, 0.8),
			new Absorbers("Legacy", 1, 1), new Absorbers("Svx", 2, 1.2),
			new Absorbers("Impreza", 3, 1.4) };

	public Absorbers(String name, int id, double value) {
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
