package ca.rivalstudios.mtg.handlers;

import java.util.List;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.World;

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
		
		Player p = 
		
		
		// Get the client params
		float x = params.getFloat("x");
		float y = params.getFloat("y");
		
		// TODO: check for valid X, Y

		// Create a response object
		ISFSObject resObj = SFSObject.newInstance();
		//resObj.putInt("moveAck", Constant.OK);
		
		send("move", resObj, user);
		// TODO: should send the X,Y to ALL clients
	}

}
