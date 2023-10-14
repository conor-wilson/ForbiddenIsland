package board;

import java.util.*;

import cards.Card;
import cards.FloodDeck;
import enums.*;

public class Board {
	
	/* Instance variables */
	private static Board board;
	private Tile[][] tileArray;
	private Stack<Location> locationOrder;

	/* Singleton */
	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}
	
	/* Constructor */
	private Board() {
		this.tileArray = new Tile[6][6];
		this.locationOrder = new Stack<Location>();
		
	}
	
	/*
	 * Initialises the Board, laying out the Tiles and sinking the initial 6. 
	 */
	public void setup() {
		// Initialise the Location order and the Board layout
		setLocationOrder();
		initialiseBoard();
		
		// Sink the initial 6 Tiles
		FloodDeck floodDeck = FloodDeck.getInstance();
		for (int i = 0; i < 6; i++) {
			floodDeck.draw();
		}
	}
	
	/* Getters */
	public Tile[][] getTiles() {
		return this.tileArray;
	}
	
	/* 
	 * Returns the Tile at a specific set of coordinates. 
	 */
	public Tile getTile(int xPos, int yPos) {
		return tileArray[xPos][yPos];
	}

	/* 
	 * Returns the Location at a specific set of coordinates. 
	 */
	public Location getLocation(int i, int j) {
		Tile tile = tileArray[i][j];
		
		if(tile instanceof NullTile)
			return null;
		else
			return tile.getLocation();
	}

	/* 
	 * Returns the coordinates of a specific Location. 
	 */
	public int[] getCoords(Location location) {
		int[] tileCoords = new int[] {0,1,2,3,4,5};
		
		for(int i:tileCoords) {
			for(int j:tileCoords) {
				if(tileArray[i][j].getLocation() == location)
					return new int[] {i,j};
			}
		}
		
		return null;
	}
	
	/*
	 * Return the Locations on the board that the Pilot or the Helicopter Lift can fly to. 
	 */
	public ArrayList<Location> getLandableLocations(Location currentLocation) {
		ArrayList<Location> landableLocations = new ArrayList<Location>();
		
		for(Tile[] tileRow : tileArray)
			for(Tile tile : tileRow) 
				if(tile.getLocation() != currentLocation && tile.getState() != TileState.SUNK)
					landableLocations.add(tile.getLocation());
		
		return landableLocations;
	}
	
	/* 
	 * Generates the locations of the tiles and randomises their order (ie: locationOrder). 
	 */
	private void setLocationOrder() {
		
		// Add all the Location names to locationOrder. 
		Location locations[] = Location.values();
		for(Location location : locations)
			locationOrder.push(location);
		
		// Randomise the locations.
		Collections.shuffle(locationOrder);
	}
	
	/* 
	 * Generates Tiles and lays them out in the correct arrangement. 
	 */
	private void initialiseBoard() {
		int[] tileCoords = new int[] {0,1,2,3,4,5};
		
		for(int j:tileCoords) {
			for(int i:tileCoords) {
				if(isNullSpace(i,j))
					tileArray[i][j] = new NullTile();
				else if(isTreasureTile(locationOrder.peek())) 
					tileArray[i][j] = new TreasureTile(locationOrder.pop());
				else
					tileArray[i][j] = new Tile(locationOrder.pop());
			}
		}
	}
	
	/* 
	 * Returns whether a set of coordinates should be the position of a NullTile (ie: the 
	 * three Tiles at each corner). These spaces are found at coordinates (x,y) where
	 * (x+y)/9 has a remainder of 1 or 0, and where (x-y+5)/9 has a remainder of 1 or 0.
	 */
	private boolean isNullSpace(int x, int y) {
		return ((x+y)%9 <= 1 || (x-y+5)%9 <= 1);
	}
	
	/* 
	 * Returns whether a Location on the board is a TreasureTile. 
	 */ 
	private boolean isTreasureTile(Location location) {
		return 
				location == Location.TEMPLE_OF_THE_MOON || 
				location == Location.TEMPLE_OF_THE_SUN 	|| 
				location == Location.CAVE_OF_EMBERS 	|| 
				location == Location.CAVE_OF_SHADOWS 	|| 
				location == Location.HOWLING_GARDEN 	|| 
				location == Location.WHISPERING_GARDEN 	|| 
				location == Location.CORAL_PALACE 		|| 
				location == Location.TIDAL_PALACE;
	}
	
	/*
	 * Flood a tile on the TileArray. 
	 */
	public TileState flood(Card<Location> c) {
		for (Tile [] tileRow : tileArray) {
			for (Tile t : tileRow) {
				if (t.getLocation() == c.type) {
					t.flood();
					return t.getState();
				}
			}
		}
		return null;
	}
	
	/*
	 * Shore Up a tile on the TileArray. 
	 */
	public boolean shoreUp(Location l) {
		for (Tile [] tileRow : tileArray) {
			for (Tile t : tileRow) {
				if (t.getLocation() == l) {
					return t.shoreUp();
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String boardString = "";
		
		// Generate a simplified version of the board for debugging. 
		for(int x = 0; x < 6; x++) {
			for(int y = 0; y < 6; y++) {
				boardString += tileArray[y][x] + "\t";
			}
			boardString += "\n";
		}
		
		return boardString;
	}
}
