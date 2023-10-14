package board;

import enums.*;

public class TreasureTile extends Tile{
	
	/* Instance variables */
	private Treasures treasure;
	
	/* Constructor */
	public TreasureTile(Location location) {
		super(location);
		setTreasure();
	}
	
	/* Getters */
	public Treasures getTreasure() {
		return treasure;
	}
	
	/* Setters */
	
	/*
	 * Determines what Treasure should be found here and places it. 
	 */
	private void setTreasure() {
		if(location == Location.TEMPLE_OF_THE_MOON || location == Location.TEMPLE_OF_THE_SUN)
			treasure = Treasures.EARTH;
		else if(location == Location.CAVE_OF_EMBERS || location == Location.CAVE_OF_SHADOWS)
			treasure = Treasures.FIRE;
		else if(location == Location.HOWLING_GARDEN || location == Location.WHISPERING_GARDEN)
			treasure = Treasures.WIND;
		else if(location == Location.CORAL_PALACE || location == Location.TIDAL_PALACE)
			treasure = Treasures.OCEAN;
		else 
			System.out.println("DEBUG: This shouldn't be a treasure tile!");
	}
}
