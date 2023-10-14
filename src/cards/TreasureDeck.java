package cards;

import java.util.ArrayList;
import java.util.Stack;

import enums.Treasures;

public class TreasureDeck extends Deck<Treasures> {
	
	/* Constants defining the number of each Treasure Card type in the Deck */
	private static int NUM_CARDS_PER_TYPE = 5;	// Standard Treasure Cards
	private static int NUM_HELI_LIFT = 3;		// Helicopter Lift Cards
	private static int NUM_SANDBAGS = 2;		// Sandbag Cards
	private static int NUM_WATERS_RISE = 3;		// Waters Rise Cards
	
	/* Singleton */
	private static TreasureDeck deck;
	public static TreasureDeck getInstance() {
		if (deck == null) {
			deck = new TreasureDeck();
		}
		return deck;
	}
	
	/* Constructor */
	private TreasureDeck() {
		super();
	}
	
	/*
	 * Initialise the Treasure Deck. 
	 */
	public void setup() {
		// Define Cards as type Treasures. 
		drawPile    = new Stack<Card<Treasures>>();
		discardPile = new ArrayList<Card<Treasures>>();
		
		// Add all standard treasure cards to discard pile. 
		for (int i = 0; i < NUM_CARDS_PER_TYPE; i++) {
			discardPile.add(new Card<Treasures>(Treasures.EARTH));
			discardPile.add(new Card<Treasures>(Treasures.WIND));
			discardPile.add(new Card<Treasures>(Treasures.FIRE));
			discardPile.add(new Card<Treasures>(Treasures.OCEAN));
		}
		
		// Add Helicopter Lift Cards and Sandbag Cards to discard pile. 
		for (int i = 0; i < NUM_HELI_LIFT; i++) {
			discardPile.add(new HeliLiftCard());
		}
		for (int i = 0; i < NUM_SANDBAGS; i++) {
			discardPile.add(new SandbagCard());
		}
		
		// Shuffle the discard pile, adding them to the draw pile. 
		shuffle();
	}
	
	/*
	 * Add Waters Rise Cards (called after players have draw their initial cards). 
	 */
	public void addWatersRiseCards() {
		
		for (int i = 0; i < NUM_WATERS_RISE; i++) {
			discardPile.add(new WatersRiseCard());
		}
		
		// Shuffle the whole deck. 
		fullShuffle();
	}
	
	
	/*
	 * Add a Card to discard pile. 
	 */
	@Override
	public void discard(Card<Treasures> c) {
		
		// If this is an Action Card, use it first. 
		if(c instanceof ActionCard) {
			((ActionCard)c).use();
		}
		
		discardPile.add(c);
	}
	
	/*
	 * Draw a card from the Treasure Deck and return it. 
	 */
	@Override
	public Card<Treasures> draw() {
		Card<Treasures> c = super.draw();
		
		// If this is a Waters Rise Card, discard it immediately. 
		if (c instanceof WatersRiseCard) {
			TreasureDeck.getInstance().discard(c);
			return null;
		}
		
		return c;
	}
}
