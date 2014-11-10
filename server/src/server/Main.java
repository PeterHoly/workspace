package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				String command = dis.readUTF();
				
				if(command.equals("create"))
				{
					Game myGame = new Game(createdGame.size());
					final Player p = new Player(s, myGame);
					myGame.addPlayer(p);
					createdGame.add(myGame);
					System.out.println("created!");
					
					Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					t.start();
				}
				else if(command.equals("join")){
					Game myGame = createdGame.get(dis.readInt());
					final Player p = new Player(s, myGame);
					myGame.addPlayer(p);
					System.out.println("join!");
					
					Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					t.start();
				}
				else if(command.equals("getGames")){
					dos.writeUTF(getIdGames(createdGame));
					dos.flush();
					s.close();
				}
				
				if(command.equals("exit")){
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
