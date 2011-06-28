package ca.rivalstudios.mtg.handlers;

import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.Player;
import ca.rivalstudios.mtg.simulation.World;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserLeaveHandler extends BaseServerEventHandler {

	/**
	 * Handle a user leaving the room.
	 * 
	 */
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		trace("Entering UserLeaveHandler.");
		
		// Get the room
		Room room = (Room)event.getParameter(SFSEventParam.ROOM);
		
		// Get the user
		User user = (User)event.getParameter(SFSEventParam.USER);
		
		// Remove the user from the room
		if (room != null) {
			// Get the current game
			World currWorld = ((MTGExtension) (getParentExtension())).getGames().get(room.getId());
			// Get the player that left
			Player currPlayer = (Player)currWorld.getPlayers().get(user.getId());
			
			if (currPlayer != null) {
				// Remove the user
				currWorld.getPlayers().remove(user.getId());
				
				// If there are no more users then remove the game
				// TODO: This should be changed to "if there are no more users on
				//       enemy team then remove the game (ie. if redteam count == 0 or
				//       blueteam count == 0 then remove game.
				if (currWorld.getPlayers().size() == 0) {
					((MTGExtension) (getParentExtension())).getGames().remove(currWorld);
				}
			}
		}
	}

}
