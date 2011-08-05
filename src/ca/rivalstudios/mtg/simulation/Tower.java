package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import ca.rivalstudios.mtg.Constants;

public class Tower extends BasicUnit {
	
	private long nextFiringTime = 0;
	
	private Object target;

	public Tower(int team, Transform transform) {
		this.hp = 100.0f;
		this.damage = 5.0f;
		this.range = 50.0f;
		this.armour = 1.0f;
		this.level = 1;
		this.team = team;
		
		this.radius = Constants.RADIUS_TOWER;
		
		this.firingDelay = 2000;
		
		this.transform = transform;
	}
	
	public void Update(float deltaTime, World world) {
		UpdateTarget(world);
		AttackTarget();
	}
	
	/**
	 * Gets the first enemy minion that is within range otherwise it gets the first
	 * enemy that is the closest.
	 * 
	 * We can possibly change this algorithm in the future to attack the closest enemy
	 * 
	 * @param world The current game
	 */
	public void UpdateTarget(World world) {
		for (ListIterator<Minion> m = world.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			// Is the minion on our team?
			if (this.getTeam() != currMinion.getTeam()) {
			
				// If current minion is within range, select as target
				if (this.transform.getDistanceTo(currMinion.getTransform()) < range) {
					target = currMinion;
					return;
				}
			}
		}

		for (Enumeration<Player> p = world.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// Is this player on our team?
			if (this.getTeam() != currPlayer.getTeam()) {
				
				// If current player is within range, select as target
				if (this.transform.getDistanceTo(currPlayer.getTransform()) < range) {
					target = currPlayer;
					return;
				}
			}
		}
	}
	
	public void AttackTarget() {
		if (System.currentTimeMillis() > nextFiringTime) {
			if (target instanceof Player) {
				((Player) target).subtractHP(damage);
			} else {
				((BasicUnit) target).subtractHP(damage);
			}
			
			// next opportunity to fire bullet
			nextFiringTime = System.currentTimeMillis() + firingDelay;
		}
	}
}
