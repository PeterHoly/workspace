package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.bclib.CommandClass;

public class Main {

	public static void main(String[] args) {
		boolean a = true;
		ServerSocket server = null;
		
		final ArrayList<Game> createdGame = new ArrayList<Game>();
		
		try {
			server = new ServerSocket(8096);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(a){
			
			try {
				System.out.println("waiting");
				final Socket s = server.accept();
				System.out.println("connected");
				
				OutputStream os = s.getOutputStream();
				InputStream is = s.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				final DataOutputStream dos = new DataOutputStream(os);
				
				int command = is.read();
				
				if(command==CommandClass.cmdCreate)
				{
					final Game myGame = new Game(createdGame.size());
					
					double width = dis.readDouble();
					double height = dis.readDouble();
					int bodyworkIndex = dis.readInt();
					int glassIndex = dis.readInt();
					int countPlay = dis.readInt();
					double ySpeed = dis.readDouble();
					double xSpeed = dis.readDouble();
					int nitro = dis.readInt();
					
					System.out.println("Y je: "+ySpeed);
				
					myGame.setCountPlay(countPlay);
					
					final Player p = new Player(s, myGame, width, height, bodyworkIndex, glassIndex, ySpeed, xSpeed, nitro);
					
					dos.writeInt(p.ID);
					dos.flush();
					
					myGame.addPlayer(p);
					createdGame.add(myGame);
					
					System.out.println("created!");
					
					
					final Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							while(myGame.getCountPlay() != myGame.getCountPlayers()){
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							try {
								//synchronizace startu
								dos.writeBoolean(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							t.start();
							myGame.run();
						}
					});
					
					t2.start();
				
					
				}
				else if(command==CommandClass.cmdJoin){
					final Game myGame = createdGame.get(dis.readInt());
					
					double width = dis.readDouble();
					double height = dis.readDouble();
					int bodyworkIndex = dis.readInt();
					int glassIndex = dis.readInt();
					double ySpeed = dis.readDouble();
					double xSpeed = dis.readDouble();
					int nitro = dis.readInt();
					
					System.out.println("Y je: "+ySpeed);
					
					final Player p = new Player(s, myGame, width, height, bodyworkIndex, glassIndex, ySpeed, xSpeed, nitro);
					myGame.addPlayer(p);
					
					dos.writeInt(p.ID);
					dos.writeInt(myGame.getCountPlay());
					dos.flush();
					
					System.out.println("join!");
					
					final Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							while(myGame.getCountPlay() != myGame.getCountPlayers()){
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							try {
								//synchronizace startu
								dos.writeBoolean(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							t.start();
						}
					});
					
					t2.start();
				}
				else if(command==CommandClass.cmdGetGame){
					dos.writeUTF(getIdGames(createdGame));
					dos.flush();
					s.close();
				}
				
				if(command==CommandClass.cmdExit){
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getIdGames(ArrayList<Game> games){
		String idGames = "";
		for(Game g : games){
			idGames += g.getId()+",";
		}
		return idGames;
	}

}
