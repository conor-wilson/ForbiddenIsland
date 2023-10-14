package gameplay.setup;

import board.Board;
import cards.FloodDeck;
import cards.TreasureDeck;
import gameplay.GameLogic;
import players.PlayerList;
import ui.View;
//import uix.ForbiddenFrame;

public class Setup {
	private static Setup setup;
	
	private View         ui;
//	private ForbiddenFrame    uix;
	private FloodDeck    floodDeck;
	private TreasureDeck treasureDeck;
	private Board        board;
	private PlayerList   playerList;
	private GameLogic    gameLogic;

	/*
	 * Singleton instance
	 */
	public static Setup getInstance() {
		if (setup == null) {
			setup = new Setup();
		}
		return setup;
	}
	
	/*
	 * Force all singletons to exist
	 */
	private Setup() {
//		this.uix          = ForbiddenFrame.getInstance();
		this.ui           = View.getInstance();
		this.floodDeck    = FloodDeck.getInstance();
		this.treasureDeck = TreasureDeck.getInstance();
		this.board        = Board.getInstance();
		this.playerList   = PlayerList.getInstance();
		this.gameLogic    = GameLogic.getInstance();
	}
	
	/*
	 * Make singletons run their setup
	 */
	public void runSetup() {
//		uix.setup();
		floodDeck.setup();
		treasureDeck.setup();
		gameLogic.setup();
		board.setup();
		playerList.setup();
		ui.setup();
		
		treasureDeck.addWatersRiseCards();
	}
}
