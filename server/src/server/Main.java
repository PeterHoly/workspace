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
					myGame.addPlayer(p);
					createdGame.add(myGame);
					
					System.out.println("created!");
					
					
					Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							myGame.run();
						}
					});
					
					System.out.println(myGame.getCountPlay());
					System.out.println(",");
					System.out.println(myGame.getCountPlayers());
					
					if(myGame.getCountPlay() == myGame.getCountPlayers()){
						t.start();
						t2.start();
						System.out.println("jooooooo");
					}
				}
				else if(command==CommandClass.cmdJoin){
					final Game myGame = createdGame.get(dis.readInt());
					
					double width = dis.readDouble();
					double height = dis.readDouble();
					
					final Player p = new Player(s, myGame, width, height);
					myGame.addPlayer(p);
					
					System.out.println("join!");
					
					Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							myGame.run();
						}
					});
					
					if(myGame.getCountPlay() == myGame.getCountPlayers()){
						t.start();
						t2.start();
					}
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
