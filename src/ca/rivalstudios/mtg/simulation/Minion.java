package ca.rivalstudios.mtg.simulation;

public class Minion {

	private float hp;
	private float damage;
	private float range;
	private float armour;
	private float speed;
	private float radius;
	private int level;
	private int team;

	public Minion(int level, int team) {
		float multiplier = 1.0f;

		if (level != 1) {
			multiplier = 1.5f;
		}
		
		this.hp = 10.0f * multiplier;
		this.damage = 5.0f * multiplier;
		this.range = 10.0f * multiplier;
		this.armour = 1.0f * multiplier;
		this.speed = 5.0f * multiplier;
		this.radius = 10.0f;
		this.level = level;
		this.team = team;
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
		return level;
	}
	
	public int getTeam() {
		return team;
	}
}
