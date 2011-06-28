package ca.rivalstudios.mtg.handlers;

import java.util.ArrayList;
import java.util.Random;

import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.Player;
import ca.rivalstudios.mtg.simulation.World;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserJoinHandler extends BaseServerEventHandler {

	/**
	 * Handle when the user joins a game.
	 */
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		trace("Entering UserJoinHandler.");
		
		Random random = new Random();
		
		Room room = (Room)event.getParameter(SFSEventParam.ROOM);
		
		if (room.isGame()) {
			User user = (User)event.getParameter(SFSEventParam.USER);
			
			World currWorld = ((MTGExtension) (getParentExtension())).getGames().get(room.getId());
			
			// If the current world does not exist then create the new world
			if (currWorld == null) {
				currWorld = new World(room.getId());
				((MTGExtension) (getParentExtension())).getGames().put(room.getId(), currWorld);
				
			}
			
			// If the current world exists then add the new player
			if (currWorld != null) {
				Player p = new Player(user.getId(), user);
				p.setGameID(currWorld.getGameID());
				p.setX(random.nextInt(60) + Constants.WORLD_MIN_X);
				p.setY(random.nextInt(60) + Constants.WORLD_MIN_Z);
				
				currWorld.getPlayers().put(user.getId(), p);
			}
		}
	}

}
