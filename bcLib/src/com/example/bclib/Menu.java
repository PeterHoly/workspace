package com.example.bclib;

import java.io.IOException;

public class Menu {
	public Menu(Client client){
		try {
			
			System.out.println("Choose one: 1)Create 2)GetGames 3)Join");
			char z = (char) System.in.read();
			System.in.read();
			
			switch (z) {
			case '1':{
				client.dos.writeUTF("create");
				break;}
			case '2':{
				client.dos.writeUTF("getGames");
				client.games = client.dis.readUTF();
				System.out.println(client.games);
				
				break;}
			case '3':{
				System.out.println("Choose id game!");
				char u = (char) System.in.read();
				System.in.read();
				
				client.dos.writeUTF("join");
				client.dos.writeInt(Character.getNumericValue(u));
				
				break;}

			default:
				break;
			}

			client.dos.flush();
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
