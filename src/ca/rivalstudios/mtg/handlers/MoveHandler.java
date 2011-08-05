package ca.rivalstudios.mtg.handlers;

import java.util.List;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.Player;
import ca.rivalstudios.mtg.simulation.World;
import ca.rivalstudios.mtg.simulation.Transform;

import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class MoveHandler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		trace("Entering MoveHandler.");
		
		// Get the user's room
		Room room = null;
		List<Room> rooms = user.getJoinedRooms();
		if (rooms.size() > 0) {
			room = rooms.get(0);
		}
		
		// Get the world simulation
		World world = ((MTGExtension) (getParentExtension())).getGames().get(room.getId());
		
		// Get the player
		Player currPlayer = world.getPlayers().get(user.getId());
		
		// Get the player click coordinates
		float x = params.getFloat("x");
		float y = params.getFloat("y");
		float z = params.getFloat("z");
		
		if (isValidMove(x, y, z)) {
			currPlayer.setDestTransform(new Transform(x, y, z));
			currPlayer.setState(Constants.STATE_MOVING);
	
			/*
			// Send the updates to the opponents
			ISFSObject resObj = new SFSObject();
			resObj.putInt(Constants.ID, user.getId());
			resObj.putFloat(Constants.X, px);
			resObj.putFloat(Constants.Y, py);
			
			List<User> recipients = room.getUserList();
			//recipients.remove(currPlayer.getSfsUser());
			
			send(Commands.MOVE, resObj, recipients);
			*/
		} else {
			// TODO: Handle invalid move here
		}
	}
	
	/*
	 * Validate if the players position is a legal move.
	 */
	private boolean isValidMove(float x, float y, float z) {
		return true;
	}
}