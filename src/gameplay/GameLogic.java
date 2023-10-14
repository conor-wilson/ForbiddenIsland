package gameplay;

import java.util.HashSet;

import board.Board;
import board.Tile;
import board.TreasureTile;
import cards.FloodDeck;
import enums.Location;
import enums.TileState;
import enums.Treasures;
import players.Player;
import players.PlayerList;
import ui.GameView;

public class GameLogic {
	private static GameLogic gameLogic;
	private HashSet<Treasures> treasuresCaptured;
	private GameView ui;
	private int waterLevel;
	
	/*
	 * Singleton instance
	 */
	public static GameLogic getInstance() {
		if (gameLogic == null) {
			gameLogic = new GameLogic();
		}
		return gameLogic;
	}
	
	/*
	 * Set water level to difficulty level
	 */
	public void setup() {
		treasuresCaptured = new HashSet<Treasures>();
		waterLevel = ui.queryDifficulty();
	}
	
	/*
	 * Constructor
	 */
	private GameLogic() {
		treasuresCaptured = new HashSet<Treasures>();
		ui = GameView.getInstance();
		waterLevel = 1;
	}
	
	/*
	 * Keep track of captured treasures
	 */
	public void treasureCaptured(Treasures treasure) {
		treasuresCaptured.add(treasure);	
	}
	
	/*
	 * Called whenever a helicopter lift card is used
	 * Win the game if all other win conditions are met
	 */
	public void checkWin() {
		int[] foolsLanding = Board.getInstance().getCoords(Location.FOOLS_LANDING);
		
		// All players on fools landing
		for (Player p : PlayerList.getInstance().getPlayers()) {
			if (p.getCoords()[0] != foolsLanding[0] || p.getCoords()[1] != foolsLanding[1]) {
				return;
			}
		}
		
		// All treasures have been captured		
		if (!treasuresCaptured.contains(Treasures.EARTH)) {return;}
		if (!treasuresCaptured.contains(Treasures.FIRE))  {return;}
		if (!treasuresCaptured.contains(Treasures.OCEAN)) {return;}
		if (!treasuresCaptured.contains(Treasures.WIND))  {return;}
		
		// Win the game
		ui.win();
		System.exit(0);
	}
	
	/*
	 * Method called when flood card is drawn
	 */
	public TileState tileFlooded(Location location) {
		int[] coords = Board.getInstance().getCoords(location);
		TileState state = Board.getInstance().getTile(coords[0], coords[1]).getState();
		if (state == TileState.DRY) {
			ui.tileFlooded(location);
			return TileState.FLOODED;
		} else {
			ui.tileSunk(location);
			return TileState.SUNK;
		}
	}
	
	/*
	 * Check Lose conditions when a tile sinks
	 */
	public void tileSank(Tile t) {
		// Check if fools landing sank
		if (t.getLocation() == Location.FOOLS_LANDING) {
			ui.gameOverFoolsLandingSank();
			System.exit(0);
		}

		// Check if treasure tile sank
		if (t instanceof TreasureTile) {
			// Check if players has not captured the relevant treasure yet
			if (!treasuresCaptured.contains(((TreasureTile) t).getTreasure())) {
				// Get other treasure location
				Location otherLocation = null;
				switch (t.getLocation()) {
					case TEMPLE_OF_THE_MOON: otherLocation = Location.TEMPLE_OF_THE_SUN; break;
					case TEMPLE_OF_THE_SUN:  otherLocation = Location.TEMPLE_OF_THE_MOON; break;
					case WHISPERING_GARDEN:  otherLocation = Location.HOWLING_GARDEN; break;
					case HOWLING_GARDEN:     otherLocation = Location.WHISPERING_GARDEN; break;
					case CAVE_OF_EMBERS:     otherLocation = Location.CAVE_OF_SHADOWS; break;
					case CAVE_OF_SHADOWS:    otherLocation = Location.CAVE_OF_EMBERS; break;
					case CORAL_PALACE:       otherLocation = Location.TIDAL_PALACE; break;
					case TIDAL_PALACE:       otherLocation = Location.CORAL_PALACE; break;
					default:                 break;
				}
				
				// Check if other treasure location has not sunk
				Tile[][] tiles = Board.getInstance().getTiles();
				for (Tile[] tileRow : tiles) {
					for (Tile tile : tileRow) {
						if (tile.getLocation() == otherLocation && tile.getState() == TileState.SUNK) {
							ui.gameOverCannotCaptureTreasure((TreasureTile) t);
							System.exit(0);
						}
					}
				}
			}
		}
		
		// Player on sinking tile
		for (Player p : PlayerList.getInstance().getPlayers()) {
			if (Board.getInstance().getLocation(p.getCoords()[0], p.getCoords()[1]) == t.getLocation()) {
				p.tileIsSinking();
			}
		}
	}
	
	/*
	 * Increase water level
	 * Check to see if water level is above game over limit
	 */
	public void watersRise() {
		waterLevel++;
		if (waterLevel > 10) {
			ui.gameOverWaterLevelToHigh();
			System.exit(0);
		}
		FloodDeck.getInstance().shuffle();
		ui.watersRising();
	}
	
	/*
	 * Get the number of flood cards to draw based on the current water level
	 */
	public int numFloodCardsToDraw() {
		switch(waterLevel) {
			case 1:
			case 2: return 2;
			case 3:
			case 4:
			case 5: return 3;
			case 6:
			case 7: return 4;
			case 8:
			case 9: return 5;
			default:
				ui.gameOverWaterLevelToHigh();
				System.exit(0);
				return 24;
		}
	}
	
	/*
	 * Allow players to quit early
	 */
	public void quit() {
		if (ui.confirmQuit()) {
			System.exit(0);
		}
	}
	
	public HashSet<Treasures> getTreasuresCaptured() {return treasuresCaptured;}
	
	public int getWaterLevel() {return waterLevel;}
}
