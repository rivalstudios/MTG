package ca.rivalstudios.mtg.simulation;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;

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
	private float x;
	private float y;
	
	private float targetX;
	private float targetY;
	
	private boolean moving;
	
	private int gameId = 0;
	
	private int id = 0;
	private User sfsUser = null;

	public Player(int id, User sfsUser, int game, float x, float y) {
		this.id = id;
		this.sfsUser = sfsUser;
		this.gameId = game;
		this.x = x;
		this.y = y;
		
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
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setTargetX(float x) {
		this.targetX = x;
	}
	
	public void setTargetY(float y) {
		this.targetY = y;
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
		float dx = targetX - x;
		float dy = targetY - y;
		
		// normalize the vector
		double hyp = Math.sqrt(dx * dx + dy * dy);
		
		dx = (float)(dx / hyp);
		dy = (float)(dy / hyp);
		
		x += dx * speed * deltaTime;
		y += dy * speed * deltaTime;
		
		// calculate whether we are nearby the click point, we should have a tolerance
		// then isMoving = false
		
		ISFSObject obj = new SFSObject();
		obj.putInt(Constants.ID, sfsUser.getId());
		obj.putFloat(Constants.X, x);
		obj.putFloat(Constants.Y, y);

		e.send(Commands.MOVE, obj, sfsUser);
	}
}