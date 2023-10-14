package ui;

import java.util.ArrayList;

import cards.Card;
import enums.Location;
import enums.Treasures;
import players.Player;

public class SpecialActionView extends View {
	private static SpecialActionView ui;
	private enum PilotMove {MOVE, FLY};
		
	/*
	 * Singleton instance
	 */
	public static SpecialActionView getInstance() {
		if (ui == null) {
			ui = new SpecialActionView();
		}
		return ui;
	}
	
	/*
	 * Constructor
	 */
	private SpecialActionView() {}
	
	/* 
	 * Prompt User to select the Location they'd like to shore-up with the Sandbag Card. 
	 */ 
	public Location querySandbagLocation(ArrayList<Location> floodedLocations) {
		return (Location) promptPlayerForItemFromList(floodedLocations, "Which of the following FLOODED tiles would you like to Shore Up with the Sandbag?" , cancel.ALLOW);
	}
	
	/*
	 * Prompt User to select where they'd like to fly from with the Helicopter Lift. 
	 */
	public Location queryFromLocation(ArrayList<Location> possibleFromLocations) {
		return (Location) promptPlayerForItemFromList(possibleFromLocations, "Which of these tiles would you to fly FROM?" , cancel.ALLOW);
	}
	
	/*
	 * Prompt User to select which players will be taking the Helicopter Lift. 
	 */
	public ArrayList<Player> queryPassengers(ArrayList<Player> possiblePassengers) {
		ArrayList<Player> passengers = new ArrayList<Player>();
		
		for(Player player : possiblePassengers) {
			boolean include = promptYesNo("Will " + player.getName() + " be taking the Helicopter lift?"); // to " + toLocation.toString() + "?");
			if(include) { passengers.add(player); }
		}
		
		return passengers;
	}
	
	/*
	 * Prompt User to select where they'ed like to fly to with Helicopter Lift or as Pilot. 
	 */
	public Location queryToLocation(ArrayList<Location> possibleToLocations) {
		return (Location) promptPlayerForItemFromList(possibleToLocations, "Which of these tiles would you to fly TO?" , cancel.ALLOW);
	}
	
	/*
	 * Ask pilot if they want to fly or move normally
	 */
	public boolean queryPilotFlight() {
		ArrayList<PilotMove> moveOptions = new ArrayList<PilotMove>();
		for(PilotMove option : PilotMove.values())
			moveOptions.add(option);
		
		PilotMove move = (PilotMove) promptPlayerForItemFromList(moveOptions, "You have not used your Pilot's Flight ability yet. You can use this once per turn.\n "
				+ "What kind of movement would you like to make?", cancel.ALLOW);
		
		if(move == PilotMove.FLY)
			return true;
		else
			return false;
	}

	/*
	 * Tell diver that they can't stop on a sunken tile
	 */
	public void diverCantStopOnSunken() {
		System.out.println("You are on a tile that sunk! You must keep moving.");
	}

	public Location querySandbag() {
		while (true) {
			System.out.println("Which tile would you like to shore-up with the Sandbag?\n"
								+ "Inut the name of the location on the tile.\n");
			
			ArrayList<String> locationStrings = new ArrayList<String>();
			for(Location location : Location.values())
				locationStrings.add(location.toString());
			
			String output = input.nextLine();
			if(locationStrings.contains(output))
				return Location.valueOf(output);
			
			System.out.println("Invalid input. Input should be either the name of a location on a tile.");
		}
	}

	/*
	 * Ask players if they want to use a special action card before drawing from the flood deck
	 */
	public boolean useSpecialActionBeforeFlood() {
		return promptYesNo("Would you like to use a special action card before drawing flood cards?");
	}

	/*
	 * Ask player which special action card to use
	 */
	public Card<Treasures> useSpecialActionCard(ArrayList<Card<Treasures>> cards) {
		return (Card<Treasures>) promptPlayerForItemFromList(cards, "Which card would you like to use?", cancel.ALLOW);
	}
}
