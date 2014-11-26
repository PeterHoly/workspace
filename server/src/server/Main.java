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
				DataOutputStream dos = new DataOutputStream(os);
				
				int command = is.read();
				
				if(command==CommandClass.cmdCreate)
				{
					final Game myGame = new Game(createdGame.size());
					
					double width = dis.readDouble();
					double height = dis.readDouble();
					int countPlay = dis.readInt();
				
					myGame.setCountPlay(countPlay);
					
					final Player p = new Player(s, myGame, width, height);
					
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
					
					final Player p = new Player(s, myGame, width, height);
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
