package gameplay;

import gameplay.setup.Setup;

public class ForbiddenIsland {
	public static void main(String[] args) {
		Setup.getInstance().runSetup();
		Gameloop.getInstance().runGame();
	}
}
