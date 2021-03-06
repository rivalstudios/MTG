package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;

public class BasicUnit {
	protected float hp;
	protected float damage;
	protected float xp;
	protected float range;
	protected float armour;
	protected float speed;
	protected long firingDelay;
	protected int level;
	protected int team;
	protected float radius;
	
	protected int STATE; // idle, moving, pursuing, attacking

	protected Transform transform;
	protected Transform destTransform;
	
	protected World world;
	protected MTGExtension extension;
	
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
	
	public float getSpeed() {
		return speed;
	}
	
	public float getLevel() {
		return level; // should be determined by XP?
	}
	
	public float getTeam() {
		return team;
	}
	
	public float getXp() {
		return xp;
	}
	
	public void addXp(float amount) {
		xp = xp + amount;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setTransform(Transform t) {
		this.transform = t;
	}
	
	public void setDestTransform(Transform t) {
		destTransform = t;
	}
	
	public void setState(int state) {
		STATE = state;
	}
	
	public int getState() {
		return STATE;
	}
	
	public float getRadius() {
		return radius;
	}
	
	// THIS SHOULD BE SENT TO ALL USERS
	// maybe there should be a command send class? otherwise, how should i do this?
	public void subtractHP(float amount) {
		this.hp -= amount;
		
		/*if (hp <= 0) {
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			extension.send(Commands.DEAD, obj, sfsUser);
		} else {
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			obj.putFloat(Constants.HP, hp);
			extension.send(Commands.HEALTH, obj, sfsUser);
		}*/
	}
	
	/**
	 * Gets the first enemy minion that is within range otherwise it gets the first
	 * enemy that is the closest.
	 * 
	 * We can possibly change this algorithm in the future to attack the closest enemy
	 * 
	 * @param world The current game
	 * @return The selected target unit
	 */
	public BasicUnit GetTarget(World world) {
		for (ListIterator<Minion> m = world.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			// Is the minion on our team?
			if (this.getTeam() != currMinion.getTeam()) {
			
				// If current minion is within range, select as target
				if (this.transform.getDistanceTo(currMinion.getTransform()) < range) {
					return currMinion;
				}
			}
		}

		for (Enumeration<Player> p = world.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// Don't compare with ourself
			if (currPlayer != this) {
				// Is this player on our team?
				if (this.getTeam() != currPlayer.getTeam()) {
					
					// If current player is within range, select as target
					if (this.transform.getDistanceTo(currPlayer.getTransform()) < range) {
						return currPlayer;
					}
				}
			}
		}
		
		for (ListIterator<Tower> t = world.getTowers().listIterator(); t.hasNext(); ) {
			Tower currTower = (Tower)t.next();
			
			// Is this tower on our team?
			if (this.getTeam() != currTower.getTeam()) {
				
				// If current tower is within range, select as target
				if (this.transform.getDistanceTo(currTower.getTransform()) < range) {
					return currTower;
				}
			}
		}
		
		for (ListIterator<Throne> r = world.getThrones().listIterator(); r.hasNext(); ) {
			Throne currThrone = (Throne)r.next();
			
			// Is this throne on our team?
			if (this.getTeam() != currThrone.getTeam()) {
				
				// If current tower is within range, select as target
				if (this.transform.getDistanceTo(currThrone.getTransform()) < range) {
					return currThrone;
				}
			}
		}
		
		// If we reached this point, then no target was found
		return null;
	}
}
