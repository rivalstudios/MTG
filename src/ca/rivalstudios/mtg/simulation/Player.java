package ca.rivalstudios.mtg.simulation;

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
	private int level;
	private int team;

	private Transform transform;
	private Transform destTransform;
	
	private boolean moving;
	
	private int gameId = 0;
	
	private int id = 0;
	private User sfsUser = null;

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
		this.level = 1;
		//this.team = team;
		
		this.moving = false;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void setMoving(boolean state) {
		moving = state;
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
	
	public void UpdatePosition(float deltaTime, MTGExtension e, World w) {
		// Check if we are moving
		if (moving) {
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
			
			// Check for collsions which may set moving to false;
			// Check to see if we are near the click point, tolerance
			
			ISFSObject obj = new SFSObject();
			obj.putInt(Constants.ID, sfsUser.getId());
			obj.putFloat(Constants.X, transform.getX());
			obj.putFloat(Constants.Y, transform.getY());
			obj.putFloat(Constants.Z, transform.getZ());

			e.send(Commands.MOVE, obj, sfsUser);
		}
	}
}