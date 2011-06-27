package ca.rivalstudios.mtg;

import java.util.concurrent.ConcurrentHashMap;

import ca.rivalstudios.mtg.handlers.AttackHandler;
import ca.rivalstudios.mtg.handlers.MoveHandler;
import ca.rivalstudios.mtg.handlers.OnUserDisconnect;
import ca.rivalstudios.mtg.handlers.ReadyHandler;
import ca.rivalstudios.mtg.simulation.World;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MTGExtension extends SFSExtension {
		
	private ConcurrentHashMap<Integer, World> games = null;

	@Override
	public void init() {
		trace("MTGMain extension started. Running version: " + Constants.SERVER_VERSION);
		
		games = new ConcurrentHashMap<Integer, World>();
		
		// Custom Request Handlers
		// TODO: these strings should be changed to ENUMS or Constants
		addRequestHandler(Commands.MOVE, MoveHandler.class);
		addRequestHandler(Commands.READY, ReadyHandler.class);
		addRequestHandler(Commands.ATTACK, AttackHandler.class);
		
		// Server Event Handlers
		addEventHandler(SFSEventType.USER_DISCONNECT, OnUserDisconnect.class);
		addEventHandler(SFSEventType.USER_LEAVE_ROOM, OnUserDisconnect.class);
	}

	@Override
	public void destroy() {
		trace("MTGMain destroy().");
		super.destroy();
	}
	
	void startGame() {
		// TODO:
	}
	
	public Room getRoom() {
		return this.getParentRoom();
	}
	
	public Zone getZone() {
		return this.getParentZone();
	}
	
	public ConcurrentHashMap<Integer, World> getGames() {
		return games;
	}
}
