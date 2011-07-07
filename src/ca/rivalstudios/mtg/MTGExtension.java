package ca.rivalstudios.mtg;

import java.util.concurrent.ConcurrentHashMap;

import ca.rivalstudios.mtg.handlers.AttackHandler;
import ca.rivalstudios.mtg.handlers.MoveHandler;
import ca.rivalstudios.mtg.handlers.ReadyHandler;
import ca.rivalstudios.mtg.handlers.UserDisconnectHandler;
import ca.rivalstudios.mtg.handlers.UserJoinHandler;
import ca.rivalstudios.mtg.handlers.UserLeaveHandler;
import ca.rivalstudios.mtg.simulation.GameController;
import ca.rivalstudios.mtg.simulation.World;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.extensions.SFSExtension;

/**
 * @author Melvin Parinas
 * @version 1.0
 * 
 * The main entry point into the MTG SmartFoxServer 2X server code.
 */
public class MTGExtension extends SFSExtension {

	/**
	 * Stores the state of all the MTG games currently active.
	 */
	private ConcurrentHashMap<Integer, World> games = null;
	/**
	 * Creates are new GameController used to execute background tasks.
	 */
	private GameController gameController;

	/**
	 * Initialize the MTGExtension and register Request and Event handlers.
	 */
	@Override
	public void init() {
		trace("MTGExtension extension started. Running version: " + Constants.SERVER_VERSION);
		
		games = new ConcurrentHashMap<Integer, World>();
		
		gameController = new GameController(this);
		gameController.start();
		
		addRequestHandler(Commands.MOVE, MoveHandler.class);
		addRequestHandler(Commands.READY, ReadyHandler.class);
		addRequestHandler(Commands.ATTACK, AttackHandler.class);
		
		addEventHandler(SFSEventType.USER_JOIN_ROOM, UserJoinHandler.class);
		addEventHandler(SFSEventType.USER_LEAVE_ROOM, UserLeaveHandler.class);
		addEventHandler(SFSEventType.USER_LOGOUT, UserDisconnectHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserDisconnectHandler.class);
	}

	/**
	 * Destroy the MTGExtension and remove Request and Event handlers.
	 */
	@Override
	public void destroy() {
		trace("MTGExtension destroy().");
		super.destroy();
		
		gameController.setTimeEventsRunning(false);
		gameController = null;
		
		removeRequestHandler(Commands.MOVE);
		removeRequestHandler(Commands.READY);
		removeRequestHandler(Commands.ATTACK);
		
		removeEventHandler(SFSEventType.USER_JOIN_ROOM);
		removeEventHandler(SFSEventType.USER_LEAVE_ROOM);
		removeEventHandler(SFSEventType.USER_LOGOUT);
		removeEventHandler(SFSEventType.USER_DISCONNECT);
	}

	/**
	 * Start the game when ready.
	 */	
	public void startGame() {
		// TODO:
	}

	/**
	 * Retrieve the current room.
	 * 
	 * @return the current room.
	 */
	public Room getRoom() {
		return this.getParentRoom();
	}

	/**
	 * Retrieve the current zone.
	 * 
	 * @return the current zone.
	 */
	public Zone getZone() {
		return this.getParentZone();
	}

	/**
	 * Retrieve the list of all currently active games.
	 * 
	 * @return a ConcurrentHashMap containing all active games.
	 */
	public ConcurrentHashMap<Integer, World> getGames() {
		return games;
	}
}
