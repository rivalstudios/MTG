package ca.rivalstudios.mtg.simulation;

/**
 * Time management singleton class
 * 
 * @author mjparinas
 * @version 1.0
 */
public class TimeController {
	private long startTime;
	private long endTime;
	private long deltaTime;
	
	public static final TimeController INSTANCE = new TimeController();
	
	public TimeController() {
		startTime = 0;
		endTime = 0;
		deltaTime = 0;
	}
	
	public void SetStartTime() {
		startTime = System.currentTimeMillis();
	}
	
	public void SetEndTime() {
		endTime = System.currentTimeMillis();
		deltaTime = endTime - startTime;
	}
	
	public float GetTimeDelta() {
		return deltaTime / 1000.0f;
	}
	
	public static TimeController getInstance() {
		return INSTANCE;
	}
}
