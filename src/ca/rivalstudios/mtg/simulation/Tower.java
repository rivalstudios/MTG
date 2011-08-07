package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import ca.rivalstudios.mtg.Constants;

public class Tower extends BasicUnit {
	
	private long nextFiringTime = 0;
	
	private BasicUnit target;

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
		target = GetTarget(world);
		AttackTarget();
	}
	
	public void AttackTarget() {
		// If we have a target
		if (target != null) {
			if (System.currentTimeMillis() > nextFiringTime) {
				if (target instanceof Player) {
					((Player) target).subtractHP(damage);
				} /*else {
					((BasicUnit)target).subtractHP(damage);
				}*/
				
				// next opportunity to fire bullet
				nextFiringTime = System.currentTimeMillis() + firingDelay;
			}
		}
	}
}
