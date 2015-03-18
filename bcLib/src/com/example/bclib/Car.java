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

	private int hp;
	private int win;
	private double angle2;
	private int crashImg;
	private boolean nitroActived = false;
	
	//celkova rychlost = y
	private Engine engine = Engine.engines[0];
	private Exhaust exhaust = Exhaust.exhausts[0];
	
	//ovladani = x
	private Absorbers absorbers = Absorbers.absorbers[0];
	private Wheel wheel = Wheel.wheels[0];
	
	//zrychleni ze startu po urcitou dobu
	private Filter filter = Filter.filters[0];
	
	//okamzite zrychleni po urcitou dobu
	private Nitro nitro = Nitro.nitrous[0];
	
	//vzhled auta
	private Bodywork bodywork = Bodywork.bodyworks[0];
	private Glass glass = Glass.glasses[0];
	
	public Car(double x, double y, double width, double height) {
		super(x , y, width, height);
		angle = Math.PI/2;
		hp = 10;
		angle2 = 0;
		win = -1;
	}
	
	public void reductionHP(){
		if(this.hp > 0){
			this.hp -= 1;
		}
		
		if(this.hp == 0){
			this.trajectory.stop();
		}
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
	
	public void setTrajectory() {
		this.trajectory.setXwithComponent((engine.getValue()+exhaust.getValue())/2);
		this.trajectory.setYwithComponent((absorbers.getValue()+wheel.getValue())/2);
	}
	
	public int getHp(){
		return this.hp;
	}
	
	public void setHp(int hp){
		this.hp = hp;
	}
	
	public int getWin(){
		return this.win;
	}
	
	public void setWin(int win){
		this.win = win;
	}
	
	int number;
	public int getCrashImg(int crashCar){
		
		if(crashCar%2 == 0){
		
			if(this.crashImg == 7){
				number = -1;
				return number;
			}
			
			number = this.crashImg;

			this.crashImg++;
			return number;
		}
		else{
			return number;
		}
	}
	
	public boolean getnitroActived(){
		return this.nitroActived;
	}
	
	public void setnitroActived(boolean actived){
		this.nitroActived = actived;
	}
}
