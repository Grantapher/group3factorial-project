/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.Calendar;
import model.Job;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

import exception.BusinessRuleException;

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
    private Volunteer testerCopy;

    /**
     * Creates a test object.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tester = new Volunteer(LAST, FIRST, EMAIL);
        testerCopy = new Volunteer(LAST, FIRST, EMAIL);
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
                + actualFirst + "\"", FIRST, actualFirst);
        final String actualEmail = tester.getEmail();
        assertSame("Last names differ: expected \"" + EMAIL + "\", actual was \""
                + actualEmail + "\"", EMAIL, actualEmail);
    }

    /**
     * Test method for {@link model.Volunteer#getJobs()}.
     *
     * @throws IOException if the file isn't found
     * @throws ClassNotFoundException
     */
    @Test
    public final void testGetJobs() throws IOException, ClassNotFoundException {
        final Map<LocalDate, List<Job>> map = Calendar.getInstance().getJobs();
        final List<Job> list = map.get(LocalDate.parse("2015-08-19"));
        final Job job = list.get(0);
        try {
            job.addVolunteer(tester, 'l');
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
        final List<Job> jobs = tester.getJobs();
        assertTrue("Volunteer wasn't added to the job or get jobs didn't get the job.",
                jobs.contains(job));
    }

    /**
     * Test method for {@link model.Volunteer#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        final Volunteer other = new Volunteer("Testing", "Is", "Fun!");
        assertEquals("false negative", tester, testerCopy);
        assertNotEquals("false positive", tester, other);
    }

}
