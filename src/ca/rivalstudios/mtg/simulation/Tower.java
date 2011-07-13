package ca.rivalstudios.mtg.simulation;

public class Tower {
	private float hp;
	private float damage;
	private float range;
	private float armour;
	private int level;
	private int team;
	
	private Transform transform;

	public Tower(int team) {
		this.hp = 100.0f;
		this.damage = 5.0f;
		this.range = 10.0f;
		this.armour = 1.0f;
		this.level = 1;
		this.team = team;
		
		// TODO: Set transform
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
		UpdateTarget();
	}
	
	public void UpdateTarget() {
		
	}
	
	public Transform getTransform() {
		return transform;
	}
}
