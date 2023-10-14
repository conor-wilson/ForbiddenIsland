package testSuite;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import board.Board;
import board.NullTile;
import board.Tile;
import board.TreasureTile;
import cards.Card;
import cards.FloodDeck;
import enums.Location;
import enums.TileState;

public class JTestBoard {
	/*
	 * Create board
	 */
	@BeforeClass
	public static void setup() {
		FloodDeck.getInstance().setup();
		Board.getInstance().setup();
	}

	/*
	 * Flood every tile which won't lead to a game over
	 */
	@Test
	public void testTileCanBeFlooded() {
		System.out.println("\nTest: START of board flooding test.");
		for (Tile[] tileRow : Board.getInstance().getTiles()) {
			for (Tile tile : tileRow) {
				if (tile instanceof NullTile) {continue;}
				if (tile.getState() == TileState.DRY) {
					Board.getInstance().flood(new Card<Location>(tile.getLocation()));
					System.out.println("Test: Checking tile can be flooded.");
					assertEquals("Test: Checking tile floods.", TileState.FLOODED, tile.getState());
					System.out.println("Test: " + tile + "  flooded.");
				} else {
					// Prevent game overs
					if (tile.getLocation() == Location.FOOLS_LANDING || tile instanceof TreasureTile) {continue;}
					Board.getInstance().flood(new Card<Location>(tile.getLocation()));
					System.out.println("Test: Checking tile can be sunk.");
					assertEquals("Test: Checking tile sinks.", TileState.SUNK, tile.getState());
					System.out.println("Test: " + tile + "  sank.");
				}
			}
		}
		System.out.println("Test: END of board flooding test.");
	}

	/*
	 * Try to shore up every tile which is flooded or sunken
	 */
	@Test
	public void testTileCanBeShoredUp() {
		System.out.println("\nTest: START of board shore up test.");
		for (Tile[] tileRow : Board.getInstance().getTiles()) {
			for (Tile tile : tileRow) {
				if (tile instanceof NullTile) {continue;}
				if (tile.getState() == TileState.FLOODED) {
					Board.getInstance().shoreUp(tile.getLocation());
					System.out.println("Test: Checking flooded tile can be shored up.");
					assertEquals("Test: Checking flooded tile dry after shore up.", TileState.DRY, tile.getState());
					System.out.println("Test: " + tile + " shore up.");
				} else if (tile.getState() == TileState.SUNK) {
					Board.getInstance().shoreUp(tile.getLocation());
					System.out.println("Test: Checking sunken tile can't be shored up.");
					assertEquals("Test: Checking sunken tile still sunken after shore up.", TileState.SUNK, tile.getState());
					System.out.println("Test: " + tile + " not shored up.");
				}
			}
		}
		System.out.println("Test: END of board shore up test.");
	}
}
