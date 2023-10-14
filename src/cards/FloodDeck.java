package cards;

import java.util.ArrayList;
import java.util.Stack;

import board.Board;
import enums.Location;
import enums.TileState;
import gameplay.GameLogic;

public class FloodDeck extends Deck<Location> {
	
	/* Singleton */
	private static FloodDeck deck;
	public static FloodDeck getInstance() {
		if (deck == null) {
			deck = new FloodDeck();
		}
		return deck;
	}
	
	/* Constructor */
	private FloodDeck() {
		super();
	}
	
	/*
	 * Initialise the Flood Deck. 
	 */
	public void setup() {
		// Define Cards as type Location. 
		drawPile    = new Stack<Card<Location>>();
		discardPile = new ArrayList<Card<Location>>();
		
		// Create a card for each Location in the game and add them to the discard pile. 
		for (Location l : Location.values()) {
			Card<Location> c = new Card<Location>(l);
			discard(c);
		}
		
		// Shuffle discard pile, adding them to the draw pile. 
		shuffle();
	}
	
	/*
	 * Draw a card from the Flood Deck and return it. 
	 */
	@Override
	public Card<Location> draw() {
		Card<Location> c = super.draw();
		
		// Obtain the TileState of the corresponding Tile on the board. 
		TileState state = GameLogic.getInstance().tileFlooded(c.type);
		
		// If the Tile is DRY or FLOODED, add the card to the discard pile. 
		if (state != TileState.SUNK) {
			discard(c);
		}
		
		// Flood the corresponding tile. 
		Board.getInstance().flood(c);
		return c;
	}
}
