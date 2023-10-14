package players;
import java.util.ArrayList;

import board.Board;
import board.Tile;
import board.TreasureTile;
import cards.ActionCard;
import cards.Card;
import cards.FloodDeck;
import cards.TreasureDeck;
import enums.Action;
import enums.Direction;
import enums.Location;
import enums.Option;
import enums.PlayerType;
import enums.TileState;
import enums.Treasures;
import gameplay.GameLogic;
import ui.BoardView;
import ui.GameView;
import ui.PlayerView;
import ui.SpecialActionView;

public class Player {
	private String name;
	private PlayerType type;
	protected int xPos;
	protected int yPos; // (0, 0) is at the north west corner of the island and (5, 5) south east
	protected int actionsRemaining;
	private ArrayList<Card<Treasures>> treasureCards;
	protected GameView ui;
	protected PlayerView playerui;
	protected SpecialActionView actionui;
	protected String specialActionDescription;
	protected Location startLocation;
	
	public Player(String name, PlayerType type) {
		this.name = name;
		this.type = type;
		this.actionsRemaining = 3;
		this.treasureCards = new ArrayList<Card<Treasures>>();
		this.ui = GameView.getInstance();
		this.playerui = PlayerView.getInstance();
		this.actionui = SpecialActionView.getInstance();
	}
	
	/*
	 * Set starting location
	 * Draw starting treasure cards
	 */
	public void setup() {
		setStartLocation();
		int cardsToDraw = 2;
		while (cardsToDraw > 0) {
			Card<Treasures> c = TreasureDeck.getInstance().draw();
			if (c.type == Treasures.WATERS_RISE) {
				TreasureDeck.getInstance().discard(c);
			} else {
				treasureCards.add(c);
				cardsToDraw--;
			}
			TreasureDeck.getInstance().fullShuffle();
		}
		playerui.playerCreated(this);
	}
	
	/*
	 * Allow player to move in a legal direction
	 */
	public void move() {
		ArrayList<Direction> legalDirections = getMoveOptions();
		Direction d = playerui.promptDirection(legalDirections);
		
		if (d == null) {return;}
		
		switch(d) {
			case LEFT:       xPos--; break;
			case RIGHT:      xPos++; break;
			case UP:         yPos--; break;
			case DOWN:       yPos++; break;
			case UP_LEFT:    xPos--;	yPos--; break;
			case UP_RIGHT:   xPos++; yPos--;	break;
			case DOWN_LEFT:	 xPos--; yPos++; break;
			case DOWN_RIGHT: xPos++; yPos++; break;
			default:         return;
			}
		actionsRemaining--;
	}

	/*
	 * Allow players to shore up a flooded tile for an action
	 */
	protected void shoreUp() {
		ArrayList<Location> shoreUpOptions = getShoreUpOptions();		
		
		if (shoreUpOptions.size() == 0) {
			return;
		}
		
		Location location = playerui.promptShoreUp(shoreUpOptions);
		
		if (location != null) {
			Board.getInstance().shoreUp(location);
			actionsRemaining--;
		}
	}
	
	/*
	 * Allow player to give a treasure card to another player
	 */
	public void giveTreasure() {
		ArrayList<Card<Treasures>> cards = getTreasureCardsOnly();
		if (cards.size() == 0) {
			playerui.noCardsToGive();
			return;
		}
		
		ArrayList<Player> players = playersOnMyTile();
		if (players.size() == 0) {
			playerui.noOneToGiveTreasureTo();
			return;
		}
		
		Player p = playerui.getPlayerToGiveTreasureTo(players);
		
		Card<Treasures> c = playerui.getCardToGive(cards);
		if (c == null) {return;}
				
		if (p.acceptCard(c)) {
			discardTreasure(c);
			actionsRemaining--;
		}
	}

