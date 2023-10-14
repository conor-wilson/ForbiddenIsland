package ui;

import java.util.ArrayList;
import java.util.Arrays;

import board.*;
import players.*;
import enums.*;
import gameplay.GameLogic;

public class BoardView {
	/* Static Singleton Instance */
	private static BoardView visualiser;
	
	/* Instance variables to be visualised on the board. */ 
	private Board 	 board;
	private Tile[][] tileArray;
	private ArrayList<Player> players;
	
	/* Constants for spacing the strings that will be printed. */
	private static double tileWidth = 21;
	private static double numTilesWidth = 6;
	private static double boardWidth = numTilesWidth*(tileWidth+1)+1;
	
	/* Filler characters for spacing. */
	private static char waterChar = ' ';
	private static char spaceChar = ' ';
	private String tileTop;
	
	/* Blank Player used as a placeholder when the number of Players is not the maximum. */
	private static Player blankPlayer = new Player(" ", null);
	
	
	/* Class Constructor */
	private BoardView() {
		this.board 		= Board.getInstance();
		this.tileArray  = board.getTiles();
		this.tileTop 	= align("", tileWidth+1, '-');
	}
	
	/* Singleton Instance */ 
	public static BoardView getInstance() {
		if (visualiser == null) {
			visualiser = new BoardView();
		}
		return visualiser;
	} 
	
	/* Setup method that instantiates the Players. */
	public void setup() {
		this.players = PlayerList.getInstance().getPlayers();
	}
	
