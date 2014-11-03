package com.example.bcpokus;

import android.graphics.Rect;

public class GameUI {
	private Rect buttonLeft;
	private Rect buttonRight;
	private String text = "";
	
	public GameUI(){
		buttonLeft = new Rect(2,370,159,428);
		buttonRight = new Rect(161,370,318,428);
		
	}
	
	public Rect getButtonLeft(){
		return buttonLeft;
	}
	
	public Rect getButtonRight(){
		return buttonRight;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
}
