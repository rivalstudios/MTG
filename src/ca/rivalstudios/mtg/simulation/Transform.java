package ca.rivalstudios.mtg.simulation;

/**
 * Transform data-type class
 *
 * @author Melvin Parinas
 * @version 1.0
 *
 */
public class Transform {

	private float x;
	private float y;
	private float z;
	
	public Transform(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Transform getNormalized() {
		float length = (float)(Math.sqrt(x * x + y * y + z * z));
		return new Transform(x / length, y / length, z / length);
	}
	
	public float getDistanceTo(Transform target) {
		float dx = x - target.getX();
		float dy = y - target.getY();
		float dz = z - target.getZ();
		
		float length = (float)(Math.sqrt(dx * dx + dy * dy + dz * dz));
		
		return length;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	/**
	 * 
	 */
	public void translateBy(Transform t) {
		x += t.getX();
		y += t.getY();
		z += t.getZ();
	}
	
	/**
	 * 
	 */
	public void translateTo(Transform t) {
		x = t.getX();
		y = t.getY();
		z = t.getZ();
	}
}
