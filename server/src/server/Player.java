package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
	public Socket socket;
	public Game game;
	public int position;
	
	public Player(Socket s, Game g){
		socket = s;
		game = g;
	}
	
	public void run(){
		
		while(!socket.isClosed()){
			
			String command = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				command = dis.readUTF();
			
				if(command.equals("setPos"))
				{
					position = dis.readInt();
				}
				else if(command.equals("getPos"))
				{
					int j = dis.readInt();
					dos.writeInt(game.getPlayer(j).position);
				}
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
	
