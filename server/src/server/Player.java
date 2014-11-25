package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.bclib.Car;
import com.example.bclib.CommandClass;
import com.example.bclib.Display;

public class Player {
	public Socket socket;
	public Game game;
	public double positionX;
	public double positionY;
	public double angle;
	public int ID;
	public int IDiterace;
	
	public Car myCar;
	
	public Display display;
	
	public Player(Socket s, Game g, double width, double height){
		socket = s;
		game = g;
		ID = g.getCountPlayers();
		myCar = new Car(150, 100, 40, 20);
		display = new Display(0, 0, width, height);
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
					/*positionX = dis.readDouble();
					positionY = dis.readDouble();
					angle = dis.readDouble();*/
					
					myCar.setX(dis.readDouble());
					myCar.setY(dis.readDouble());
					myCar.setAngle(dis.readDouble());
				}
				else if(command==CommandClass.cmdGetPos)
				{
					int j = dis.readInt();
					Player p = game.getPlayer((ID+j+1) % game.getCountPlayers());
					
					/*dos.writeDouble(p.positionX);
					dos.writeDouble(p.positionY);
					dos.writeDouble(p.angle);*/
					
					dos.writeDouble(p.myCar.getX());
					dos.writeDouble(p.myCar.getY());
					dos.writeDouble(p.myCar.getAngle());
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
				else if(command==CommandClass.cmdLeftPush)
				{
					myCar.setIncrement(0.09f, 0.79f);
				}
				else if(command==CommandClass.cmdRightPush)
				{
					myCar.setIncrement(-0.09f, 0.79f);
				}
				else if(command==CommandClass.cmdRelease)
				{
					myCar.setIncrement(0.09f, 0f);
				}

				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
	
