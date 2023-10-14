package ui;

import java.util.ArrayList;

import board.TreasureTile;
import cards.Card;
import enums.*;
import gameplay.GameLogic;
import players.Player;
import players.PlayerList;

public class GameView extends View {
	private static GameView view;
	
	/*
	 * Singleton instance
	 */
	public static GameView getInstance() {
		if (view == null) {
			view = new GameView();
		}
		return view;
	}
	
	/*
	 * Constructor
	 */
	private GameView() {}
	
	/* Setup functions */
	
	/*
	 * Ask players what difficulty they want to play on
	 */
	public int queryDifficulty() {
		ArrayList<String> levels = new ArrayList<String>();
		levels.add("NOVICE");
		levels.add("NORMAL");
		levels.add("ELITE");
		levels.add("LEGENDARY");
		
		String difficulty = (String) this.promptPlayerForItemFromList(levels, "What difficulty would you like to play on?", cancel.DISALLOW);
		
		switch (difficulty) {
			case "NOVICE":    return 1;
			case "NORMAL":    return 2;
			case "ELITE":     return 3;
			case "LEGENDARY": return 4;
			default:          return 1;
		}
	}
	
	/*
	 * Ask how many players there are
	 */
	public int queryNumberOfPlayers() {
		int nPlayers = -1;
		while (true) {
			System.out.println("How many players will be playing? [2-4]\n");
			try {
				nPlayers = Integer.parseInt(input.nextLine());
			} catch (Exception NumberFormatError) {
				nPlayers = -1;
			}
			if (nPlayers >= 2 && nPlayers <= 4) {
				break;
			}
			System.out.println("Invalid input. Input should be an integer between 2 and 4 inclusive\n");
		}
		
		return nPlayers;
	}
	
	/*
	 * Ask a player for their name
	 */
	public String queryPlayerName(int playerNumber) {
		System.out.println("Player " + playerNumber + " what is your name?\n");
		return input.nextLine();
	}
	
	/* Game play functions */
	
	/*
	 * Display the hands of every player
	 */
	public void displayAllHands() {
		System.out.println("Here are the cards in each players hands.");
		for (Player p : PlayerList.getInstance().getPlayers()) {
			boolean extraTab = (p.getName().length() < 16);
			System.out.println("\t" + p + ":\t" + (extraTab ? "\t" : "") + p.getHand());
		}
	}

	/*
	 * Make sure players are sure they want to quit the game
	 */
	public boolean confirmQuit() {
		return promptYesNo("Are you sure you want to quit?\nAll progress will be lost");
	}
	
	/*
	 * Message to let player know what treasure card they drew
	 */
	public void treasureCardDrawn(Card<Treasures> c) {
		System.out.println("You drew a " + c + " card.");
	}

	/*
	 * Print any time a waters rise card is drawn
	 */
	public void watersRising() {
		System.out.println("The waters are rising!");
		printWaterLevel();
	}
	
	/*
	 * Let players know the current water level
	 */
	public void printWaterLevel() {
		System.out.println("Current water level: " + GameLogic.getInstance().getWaterLevel());
	}

	/*
	 * Congratulate players when they capture a treasure
	 */
	public void treasureCaptured(Treasures treasure) {
		System.out.println("Congratulations! You have captured the " + treasure + " treasure");
	}
	
	/*
	 * Let players know when tile floods
	 */
	public void tileFlooded(Location location) {
		System.out.println(location + " has flooded.");
	}
	
	/*
	 * Let players know when tile has sunk
	 */
	public void tileSunk(Location location) {
		System.out.println(location + " has sunk!");
	}
	
	/* Game over methods */

	/*
	 * Message when the players have lost because Fools Landing sank
	 */
	public void gameOverFoolsLandingSank() {
		System.out.println("Oh no!");
		System.out.println("FOOLS_LANDING has sunk!");
		System.out.println("Game over :(");
	}
	
	/*
	 * Message when both treasure tile have sunk and the treasure has not yet been captured
	 */
	public void gameOverCannotCaptureTreasure(TreasureTile t) {
		System.out.println("Oh no!");
		System.out.println("" + t.getLocation() +" has sunk!");
		System.out.println("There's no way to get the " + t.getTreasure() + " treasure anymore!");
		System.out.println("Game over :(");
	}
	
	/*
	 * Message when a tile sinks with a player on it and no moves are available for them
	 */
	public void gameOverPlayerDrowned(Player player) {
		System.out.println("Oh no!");
		System.out.println(player.getName() +" has drowned!");
		System.out.println("Game over :(");
	}
	
	/*
	 * Message when the water level exceeds the game over limit
	 */
	public void gameOverWaterLevelToHigh() {
		System.out.println("Oh no!");
		System.out.println("The waters hive risen to high!");
		System.out.println("Game over :(");
	}

	/*
	 * Message when the players have beaten the game
	 */
	public void win() {
		System.out.println("Congratulations!");
		System.out.println("You have escaped the Forbidden Island with all the treasures!");
		System.out.println("You win!");
	}
}