package ca.rivalstudios.mtg.simulation;

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

	public Player(String name, int team) {
		this.name = name;
		this.hp = 100.0f;
		this.xp = 0.0f;
		this.damage = 5.0f;
		this.range = 10.0f;
		this.armour = 1.0f;
		this.speed = 5.0f;
		this.level = 1;
		this.team = team;
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
}