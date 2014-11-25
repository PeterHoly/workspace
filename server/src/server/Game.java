package server;

import java.util.ArrayList;

import com.example.bclib.Collision;
import com.example.bclib.Display;
import com.example.bclib.Map;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int ID;
	private int IDiterace;
	private int countPlayers;
	private Map map;
	
	double angle2 = 0;
	
	public Game(int id){
		this.ID=id;
		this.IDiterace=0;
		this.map = new Map();
	}
	
	public void run(){
		while(true){
			for(Player p : players){
				//aktualizace pozice a uhlu
				if(p.myCar.setPositionAndAngle(angle2)){
					this.angle2 = 0;
				}
							
				//aktualizace displeje
				p.display.update(p.myCar.getIncrementY());
				
				//test kolizi
				double col = Collision.TestCollision(map, p.display, p.myCar);
				if(col != -1){
					this.angle2 = col;
				}
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
}
