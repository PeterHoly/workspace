package com.example.bclib.components;

public class Wheel {
	private String name;
	private int id;
	private double value;

	public static Wheel[] wheels = { new Wheel("Platin", 0, 0.8),
			new Wheel("Gepard", 1, 1), new Wheel("Ronal", 2, 1.2),
			new Wheel("Enzo", 3, 1.4) };

	public Wheel(String name, int id, double value) {
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
