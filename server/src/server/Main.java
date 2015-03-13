package server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.bclib.CommandClass;

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
				
				OutputStream os = s.getOutputStream();
				InputStream is = s.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				final DataOutputStream dos = new DataOutputStream(os);
				
				int command = is.read();
				
				if(command==CommandClass.cmdCreate)
				{
					final Game myGame = new Game(createdGame.size());
					
					String mapImgName = dis.readUTF();
					double width = dis.readDouble();
					double height = dis.readDouble();
					int bodyworkIndex = dis.readInt();
					int glassIndex = dis.readInt();
					int countPlay = dis.readInt();
					double ySpeed = dis.readDouble();
					double xSpeed = dis.readDouble();
					int nitro = dis.readInt();
					int filter = dis.readInt();
					
					myGame.setMapImgName(mapImgName);
					myGame.setCountPlay(countPlay);
					myGame.setMapObstacleAndStart(mapImgName);
					
					final Player p = new Player(s, myGame, width, height, bodyworkIndex, glassIndex, ySpeed, xSpeed, nitro, filter, myGame.getCountPlayers());
					
					dos.writeInt(p.ID);
					dos.flush();
					
					myGame.addPlayer(p);
					createdGame.add(myGame);
					
					System.out.println("created!");
					
					
					final Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							while(myGame.getCountPlay() != myGame.getCountPlayers()){
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							try {
								//synchronizace startu
								dos.writeBoolean(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							t.start();
							myGame.run();
						}
					});
					
					t2.start();
				
					
				}
				else if(command==CommandClass.cmdJoin){
					final Game myGame = createdGame.get(dis.readInt());
					
					double width = dis.readDouble();
					double height = dis.readDouble();
					int bodyworkIndex = dis.readInt();
					int glassIndex = dis.readInt();
					double ySpeed = dis.readDouble();
					double xSpeed = dis.readDouble();
					int nitro = dis.readInt();
					int filter = dis.readInt();
					
					final Player p = new Player(s, myGame, width, height, bodyworkIndex, glassIndex, ySpeed, xSpeed, nitro, filter, myGame.getCountPlayers());
					myGame.addPlayer(p);
					
					dos.writeInt(p.ID);
					dos.writeInt(myGame.getCountPlay());
					dos.flush();
					
					System.out.println("join!");
					
					final Thread t = new Thread (new Runnable() {
						@Override
						public void run() {
							p.run();
						}
					});
					
					Thread t2 = new Thread (new Runnable() {
						@Override
						public void run() {
							while(myGame.getCountPlay() != myGame.getCountPlayers()){
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							try {
								//synchronizace startu
								dos.writeBoolean(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							t.start();
						}
					});
					
					t2.start();
				}
				else if(command==CommandClass.cmdGetGame){
					dos.writeUTF(getIdGames(createdGame));
					dos.flush();
					s.close();
				}
				else if(command==CommandClass.cmdLoadMap){
					String m = dis.readUTF();
					byte[] map = loadMap(m);
					dos.writeInt(map.length);
					dos.write(map);
					dos.flush();
				}
				else if(command==CommandClass.cmdGetMaps){
					dos.writeUTF(getMaps());
					dos.flush();
				}
				else if(command==CommandClass.cmdGetMapsObstacle){
					String m = dis.readUTF();
					dos.writeUTF(loadObstacles(m));
					dos.flush();
				}
				else if(command==CommandClass.cmdGetMapName){
					dos.writeUTF(createdGame.get(dis.readInt()).getMapImgName());
					dos.flush();
				}
				
				if(command==CommandClass.cmdExit){
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
	
	public static byte[] loadMap(String mapName){
		try {
			byte[] imageInByte;
			BufferedImage bi = ImageIO.read(Main.class.getResourceAsStream("../maps/"+mapName+".png"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static String getMaps(){
		String name = "";
		File dir = new File("src/maps/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if(child.getName().endsWith(".png")){
		        	name += child.getName().subSequence(0, child.getName().length()-4)+",";
		        }
			}
		}
		return name;
	}
	
	public static String loadObstacles(String xmlName){
		String xmlMap = "";
		try {

			File fXmlFile = new File("src/maps/"+xmlName+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			xmlMap += doc.getDocumentElement().getAttribute("addRow")+",";
			xmlMap += doc.getDocumentElement().getAttribute("width")+",";
			xmlMap += doc.getDocumentElement().getAttribute("height");

			NodeList nList = doc.getDocumentElement().getChildNodes();
			
			NodeList nodeList1 = nList.item(0).getChildNodes();
			xmlMap += "/";
			for (int temp = 0; temp < nodeList1.getLength(); temp++) {
				Element eElement = (Element) nodeList1.item(temp);
				xmlMap += eElement.getAttribute("x1")+",";
				xmlMap += eElement.getAttribute("y1")+",";
				xmlMap += eElement.getAttribute("x2")+",";
				xmlMap += eElement.getAttribute("y1")+"=";
			}
			
			NodeList nodeList2 = nList.item(1).getChildNodes();
			xmlMap += "/";
			for (int temp = 0; temp < nodeList2.getLength(); temp++) {
				Element eElement = (Element) nodeList2.item(temp);
				xmlMap += eElement.getAttribute("x1")+",";
				xmlMap += eElement.getAttribute("y1")+",";
				xmlMap += eElement.getAttribute("x1")+",";
				xmlMap += eElement.getAttribute("y2")+",";
				xmlMap += eElement.getAttribute("angle")+"=";
			}
		}
		catch (Exception e) {
		   	e.printStackTrace();
		}
		return xmlMap;
	}
}
