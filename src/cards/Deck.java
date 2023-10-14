package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public abstract class Deck<E> {
	
	/* Instance variables */
	protected Stack<Card<E>> drawPile;			// Stack of undrawn cards
	protected ArrayList<Card<E>> discardPile;	// List of discarded cards
	
	/* Constructor */ 
	protected Deck() {
		this.drawPile    = new Stack<Card<E>>();
		this.discardPile = new ArrayList<Card<E>>();
	}
	
	/*
	 * Draw a card from the draw pile and return it. 
	 */
	public Card<E> draw() {
		// If there are no more cards left to draw, shuffle and reset first. 
		if (drawPile.empty()) {
			shuffle();
		}
		
		return drawPile.pop();
	}
	
	/*
	 * Add card to discard pile
	 */
	public void discard(Card<E> c) {
		discardPile.add(c);
	}
	
	/*
	 * Shuffle discard pile and place the cards back on top of the draw pile. 
	 */
	public void shuffle() {
		Collections.shuffle(discardPile);
		
		// Return the shuffled cards to the top of the Draw Pile. 
		for (int i = 0; i < discardPile.size(); i++) {
			drawPile.push(discardPile.get(i));
		}
		
		// Reset the Discard Pile as an empty ArrayList. 
		discardPile = new ArrayList<Card<E>>();
	}
	
	/*
	 * Shuffle all the cards in the discard pile and draw pile
	 */
	public void fullShuffle() {
		int drawPileSize = drawPile.size();
		while (drawPileSize-- > 0) {
			Card<E> c = drawPile.pop();
			discardPile.add(c);
		}
		shuffle();
	}
}
