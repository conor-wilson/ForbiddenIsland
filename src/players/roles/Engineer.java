package players.roles;

import java.util.ArrayList;

import board.Board;
import enums.Location;
import enums.PlayerType;
import players.Player;

public class Engineer extends Player {

	public Engineer(String name) {
		super(name, PlayerType.ENGINEER);
		specialActionDescription = "Shore up 2 tiles for 1 action.";
		startLocation = Location.BRONZE_GATE;
	}
	
	/*
	 * Engineer can shore up 2 tiles for one action
	 */
	@Override
	protected void shoreUp() {
		super.shoreUp();	
		freeShoreUp();
	}
	
	/*
	 * Allow engineer to shore up without using an action
	 */
	private void freeShoreUp() {
		ArrayList<Location> shoreUpOptions = getShoreUpOptions();	
		
		if (shoreUpOptions.size() == 0) {
			return;
		}
		
		Location location = playerui.promptShoreUp(shoreUpOptions);
		
		if (location != null) {
			Board.getInstance().shoreUp(location);
		}
	}
}
