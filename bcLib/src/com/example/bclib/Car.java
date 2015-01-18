package com.example.bclib;

import com.example.bclib.components.Absorbers;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Engine;
import com.example.bclib.components.Exhaust;
import com.example.bclib.components.Filter;
import com.example.bclib.components.Glass;
import com.example.bclib.components.Nitro;
import com.example.bclib.components.Wheel;

public class Car extends Vehicle {

	private double HP;
	private double angle2;
	
	//celkova rychlost = y
	private Engine engine = Engine.engines[0];
	private Exhaust exhaust = Exhaust.exhausts[0];
	
	//ovladani = x
	private Absorbers absorbers = Absorbers.absorbers[0];
	private Wheel wheel = Wheel.wheels[0];
	
	//zrychleni ze startu po dobu 2s
	private Filter filter = Filter.filters[0];
	
	//po zapnuti zrychleni 2s, pak nelze 15s zapnout
	private Nitro nitro = Nitro.nitrous[0];
	
	//vzhled auta
	private Bodywork bodywork = Bodywork.bodyworks[0];
	private Glass glass = Glass.glasses[0];
	
	public Car(double x, double y, double width, double height) {
		super(x , y, width, height);
		angle = Math.PI/2;
		HP = 3;
		angle2 = 0;
	}
	
	public void reductionHP(){
		this.HP -= 1;
	}
	
	public boolean setPositionAndAngle(double angle2){
		if(angle2 != 0)
		{
			this.setNewPosition(angle2);
			this.setNewAngle();
			return true;
		}	
		else{
			this.setNewPosition();
			this.setNewAngle();
			return false;
		}
	}
	
	public double getAngle2() {
		return angle2;
	}
	
	public void setAngle2(double angle2) {
		this.angle2 = angle2;
	}
	
	
	public Engine getEngine() {
		return engine;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	public Absorbers getAbsorbers() {
		return absorbers;
	}
	
	public void setAbsorbers(Absorbers absorbers) {
		this.absorbers = absorbers;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	public Exhaust getExhaust() {
		return exhaust;
	}
	
	public void setExhaust(Exhaust exhaust) {
		this.exhaust = exhaust;
	}
	
	public Nitro getNitro() {
		return nitro;
	}
	
	public void setNitro(Nitro nitro) {
		this.nitro = nitro;
	}
	
	public Wheel getWheel() {
		return wheel;
	}
	
	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}
	
	public Bodywork getBodywork() {
		return bodywork;
	}
	
	public void setBodywork(Bodywork bodywork) {
		this.bodywork = bodywork;
		this.height = bodywork.getHeight();
		this.width = bodywork.getWidth();
	}
	
	public Glass getGlass() {
		return glass;
	}
	
	public void setGlass(Glass glass) {
		this.glass = glass;
	}
	
	public String getImgCode() {
		return this.bodywork.getCode()+this.glass.getCode();
	}
}
