package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;

/**
 * Game thread controller responsible for processing all game events
 * 
 * @author Melvin Parinas
 * @version 1.0
 */
public class GameController extends Thread {
	
	private MTGExtension extension;
	private TimeController time;
	private boolean timeEventsRunning = true;
	
	// Determine how long the previous game state took to process
	private World currWorld; 
	
	public GameController(MTGExtension extension) {
		time = TimeController.getInstance();
		this.extension = extension;
		
		extension.trace("GameController created.");
	}
	
	public void run() {
		extension.trace("GameController run()");
		
		while (timeEventsRunning) {
			time.SetStartTime();
			
			// Get all current games
			for (Enumeration<World> e = extension.getGames().elements(); e.hasMoreElements(); ) {
				currWorld = (World) e.nextElement();
				
				// Update the current game state
				UpdateBullets();
				UpdatePlayers();
				UpdateTowers();
				UpdateMinions();
			}
			
			try
			{ 
				Thread.sleep(Constants.SLEEP_DURATION); 
			}
			catch(InterruptedException e)
			{
				// Halt this thread
				extension.trace("BattleFarm extension was halted");
			}	
			
			time.SetEndTime();
		}
	}
	
	public void setTimeEventsRunning(boolean b) {
		timeEventsRunning = b;
	}
	
	public void UpdateBullets() {
		if (currWorld.getBullets().isEmpty())
			return;
		
		for (ListIterator<Bullet> b = currWorld.getBullets().listIterator(); b.hasNext(); ) {
			Bullet currBullet = (Bullet)b.next();
			
			currBullet.Update(time.GetTimeDelta(), currWorld);
			//currBullet.CheckCollision(currWorld);
		}
	}

	public void UpdatePlayers() {
		if (currWorld.getPlayers().isEmpty())
			return;
		
		for (Enumeration<Player> p = currWorld.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			currPlayer.Update(time.GetTimeDelta(), extension, currWorld);
			//extension.trace(currPlayer.getState() + " " + System.currentTimeMillis());
		}
	}
	
	public void UpdateTowers() {
		if (currWorld.getTowers().isEmpty())
			return;
		
		for (ListIterator<Tower> t = currWorld.getTowers().listIterator(); t.hasNext(); ) {
			Tower currTower = (Tower)t.next();
			
			currTower.Update(time.GetTimeDelta(), currWorld);
		}
	}
	
	public void UpdateMinions() {
		if (currWorld.getMinions().isEmpty())
			return;
		
		for (ListIterator<Minion> m = currWorld.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			currMinion.Update(time.GetTimeDelta(), currWorld);
		}		
	}
}