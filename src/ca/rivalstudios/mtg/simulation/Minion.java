package ca.rivalstudios.mtg.simulation;

import ca.rivalstudios.mtg.Constants;

public class Minion extends BasicUnit{
	
	private Transform transform;

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
		this.level = level;
		this.team = team;
		
		this.radius = Constants.RADIUS_MINION;
		
		// TODO: set transform (spawn point)
	}
	
	public void subtractHP(float amount) {
		this.hp -= amount;
	}
	
	public void Update(float deltaTime, World world) {
		UpdatePosition(deltaTime);
		UpdateTarget();
	}
	
	public void UpdatePosition(float deltaTime) {
	}
	
	public void UpdateTarget() {
		
	}
	
	public Transform getTransform() {
		return transform;
	}
}
