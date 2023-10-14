package testSuite;

import static org.junit.Assert.*;

import org.junit.*;

import board.*;
import enums.*;

public class JTestTile {
	private Tile myTile;

	/*
	 * Setup a test tile
	 */
	@Before
	public void setUp() throws Exception{
		myTile = new Tile(Location.BREAKERS_BRIDGE);
		System.out.println("setUp");
	}

	/*
	 * Check tiles location is correct
	 */
	@Test
	public void testLocation() {
		assertEquals("Test getLocation()", Location.BREAKERS_BRIDGE, myTile.getLocation());
		System.out.println("Test getLocation(). Location: " + myTile.getLocation());
	}

	/*
	 * Checking tile is dry on setup
	 */
	@Test
	public void testGetState() {
		assertEquals("Test getState()", TileState.DRY, myTile.getState());
		System.out.println("Test getState() method. tileState: " + myTile.getState());
	}

	/*
	 * Check tile reacts as expected to flooding
	 */
	@Test
	public void testFlood() {
		assertTrue("Test first flood()", myTile.flood());
		assertEquals("Test isFlooded()", TileState.FLOODED, myTile.getState());
		System.out.println("Test ability to flood a DRY tile");

		assertTrue("Test second flood()", myTile.flood());
		assertEquals("Test getState()", TileState.SUNK, myTile.getState());
		System.out.println("Test ability to flood a FLOODED tile");

		assertFalse("Test third flood()", myTile.flood());
		assertEquals("Test getState()", TileState.SUNK, myTile.getState());
		System.out.println("Test ability to flood a SUNK tile");
	}

	/*
	 * Check tile can be shored up when flooded but not when sunk
	 */
	@Test
	public void testShoreUp() {
		assertFalse("Test first shoreUp()", myTile.shoreUp());
		assertEquals("Test isFlooded()", TileState.DRY, myTile.getState());
		System.out.println("Test ability to shore up a DRY tile");

		myTile.flood();
		assertTrue("Test second shoreUp()", myTile.shoreUp());
		assertEquals("Test isFlooded()", TileState.DRY, myTile.getState());
		System.out.println("Test ability to shore up a FLOODED tile");

		myTile.flood();
		myTile.flood();
		assertFalse("Test third shoreUp()", myTile.shoreUp());
		assertEquals("Test isFlooded()", TileState.SUNK, myTile.getState());
		System.out.println("Test ability to shore up a SUNK tile");
	}
}