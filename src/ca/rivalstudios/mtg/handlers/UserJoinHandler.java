package ca.rivalstudios.mtg.handlers;

import java.util.ArrayList;
import java.util.Random;

import ca.rivalstudios.mtg.Commands;
import ca.rivalstudios.mtg.Constants;
import ca.rivalstudios.mtg.MTGExtension;
import ca.rivalstudios.mtg.simulation.Player;
import ca.rivalstudios.mtg.simulation.Tower;
import ca.rivalstudios.mtg.simulation.World;
import ca.rivalstudios.mtg.simulation.Transform;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
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
				
				// TODO: INITIALIZE NEW ROOM
				// ADD TOWERS, THRONES, ETC
				
				currWorld = new World(room.getId());
				((MTGExtension) (getParentExtension())).getGames().put(room.getId(), currWorld);
				
				Transform transform = new Transform(0.0f, 0.0f, 0.0f);
				Tower test = new Tower(0, transform);
				
				currWorld.getTowers().add(test);
			}
			
			// If the current world exists then add the new player
			if (currWorld != null) {
				Float rndX = random.nextFloat() * 290.0f + Constants.WORLD_MIN_X;
				Float rndZ = random.nextFloat() * 290.0f + Constants.WORLD_MIN_Z;
				
				Player p = new Player(user.getId(), 1, user, new Transform(rndX, 0, rndZ), currWorld, (MTGExtension)getParentExtension());				
				currWorld.getPlayers().put(user.getId(), p);
				
				ISFSObject data = new SFSObject();
				data.putFloat(Constants.X, rndX);
				data.putFloat(Constants.Y, 0);
				data.putFloat(Constants.Z, rndZ);
				data.putInt(Constants.ID, user.getId());
				
				// Send spawn command to all users
				send(Commands.SPAWN, data, room.getUserList());
			}
		}
	}

}
