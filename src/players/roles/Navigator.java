package players.roles;

import enums.Location;
import enums.PlayerType;
import players.Player;

public class Navigator extends Player {

	public Navigator(String name) {
		super(name, PlayerType.NAVIGATOR);
		specialActionDescription = "The navigator has no special ablities";
		startLocation = Location.GOLD_GATE;
	}
}
