package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.bclib.CommandClass;

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
			
			int command = -1;
			OutputStream os = null;
			InputStream is = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			
			try {
				os = socket.getOutputStream();
				is = socket.getInputStream();
				dos = new DataOutputStream(os);
				dis = new DataInputStream(is);
				command = is.read();
			
				if(command==CommandClass.cmdSetPos)
				{
					positionX = dis.readDouble();
					positionY = dis.readDouble();
					angle = dis.readDouble();
				}
				else if(command==CommandClass.cmdGetPos)
				{
					int j = dis.readInt();
					Player p = game.getPlayer((ID+j+1) % game.getCountPlayers());
					
					dos.writeDouble(p.positionX);
					dos.writeDouble(p.positionY);
					dos.writeDouble(p.angle);
				}
				else if(command==CommandClass.cmdGetCountPlayers)
				{
					dos.writeInt(game.getCountPlayers());
				}
				else if(command==CommandClass.cmdGetIter)
				{
					dos.writeInt(game.getIDiterace());
				}
				else if(command==CommandClass.cmdSetIter)
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
	
