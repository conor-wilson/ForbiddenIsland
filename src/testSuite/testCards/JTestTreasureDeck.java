package testSuite.testCards;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import cards.Card;
import cards.TreasureDeck;
import enums.Treasures;
import gameplay.GameLogic;

public class JTestTreasureDeck {
	private int currentWaterLevel;

	/*
	 * Create deck and gamelogic
	 * Add waters rise cards to the deck
	 */
	@Before
	public void setup() {
		TreasureDeck.getInstance().setup();
		TreasureDeck.getInstance().addWatersRiseCards();
		GameLogic.getInstance();
		currentWaterLevel = GameLogic.getInstance().getWaterLevel();
	}

	/*
	 * Draw all cards and check that the water level is changing as expected
	 */
	@Test
	public void testWatersRise() {
		System.out.println("\nTest: START of drawing treasure cards test to check waters rise.");
		for (int i = 0; i < 24; i++) {
			Card<Treasures> card = TreasureDeck.getInstance().draw();
			if (card == null) {
				System.out.println("Test: Waters rise drawn. Checking water level rose.");
				assertEquals("Test: Waters rise drawn. Checking water level rose.", ++currentWaterLevel, GameLogic.getInstance().getWaterLevel());
			} else {
				System.out.println("Test: Non waters rise card drawn. Checking no change in water level.");
				assertEquals("Test: Non waters rise card drawn. Checking no change in water level", currentWaterLevel, GameLogic.getInstance().getWaterLevel());
			}
		}
		System.out.println("Test: END of drawing treasure cards test to check waters rise.");
	}
}
