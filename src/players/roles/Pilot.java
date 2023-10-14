package players.roles;

import java.util.ArrayList;

import board.Board;
import enums.Location;
import enums.PlayerType;
import gameplay.special.Flight;
import players.Player;
import ui.SpecialActionView;

public class Pilot extends Player {
	private boolean flown; // True if pilot has used the fly ability this turn
	protected SpecialActionView flyUI;

	/*
	 * Constructor
	 */
	public Pilot(String name) {
		super(name, PlayerType.PILOT);
		specialActionDescription = "Once per turn, fly to any tile on the island for 1 action.";
		startLocation = Location.FOOLS_LANDING;
		this.flyUI = SpecialActionView.getInstance();
	}
	
	/*
	 * Pilot can also fly once per turn as a movement
	 */
	@Override
	public void move() {
		boolean takeFlight = false;
		
		if(!flown) {
			takeFlight = flyUI.queryPilotFlight();
		}
		
		if(takeFlight) {
			Flight flight = generateFlight();
			if(flight == null) { return; }
			
			flight.fly();
			flown = true;
			actionsRemaining--;
		}
		else
			super.move();
	}
	
	/*
	 * Generate the flight details of the Pilot's flight move option. 
	 */
	public Flight generateFlight() {
		ArrayList<Player> pilotAsList = new ArrayList<Player>();
		pilotAsList.add(this);
		
		Location toLocation; int[] toCoords;
		
		// Generate possible landing Locations. 
		Location fromLocation = Board.getInstance().getLocation(xPos, yPos);
		ArrayList<Location> possibleToLocations = Board.getInstance().getLandableLocations(fromLocation);
		
		// Allow the User to determine where they'd like to fly TO. 
		toLocation  = flyUI.queryToLocation(possibleToLocations);
		if(toLocation == null) { return null; }
		toCoords 	= Board.getInstance().getCoords(toLocation);
		
		// Return the flight details.  
		return new Flight(pilotAsList, toCoords);
	}
	
	/*
	 * Set flown to false at the start of each turn
	 */
	@Override
	public void takeTurn() {
		flown = false;
		super.takeTurn();
	}
}
