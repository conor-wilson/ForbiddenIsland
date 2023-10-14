package ui;

import java.util.ArrayList;

import cards.Card;
import enums.Action;
import enums.Direction;
import enums.Location;
import enums.Option;
import enums.Treasures;
import players.Player;
import players.PlayerList;

public class PlayerView extends View{
	
	private static PlayerView view;
	
	/*
	 * Singleton instance
	 */
	public static PlayerView getInstance() {
		if (view == null) {
			view = new PlayerView();
		}
		return view;
	}
	
	/*
	 * Constructor
	 */
	private PlayerView() {}
	
	/*
	 * Display the hand of the given player
	 */
	public void displayHand(Player p) {
		System.out.println(p.getName() + " has these treasure cards:\n"
							+ "\t" + p.getHand()+ "\n");
	}
	
	/*
	 * Ask player what treasure card to give away
	 */
	public Card<Treasures> getCardToGive(ArrayList<Card<Treasures>> treasureCards) {
		return (Card<Treasures>) promptPlayerForItemFromList(treasureCards, "Which card would you like to give away?", cancel.ALLOW);
	}
	
	/*
	 * Ask player who to give a treasure card to
	 */
	public Player getPlayerToGiveTreasureTo(ArrayList<Player> players) {
		return (Player) promptPlayerForItemFromList(players, "Who would you like to give a card to?", cancel.ALLOW);
	}
	
	/*
	 * Force player to discard cards down to 5
	 */
	public Card<Treasures> handLimitExceeded(Player p) {
		return (Card<Treasures>) promptPlayerForItemFromList(p.getHand(), "Hand limit exceeded. Please choose a card to discard:", cancel.DISALLOW);
	}
	
	/*
	 * Method to tell player they can't give cards as their hand is empty
	 */
	public void noCardsToGive() {
		System.out.println("You can't give away any cards if you don't have any  to give!");
		System.out.println("It is not possible to give away special action cards.");		
	}

	/*
	 * Method called when a player tries to give a treasure card but there are no players on the same tile
	 */
	public void noOneToGiveTreasureTo() {
		System.out.println("There are no players you can give your treasure cards to.");
	}
	
	/*
	 * Player does not have enough of the correct treasure card to capture the treasure
	 */
	public void notEnoughTreasureCards(Treasures treasure) {
		System.out.println("You do not have enough " + treasure + " cards to capture the " + treasure + " treasure.");
		System.out.println("You need 4 matching treasure cards to capture a treasure.");
	}
	
	/*
	 * Tell players they can't capture a treasure as they are not on a treasure tile
	 */
	public void notOnTreasureTile() {
		System.out.println("You are not on a treasure tile.");
	}
	
	/*
	 * Tell player they don't have an action card if they try to use one they don't have
	 */
	public void playerHasNoActionCards(Player p) {
		System.out.println(p.getName() + " does not have any action cards.");
	}
	
	/*
	 * Give options for player to move when the tile they are on has sunk
	 */
	public Direction playerSinking(Player player, ArrayList<Direction> legalDirections) {		
		return (Direction) promptPlayerForItemFromList(legalDirections, player.getName() + " the tile you are on has sunk!", cancel.DISALLOW);
	}
	
	/*
	 * Get the action the player wants to take
	 */
	public Action promptActions(Player p) {
		ArrayList<Action> actions = new ArrayList<Action>();
		for (Action a : Action.values()) {
			actions.add(a);
		}
		
		return (Action) promptPlayerForItemFromList(actions, p.getName() + " has " + p.getActionsRemaining() + " actions remaining.\nWhat action would you like to take?", cancel.DISALLOW);
	}
	
	/*
	 * Ask diver if they want to continue moving when on a flooded tile
	 */
	public boolean promptContinueMoving(String string) {
		return promptYesNo("You are currently on a flooded tile so can continue to move without spending actions.\nWould you like to continue moving?");
	}
	
	/*
	 * Get a movement direction from the player
	 */
	public Direction promptDirection(ArrayList<Direction> legalDirections) {
		return (Direction) promptPlayerForItemFromList(legalDirections, "Which direction would you like to move?", cancel.ALLOW);
	}
	
	/*
	 * Display list of options to player which don't require actions
	 */
	public Option promptOptions() {
		ArrayList<Option> options = new ArrayList<Option>();
		for (Option o : Option.values()) {
			options.add(o);
		}
        return (Option) promptPlayerForItemFromList(options, "Options:", cancel.ALLOW);
	}
	
	/*
	 * Ask which player wants to play their special action card
	 */
	public Player promptPlayerUseSpecialAction() {
		ArrayList<String> playersWithCards = new ArrayList<String>();
		for (Player p : PlayerList.getInstance().getPlayers()) {
			playersWithCards.add(p.getName() + ":\t" + p.getHand());
		}
		String player = (String) promptPlayerForItemFromList(playersWithCards, "Who wants to use one of their action cards?", cancel.ALLOW);
		if (player == null) {return null;}
		for (int i = 0; i < playersWithCards.size(); i++) {
			if (playersWithCards.get(i) == player) {
				return PlayerList.getInstance().getPlayers().get(i);
			}
		}
		return null;
	}
	
	/*
	 * Ask players which tile they want to shore up
	 */
	public Location promptShoreUp(ArrayList<Location> shoreUpOptions) {
		return (Location) promptPlayerForItemFromList(shoreUpOptions, "What tile would you like to shore up?", cancel.ALLOW);
	}
	
	/*
	 * Called at the start of a turn
	 */
	public void newPlayersTurn(Player p) {
		BoardView.getInstance().printBoard();
		System.out.println(p.getName() + " it's your turn");
		displayHand(p);
	}
	
	/*
	 * Message when player is created to let player know of their role, abilities, and treasure cards
	 */
	public void playerCreated(Player player) {
		System.out.println(player.getName() + " you are the groups " + player.getType());
		displayPlayerSpecialAction(player);
		displayHand(player);
	}
	
	/*
	 * Tell player about the role they have and its abilities
	 */
	private void displayPlayerSpecialAction(Player player) {
		System.out.println("Special ablities:");
		System.out.println("\t" + player.getSpecialActionDescription());
	}
}
