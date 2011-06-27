package ca.rivalstudios.mtg.simulation;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class World {
	
	private ConcurrentHashMap<Integer, Player> players;
	private ArrayList<Bullet> bullets;
	private ArrayList<Tower> towers;
	private ArrayList<Minion> minions;
	private ArrayList<Throne> thrones;
	
	public World() {
		players = new ConcurrentHashMap<Integer, Player>();
		bullets = new ArrayList<Bullet>();
		towers = new ArrayList<Tower>();
		minions = new ArrayList<Minion>();
		thrones = new ArrayList<Throne>();
		
		reset();
	}
	
	// reset map to initial state
	private void reset() {
	}
	
	public ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}
}
