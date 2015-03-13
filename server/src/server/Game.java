package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.bclib.Collision;
import com.example.bclib.Display;
import com.example.bclib.Map;
import com.example.bclib.Obstacle;
import com.example.bclib.Vector;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Nitro;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int ID;
	private int IDiterace;
	private int countPlayers;
	private Map map;
	private double centerAngle = 1.570796327;
	private String mapImgName;
	
	public Game(int id){
		this.ID=id;
		this.IDiterace=0;
		this.map = new Map();
	}
	
	public void run(){
		while(true){
			synchronized (players) {
				
				for(Player p : players){
					//aktualizace pozice a uhlu
					if(p.myCar.setPositionAndAngle(p.myCar.getAngle2())){
						p.myCar.setAngle2(0);
					}
								
					//aktualizace displeje
					p.display.update(p.myCar.getIncrementY());
					
					//test kolizi mezi autem a prekazkou
					double col = Collision.TestCollision(map, p.display, p.myCar);
					/*
					if(col != -1){
						p.myCar.setAngle2(col);
						p.myCar.reductionHP();
					}
					*/
					if(col != -1){
						p.settingAnle = (col-centerAngle)/5;
						p.pomAngle = centerAngle;
						p.iSA = 5;

						p.myCar.reductionHP();
					}
					if(p.iSA <= 5 && p.iSA > 0){
						p.pomAngle += p.settingAnle;
						p.myCar.setAngle2(p.pomAngle);
						
						p.iSA--;
					}
					
					if(p.nitrous && p.nitroIsUsed){
						if(p.i==200){
							p.myCar.getTrajectory().setYwithComponent(1/Nitro.nitrous[p.nitroIndex].getValue());
							p.i=0;
							p.nitroIsUsed = false;
							p.myCar.setnitroActived(false);
						}
						p.i++;
					}
				}
				
				//test kolizi mezi auty
				for(int i=0; i<players.size(); i++){
					for(int j=0; j<players.size(); j++){
						if(i==j) continue;
						
						if(Collision.TestCollisionBetweenCars(players.get(i).myCar, players.get(j).myCar)){
							if(players.get(i).myCar.getX() > players.get(j).myCar.getX()){
								players.get(i).myCar.setX(players.get(i).myCar.getX()+5);
								players.get(j).myCar.setX(players.get(j).myCar.getX()-5);
							}
							else{
								players.get(i).myCar.setX(players.get(i).myCar.getX()-5);
								players.get(j).myCar.setX(players.get(j).myCar.getX()+5);
							}
							
							double angle = players.get(i).myCar.getAngle();
							players.get(i).myCar.setAngle(players.get(j).myCar.getAngle());
							players.get(j).myCar.setAngle(angle);
							
							players.get(i).myCar.reductionHP();
							players.get(j).myCar.reductionHP();
						}
					}
				}
				
			}
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setCountPlay(int c){
		countPlayers = c;
	}
	
	public int getCountPlay(){
		return countPlayers;
	}
	
	public void addPlayer(Player p){
		players.add(p);
	}
	
	public Player getPlayer(int index){
		return players.get(index);
	}
	
	public int getId(){
		return this.ID;
	}
	
	public int getCountPlayers(){
		return players.size();
	}
	
	public int getIDiterace() {
		return IDiterace;
	}
	
	public void setIDiterace(int iDiterace) {
		boolean a = true;
		for(Player p : players){
			if(p.IDiterace != iDiterace){
				a = false;
				break;
			}
		}
		if(a){
			IDiterace = iDiterace;
		}	
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public String getMapImgName(){
		return mapImgName;
	}
	
	public void setMapImgName(String mapImgName){
		this.mapImgName = mapImgName;
	}
	
	public void setMapObstacleAndStart(String map) {
		
		this.map.obstacles.add(new Obstacle(15, 30, 40, 170));
		this.map.obstacles.add(new Obstacle(215, 30, 240, 170));
		/*
		String  size = map.split("/")[0];
		String startLines = map.split("/")[1];
		String obstacles = map.split("/")[2];
		
		String addRow = size.split(",")[0];
		String width = size.split(",")[1];
		String height = size.split(",")[2];
		
		String start = startLines.split("=")[0];
		String cil = startLines.split("=")[1];
		
		this.map.startObs = new Obstacle(Double.valueOf(start.split(",")[0]), Double.valueOf(start.split(",")[1]), Double.valueOf(start.split(",")[2]), Double.valueOf(start.split(",")[3]));
		this.map.cilObs = new Obstacle(Double.valueOf(cil.split(",")[0]), Double.valueOf(cil.split(",")[1]), Double.valueOf(cil.split(",")[2]), Double.valueOf(cil.split(",")[3]));
		
		for(String o : obstacles.split("=")){
			Obstacle obs = new Obstacle(Double.valueOf(o.split(",")[0]), Double.valueOf(o.split(",")[1]), Double.valueOf(o.split(",")[2]), Double.valueOf(o.split(",")[3]));
			obs.setAngle(Double.valueOf(o.split(",")[4]));
			this.map.obstacles.add(obs);
		}
		
		Collections.sort(this.map.obstacles, new Comparator<Obstacle>() {
	        @Override public int compare(Obstacle o1, Obstacle o2) {
	            return (int)o1.getY() - (int)o2.getY();
	        }
	    });
	    */
	}
}
