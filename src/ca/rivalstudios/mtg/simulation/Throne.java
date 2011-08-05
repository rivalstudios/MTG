package ca.rivalstudios.mtg.simulation;

import ca.rivalstudios.mtg.Constants;

public class Throne extends BasicUnit {

	public Throne(int team) {
		this.hp = 100.0f;
		this.armour = 1.0f;
		this.team = team;
		this.radius = Constants.RADIUS_THRONE;
		
		// TODO: set transform
	}
	
	public void Update(float deltaTime, World world) {
		// TODO: updates
	}
}
