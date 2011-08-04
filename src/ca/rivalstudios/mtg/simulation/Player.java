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
	
	private World world;
	private MTGExtension extension;
	
	// firing time
	private long nextFiringTime = 0;

	public Player(int id, int team, User sfsUser, int game, Transform transform, World world, MTGExtension extension) {
		this.id = id;
		this.sfsUser = sfsUser;
		this.gameId = game;
		this.transform = transform;
		this.destTransform = new Transform(0, 0, 0);
		
		this.world = world;
		this.extension = extension;
		
		//this.name = name;
		this.hp = 100.0f;
		this.xp = 0.0f;
		this.damage = 5.0f;
		this.range = 10.0f;
		this.armour = 1.0f;
		this.speed = 20.0f;
		this.firingDelay = 2000; // in MS
		this.level = 1;
		this.team = team;
		
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
	
	// THIS SHOULD BE SENT TO ALL USERS
	// maybe there should be a command send class? otherwise, how should i do this?
	public void subtractHP(float amount) {
		this.hp -= amount;
		
		if (hp <= 0) {
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			extension.send(Commands.DEAD, obj, sfsUser);
		} else {
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			obj.putFloat(Constants.HP, hp);
			extension.send(Commands.HEALTH, obj, sfsUser);
		}
	}
	
	public void Update(float deltaTime, MTGExtension e, World w) {
		CalculateMovement(deltaTime, w);
		UpdatePlayers(e);
		//CheckCollisions(w, e);
		//PursueTarget();
		//AttackEnemies();
	}
	
	public void CalculateMovement(float deltaTime, World w) {		
		// Check if we are moving
		if (STATE == Constants.STATE_MOVING) {
			
			Transform movement = new Transform(destTransform.getX() - transform.getX(), destTransform.getY() - transform.getY(), destTransform.getZ() - transform.getZ());
			
			movement.normalize();
			
			// Scale the movement vector
			movement.scale(speed * deltaTime);
			
			// Translate the vector
			transform.translateBy(movement);
			
			CheckCollisions(w);

			// Check to see if we are near the click point tolerance
			if (transform.getDistanceTo(destTransform) <= Constants.MOVE_TOLERANCE) {
				STATE = Constants.STATE_IDLE;
			}
		}
	}
	
	/** TODO: We need to keep any eye on this. There might be a scenario where we are constantly being
	  *       push backed by everything simultaneous causing us to hop around a lot.
	  */
	public void CheckCollisions(World w) {		
		// Checks for collisions with Players
		for (Enumeration<Player> p = world.getPlayers().elements(); p.hasMoreElements(); ) {
			Player currPlayer = (Player)p.nextElement();
			
			// Don't compare with yourself
			if (currPlayer != this) {
				// If we are colliding with the currPlayer then stop moving
				if (transform.isColliding(currPlayer.getTransform(), Constants.RADIUS_PLAYER, Constants.RADIUS_PLAYER)) {
					Transform pushBack = new Transform(transform.getX() - currPlayer.getTransform().getX(), transform.getY() - currPlayer.getTransform().getY(), transform.getZ() - currPlayer.getTransform().getZ());
					pushBack.normalize();	
					float pushFactor = Constants.RADIUS_PLAYER + Constants.RADIUS_TOWER - transform.getDistanceTo(currPlayer.getTransform());
					pushBack.scale(pushFactor);
					transform.translateBy(pushBack);
					
					STATE = Constants.STATE_IDLE;
				}
			}
		}
		
		// Checks for collisions with Towers
		for (ListIterator<Tower> t = world.getTowers().listIterator(); t.hasNext(); ) {
			Tower currTower = (Tower)t.next();
			
			// We push back the player from the tower so that their radii no longer intersect
			if (transform.isColliding(currTower.getTransform(), Constants.RADIUS_PLAYER, Constants.RADIUS_TOWER)) {
				Transform pushBack = new Transform(transform.getX() - currTower.getTransform().getX(), transform.getY() - currTower.getTransform().getY(), transform.getZ() - currTower.getTransform().getZ());
				pushBack.normalize();	
				float pushFactor = Constants.RADIUS_PLAYER + Constants.RADIUS_TOWER - transform.getDistanceTo(currTower.getTransform());
				pushBack.scale(pushFactor);
				transform.translateBy(pushBack);
				
				STATE = Constants.STATE_IDLE;
			}
		}
		
		// Checks for collisions with Minions
		for (ListIterator<Minion> m = world.getMinions().listIterator(); m.hasNext(); ) {
			Minion currMinion = (Minion)m.next();
			
			if (transform.isColliding(currMinion.getTransform(), Constants.RADIUS_PLAYER, Constants.RADIUS_MINION)) {
				Transform pushBack = new Transform(transform.getX() - currMinion.getTransform().getX(), transform.getY() - currMinion.getTransform().getY(), transform.getZ() - currMinion.getTransform().getZ());
				pushBack.normalize();	
				float pushFactor = Constants.RADIUS_PLAYER + Constants.RADIUS_TOWER - transform.getDistanceTo(currMinion.getTransform());
				pushBack.scale(pushFactor);
				transform.translateBy(pushBack);
				
				STATE = Constants.STATE_IDLE;
			}
		}
		
		// Checks for collisions with Thrones
		for (ListIterator<Throne> th = world.getThrones().listIterator(); th.hasNext(); ) {
			Throne currThrone = (Throne)th.next();
			
			if (transform.isColliding(currThrone.getTransform(), Constants.RADIUS_PLAYER, Constants.RADIUS_THRONE)) {
				Transform pushBack = new Transform(transform.getX() - currThrone.getTransform().getX(), transform.getY() - currThrone.getTransform().getY(), transform.getZ() - currThrone.getTransform().getZ());
				pushBack.normalize();	
				float pushFactor = Constants.RADIUS_PLAYER + Constants.RADIUS_TOWER - transform.getDistanceTo(currThrone.getTransform());
				pushBack.scale(pushFactor);
				transform.translateBy(pushBack);
				
				STATE = Constants.STATE_IDLE;
			}
		}
	}
	
	public void UpdatePlayers(MTGExtension e) {
		// Send new position to the user
		ISFSObject obj = new SFSObject();
		obj.putInt(Constants.ID, sfsUser.getId());
		obj.putFloat(Constants.X, transform.getX());
		obj.putFloat(Constants.Y, transform.getY());
		obj.putFloat(Constants.Z, transform.getZ());
		e.send(Commands.MOVE, obj, sfsUser);
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