package players.roles;

import java.util.ArrayList;

import board.Board;
import board.NullTile;
import board.Tile;
import enums.Direction;
import enums.Location;
import enums.PlayerType;
import enums.TileState;
import players.Player;
import ui.BoardView;

public class Diver extends Player {
	
	public Diver(String name) {
		super(name, PlayerType.DIVER);
		specialActionDescription = "Move through 1 or more adjcent flooded and/or missing tiles for 1 action. (Must end your turn on a tile)";
		startLocation = Location.IRON_GATE;
	}
	
	/*
	 * Diver can move through tiles even if they have sunk 
	 */
	@Override
	public ArrayList<Direction> getMoveOptions() {
		ArrayList<Direction> legalDirections = new ArrayList<Direction>();
		
		Tile[][] tiles = Board.getInstance().getTiles();
		
		// Check tile exists at each possible move location
		if (xPos > 0) {
			if (!(tiles[xPos-1][yPos] instanceof NullTile)) {legalDirections.add(Direction.LEFT);}
		}
		if (xPos < 5) {
			if (!(tiles[xPos+1][yPos] instanceof NullTile)) {legalDirections.add(Direction.RIGHT);}
		}
		if (yPos > 0) {
			if (!(tiles[xPos][yPos-1] instanceof NullTile)) {legalDirections.add(Direction.UP);}
		}
		if (yPos < 5) {
			if (!(tiles[xPos][yPos+1] instanceof NullTile)) {legalDirections.add(Direction.DOWN);}
		}
		
		return legalDirections;
	}
	
	/*
	 * Diver doesn't use actions to move onto flooded/sunken tiles
	 */
	@Override
	public void move() {
		ArrayList<Direction> legalDirections = getMoveOptions();
		Direction d = playerui.promptDirection(legalDirections);
		if (d == null) {
			if (Board.getInstance().getTile(xPos, yPos).getState() == TileState.SUNK) {
				actionui.diverCantStopOnSunken();
				move();
			}
			return;
		}
		
		switch(d) {
			case LEFT:  xPos--; break;
			case RIGHT: xPos++; break;
			case UP:    yPos--; break;
			case DOWN:  yPos++; break;
			default:    return;
		}
		
		TileState state = Board.getInstance().getTile(xPos, yPos).getState();
		
		if (state == TileState.DRY) {
			actionsRemaining--;
		} else if (state == TileState.FLOODED){
			BoardView.getInstance().printBoard();
			if(playerui.promptContinueMoving("Would you like to continue moving?")) {
				move();
			} else {
				actionsRemaining--;
			}
		} else {
			actionui.diverCantStopOnSunken();
			move();
		}
	}
	
	/*
	 * Diver can continue to move through flooded or sunken tiles
	 */
	@Override
	public void tileIsSinking() {
		ArrayList<Direction> legalDirections = getMoveOptions();

		Direction d = playerui.playerSinking(this, legalDirections);
		
		if (d == null) {
			if (Board.getInstance().getTile(xPos, yPos).getState() == TileState.SUNK) {
				actionui.diverCantStopOnSunken();
				move();
			}
			return;
		}
		
		switch(d) {
			case LEFT:  xPos--; break;
			case RIGHT: xPos++; break;
			case UP:    yPos--; break;
			case DOWN:  yPos++; break;
			default:    return;
		}
		
		TileState state = Board.getInstance().getTile(xPos, yPos).getState();
		
		if (state == TileState.DRY) {
			actionsRemaining--;
		} else if (state == TileState.FLOODED){
			BoardView.getInstance().printBoard();
			if(playerui.promptContinueMoving("Would you like to continue moving?")) {
				tileIsSinking();
			} else {
				actionsRemaining--;
			}
		} else {
			actionui.diverCantStopOnSunken();
			tileIsSinking();
		}
	}
}
