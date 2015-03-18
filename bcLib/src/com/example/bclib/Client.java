package com.example.bclib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Glass;

public class Client {
	private String ip;
	private int port;
	
	private Socket s = null;
	private OutputStream os = null;
	private InputStream is = null;
	private DataOutputStream dos = null;
	private DataInputStream dis  = null;
	
	private String games;
	private String maps;
	
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public void closeSocket() {
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return s;
	}
	
	private void initSocket() {
		if(s == null) {
			try {
				s = new Socket(ip, port);
				
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
	
	public void syncStart(){
		try {
			dis.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void getPosesAndHp(List <Car> cars){
		try {
			os.write(CommandClass.cmdGetPoses);
			dos.flush();
			
			for(Car car : cars){
				
				if(dis.readBoolean()) continue;
				
				double positionX = dis.readDouble();
				double positionY = dis.readDouble();
				double angle = dis.readDouble();
				int hp = dis.readInt();
				boolean nitroActived = dis.readBoolean();
				int win = dis.readInt();
				
				car.setX(positionX);
				car.setY(positionY);
				car.setAngle(angle);
				car.setHp(hp);
				car.setnitroActived(nitroActived);
				car.setWin(win);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getImgs(List <Car> cars){
		try {
			os.write(CommandClass.cmdGetImgs);
			dos.flush();
			
			for(Car car : cars){
				int bodyworkIndex = dis.readInt();
				int glassIndex = dis.readInt();
				
				car.setBodywork(Bodywork.bodyworks[bodyworkIndex]);
				car.setGlass(Glass.glasses[glassIndex]);
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
	
	public void createGame(String m, Display display, int countPlay, Game game, int indexBodywork, int indexGlass, double ySpeed, double xSpeed, int nitro, int filter){
		initSocket();
		
		try {
			os.write(CommandClass.cmdCreate);
			dos.writeUTF(m);
			dos.writeDouble(display.getWidth());
			dos.writeDouble(display.getHeight());
			dos.writeInt(indexBodywork);
			dos.writeInt(indexGlass);
			dos.writeInt(countPlay);
			dos.writeDouble(ySpeed);
			dos.writeDouble(xSpeed);
			dos.writeInt(nitro);
			dos.writeInt(filter);
			dos.flush();
			game.setIDplayer(dis.readInt());
			double cilY = dis.readDouble();
			game.getMap().setCilObs(new Obstacle(0, cilY, 0, cilY));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getGames(){
		try {
			Socket s = new Socket(ip, port);
			OutputStream os = s.getOutputStream();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			os.write(CommandClass.cmdGetGame);
			os.flush();
			this.games = dis.readUTF();
			s.close();
		} catch (IOException e) {
			return "";
		}
		return this.games;
	}
	
	public int joinGame(int u, Display display, Game game, int indexBodywork, int indexGlass, double ySpeed, double xSpeed, int nitro, int filter){
		initSocket();
		
		try {			
			os.write(CommandClass.cmdJoin);
			dos.writeInt(u);
			dos.writeDouble(display.getWidth());
			dos.writeDouble(display.getHeight());
			dos.writeInt(indexBodywork);
			dos.writeInt(indexGlass);
			dos.writeDouble(ySpeed);
			dos.writeDouble(xSpeed);
			dos.writeInt(nitro);
			dos.writeInt(filter);
			dos.flush();
			
			game.setIDplayer(dis.readInt());
			double cilY = dis.readDouble();
			game.getMap().setCilObs(new Obstacle(0, cilY, 0, cilY));
			
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
	
	public void nitroPush(){
		try {			
			os.write(CommandClass.cmdNitroPush);
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
	
	public byte[] loadMap(String map){
		try {		
			byte[] array = null;
			Socket s = new Socket(ip, port);
			OutputStream os = s.getOutputStream();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			os.write(CommandClass.cmdLoadMap);
			dos.writeUTF(map);
			dos.flush();
			int len = dis.readInt();
			array = new byte[len];
			dis.readFully(array);
			s.close();
			return array;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMaps() {
		try {		
			Socket s = new Socket(ip, port);
			OutputStream os = s.getOutputStream();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			os.write(CommandClass.cmdGetMaps);
			os.flush();
			this.maps = dis.readUTF();
			s.close();
		} catch (IOException e) {
			return "";
		}
		return this.maps;
	}
	
	public String getMapName(int id){
		try {		
			Socket s = new Socket(ip, port);
			OutputStream os = s.getOutputStream();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			os.write(CommandClass.cmdGetMapName);
			dos.writeInt(id);
			dos.flush();
			String mapName = dis.readUTF();
			s.close();
			return mapName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String loadMapsObstacle(String map){
		try {		
			Socket s = new Socket(ip, port);
			OutputStream os = s.getOutputStream();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			os.write(CommandClass.cmdGetMapsObstacle);
			dos.writeUTF(map);
			dos.flush();
			String obs = dis.readUTF();
			s.close();
			return obs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
