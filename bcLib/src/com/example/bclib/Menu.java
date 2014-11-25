package com.example.bclib;

import java.io.IOException;

public class Menu {
	
	public int idGame;
	public Menu(Client client, Display display, Game game){
		try {
			
			System.out.println("Choose one: 1)Create 2)GetGames 3)Join");
			char z = (char) System.in.read();
			System.in.read();
			
			switch (z) {
			case '1':{
					System.out.println("Write count players.");
					int u = Character.getNumericValue(System.in.read());
					System.in.read();
					
					game.createCars(u);
				
					client.createGame(display, u);
					
					break;
				}
			case '2':{
					System.out.println(client.getGame());
					break;
				}
			case '3':{
					System.out.println("Choose id game!");
					char u = (char) System.in.read();
					System.in.read();
					
					client.joinGame(u,display);
					break;
				}
			default:
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*int increment = 0;
		int idEnemy = 0;
		try {
			System.out.println("Set increment");
			char ic = (char) System.in.read();
			System.in.read();
			increment = Character.getNumericValue(ic);
			
			System.out.println("Set enemy");
			char ie = (char) System.in.read();
			System.in.read();
			idEnemy = Character.getNumericValue(ie);
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
	}
}
