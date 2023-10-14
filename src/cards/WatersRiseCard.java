package cards;

import enums.Treasures;
import gameplay.GameLogic;

public class WatersRiseCard extends Card<Treasures> implements ActionCard{
	
	/* Constructor */
	protected WatersRiseCard() {
		super(Treasures.WATERS_RISE);
	}
	
	/* 
	 * Use Waters Rise Card (implemented in GameLogic.java). 
	 */
	@Override
	public void use() {
		GameLogic.getInstance().watersRise();
	}
}
