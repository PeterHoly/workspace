package server;

import java.util.ArrayList;
import java.util.List;

import com.example.bclib.Collision;
import com.example.bclib.Display;
import com.example.bclib.Map;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int ID;
	private int IDiterace;
	private int countPlayers;
	private Map map;
	
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
					
					//test kolizi
					double col = Collision.TestCollision(map, p.display, p.myCar);
					if(col != -1){
						p.myCar.setAngle2(col);
					}
				}
				
				for(int i=0; i<players.size(); i++){
					for(int j=0; j<players.size(); j++){
						if(i==j) continue;
						
						Collision.TestCollisionBetweenCars(players.get(i).myCar, players.get(j).myCar);
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
}
