package com.example.bclib;

public class Logic {
	
	public void increaseValue(Game myGame, Client client){
		
		double y = myGame.getMap().getCars().get(myGame.getIDplayer()).getY();
		
		myGame.getMap().setAllCars(myGame, client);
		
		if(myGame.getMap().getCars().get(myGame.getIDplayer()).getY() < myGame.getMap().getCilObs().getY()){
			y = myGame.getMap().getCars().get(myGame.getIDplayer()).getY() - y;
			myGame.getDisplay().update(y);
		}
	}
}
