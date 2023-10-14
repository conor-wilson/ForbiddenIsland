package testSuite.testSetups;

//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//
//import org.junit.*;
//
//import board.Board;
//import cards.FloodDeck;
//import cards.TreasureDeck;
//import enums.Location;
//import gameplay.GameLogic;
//import players.Player;
//import players.roles.*;
//import ui.GameView;

public class JTestPlayerSetup {
//	private ArrayList<Player> players;
//
//	/*
//	 * Create an instance of every player type without user input
//	 */
//	@Before
//	public void playersSetup() {
//		FloodDeck.getInstance().setup();
//		TreasureDeck.getInstance().setup();
//		Board.getInstance().setup();
//		GameLogic.getInstance();
//		GameView.getInstance().setup();
//
//		players = new ArrayList<Player>();
//
//		players.add(new Diver("Diver"));
//		players.add(new Engineer("Engineer"));
//		players.add(new Explorer("Explorer"));
//		players.add(new Messenger("Messenger"));
//		players.add(new Navigator("Navigator"));
//		players.add(new Pilot("Pilot"));
//
//		for (Player p : players) {
//			p.setup();
//		}
//	}
//
//	/*
//	 * Check all players have 2 treasure cards
//	 */
//	@Test
//	public void startWithTwoTreasureCards() {
//		System.out.println("\nTest: START of player starts with 2 treasure cards test.");
//		for (Player p : players) {
//			System.out.println("Test: Checking " + p + " has 2 treasure cards");
//			assertEquals("Test: Player starts with 2 treasure cards.", 2, p.getHand().size());
//		}
//		System.out.println("Test: END of player starts with 2 treasure cards test.");
//	}
//
//	/*
//	 * Check all players start in the correct location
//	 */
//	@Test
//	public void startingLocation() {
//		System.out.println("\nTest: START of player starting location test.");
//		for (Player p : players) {
//			Location expectedStart = null;
//			switch (p.getType()) {
//				case MESSENGER: expectedStart = Location.SILVER_GATE; break;
//				case ENGINEER:  expectedStart = Location.BRONZE_GATE; break;
//				case PILOT:     expectedStart = Location.FOOLS_LANDING; break;
//				case EXPLORER:  expectedStart = Location.COPPER_GATE; break;
//				case DIVER:     expectedStart = Location.IRON_GATE; break;
//				case NAVIGATOR: expectedStart = Location.GOLD_GATE; break;
//			}
//			System.out.println("Test: checking " + p + " starts on " + expectedStart);
//			assertEquals("Test: " + p.getType() + " starting location.", expectedStart, Board.getInstance().getLocation(p.getCoords()[0], p.getCoords()[1]));
//		}
//		System.out.println("Test: End of player starting location test.");
//	}
}
