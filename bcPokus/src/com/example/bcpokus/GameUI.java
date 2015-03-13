package com.example.bcpokus;

import com.example.bclib.Display;

import android.graphics.Rect;

public class GameUI {
	private Rect buttonLeft;
	private Rect buttonRight;
	private Rect buttonNitro;
	private String text = "";
	private Display d;
	public String[] HP = {"HP 0","HP 1","HP 2","HP 3","HP 4","HP 5","HP 6","HP 7","HP 8","HP 9","HP 10"};
	
	public GameUI(Display d){
		
		buttonLeft = new Rect(2,(int)d.getHeight()-10,(int)d.getWidth()/2-1,(int)d.getHeight()+48);
		buttonRight = new Rect((int)d.getWidth()/2+1,(int)d.getHeight()-10,(int)d.getWidth()-2,(int)d.getHeight()+48);
		buttonNitro = new Rect((int)d.getWidth()-54,(int)d.getHeight()-175,(int)d.getWidth()-4,(int)d.getHeight()-25);
	}
	
	public Rect getButtonLeft(){
		return buttonLeft;
	}
	
	public Rect getButtonRight(){
		return buttonRight;
	}
	
	public Rect getButtonNitro(){
		return buttonNitro;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
}
