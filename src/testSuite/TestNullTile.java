package testSuite;

import static org.junit.Assert.*;

import org.junit.*;

import board.*;
import enums.TileState;

public class TestNullTile {
	private Tile myNullTile;

	/*
	 * Create null tile
	 */
	@Before
	public void setUp() throws Exception{
		myNullTile = new NullTile();
		System.out.println("setUp");
	}

	/*
	 * Check null tile Location is null
	 */
	@Test
	public void testLocationIsNull() {
		assertNull("Test getLocation()", myNullTile.getLocation());
		System.out.println("Test getLocation(). Location: " + myNullTile.getLocation());
	}

	/*
	 * Check null tile state is sunk
	 */
	@Test
	public void testStateIsSunk() {
		assertEquals("Test getState()", TileState.SUNK, myNullTile.getState());
		System.out.println("Test getState() method. tileState: " + myNullTile.getState());
	}

	/*
	 * Check tile can't be flooded
	 */
	@Test
	public void testFlood() {
		assertFalse("Test first flood()", myNullTile.flood());
		System.out.println("Test ability to flood tile");

		assertFalse("Test second flood()", myNullTile.flood());
		System.out.println("Test ability to flood tile");
	}

	/*
	 * Test null tile can't be shoren up
	 */
	@Test
	public void testShoreUp() {
		assertFalse("Test first shoreUp()", myNullTile.shoreUp());
		System.out.println("Test ability to shore up tile");

		assertFalse("Test second shoreUp()", myNullTile.shoreUp());
		System.out.println("Test ability to shore up tile");
	}

}
