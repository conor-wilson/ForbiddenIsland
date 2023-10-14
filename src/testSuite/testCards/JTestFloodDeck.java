package testSuite.testCards;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import board.Board;
import cards.Card;
import cards.FloodDeck;
import enums.Location;
import enums.TileState;
import gameplay.GameLogic;
import ui.GameView;

public class JTestFloodDeck {
	/*
	 * Setup all components need for testing flooding
	 */
	@BeforeClass
	public static void initialisation() {
		GameView.getInstance();
		FloodDeck.getInstance().setup();
		Board.getInstance().setup();
		GameLogic.getInstance();
	}

	/*
	 * Confirm tile floods when its card is drawn
	 */
	@Test
	public void testTileIsFloodedOnDraw() {
		System.out.println("\nTest: START of flood tile when card drawn test");
		Card<Location> floodedLocation = FloodDeck.getInstance().draw();
		int[] coords = Board.getInstance().getCoords(floodedLocation.type);
		assertEquals("Test:   ", TileState.FLOODED, Board.getInstance().getTile(coords[0], coords[1]).getState());
		System.out.println("Test: END of flood tile when card drawn test");
	}
}
