package testSuite.testSetups;

//import static org.junit.Assert.assertEquals;
//
//import org.junit.*;
//
//import board.Board;
//import board.NullTile;
//import board.Tile;
//import cards.FloodDeck;
//import enums.TileState;

public class JTestBoardSetup {
//	/*
//	 * Setup board
//	 */
//	@Before
//	public void boardSetup() {
//		FloodDeck.getInstance().setup();
//		Board.getInstance().setup();
//	}
//
//	/*
//	 * Check 6 tiles are flooded at the start
//	 * Check 0 tiles are sunken at the start
//	 * Check 12 null tiles
//	 */
//	@Test
//	public void startingTilesFlooded() {
//		System.out.println("\nTest: START of board setup test.");
//		int numTilesFlooded = 0;
//		int numTilesSunk = 0;
//		int numNullTiles = 0;
//
//		for (Tile [] tileRow : Board.getInstance().getTiles()) {
//			for (Tile t : tileRow) {
//				if (t.getState() == TileState.FLOODED) {
//					numTilesFlooded++;
//				} else if (t instanceof NullTile) {
//					numNullTiles++;
//				} else if (t.getState() == TileState.SUNK) {
//					numTilesSunk++;
//				}
//			}
//		}
//
//		System.out.println("Test: Checking 6 tiles are flooded");
//		assertEquals("Test: 6 tiles flooded after setup.", 6, numTilesFlooded);
//
//		System.out.println("Test: Checking 0 tiles are sunken");
//		assertEquals("Test: 0 tiles sunk after setup.", 0, numTilesSunk);
//
//		System.out.println("Test: Checking 12 tiles are null");
//		assertEquals("Test: 12 null tiles exist.", 12, numNullTiles);
//
//		System.out.println("Test: END of board setup test.");
//	}
}
