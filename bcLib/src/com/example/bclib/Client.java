package com.example.bclib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {
	private Socket s = null;
	private OutputStream os = null;
	private InputStream is = null;
	private DataOutputStream dos = null;
	private DataInputStream dis  = null;
	
	private String games;
	
	public Client(){
		
		try {
			s = new Socket(InetAddress.getLocalHost(), 8096);
			os = s.getOutputStream();
			is = s.getInputStream();
			dos = new DataOutputStream(os);
			dis = new DataInputStream(is);
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public int getCountPlayers(){
		int countPlayers = -1;
		try {
			os.write(CommandClass.cmdGetCountPlayers);
			os.flush();
			countPlayers = dis.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countPlayers;
	}
	
	public void setPos(Car car){
		try {
			os.write(CommandClass.cmdSetPos);
			dos.writeDouble(car.getX());
			dos.writeDouble(car.getY());
			dos.writeDouble(car.getAngle());
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setIter(int idIterace){
		try {
			os.write(CommandClass.cmdSetIter);
			dos.writeInt(idIterace);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getIter(){
		int serverIDiter = -1;
		try {
			os.write(CommandClass.cmdGetIter);
			os.flush();
			
			serverIDiter = dis.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverIDiter;
	}
	
	public void getPoses(List <Car> cars){
		try {
			os.write(CommandClass.cmdGetPoses);
			dos.flush();
			
			for(Car car : cars){
				double positionX = dis.readDouble();
				double positionY = dis.readDouble();
				double angle = dis.readDouble();
				
				car.setX(positionX);
				car.setY(positionY);
				car.setAngle(angle);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPos(int i, Car car){
		try {
			os.write(CommandClass.cmdGetPos);
			dos.writeInt(i);
			dos.flush();
			
			double positionX = dis.readDouble();
			double positionY = dis.readDouble();
			double angle = dis.readDouble();
			
			car.setX(positionX);
			car.setY(positionY);
			car.setAngle(angle);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGame(Display display, int countPlay, Game game){
		try {
			os.write(CommandClass.cmdCreate);
			dos.writeDouble(display.getWidth());
			dos.writeDouble(display.getHeight());
			dos.writeInt(countPlay);
			dos.flush();
			game.setIDplayer(dis.readInt());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getGame(){
		try {
			os.write(CommandClass.cmdGetGame);
			os.flush();
			this.games = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.games;
	}
	
	public int joinGame(char u, Display display, Game game){
		try {			
			os.write(CommandClass.cmdJoin);
			dos.writeInt(Character.getNumericValue(u));
			dos.writeDouble(display.getWidth());
			dos.writeDouble(display.getHeight());
			dos.flush();
			
			game.setIDplayer(dis.readInt());
			
			return dis.readInt();
			
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void leftPush(){
		try {			
			os.write(CommandClass.cmdLeftPush);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rightPush(){
		try {			
			os.write(CommandClass.cmdRightPush);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void release(){
		try {			
			os.write(CommandClass.cmdRelease);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
