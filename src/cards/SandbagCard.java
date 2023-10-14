package cards;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import enums.*;

public class SandbagCard extends Card<Treasures> implements ActionCard{
	
	/* Constructor */
	public SandbagCard() {
		super(Treasures.SANDBAG);
	}
	
	/*
	 * Use the Sandbag Card. 
	 */
	public void use() {
		// Obtain the Location the User wishes to Shore Up. 
		ArrayList<Location> floodedLocations = generateFloodedLocations();
		Location l = ui.querySandbagLocation(floodedLocations);
		
		// If the action was cancelled, return without shoring up. 
		if(l == null) { return; }
		
		// Otherwise, Shore Up the desired Location. 
		Board.getInstance().shoreUp(l);
	}
	
	/*
	 * Generate the ArrayList of flooded locations that the user can Sandbag. 
	 */
	private ArrayList<Location> generateFloodedLocations() {
		ArrayList<Location> floodedLocations = new ArrayList<Location>();
		
		for(Tile[] tileRow : Board.getInstance().getTiles())
			for(Tile tile : tileRow)
				if(tile.getState() == TileState.FLOODED)
					floodedLocations.add(tile.getLocation());
		
		return floodedLocations;
	}
}
