package board;

import enums.*;
import gameplay.GameLogic;

public class Tile {
	/* Instance variables */
	protected Location 	location;
	protected TileState state;
	
	/* Constructor */
	public Tile(Location location) {
		this.location = location;
		this.state 	  = TileState.DRY;
	}
	
	/* Getters */
	public Location getLocation() 	{ return this.location; }
	public TileState getState() 	{ return state; }
	
	/* 
	 * Flood the Tile. Returns a boolean indicator of whether this is possible. 
	 */
	public boolean flood() {
		// If the Tile is DRY, it becomes FLOODED. 
		if(state == TileState.DRY) {
			state = TileState.FLOODED;
			return true;
		} 
		// If the Tile is FLOODED, it becomes SUNK. 
		else if(state == TileState.FLOODED) {
			state = TileState.SUNK;
			GameLogic.getInstance().tileSank(this);
			return true;
		}
		
		// Otherwise, it's impossible to flood this Tile. 
		else 
			return false;
	}
	
	/* 
	 * Shore-up the tile. Returns a boolean indicator of whether this is possible. 
	 */
	public boolean shoreUp() {
		// If the Tile is FLOODED, it becomes DRY. 
		if(state == TileState.FLOODED) {
			state = TileState.DRY;
			return true;
		}
		
		// Otherwise, it is impossible to shored-up this Tile. 
		else 
			return false;
	}
	
	/* 
	 * toString used for debugging.
	 */
	@Override
	public String toString() {
		String tileString = location.toString();

		// String must be spaced to 18 characters
		for(int i = location.toString().length(); i < 19 ; i++)
			tileString += " ";
		
		return tileString;
	}
}
