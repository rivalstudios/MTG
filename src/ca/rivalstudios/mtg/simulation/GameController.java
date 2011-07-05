package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;

/**
 * Game thread controller responsible for processing all timed game events
 * 
 * @author Melvin
 * @version 1.0
 */
public class GameController extends Thread {
	
	MTGExtension extension;
	
	public GameController(MTGExtension extension) {
		this.extension = extension;
	}
	
	public void run() {
		// Get all current games
		for (Enumeration<World> e = extension.getGames().elements(); e.hasMoreElements(); ) {
			World currWorld = (World) e.nextElement();
			
			UpdateBullets();
			UpdatePlayers();
			UpdateTurrets();
			UpdateMinions();
			UpdateThrones(); // check for end game conditions
			
			// Get all players for the current game
			for (Enumeration<Player> p = currWorld.getPlayers().elements(); p.hasMoreElements(); ) {
				Player currPlayer = (Player)p.nextElement();
				
				// If the player is currently moving, then translate the player
				if (currPlayer.isMoving()) {
					currPlayer.move();
				}
			}
		}
		
		try {
			Thread.sleep(Constants.SLEEP_DURATION);
		} catch (Exception e) {
			extension.trace(e);
		}
	}

}