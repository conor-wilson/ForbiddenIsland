package players.roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import enums.Direction;
import enums.Location;
import enums.PlayerType;
import enums.TileState;
import players.Player;

public class Explorer extends Player {

	public Explorer(String name) {
		super(name, PlayerType.EXPLORER);
		specialActionDescription = "Move and/or shore up diagonally.";
		startLocation = Location.COPPER_GATE;
	}
	
	/*
	 * Explorer can move diagonally
	 */
	@Override
	public ArrayList<Direction> getMoveOptions() {
		ArrayList<Direction> legalDirections = super.getMoveOptions();
		
		Tile[][] tiles = Board.getInstance().getTiles();
		
		// Check tile exists at each possible diagonal move location
		if (xPos > 0 && yPos > 0) {
			if (tiles[xPos-1][yPos-1].getState() != TileState.SUNK) {legalDirections.add(Direction.UP_LEFT);}
		}
		if (xPos < 5 && yPos > 0) {
			if (tiles[xPos+1][yPos-1].getState() != TileState.SUNK) {legalDirections.add(Direction.UP_RIGHT);}
		}
		if (xPos > 0 && yPos < 5) {
			if (tiles[xPos-1][yPos+1].getState() != TileState.SUNK) {legalDirections.add(Direction.DOWN_LEFT);}
		}
		if (xPos < 5 && yPos < 5) {
			if (tiles[xPos+1][yPos+1].getState() != TileState.SUNK) {legalDirections.add(Direction.DOWN_RIGHT);}
		}
		
		return legalDirections;
	}
	
	/*
	 * Explorer can shore up diagonally
	 */
	@Override
	public ArrayList<Location> getShoreUpOptions() {
		ArrayList<Location> shoreUpOptions = super.getShoreUpOptions();
		
		Tile[][] tiles = Board.getInstance().getTiles();
		
		if (xPos > 0 && yPos > 0) {
			if (tiles[xPos-1][yPos-1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos-1][yPos-1].getLocation());}
		}
		if (xPos < 5 && yPos > 0) {
			if (tiles[xPos+1][yPos-1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos+1][yPos-1].getLocation());}
		}
		if (xPos > 0 && yPos < 5) {
			if (tiles[xPos-1][yPos+1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos-1][yPos+1].getLocation());}
		}
		if (xPos < 5 && yPos < 5) {
			if (tiles[xPos+1][yPos+1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos+1][yPos+1].getLocation());}
		}
		
		return shoreUpOptions;
	}
}
