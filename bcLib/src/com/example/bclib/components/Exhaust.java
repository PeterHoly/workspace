package com.example.bclib.components;

public class Exhaust {
	private String name;
	private int id;
	private double value;

	public static Exhaust[] exhausts = { new Exhaust("Izawit 004", 0, 0.8),
			new Exhaust("Izawit 006", 1, 1), new Exhaust("Izawit 008", 2, 1.2),
			new Exhaust("Izawit 010", 3, 1.4) };

	public Exhaust(String name, int id, double value) {
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
