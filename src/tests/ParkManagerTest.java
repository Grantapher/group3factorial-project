/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertEquals;
import model.ParkManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for a Park Manager
 * 
 * @author Maurice Shaw
 * @version May 2014
 */
public class ParkManagerTest {

    // Declare objects to use in the test fixture.

    /** A park manager to use in tests. */
    private ParkManager myManager1;

    /**
     * Initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myManager1 = new ParkManager("GodOfWar", "Cratos", "GreekGod@thermopylae.com");

    }

    /**
     * Test method for addPark.
     */
    @Test
    public void testaddPark() {
        final String[] arr = { "GOOD", "BETTER", "BEST", "BESTEST", "" };

        for (int x = 0; x < arr.length; x++) {
            myManager1.addPark(arr[x]);
            if (x > 3) {
                assertEquals(myManager1.getParks().size(), arr.length - 1);
            } else {
                assertEquals(myManager1.getParks().get(x), arr[x]);
            }

        }

        final int size = myManager1.getParks().size();
        final String shouldFail = "";
        myManager1.addPark(shouldFail);// should not add, size should stay the
                                       // same
        assertEquals("A job with no characters has been added", myManager1.getParks().size(),
                size);

    }

}
