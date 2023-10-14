package testSuite.playerRoles;

import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import board.Board;
import board.NullTile;
import board.Tile;
import cards.FloodDeck;
import cards.TreasureDeck;
import enums.Direction;
import enums.Location;
import enums.TileState;
import gameplay.GameLogic;
import players.roles.Explorer;
import ui.GameView;

public class JTestExplorer {
	private static Explorer explorer;

	/*
	 * Setup everything required to test the explorer
	 */
	@BeforeClass
	public static void setup() {
		FloodDeck.getInstance().setup();
		TreasureDeck.getInstance().setup();
		GameLogic.getInstance();
		Board.getInstance().setup();
		GameView.getInstance().setup();
		explorer = new Explorer("Test Explorer");
		explorer.setup();
	}

	/*
	 * Check explorer can shore up tiles adjacent, diagonal, on
	 */
	@Test
	public void shoreUpAllDirections() {
		System.out.println("\nTest: START of explorer shore up test.");
		Tile[][] tiles = Board.getInstance().getTiles();

		// Flood every tile
		for (Tile[] tileRow : tiles) {
			for (Tile t : tileRow) {
				if (t.getState() == TileState.DRY) {
					t.flood();
				}
			}
		}

		// Check shore up options
		int[] currentPos = explorer.getCoords();
		int xPos = currentPos[0];
		int yPos = currentPos[1];
		System.out.println("Test: Explorer at (" + xPos + "," + yPos + ").");
		ArrayList<Location> shoreUpOptions = explorer.getShoreUpOptions();
		System.out.println("Test: Shore up options\t" + shoreUpOptions);
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (xPos + x >= 0 && xPos + x <=5 && yPos + y >= 0 && yPos + y <= 5) {
					Tile t = tiles[xPos + x][yPos + y];
					System.out.println("Test: Tile under test\t" + t);
					System.out.println("Test: Tile state\t" + t.getState());
					System.out.println("Test: Tile location\t(" + (xPos+x) + "," + (yPos+y) + ").");
					assertEquals("Test: checking explorer shore up in direction (" + x + "," + y + ") relative to explorer.", !(t instanceof NullTile), shoreUpOptions.contains(t.getLocation()));
					System.out.println("Test: complete");
				}
			}
		}
		System.out.println("Test: END of explorer shore up test.");
	}

	/*
	 * Check explorer can move any diagonally
	 */
	@Test
	public void moveDiagonally() {
		System.out.println("\nTest: START of explorer diagonal movement test.");
		explorer.setPosition(2, 2);

		int[] currentPos = explorer.getCoords();
		int xPos = currentPos[0];
		int yPos = currentPos[1];
		System.out.println("Test: Explorer at (" + xPos + "," + yPos + ").");

		ArrayList<Direction> moveOptions = explorer.getMoveOptions();
		System.out.println("Test: Explorer move options: " + moveOptions);

		System.out.println("Test: Explorer can move up and left.");
		assertEquals("Test: checking explorer can move up and left", true, moveOptions.contains(Direction.UP_LEFT));

		System.out.println("Test: Explorer can move up and right.");
		assertEquals("Test: checking explorer can move up and right", true, moveOptions.contains(Direction.UP_RIGHT));

		System.out.println("Test: Explorer can move down and left.");
		assertEquals("Test: checking explorer can move down and left", true, moveOptions.contains(Direction.DOWN_LEFT));

		System.out.println("Test: Explorer can move down and right.");
		assertEquals("Test: checking explorer can move down and right", true, moveOptions.contains(Direction.DOWN_RIGHT));

		System.out.println("Test: END of explorer diagonal movement test.");
	}
}
