package ca.rivalstudios.mtg.simulation;

public class Throne {
	private float hp;
	private float armour;
	private int team;
	
	private Transform transform;

	public Throne(int team) {
		this.hp = 100.0f;
		this.armour = 1.0f;
		this.team = team;
		
		// TODO: set transform
	}
	
	public float getHp() {
		return hp;
	}
	
	public float getArmour() {
		return armour;
	}
	
	public float getTeam() {
		return team;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void Update(float deltaTime, World world) {
		// TODO: updates
	}
}
