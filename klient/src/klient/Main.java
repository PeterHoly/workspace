package klient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis  = null;
		
		String games;
		
		try {
			s = new Socket(InetAddress.getLocalHost(), 8096);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			System.out.println("Choose one: 1)Create 2)GetGames 3)Join");
			char z = (char) System.in.read();
			System.in.read();
			
			switch (z) {
			case '1':{
				dos.writeUTF("create");
				break;}
			case '2':{
				dos.writeUTF("getGames");
				games = dis.readUTF();
				System.out.println(games);
				
				break;}
			case '3':{
				System.out.println("Choose id game!");
				char u = (char) System.in.read();
				System.in.read();
				
				dos.writeUTF("join");
				dos.writeInt(Character.getNumericValue(u));
				
				break;}

			default:
				break;
			}

			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int increment = 0;
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
		}
		
		
		int position = 1;
		for(int i=0; i<60; i++){
			position += increment;
			
			try {
				dos.writeUTF("setPos");
				dos.writeInt(position);
				dos.flush();
				
				dos.writeUTF("getPos");
				dos.writeInt(idEnemy);
				dos.flush();
				int positionEnemy = dis.readInt();
				
				System.out.println("myPosition: "+position+" , enemyPosition: "+positionEnemy);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
