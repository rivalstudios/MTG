package ca.rivalstudios.mtg.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class World {
	
	private ConcurrentHashMap<Integer, Player> players;
	private ArrayList<Bullet> bullets;
	private ArrayList<Tower> towers;
	private ArrayList<Minion> minions;
	private ArrayList<Throne> thrones;
	
	private int id = 0;
	
	public World(int id) {
		players = new ConcurrentHashMap<Integer, Player>();
		bullets = new ArrayList<Bullet>();
		towers = new ArrayList<Tower>();
		minions = new ArrayList<Minion>();
		thrones = new ArrayList<Throne>();
		
		this.id = id;
		
		reset();
	}
	
	// reset map to initial state
	private void reset() {
	}
	
	public ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public ArrayList<Tower> getTowers() {
		return towers;
	}
	
	public ArrayList<Minion> getMinions() {
		return minions;
	}
	
	public ArrayList<Throne> getThrones() {
		return thrones;
	}
	
	public int getGameID() {
		return id;
	}
}
