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
	
	private float rotx;
	private float roty;
	private float rotz;
	
	private float radius;
	
	public Transform(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void normalize() {
		float length = (float)(Math.sqrt(x * x + y * y + z * z));
		x = x / length;
		y = y / length;
		z = z / length;
	}
	
	public void scale(float factor) {
		x = x * factor;
		y = y * factor;
		z = z * factor;
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
	
	public boolean isColliding(Transform t, float r1, float r2) {
		// Calculate the collision between two circles
		if (this.getDistanceTo(t) <= r1 + r2) {
			
			return true;
		}
		
		return false;
	}
}
