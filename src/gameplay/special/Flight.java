package gameplay.special;

import java.util.ArrayList;

import players.Player;

public class Flight {
	private ArrayList<Player> passengers;
	private int[] destination;
	
	public Flight(ArrayList<Player> passengers, int[] destinationCoords) {
		this.passengers = passengers;
		this.destination = destinationCoords;
	}
	
	/*
	 * Move players to destination
	 */
	public void fly() {
		for(Player passenger : passengers)
			passenger.setPosition(destination[0], destination[1]);
	}
}
