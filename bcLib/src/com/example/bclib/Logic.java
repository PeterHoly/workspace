package com.example.bclib;

import java.io.IOException;


public class Logic {
	
	double angle2 = 0;
	boolean b = true;
	int idIterace = 1;
	
	public void increaseValue(Game myGame, Client client){
					
			//cekani na pripojeni hracu
			while(b){
			
				int countPlayers = client.getCountPlayers();
				if(countPlayers > 1)
				{
					myGame.createCars(countPlayers-1);
					b = false;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//aktualizace pozice a uhlu
			if(myGame.getMap().car.setPositionAndAngle(angle2)){
				this.angle2 = 0;
			}
			
			//aktualizace displeje
			myGame.getDisplay().update(myGame.getMap().car.getIncrementY());
			
			//test kolizi
			double col = Collision.TestCollision(myGame.getMap(),myGame.getDisplay(),myGame.getMap().car);
			if(col != -1){
				this.angle2 = col;
			}
			
			//poslani vlastní pozice
			client.setPos(myGame.getMap().car);
			
			//poslani id iterace
			client.setIter(idIterace);
			
			//synchronizace iteraci
			/*int serverIDiter;
			while(true){
				
				serverIDiter = client.getIter();
				if(idIterace == serverIDiter){
					break;
				}
				else{
					try {
						System.out.print("Cekam na signal! ");
						System.out.print(idIterace);
						System.out.print(", ");
						System.out.println(serverIDiter);
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}*/
			
			//nastaveni pozic vsem autum
			myGame.getMap().setAllCars(myGame, client);
		
		idIterace++;
	}
	
	public void increaseValue2(Game myGame, Client client){
		double y = myGame.getMap().cars.get(myGame.getIDplayer()).getY();
		
		myGame.getMap().setAllCars(myGame, client);
		
		y = myGame.getMap().cars.get(myGame.getIDplayer()).getY() - y;
		
		myGame.getDisplay().update(y);
	}
}
