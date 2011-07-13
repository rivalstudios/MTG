package ca.rivalstudios.mtg;

/**
 * Stores all constants used by the extension
 *
 * @author Melvin Parinas
 * @version 1.0
 */
public class Constants {
	/*
	 * The current version of the server extension.
	 */
	public final static String SERVER_VERSION = "1.0";
	
	/*
	 * Reference to an object's position.
	 */
	public final static String X = "x";
	public final static String Y = "y";
	public final static String Z = "z";
	public final static String ID = "i";

	public static int ERROR = -1;
	public static int OK = 1;

	/*
	 * Player team codes.
	 */
	public static int TEAM_RED = 1;
	public static int TEAM_BLUE = 2;

	/*
	 * Experience points values for all characters
	 */
	public static int XP_TOWER = 100;
	public static int XP_MINION_LEVEL1 = 5;
	public static int XP_MINION_LEVEL2 = 8;
	public static int XP_PLAYER = 15;

	/*
	 * Collider radiuses for all characters.
	 */
	public static float RADIUS_SHELL = 1.0f;
	public static float RADIUS_MINION = 5.0f;
	public static float RADIUS_PLAYER = 10.0f;
	public static float RADIUS_TOWER = 15.0f;
	public static float RADIUS_THRONE = 20.0f;

	/*
	 * Map size reference values.
	 */
	public static int WORLD_MIN_X = -145;
	public static int WORLD_MAX_X = 145;
	public static int WORLD_MIN_Z = -145;
	public static int WORLD_MAX_Z = 145;
	
	/*
	 * Amount of time in ms the GameController should sleep between runs
	 */
	public static int SLEEP_DURATION = 100;
	
	/*
	 * Player states
	 */
	public static int STATE_IDLE = 0;
	public static int STATE_MOVING = 1;
	public static int STATE_PURSUING = 2;
	public static int STATE_ATTACKING = 3;
	
	public static float MOVE_TOLERANCE = 2.0f;
}
