package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
	public Socket socket;
	public Game game;
	public double positionX;
	public double positionY;
	public double angle;
	public int ID;
	public int IDiterace;
	
	public Player(Socket s, Game g){
		socket = s;
		game = g;
		ID = g.getCountPlayers();
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
					positionX = dis.readDouble();
					positionY = dis.readDouble();
					angle = dis.readDouble();
				}
				else if(command.equals("getPos"))
				{
					int j = dis.readInt();
					Player p = game.getPlayer((ID+j+1) % game.getCountPlayers());
					
					dos.writeDouble(p.positionX);
					dos.writeDouble(p.positionY);
					dos.writeDouble(p.angle);
				}
				else if(command.equals("getCountPlayers"))
				{
					dos.writeInt(game.getCountPlayers());
				}
				else if(command.equals("getIter"))
				{
					dos.writeInt(game.getIDiterace());
				}
				else if(command.equals("setIter"))
				{
					IDiterace = dis.readInt();
					game.setIDiterace(IDiterace);
				}
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
	
