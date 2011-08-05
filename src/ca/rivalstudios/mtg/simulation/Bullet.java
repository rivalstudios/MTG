package ca.rivalstudios.mtg.simulation;

public class Bullet extends BasicUnit {
	
	public Bullet() {
		
	}
	
	public void UpdatePosition(float deltaTime) {
		
	}
	
	public void CheckCollision(World world) {
		
	}
	
	public void Update(float deltaTime, World world) {
		UpdatePosition(deltaTime);
		CheckCollision(world);
	}
}
