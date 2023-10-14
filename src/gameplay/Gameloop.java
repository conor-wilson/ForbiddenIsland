package gameplay;

import players.Player;
import players.PlayerList;

public class Gameloop {
	private static Gameloop gameloop;

	private PlayerList playerList;
	
	/*
	 * Singleton instance
	 */
	public static Gameloop getInstance() {
		if (gameloop == null) {
			gameloop = new Gameloop();
		}
		return gameloop;
	}
	
	/*
	 * Main game play loop
	 * Let each player take their turn forever
	 */
	public void runGame() {
		while (true) {
			for (Player p : playerList.getPlayers()) {
				p.takeTurn();
			}
		}
	}
	
	/*
	 * Constructor
	 */
	private Gameloop() {
		this.playerList = PlayerList.getInstance();
	}
}
