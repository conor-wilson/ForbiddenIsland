package cards;

import java.util.ArrayList;

import board.Board;
import enums.*;
import gameplay.GameLogic;
import gameplay.special.Flight;
import players.Player;
import players.PlayerList;

public class HeliLiftCard extends Card<Treasures> implements ActionCard{
	
	/* Constructor */
	public HeliLiftCard() {
		super(Treasures.HELI_LIFT);
	}
	
	/*
	 * Use the Helicopter Lift Card. 
	 */
	public void use() {
		// Check if this is being used to win the game. 
		GameLogic.getInstance().checkWin();
		
		// Obtain the flight information from the User. 
		Flight lift = generateFlight();
		
		// If they haven't cancelled using the card, execute the flight. 
		if(lift != null) {
			lift.fly(); 
		}
	}
	
	/*
	 * Generate the flight details for the Helicopter Lift. 
	 */
	private Flight generateFlight() {
		ArrayList<Location> possibleFromLocations = new ArrayList<Location>();
		Location fromLocation; int[] fromCoords;
		
		ArrayList<Location> possibleToLocations = new ArrayList<Location>();
		Location toLocation; int[] toCoords;
		
		ArrayList<Player> possiblePassengers = new ArrayList<Player>();
		ArrayList<Player> passengers = new ArrayList<Player>();
		
		// Generate possible Locations to fly from
		for(Player player : PlayerList.getInstance().getPlayers()) {
			int[] coords = player.getCoords();
			Location l   = Board.getInstance().getLocation(coords[0],coords[1]);
			
			if(!possibleFromLocations.contains(l))
				possibleFromLocations.add(l);
		}
		
		// Allow the User to determine where they'd like to fly FROM. 
		fromLocation = ui.queryFromLocation(possibleFromLocations);
		if(fromLocation == null) { return null; }
		fromCoords	 = Board.getInstance().getCoords(fromLocation);
		
		// Generate possible Players to take the flight
		for(Player player : PlayerList.getInstance().getPlayers()) {
			int[] playerCoords = player.getCoords();
			if(playerCoords[0] == fromCoords[0] && playerCoords[1] == fromCoords[1])
				possiblePassengers.add(player);
		}
		
		// Allow the User to determine which Players are taking the flight. 
		passengers = ui.queryPassengers(possiblePassengers);
		if(passengers.isEmpty()) { return null; }
		
		// Generate possible landing Locations.
		possibleToLocations = Board.getInstance().getLandableLocations(fromLocation);
		
		// Allow the User to determine where they'd like to fly TO. 
		toLocation  = ui.queryToLocation(possibleToLocations);
		if(toLocation == null) { return null; }
		toCoords 	= Board.getInstance().getCoords(toLocation);
		
		// Return the Flight Plans. 
		return new Flight(passengers, toCoords);
	}
}
