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
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Filter;
import com.example.bclib.components.Nitro;

public class Player {
	public Socket socket;
	public Game game;
	public double positionX;
	public double positionY;
	public double angle;
	public int ID;
	public int IDiterace;
	public int bodyworkIndex;
	public int glassIndex;
	public int nitroIndex;
	public boolean nitrous = false;
	public boolean nitroIsUsed = true;
	public int i=0;
	public double settingAnle;
	public double pomAngle;
	public int iSA=0;
	
	public Car myCar;
	
	public Display display;
	
	public Player(Socket s, Game g, double width, double height, int indexBodywork, int indexGlass, double ySpeed, double xSpeed, int nitro, int filter, int order){
		socket = s;
		game = g;
		ID = g.getCountPlayers();
		myCar = new Car(100+50*order, 100, Bodywork.bodyworks[indexBodywork].getWidth(), Bodywork.bodyworks[indexGlass].getHeight());
		myCar.getTrajectory().setXwithComponent(xSpeed);
		myCar.getTrajectory().setYwithComponent(ySpeed);
		myCar.getTrajectory().setFilter(Filter.filters[filter].getValue());
		display = new Display(0, 0, width, height);
		bodyworkIndex = indexBodywork;
		glassIndex = indexGlass;
		nitroIndex = nitro;
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
					
					myCar.setX(dis.readDouble());
					myCar.setY(dis.readDouble());
					myCar.setAngle(dis.readDouble());
				}
				else if(command == CommandClass.cmdGetPoses){
					for(Player p : game.getPlayers()){
						dos.writeDouble(p.myCar.getX());
						dos.writeDouble(p.myCar.getY());
						dos.writeDouble(p.myCar.getAngle());
						dos.writeInt(p.myCar.getHp());
						dos.writeBoolean(p.myCar.getnitroActived());
					}
				}
				else if(command==CommandClass.cmdGetPos)
				{
					int j = dis.readInt();
					//Player p = game.getPlayer((ID+j+1) % game.getCountPlayers());
					Player p = game.getPlayer(j);
					
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
				else if(command==CommandClass.cmdNitroPush)
				{
					if(!nitrous){
						nitrous = true;
						myCar.getTrajectory().setYwithComponent(Nitro.nitrous[nitroIndex].getValue());
						
						myCar.setnitroActived(true);
					}
				}
				else if(command==CommandClass.cmdRelease)
				{
					myCar.setIncrement(0.09f, 0f);
				}
				else if(command==CommandClass.cmdGetImgs)
				{
					for(Player p : game.getPlayers()){
						//System.out.println("get " + p.ID + ": " + p.myCar.getTrajectory().x+", "+p.myCar.getTrajectory().y);
						
						dos.writeInt(p.bodyworkIndex);
						dos.writeInt(p.glassIndex);
					}
				}

				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
	
