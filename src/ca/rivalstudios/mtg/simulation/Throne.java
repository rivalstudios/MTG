package ca.rivalstudios.mtg.simulation;

public class Throne {
	private float hp;
	private float armour;
	private int team;

	public Throne(int team) {
		this.hp = 100.0f;
		this.armour = 1.0f;
		this.team = team;
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
}
