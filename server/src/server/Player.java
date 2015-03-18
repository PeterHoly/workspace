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
	private Socket socket;
	private Game game;
	private int ID;
	private int IDiterace;
	private int bodyworkIndex;
	private int glassIndex;
	private int nitroIndex;
	private boolean nitrous = false;
	private boolean nitroIsUsed = true;
	private int i=0;
	private double settingAnle;
	private double pomAngle;
	private int iSA=0;
	private boolean isOffline=false;
	
	private Car myCar;
	private Display display;
	
	public Player(Socket s, Game g, double width, double height, int indexBodywork, int indexGlass, double ySpeed, double xSpeed, int nitro, int filter, int order){
		socket = s;
		game = g;
		ID = g.getCountPlayers();
		myCar = new Car(100+50*order, 0, Bodywork.bodyworks[indexBodywork].getWidth(), Bodywork.bodyworks[indexGlass].getHeight());
		myCar.getTrajectory().setXwithComponent(xSpeed);
		myCar.getTrajectory().setYwithComponent(ySpeed);
		myCar.getTrajectory().setFilter(Filter.filters[filter].getValue());
		display = new Display(0, 0, width, height);
		bodyworkIndex = indexBodywork;
		glassIndex = indexGlass;
		nitroIndex = nitro;
	}
	
	public boolean isOffline(){
		return isOffline;
	}
	
	public double getSettingAngle(){
		return this.settingAnle;
	}
	
	public double getPomAngle(){
		return this.pomAngle;
	}
	
	public void setSettingAngle(double a){
		this.settingAnle = a;
	}
	
	public void setPomAngle(double a){
		this.pomAngle = a;
	}
	
	public int getISA(){
		return this.iSA;
	}
	
	public void setISA(int isa){
		this.iSA = isa;
	}
	
	public int getI(){
		return this.i;
	}
	
	public void setI(int i){
		this.i = i;
	}
	
	public boolean getNitroIsUsed(){
		return this.nitroIsUsed;
	}
	
	public void setNitroIsUsed(boolean b){
		this.nitroIsUsed = b;
	}
	
	public boolean getNitrous(){
		return this.nitrous;
	}
	
	public int getNitroIndex(){
		return this.nitroIndex;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public int getIDiterace(){
		return this.IDiterace;
	}
	
	public Display getDisplay(){
		return this.display;
	}
	
	public Car getCar(){
		return this.myCar;
	}
	
	public void run(){
		
		while(true){
			
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
				
				if(command == -1){
					isOffline = true;
					break;
				}
			
				if(command==CommandClass.cmdSetPos)
				{
					myCar.setX(dis.readDouble());
					myCar.setY(dis.readDouble());
					myCar.setAngle(dis.readDouble());
				}
				else if(command == CommandClass.cmdGetPoses){
					for(Player p : game.getPlayers()){
						dos.writeBoolean(p.isOffline());
						if(p.isOffline()) continue;
						
						dos.writeDouble(p.myCar.getX());
						dos.writeDouble(p.myCar.getY());
						dos.writeDouble(p.myCar.getAngle());
						dos.writeInt(p.myCar.getHp());
						dos.writeBoolean(p.myCar.getnitroActived());
						dos.writeInt(p.myCar.getWin());
						
					}
				}
				else if(command==CommandClass.cmdGetPos)
				{
					int j = dis.readInt();
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
					if(myCar.getY() < game.getMap().getCilObs().getY()){
						myCar.setIncrement(0.09f, 0.79f);
					}
				}
				else if(command==CommandClass.cmdRightPush)
				{
					if(myCar.getY() < game.getMap().getCilObs().getY()){
						myCar.setIncrement(-0.09f, 0.79f);
					}
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
	
