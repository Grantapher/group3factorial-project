/*
 * TCSS 360 Project - Group 3!
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    /** myArray used for various test methods. */
    private final String[] myArr = { "GOOD", "BETTER", "BEST", "BESTEST" };

    /**
     * Initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myManager1 = new ParkManager("GodOfWar", "Cratos", "GreekGod@thermopylae.com");

    }

    /**
     * Test method for addPark with strings that should be accepted.
     */
    @Test
    public void testaddParks() {
        final String[] myArr = { "GOOD", "BETTER", "BEST", "BESTEST" };

        for (int x = 0; x < myArr.length; x++) {
            myManager1.addPark(myArr[x]);
            assertEquals(myManager1.getParks().get(x), myArr[x]);
        }

        final int size = myManager1.getParks().size();
        final String shouldFail = "";
        myManager1.addPark(shouldFail);// should not add park, size should stay
        // the same
        assertEquals("A job with no characters has been added", myManager1.getParks().size(),
                size);
    }

    /**
     * Test method for addPark with an empty string.
     */
    @Test
    public void testaddParkWithEmptyString() {
        final String str = "";

        myManager1.addPark(str);
        final int empty = 0;
        assertEquals(myManager1.getParks().size(), empty);
    }

    /**
     * Test method for isMyPark with a string that has being recently added.
     */
    @Test
    public void testisMyParkWithParksJustAdded() {
        final String[] myArr = { "GOOD", "BETTER", "BEST", "BESTEST" };

        for (int x = 0; x < myArr.length; x++) {
            myManager1.addPark(myArr[x]);
            if (x < 4) {
                assertTrue(myManager1.isMyPark(myArr[x]));
            }
        }

    }

    /**
     * Test method for isMyPark with a park that is not added to the list of
     * parks managed.
     */
    @Test
    public void testisMyParkWithParkThatIsNotAddedToList() {

        final String fail = "false";
        for (int x = 0; x < myArr.length; x++) {
            myManager1.addPark(myArr[x]);
        }
        assertFalse(myManager1.isMyPark(fail));

        final int size = myManager1.getParks().size();
        final String shouldFail = "";
        myManager1.addPark(shouldFail);// should not add, size should stay the
        // same
        assertEquals("A job with no characters has been added", myManager1.getParks().size(),
                size);

    }

    /**
     * Test method for isMyPark with an empty string.
     */
    @Test
    public void testisMyParkWithEmptyString() {

        final String fail = "";
        for (int x = 0; x < myArr.length; x++) {
            myManager1.addPark(myArr[x]);
        }
        assertFalse(myManager1.isMyPark(fail));

    }

}
