package com.example.bclib.components;

public class Nitro {
	private String name;
	private int id;
	private double value;

	public static Nitro[] nitrous = { new Nitro("EJ20", 0, 1.2),
			new Nitro("EJ30", 1, 1.4), new Nitro("ZEXi20", 2, 1.6),
			new Nitro("ZEXi30", 3, 1.8) };

	public Nitro(String name, int id, double value) {
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
