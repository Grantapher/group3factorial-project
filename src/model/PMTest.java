
package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Test class for a Park Manager 
 * @author Maurice Shaw
 * @version May 2014
 */
public class PMTest {
    
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
        String[] arr = {"GOOD", "BETTER", "BEST", "BESTEST", ""};
        
        for (int x = 0; x < arr.length; x++) {
            myManager1.addPark(arr[x]);
            if (x > 3) // accepts only string that are at east one character long
                assertEquals(myManager1.getParks().size(), (arr.length - 1));
            else //add park to list as needed
                assertEquals(myManager1.getParks().get(x), arr[x]);
        }    
        
    }

}
