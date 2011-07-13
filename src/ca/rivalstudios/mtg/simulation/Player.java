package ca.rivalstudios.mtg.simulation;

import java.util.Enumeration;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.Transform;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

/**
 * 
 * @author Melvin Parinas
 * @version 1.0
 */
public class Player {
	private String name;
	private float hp;
	private float damage;
	private float xp;
	private float range;
	private float armour;
	private float speed;
	private long firingDelay;
	private int level;
	private int team;
	
	private int STATE; // idle, moving, pursuing, attacking

	private Transform transform;
	private Transform destTransform;

	private int gameId = 0;
	
	private int id = 0;
	private User sfsUser = null;
	
	// firing time
	private long nextFiringTime = 0;

	public Player(int id, User sfsUser, int game, Transform transform) {
		this.id = id;
		this.sfsUser = sfsUser;
		this.gameId = game;
		this.transform = transform;
		this.destTransform = new Transform(0, 0, 0);
		
		//this.name = name;
		this.hp = 100.0f;
		this.xp = 0.0f;
		this.damage = 5.0f;
		this.range = 10.0f;
		this.armour = 1.0f;
		this.speed = 10.0f;
		this.firingDelay = 2000; // in MS
		this.level = 1;
		//this.team = team;
		
		STATE = Constants.STATE_IDLE;
	}
	
	public String getName() {
		return name;
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
	
	public User getSfsUser() {
		return sfsUser;
	}
	
	public void setGameID(int id) {
		this.gameId = id;
	}
	
	public int getGameID() {
		return gameId;
	}
	
	public void setState(int state) {
		STATE = state;
	}
	
	public int getState() {
		return STATE;
	}
	
	public void Update(float deltaTime, MTGExtension e, World w) {
		UpdatePosition(deltaTime, e, w);
		CheckCollisions(w);
		PursueTarget();
		AttackEnemies();
	}
	
	public void UpdatePosition(float deltaTime, MTGExtension e, World w) {		
		// Check if we are moving
		if (STATE == Constants.STATE_MOVING) {
			float dx = transform.getX() - destTransform.getX();
			float dy = transform.getY() - destTransform.getY();
			float dz = transform.getZ() - destTransform.getZ();
	
			// normalize the vector
			float length = (float)(Math.sqrt(dx * dx + dy * dy + dz * dz));
			dx = dx / length * speed * deltaTime;
			dy = dy / length * speed * deltaTime;
			dz = dz / length * speed * deltaTime;
			
			Transform translation = new Transform(dx, dy, dz);
			
			// Translate the vector
			transform.translateBy(translation);
			
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			obj.putFloat(Constants.X, transform.getX());
			obj.putFloat(Constants.Y, transform.getY());
			obj.putFloat(Constants.Z, transform.getZ());

			e.send(Commands.MOVE, obj, sfsUser);
			
			// Check to see if we are near the click point tolerance
			if (transform.getDistanceTo(destTransform) <= Constants.MOVE_TOLERANCE) {
				STATE = Constants.STATE_IDLE;
			}
		}
	}
	
	public void CheckCollisions(World world) {		
		// Checks for collisions with Players
		for (Enumeration<Player> p = world.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// Don't compare with yourself
			if (currPlayer != this) {
				// If we are colliding with the currPlayer then stop moving
				if (this.isColliding(currPlayer.getTransform(), Constants.RADIUS_PLAYER)) {
					STATE = Constants.STATE_IDLE;
				}
			}
		}
		
		// Checks for collisions with Towers
		for (ListIterator<Tower> t = world.getTowers().listIterator(); t.hasNext(); ) {
			Tower currTower = (Tower)t.next();
			
			if (this.isColliding(currTower.getTransform(), Constants.RADIUS_TOWER)) {
				STATE = Constants.STATE_IDLE;
			}
		}
		
		// Checks for collisions with Minions
		for (ListIterator<Minion> m = world.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			if (this.isColliding(currMinion.getTransform(), Constants.RADIUS_MINION)) {
				STATE = Constants.STATE_IDLE;
			}
		}
		
		// Checks for collisions with Thrones
		for (ListIterator<Throne> th = world.getThrones().listIterator(); th.hasNext(); ) {
			Throne currThrone = (Throne)th.next();
			
			if (this.isColliding(currThrone.getTransform(), Constants.RADIUS_THRONE)) {
				STATE = Constants.STATE_IDLE;
			}
		}
	}
	
	public boolean isColliding(Transform t, float radius) {
		// Calculate the collision between two circles
		if (this.getTransform().getDistanceTo(t) <= Constants.RADIUS_PLAYER + radius) {
			return true;
		}
		
		return false;
	}
	
	public void PursueTarget() {
		
	}
	
	public void AttackEnemies() {
		if (System.currentTimeMillis() > nextFiringTime) {
			// get the closest enemy (towers, minions, thrones, players)
			
			// fire bullet
			nextFiringTime = System.currentTimeMillis() + firingDelay;
		}
	}
}