	/*
	 * List of all other players on the same tile
	 */
	private ArrayList<Player> playersOnMyTile() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player p : PlayerList.getInstance().getPlayers()) {
			if (p.getCoords()[0] == getCoords()[0] && p.getCoords()[1] == getCoords()[1] && p.type != type) {
				players.add(p);
			}
		}
		return players;
	}

	/*
	 * Remove first instance of treasure card from hand
	 * Add it to discard pile
	 */
	protected void discardTreasure(Card<Treasures> c) {
		for (int i = 0; i < treasureCards.size(); i++) {
			if (treasureCards.get(i) == c) {
				treasureCards.remove(i);
				break;
			}
		}
		TreasureDeck.getInstance().discard(c);
	}
	
	/*
	 * When offered a card make sure there is space in your hand
	 */
	public boolean acceptCard(Card<Treasures> c) {
		treasureCards.add(c);

		if (treasureCards.size() > 5) {
			Card<Treasures> card = playerui.handLimitExceeded(this);
			discardTreasure(card);
			if (card.type == c.type) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Draw cards and add them to players hand
	 */
	public void drawTreasure(int numToDraw) {
		while (numToDraw-- > 0) {
			Card<Treasures> c = TreasureDeck.getInstance().draw();
			if (c != null) {
				treasureCards.add(c);
				ui.treasureCardDrawn(c);
			}
		}
		
		// Prevent hand exceeding 5
		while (getHand().size() > 5) {
			Card<Treasures> c = playerui.handLimitExceeded(this);
			discardTreasure(c);
		}
	}
	
	/*
	 * Set actions to zero to end turn
	 */
	public void endTurn() {
		actionsRemaining = 0;
	}
	
	/*
	 * Called when player tries to capture a treasure
	 * 
	 * Checks player is on a treasure tile
	 * Checks player has at least 4 correct treasure cards
	 * Informs game logic treasure is captured
	 * Discards 4 treasure cards
	 */
    private void captureTreasure() {
    	Tile currentTile = Board.getInstance().getTile(xPos, yPos);
    	if (!(currentTile instanceof TreasureTile)) {
    		playerui.notOnTreasureTile();
    		return;
    	}
    	
    	Treasures treasure = ((TreasureTile) currentTile).getTreasure();
    	ArrayList<Card<Treasures>> cards = new ArrayList<Card<Treasures>>();
    	for (Card<Treasures> c : treasureCards) {
    		if (c.type == treasure) {
    			cards.add(c);
    		}
    	}
    	
    	if (cards.size() >= 4) {
    		for (int i = 0; i < 4; i++) {
    			discardTreasure(cards.get(i));
    		}
    		GameLogic.getInstance().treasureCaptured(treasure);
    		actionsRemaining--;
			ui.treasureCaptured(treasure);
    	} else {
    		playerui.notEnoughTreasureCards(treasure);
    	}
    }
	
	@Override
	public String toString() {
		return getName();
	}

	/*
	 * Perform all turn actions
	 */
	public void takeTurn() {
		playerui.newPlayersTurn(this);
		takeActions();
		drawTreasure(2);
		boolean useAction = true;
		while (useAction) {
			useAction = checkUseSpecialActionCard();
		}
		drawFloodCards();
	}

	/*
	 * Allow player to take up to 3 actions
	 */
	protected void takeActions() {
		while (actionsRemaining > 0) {
			Action action = playerui.promptActions(this);
			performAction(action);
			BoardView.getInstance().printBoard();
		}
		// Reset actions for next turn
		actionsRemaining = 3;
	}
	
	/*
	 * Perform action provided
	 */
	private void performAction(Action action) {
		switch (action) {
			case MOVE:
				move();
				break;
			case SHORE_UP:
				shoreUp();
				break;
			case GIVE_TREASURE:
				giveTreasure();
				break;
			case CAPTURE_TREASURE:
				captureTreasure();
				break;
			case USE_SPECIAL_ACTION_CARD:
				useSpecialAction();
				break;
			case OPTIONS:
				performOption();
				break;
		}
	}

	/*
	 * Perform option provided by user
	 */
	private void performOption() {
		Option opt = playerui.promptOptions();
		if (opt == null) {return;}
		switch (opt) {
			case DISPLAY_ALL_HANDS:	ui.displayAllHands(); return;
			case END_TURN:		    endTurn(); return;
			case QUIT:			    GameLogic.getInstance().quit();
			default:			    return;
		}
	}
	
	/*
	 * Allow any player to use their special action cards
	 */
	private void useSpecialAction() {
		Player p = playerui.promptPlayerUseSpecialAction();
		if (p == null) {return;}

		ArrayList<Card<Treasures>> cards = p.getSpecialActionCards();
		if (cards == null) {
			playerui.playerHasNoActionCards(p);
			return;
		}
		
		Card<Treasures> c = actionui.useSpecialActionCard(cards);
		if(c == null) return;
		
		p.discardTreasure(c);
	}
	
	/*
	 * Check if players what to use a special action card
	 */
	private boolean checkUseSpecialActionCard() {
		boolean use = actionui.useSpecialActionBeforeFlood();
		if (use) {
			useSpecialAction();
		}
		return use;
	}
	
	/*
	 * Draw flood cards up to the water level
	 */
	protected void drawFloodCards() {
		int cardsToDraw = GameLogic.getInstance().numFloodCardsToDraw();
		while (cardsToDraw-- > 0) {
			FloodDeck.getInstance().draw();
		}
	}

	/*
	 * Find legal move for player if they are on a sinking tile
	 * If they can't move game over
	 */
	public void tileIsSinking() {
		ArrayList<Direction> legalDirections = getMoveOptions();
		
		if (legalDirections.size() == 0) {
			ui.gameOverPlayerDrowned(this);
			System.exit(0);
		} else {
			Direction d = playerui.playerSinking(this, legalDirections);
			if (d == null) return;
			switch(d) {
				case LEFT:        xPos--; return;
				case RIGHT:       xPos++; return;
				case UP:          yPos--; return;
				case DOWN:        yPos++; return;
				case UP_LEFT:     xPos--; yPos--; return;
				case UP_RIGHT:    xPos++; yPos--; return;
				case DOWN_LEFT:   xPos--; yPos++; return;
				case DOWN_RIGHT:  xPos++; yPos++; return;
				default: return;
			}
		}
	}
	
	/* Getter and Setter methods */

	/*
	 * Set staring location based on players type
	 */
	public void setStartLocation() {
		Board board = Board.getInstance();
		
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				if (board.getTiles()[x][y].getLocation() == startLocation) {
					xPos = x;
					yPos = y;
					return;
				}
			}
		}
		System.out.println("DEBUG: Program should never get here. Could not find starting location [" + startLocation + "] for player " + name);
	}

	/* Getter for String Representation of Player on the BoardVisualiser. */
	public String getCounter() {
		String counter = type.toString().toLowerCase();
		char[] counterChars = new char[counter.length()];
		
		counter.getChars(0, counter.length(), counterChars, 0);
		counterChars[0] = Character.toUpperCase(counterChars[0]);
		
		counter = "";
		for(char c : counterChars)
			counter += c;
	
		return counter;
	}

	/*
	 * Find all tiles that the player could move to
	 */
	public ArrayList<Direction> getMoveOptions() {
		ArrayList<Direction> legalDirections = new ArrayList<Direction>();
		
		Tile[][] tiles = Board.getInstance().getTiles();
		
		// Check tile exists at each possible move location
		if (xPos > 0) {
			if (tiles[xPos-1][yPos].getState() != TileState.SUNK) {legalDirections.add(Direction.LEFT);}
		}
		if (xPos < 5) {
			if (tiles[xPos+1][yPos].getState() != TileState.SUNK) {legalDirections.add(Direction.RIGHT);}
		}
		if (yPos > 0) {
			if (tiles[xPos][yPos-1].getState() != TileState.SUNK) {legalDirections.add(Direction.UP);}
		}
		if (yPos < 5) {
			if (tiles[xPos][yPos+1].getState() != TileState.SUNK) {legalDirections.add(Direction.DOWN);}
		}
		
		return legalDirections;
	}

	/*
	 * List of tiles player could shore up
	 * Must be on or adjacent to tile
	 * Tile must be flooded
	 */
	public ArrayList<Location> getShoreUpOptions() {
		ArrayList<Location> shoreUpOptions = new ArrayList<Location>();
		
		Tile[][] tiles = Board.getInstance().getTiles();
		
		// Check tile exists at each possible location
		if (tiles[xPos][yPos].getState() == TileState.FLOODED)       {shoreUpOptions.add(tiles[xPos][yPos].getLocation());}
		if (xPos > 0) {
			if (tiles[xPos-1][yPos].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos-1][yPos].getLocation());}
		}
		if (xPos < 5) {
			if (tiles[xPos+1][yPos].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos+1][yPos].getLocation());}
		}
		if (yPos > 0) {
			if (tiles[xPos][yPos-1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos][yPos-1].getLocation());}
		}
		if (yPos < 5) {
			if (tiles[xPos][yPos+1].getState() == TileState.FLOODED) {shoreUpOptions.add(tiles[xPos][yPos+1].getLocation());}
		}
		
		return shoreUpOptions;
	}

	/*
	 * Get all special action cards in hand
	 */
	public ArrayList<Card<Treasures>> getSpecialActionCards() {
		ArrayList<Card<Treasures>> cards = new ArrayList<Card<Treasures>>();
		for (Card<Treasures> c : treasureCards) {
			if (c instanceof ActionCard) {
				cards.add(c);
			}
		}
		return cards;
	}

	/*
	 * List of treasure cards in hand without special action cards
	 */
	protected ArrayList<Card<Treasures>> getTreasureCardsOnly() {
		ArrayList<Card<Treasures>> cards = new ArrayList<Card<Treasures>>();
		for (Card<Treasures> c : treasureCards) {
			if (!(c instanceof ActionCard)) {
				cards.add(c);
			}
		}
		return cards;
	}

	/*
	 * Move player to location after flying
	 * Return true if successful
	 */
	public boolean setPosition(int i, int j) {
		Tile t = Board.getInstance().getTile(i, j);
		if (t.getState() != TileState.SUNK) {
			xPos = i;
			yPos = j;
			return true;
		}
		return false;
	}
	
	/*
	 * Returns a description of what the players special skill is
	 */
	public String getSpecialActionDescription() {
		return specialActionDescription;
	}
	
	/*
	 * Return the coordinates of the player on the board
	 */
	public int[] getCoords() {
		int[] pos = {this.xPos, this.yPos};
		return pos;
	}
	
	/*
	 * Gets name and role of player
	 */
	public String getName() {
		return (name + " (" + type + ")");
	}
	
	public ArrayList<Card<Treasures>> getHand() {return treasureCards;}
	
	public PlayerType getType() {return type;}
	
	public int getActionsRemaining() {return actionsRemaining;}
}