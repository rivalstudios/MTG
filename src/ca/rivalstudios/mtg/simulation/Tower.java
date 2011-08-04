package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import ca.rivalstudios.mtg.Constants;

public class Tower {
	private float hp;
	private float damage;
	private float range;
	private float armour;
	private int level;
	private int team;
	private long firingDelay;
	
	private long nextFiringTime = 0;
	
	private Minion targetMinion; // Might want to have minions and players inherit a common class
	private Player targetPlayer; // so we won't need two variables
	
	private Transform transform;

	public Tower(int team, Transform transform) {
		this.hp = 100.0f;
		this.damage = 5.0f;
		this.range = 50.0f;
		this.armour = 1.0f;
		this.level = 1;
		this.team = team;
		
		this.firingDelay = 2000;
		
		this.transform = transform;
	}
	
	public float getHp() {
		return hp;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float getRange() {
		return range;
	}
	
	public float getArmour() {
		return armour;
	}
	
	public float getLevel() {
		return level;
	}
	
	public float getTeam() {
		return team;
	}
	
	public void Update(float deltaTime, World world) {
		UpdateTarget(world);
		AttackTarget();
	}
	
	public void subtractHP(float amount) {
		this.hp -= amount;
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
					targetMinion = currMinion;
					return;
				}
			}
		}
		
		// We didn't find a suitable minion
		targetMinion = null;
		
		for (Enumeration<Player> p = world.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// Is this player on our team?
			if (this.getTeam() != currPlayer.getTeam()) {
				
				// If current player is within range, select as target
				if (this.transform.getDistanceTo(currPlayer.getTransform()) < range) {
					targetPlayer = currPlayer;
					return;
				}
			}
		}
		
		// We didn't find a suitable player
		targetPlayer = null;
	}
	
	public void AttackTarget() {
		if (System.currentTimeMillis() > nextFiringTime) {
			if (targetMinion != null) {
				targetMinion.subtractHP(damage);
			} else if (targetPlayer != null) {
				targetPlayer.subtractHP(damage);
			}
			
			// next opportunity to fire bullet
			nextFiringTime = System.currentTimeMillis() + firingDelay;
		}
	}
	
	public Transform getTransform() {
		return transform;
	}
}
