package players;

import java.util.ArrayList;
import java.util.Collections;

import enums.PlayerType;
import players.roles.*;
import ui.GameView;

public class PlayerList {
	private static PlayerList list;

	private ArrayList<Player> players;
	
	/*
	 * Singleton instance
	 */
	public static PlayerList getInstance() {
		if (list == null) {
			list = new PlayerList();
		}
		return list;
	}
	
	/*
	 * Get number of players
	 * Randomize roles
	 * Get player names
	 * Create players
	 */
	public void setup() {
		players = new ArrayList<Player>();
		
		GameView ui = GameView.getInstance();
		int nPlayers = ui.queryNumberOfPlayers();
		
		// Randomize roles for each player
		ArrayList<PlayerType> listOfTypes = new ArrayList<PlayerType>();
		for (PlayerType type: PlayerType.values()) {
			listOfTypes.add(type);
		}
		Collections.shuffle(listOfTypes);
		// Create players
		players = new ArrayList<Player>();		
		for (int i = 0; i < nPlayers; i++) {
			String name = ui.queryPlayerName(i+1);
			Player p = createPlayer(name, listOfTypes.get(i));
			p.setup();
			players.add(p);
		}
	}
	
	/*
	 * Create a player based on the role they have been given
	 */
	private Player createPlayer(String name, PlayerType type) {
		switch(type) {
			case DIVER:     return new Diver(name);
			case ENGINEER:  return new Engineer(name);
			case EXPLORER:  return new Explorer(name);
			case MESSENGER: return new Messenger(name);
			case NAVIGATOR: return new Navigator(name);
			case PILOT:     return new Pilot(name);
			default:		return null;
		}
	}
	
	
	/* Getter for Players */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/*
	 * Constructor
	 */
	private PlayerList() {
		players = new ArrayList<Player>();
	}
}