	/* Generate and print the board.  */
	public void printBoard() {
		
		Tile[] previousRow = new Tile[6];
		Arrays.fill(previousRow, new NullTile());
		Tile[][] displayTileArray = new Tile[6][6];
		
		// Flip the Tile array along the diagonal so that it is displayed correctly
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++)
				displayTileArray[i][j] = tileArray[j][i];
		}
		
		// For each row of Tiles, generate and print them to the user.
		for(Tile[] tileRow : displayTileArray) {
			System.out.println(printableTileRow(tileRow, previousRow));
			previousRow = tileRow;
		}
		
		// Print the bottom of the Tiles.
		System.out.println(align(tileTop+tileTop+'-',boardWidth,waterChar));

		printTreasuresCaptured();
	}

	/* For a row of Tiles, generate the String to represent them to the user, centering them to the Board. */
	private String printableTileRow(Tile[] tileRow, Tile[] previousRow) {
		
		String output = "";
		String s;
		
		// Loop that generates all six lines of text in each Tile row: 
		for(int i : new int[]{0,1,2,3,4,5}) {
			
			// Initialise the String for the line in the Tile row to the appropriate first character. 
			switch(i) {
			case 0:  s = "-"; break;
			default: s = "|"; break;
			}
			
			// Cycle through each of the Tiles in the row.
			for(int j = 0; j < tileRow.length ; j++) {
				// If the Tile is not a NullTile, create the String for the line. 
				if(!(tileRow[j] instanceof NullTile)) 
					switch (i) {
					case 0: s += tileTop;    break; // String representing the top of the Tiles. 
					case 1: s += locationString(tileRow[j]); break; // String representing the Location names of the Tiles.
					case 2: s += treasureString(tileRow[j]); break; // String representing the Treasures found at the Tiles.
					case 3: s += stateString(tileRow[j]);    break; // String representing the TileState of the Tiles.
					case 4: s += playerString(tileRow[j],0); break; // String representing the positions of Player 1 and 2.
					case 5: s += playerString(tileRow[j],2); break; // String representing the positions of Player 3 and 4.
				}
				// If the Tile is a NullTile and the Tile above it is not, the top of the NullTile should be generated. 
				else if(i == 0 && !(previousRow[j] instanceof NullTile))
					s += tileTop;
			}
			
			// Centre the String line to the Board. If this isn't the last line in the row, move to the next line.
			output += align(s,boardWidth,waterChar);
			if(i != 5) output += "\n";
		}
		
		return output;
	}
	
	/* Generate the String representing the Location name of a Tile, centred to the Tile. */
	private String locationString(Tile tile) {
		return align(tile.getLocation().toString(),tileWidth,spaceChar) + "|";
	}
	
	/* Generate the String representing the Treasure found at a Tile. */
	private String treasureString(Tile tile) {
		if(tile instanceof TreasureTile)
			return align("(" + ((TreasureTile)tile).getTreasure().toString() + ")", tileWidth,spaceChar) + "|";
		else 
			return align("", tileWidth,spaceChar) + "|";
	}
	
	/* Generate the String representing the TileState of a Tile, centred to the Tile. */
	private String stateString(Tile tile) {
		// It's not necessary to print the TileState if the Tile is DRY. 
		if(tile.getState() != TileState.DRY)
			return align("~" + tile.getState() + "~",tileWidth,spaceChar) + "|";
		else
			return align("", tileWidth,spaceChar) + "|";
	}
	
	/* Generate the String representing the Players on a Tile, centred to the Tile. */
	private String playerString(Tile tile, int index) {
		
		String output = "";
		
		// Array of the 2 Players to be shown on this Tile in this String line. 
		// ie: The Players with index "index" and "index+1" in ArrayList<Player> players.
		Player[] playerRow = {blankPlayer , blankPlayer};
		
		// If the difference between the index supplied and the number of Players in the 
		// game is greater than zero, there are players to be printed in this line. 
		int indexDiff = players.size()-index;
		if(indexDiff > 0) {
			// If the difference is equal to 1, there is only 1 Player to be printed on 
			// this line, if it's greater than 1, there are 2 Players to be printed. 
			playerRow[0] = players.get(index);
			if(indexDiff != 1)
				playerRow[1] = players.get(index+1);
		}
		
		for(Player player : playerRow) {
			
			// If either of the Players have the same coordinates as the Tile, add them to the String...
			if(board.getCoords(tile.getLocation())[0] == player.getCoords()[0] && board.getCoords(tile.getLocation())[1] == player.getCoords()[1])
				output += align(' ' + player.getCounter(), 10.0, spaceChar, true);
			// ...otherwise add a filler String. 
			else
				output += "          ";
		}
		
		return align(output, tileWidth,spaceChar) + "|";
	}
	
	/* Default use of align() that centres the string (see method implementation below). */
	private String align(String s, double width, char c) {
		return align(s,width,c,false);
	}
	
	/* Align a String to a given length, filling in the space with a given filler character. 
	 * If required, the String can be snapped to the left of the output (ie: snapLeft == true) */
	private String align(String s, double width, char c, boolean snapLeft) {
		// Set the length of the filler character String when centering:
		double filler  	   = width - s.length();
		double leftFiller  = Math.floor(filler/2.0);
		double rightFiller = Math.ceil(filler/2.0);
		
		// Adjusting the filler length parameters when snapLeft==true:
		if(snapLeft) {
			leftFiller  = 0;
			rightFiller = filler;
		}
		
		// Align the input String s, filling the gaps with input character c. 
		for(int i = 0; i < leftFiller ; i++)
			s = c + s;
		for(int i = 0; i < rightFiller ; i++)
			s += c;
		
		return s;
	}
	
	/* Display to the players which treasures have been captured */
	private void printTreasuresCaptured() {
		String treasuresCaptured = "";
		
		if (GameLogic.getInstance().getTreasuresCaptured().contains(Treasures.EARTH)) {treasuresCaptured += "\t\t" + Treasures.EARTH;}
		if (GameLogic.getInstance().getTreasuresCaptured().contains(Treasures.OCEAN)) {treasuresCaptured += "\t\t" + Treasures.OCEAN;}
		if (GameLogic.getInstance().getTreasuresCaptured().contains(Treasures.WIND)) {treasuresCaptured += "\t\t" + Treasures.WIND;}
		if (GameLogic.getInstance().getTreasuresCaptured().contains(Treasures.FIRE)) {treasuresCaptured += "\t\t" + Treasures.FIRE;}
		
		System.out.println("Treasures captured:" + treasuresCaptured);
	}
}
