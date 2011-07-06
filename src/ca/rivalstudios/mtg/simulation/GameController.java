package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import ca.rivalstudios.mtg.MTGExtension;

/**
 * Game thread controller responsible for processing all game events
 * 
 * @author Melvin
 * @version 1.0
 */
public class GameController extends Thread {
	
	private MTGExtension extension;
	private TimeController time;
	
	// Determine how long the previous game state took to process
	private World currWorld; 
	
	public GameController(MTGExtension extension) {
		time = TimeController.getInstance();
		this.extension = extension;
	}
	
	public void run() {
		time.SetStartTime();
		
		// Get all current games
		for (Enumeration<World> e = extension.getGames().elements(); e.hasMoreElements(); ) {
			currWorld = (World) e.nextElement();
			
			// Update the current game state
			UpdateBullets();
			UpdatePlayers();
			UpdateTurrets();
			UpdateMinions();
		}
		
		time.SetEndTime();
	}
	
	public void UpdateBullets() {
		for (ListIterator<Bullet> b = currWorld.getBullets().listIterator(); b.hasNext(); ) {
			Bullet currBullet = (Bullet)b.next();
			
			currBullet.UpdatePosition(time.GetTimeDelta());
			currBullet.CheckCollision();
		}
	}

	public void UpdatePlayers() {
		for (Enumeration<Player> p = currWorld.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// If the player is currently moving, then translate the player
			if (currPlayer.isMoving()) {
				currPlayer.UpdatePosition(time.GetTimeDelta());
			}
			
			// attack closest nearby enemies unless we are pursuing a target
		}
	}
	
	public void UpdateTurrets() {
		for (ListIterator<Tower> t = currWorld.getTowers().listIterator(); t.hasNext(); ) {
			Tower currTower = (Tower)t.next();
			
			currTower.UpdateTarget();
		}
	}
	
	public void UpdateMinions() {
		for (ListIterator<Minion> m = currWorld.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			currMinion.UpdatePosition(time.GetTimeDelta());
			currMinion.UpdateTarget();
		}		
	}
}