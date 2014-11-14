package com.example.bclib;

import java.io.IOException;


public class Logic {
	
	double angle = 0;
	double angle2 = 0;
	boolean b = true;
	int idIterace = 1;
	
	public void increaseValue(Game myGame, Client client){
		
		try {
			
			while(b){
				client.dos.writeUTF("getCountPlayers");
				client.dos.flush();
				int countPlayers = client.dis.readInt();
				if(countPlayers > 1)
				{
					myGame.createCars(countPlayers-1);
					b = false;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(this.angle2 != 0)
			{
				myGame.getMap().car.setNewPosition(this.angle2);
				this.angle2 = 0;
				this.angle = 0;
			}	
			else{
				myGame.getMap().car.setNewPosition();
			}
			
			myGame.getDisplay().update(myGame.getMap().car.getIncrementY());
			
			myGame.getMap().car.setNewAngle();
			
			Obstacle o = myGame.getMap().testVisibleObstacle();
			
			if(o != null){
				
				myGame.getMap().car.reductionHP();//snizeni HP
				
				Vector v = new Vector();
				
				Vector v1 = v.getVectorByObstacle(o);
				Vector v2 = v.getVectorByCar(myGame.getMap().car);
				
				this.angle = v1.getAngle(v2);
				
				// c3=a1b2-a2b1
				double smer = v1.getX()*v2.getY()-v1.getY()*v2.getX();
				
				if(smer < 0){
					this.angle2 = o.getAngle() + this.angle;
				}
				else{
					this.angle2 = o.getAngle() - this.angle;
				}
			}
			
			client.dos.writeUTF("setPos");
			client.dos.writeDouble(myGame.getMap().car.getX());
			client.dos.writeDouble(myGame.getMap().car.getY());
			client.dos.writeDouble(myGame.getMap().car.getAngle());
			client.dos.flush();
			
			client.dos.writeUTF("setIter");
			client.dos.writeInt(idIterace);
			client.dos.flush();
			
			int serverIDiter;
			while(true){
				client.dos.writeUTF("getIter");
				client.dos.flush();
				
				serverIDiter = client.dis.readInt();
				if(idIterace == serverIDiter){
					break;
				}
				else{
					try {
						/*System.out.print("Cekam na signal! ");
						System.out.print(idIterace);
						System.out.print(", ");
						System.out.println(serverIDiter);*/
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			for(int i=0; i<myGame.getMap().cars.size(); i++){
				client.dos.writeUTF("getPos");
				client.dos.writeInt(i);
				client.dos.flush();
				double positionEnemyX = client.dis.readDouble();
				double positionEnemyY = client.dis.readDouble();
				double angleEnemy = client.dis.readDouble();
				
				
				//testovat kolizi
				
				myGame.getMap().cars.get(i).setX(positionEnemyX);
				myGame.getMap().cars.get(i).setY(positionEnemyY);
				myGame.getMap().cars.get(i).setAngle(angleEnemy);
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		idIterace++;
	}
}
