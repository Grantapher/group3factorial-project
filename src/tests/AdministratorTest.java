/*
 * TCSS 360 Project - Group 3!
 */

package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;


/**
 * Test class for an Administrator. 
 * @author Maurice Shaw
 * @version May 2014
 */
public class AdministratorTest {

 // Declare objects to use in the test fixture.
    
    /** A park manager to use in tests. */
    private Administrator myAdmin;
   

    /**
     * Initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myAdmin = new Administrator("Zues", "God of Gods", "GreekGod@thermopylae.com");
    
    }

    /**
     * Test method for addPark.
     * @throws FileNotFoundException 
     */
    @Test
    public void testFindVolunteer() throws FileNotFoundException {
        
        Volunteer vol = new Volunteer("Abraham", "Adam", "AdamAbraham@gmail.com");
        Volunteer vol1 = new Volunteer("Adam", "Awesome", "AdamAbraham@gmail.com");
        List<Volunteer> list = myAdmin.findVolunteer(vol.getLastName());
        for (int x = 0; x < list.size(); x++) {
            assertEquals(list.get(x).getLastName(), vol.getLastName());
        }
        List<Volunteer> empty = new ArrayList<>();
        
        assertEquals(myAdmin.findVolunteer(vol1.getLastName()), empty);
        
        
        
    }

}
