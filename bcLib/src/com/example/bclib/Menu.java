package com.example.bclib;

import java.io.IOException;

public class Menu {
	
	public int idGame;
	
	public Menu(Client client, Display display, Game game, char device){
		if(device == 'd'){
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
					
						client.createGame(display, u, game);
						
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
						
						game.createCars(client.joinGame(u, display, game));
						
						break;
					}
				default:
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			game.createCars(2);
			
			client.createGame(display, 2, game);
			
		}
	}
}
