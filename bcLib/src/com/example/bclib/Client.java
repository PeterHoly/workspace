package com.example.bclib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	public void getPos(int i, Car car){
		try {
			os.write(CommandClass.cmdGetPos);
			dos.writeInt(i);
			dos.flush();
			
			double positionEnemyX = dis.readDouble();
			double positionEnemyY = dis.readDouble();
			double angleEnemy = dis.readDouble();
			
			car.setX(positionEnemyX);
			car.setY(positionEnemyY);
			car.setAngle(angleEnemy);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGame(){
		try {
			os.write(CommandClass.cmdCreate);
			os.flush();
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
	
	public void joinGame(char u){
		try {			
			os.write(CommandClass.cmdJoin);
			dos.writeInt(Character.getNumericValue(u));
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
