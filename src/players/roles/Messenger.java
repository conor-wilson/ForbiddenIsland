package players.roles;

import java.util.ArrayList;

import cards.Card;
import enums.Location;
import enums.PlayerType;
import enums.Treasures;
import players.Player;
import players.PlayerList;

public class Messenger extends Player {

	public Messenger(String name) {
		super(name, PlayerType.MESSENGER);
		specialActionDescription = "Give treasure cards to a player anywhere on the island for 1 action per card.";
		startLocation = Location.SILVER_GATE;
	}
	
	/*
	 * Messenger can give anyone a card anywhere
	 */
	@Override
	public void giveTreasure() {
		ArrayList<Card<Treasures>> cards = getTreasureCardsOnly();
		if (cards.size() == 0) {
			playerui.noCardsToGive();
			return;
		}
		
		ArrayList<Player> others = PlayerList.getInstance().getPlayers();
		for (int i = 0; i < others.size(); i++) {
			if (others.get(i) == this) {
				others.remove(i);
				break;
			}
		}
		
		Player p = playerui.getPlayerToGiveTreasureTo(others);
		
		Card<Treasures> c = playerui.getCardToGive(cards);
		if (c == null) {return;}
				
		if (p.acceptCard(c)) {
			discardTreasure(c);
			actionsRemaining--;
		}
	}
}
