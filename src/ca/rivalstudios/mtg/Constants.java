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
	public static int RADIUS_SHELL = 1;
	public static int RADIUS_MINION = 5;
	public static int RADIUS_PLAYER = 10;
	public static int RADIUS_TOWER = 15;
	public static int RADIUS_THRONE = 20;

	/*
	 * Map size reference values.
	 */
	public static int WORLD_MIN_X = -145;
	public static int WORLD_MAX_X = 145;
	public static int WORLD_MIN_Z = -145;
	public static int WORLD_MAX_Z = 145;
}
