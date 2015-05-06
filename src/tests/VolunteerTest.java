/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link model.Volunteer}.
 *
 * @author Grant Toepfer
 * @version May 1, 2015
 */
public class VolunteerTest {
    private static final String LAST = "Smith";
    private static final String FIRST = "John";
    private static final String EMAIL = "SmithJohn@gmail.com";
    private Volunteer tester;

    /**
     * Creates a test object.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new Volunteer(LAST, FIRST, EMAIL);
    }

    /**
     * Test method for
     * {@link model.Volunteer#Volunteer(java.lang.String, java.lang.String, java.lang.String, java.util.List)}
     * .
     */
    @Test
    public final void testVolunteerStringStringStringListOfJob() {
        final String actualLast = tester.getLastName();
        assertSame("Last names differ: expected \"" + LAST + "\", actual was \"" + actualLast
                + "\"", LAST, actualLast);
        final String actualFirst = tester.getFirstName();
        assertSame("First names differ: expected \"" + FIRST + "\", actual was \""
                + actualFirst + "\"", LAST, actualFirst);
        final String actualEmail = tester.getEmail();
        assertSame("Last names differ: expected \"" + EMAIL + "\", actual was \""
                + actualEmail + "\"", LAST, actualEmail);
    }

    /**
     * Test method for {@link model.Volunteer#Volunteer(model.Volunteer)} and
     * {@link model.Volunteer#clone()}.
     */
    @Test
    public final void testVolunteerVolunteer() {
        final Volunteer clone = new Volunteer(tester);
        assertEquals("equals() returns false on a clone.", tester, clone);
        assertNotSame("clone constructor returns the same object.", tester, clone);
    }

    /**
     * Test method for {@link model.Volunteer#getJobs()}.
     */
    @Test
    public final void testGetJobs() {
        // TODO test for jobs that conflict plus edge cases.
        fail("TODO");
    }

    /**
     * Test method for {@link model.Volunteer#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        final Volunteer other = new Volunteer("Testing", "Is", "Fun!");
        assertEquals("equals false negative", tester, new Volunteer(LAST, FIRST, EMAIL));
        assertNotEquals("false positive", tester, other);
    }

}
