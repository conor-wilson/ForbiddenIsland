package board;

import enums.*;

public class NullTile extends Tile {
	
	/* Constructor */
	public NullTile() {
		super(null);
	}
	
	/* Getters */
	@Override
	public TileState getState() {
		// Always returns SUNK since the Player should never go here. 
		return TileState.SUNK; 
	}
	
	/* 
	 * Flood the NullTile. Cannot be done so always returns false. 
	 */
	@Override 
	public boolean flood() {
		return false;
	}
	
	/* 
	 * Shore-up the NullTile. Cannot be done so always returns false. 
	 */
	@Override
	public boolean shoreUp() {
		return false;
	}
	
	/* 
	 * toString used for debugging.
	 */
	@Override
	public String toString() {
		return "       NULL       ";
	}
}
