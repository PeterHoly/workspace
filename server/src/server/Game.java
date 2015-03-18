package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.bclib.Collision;
import com.example.bclib.Map;
import com.example.bclib.Obstacle;
import com.example.bclib.components.Nitro;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int ID;
	private int IDiterace;
	private int countPlayers;
	private Map map;
	private double centerAngle = 1.570796327;
	private String mapImgName;
	private boolean isStarted = false;
	private boolean isWinner = true;

	public Game(int id) {
		this.ID = id;
		this.IDiterace = 0;
		this.map = new Map();
	}

	public boolean isOffline() {
		for (Player p : players) {
			if (!p.isOffline()) {
				return false;
			}
		}
		return true;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

	public void run() {
		setStartLineToCar();
		isStarted = true;
		while (true) {
			synchronized (players) {

				if (isOffline()) {
					break;
				}

				for (Player p : players) {

					if (p.isOffline())
						continue;

					// aktualizace pozice a uhlu
					if (p.getCar().setPositionAndAngle(p.getCar().getAngle2())) {
						p.getCar().setAngle2(0);
					}

					// rozhodovani o vitezi a porazenem
					if (p.getCar().getY() > map.getCilObs().getY() && isWinner) {
						p.getCar().setWin(0);
						isWinner = false;
					} else if (p.getCar().getY() > map.getCilObs().getY()
							|| p.getCar().getHp() == 0) {
						if (p.getCar().getWin() == -1) {
							p.getCar().setWin(1);
						}
					}

					// aktualizace displeje
					p.getDisplay().update(p.getCar().getIncrementY());

					// test kolizi mezi autem a prekazkou
					double col = Collision.TestCollision(map, p.getDisplay(),
							p.getCar());

					if (col != -1) {
						p.setSettingAngle((col - centerAngle) / 5);
						p.setPomAngle(centerAngle);
						p.setISA(5);

						p.getCar().reductionHP();
					}
					if (p.getISA() <= 5 && p.getISA() > 0) {
						p.setPomAngle(p.getPomAngle() + p.getSettingAngle());
						p.getCar().setAngle2(p.getPomAngle());

						p.setISA(p.getISA() - 1);
					}

					if (p.getNitrous() && p.getNitroIsUsed()) {
						if (p.getI() == 200) {
							p.getCar()
									.getTrajectory()
									.setYwithComponent(
											1 / Nitro.nitrous[p.getNitroIndex()]
													.getValue());
							p.setI(0);
							p.setNitroIsUsed(false);
							p.getCar().setnitroActived(false);
						}
						p.setI(p.getI() + 1);
					}
				}

				// test kolizi mezi auty
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).isOffline())
						continue;
					for (int j = 0; j < players.size(); j++) {
						if (players.get(j).isOffline())
							continue;
						if (i == j)
							continue;

						if (Collision.TestCollisionBetweenCars(players.get(i)
								.getCar(), players.get(j).getCar())) {
							if (players.get(i).getCar().getX() > players.get(j)
									.getCar().getX()) {
								players.get(i)
										.getCar()
										.setX(players.get(i).getCar().getX() + 5);
								players.get(j)
										.getCar()
										.setX(players.get(j).getCar().getX() - 5);
							} else {
								players.get(i)
										.getCar()
										.setX(players.get(i).getCar().getX() - 5);
								players.get(j)
										.getCar()
										.setX(players.get(j).getCar().getX() + 5);
							}

							double angle = players.get(i).getCar().getAngle();
							players.get(i)
									.getCar()
									.setAngle(
											players.get(j).getCar().getAngle());
							players.get(j).getCar().setAngle(angle);

							players.get(i).getCar().reductionHP();
							players.get(j).getCar().reductionHP();
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

	public void setStartLineToCar() {
		for (Player p : players) {
			p.getCar().setY(map.getStartObs().getY() - p.getCar().getWidth());
		}
	}

	public void setCountPlay(int c) {
		countPlayers = c;
	}

	public int getCountPlay() {
		return countPlayers;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}

	public int getId() {
		return this.ID;
	}

	public int getCountPlayers() {
		return players.size();
	}

	public int getIDiterace() {
		return IDiterace;
	}

	public void setIDiterace(int iDiterace) {
		boolean a = true;
		for (Player p : players) {
			if (p.getIDiterace() != iDiterace) {
				a = false;
				break;
			}
		}
		if (a) {
			IDiterace = iDiterace;
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public String getMapImgName() {
		return mapImgName;
	}

	public void setMapImgName(String mapImgName) {
		this.mapImgName = mapImgName;
	}

	public Map getMap() {
		return this.map;
	}

	public void setMapObstacleAndStart(String map) {

		String size = map.split("/")[0];
		String startLines = map.split("/")[1];
		String obstacles = map.split("/")[2];

		double addRow = Double.valueOf(size.split(",")[0]);
		double width = Double.valueOf(size.split(",")[1]);
		double height = Double.valueOf(size.split(",")[2]);

		String start = startLines.split("=")[0];
		String cil = startLines.split("=")[1];

		double displayWidthDouble = players.get(0).getDisplay().getWidth();

		double x1start = (Double.valueOf(start.split(",")[0]) / width)
				* displayWidthDouble;
		double y1start = (Double.valueOf(start.split(",")[1]) / width)
				* displayWidthDouble;
		double x2start = (Double.valueOf(start.split(",")[2]) / width)
				* displayWidthDouble;
		double y2start = (Double.valueOf(start.split(",")[3]) / width)
				* displayWidthDouble;

		this.map.setStartObs(new Obstacle(x1start, y1start, x2start, y2start));

		double x1cil = (Double.valueOf(cil.split(",")[0]) / width)
				* displayWidthDouble;
		double y1cil = (Double.valueOf(cil.split(",")[1]) / width)
				* displayWidthDouble;
		double x2cil = (Double.valueOf(cil.split(",")[2]) / width)
				* displayWidthDouble;
		double y2cil = (Double.valueOf(cil.split(",")[3]) / width)
				* displayWidthDouble;

		this.map.setCilObs(new Obstacle(x1cil, y1cil, x2cil, y2cil));

		for (String o : obstacles.split("=")) {

			double x1 = (Double.valueOf(o.split(",")[0]) / width)
					* displayWidthDouble;
			double y1 = (Double.valueOf(o.split(",")[1]) / width)
					* displayWidthDouble;
			double x2 = (Double.valueOf(o.split(",")[2]) / width)
					* displayWidthDouble;
			double y2 = (Double.valueOf(o.split(",")[3]) / width)
					* displayWidthDouble;

			double y = (y2 - y1) / 2 + y1;

			Obstacle obs = new Obstacle(x1, y2, x2, y1);
			obs.setAngle(Double.valueOf(o.split(",")[4]));
			obs.rotate(-Math.toRadians(Double.valueOf(o.split(",")[4])), x1, y);
			this.map.getObstacles().add(obs);
		}

		Collections.sort(this.map.getObstacles(), new Comparator<Obstacle>() {
			@Override
			public int compare(Obstacle o1, Obstacle o2) {
				return (int) o1.getY() - (int) o2.getY();
			}
		});
	}
}
