/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

import exception.BusinessRuleException;
import exception.FullCategoryException;
import exception.OverbookedVolunteerException;
import exception.PastJobException;

public class JobTest {
    private static Volunteer v;
    private static Job dumbJob;
    private static Job pastJob;
    private static Job dumbJob2;

    @Before
    public void init() {
        v = new Volunteer("Doe", "John", "john.doe@fake.com");
        final LocalDate futureDate = LocalDate.parse("2015-06-25");
        final LocalDate pastDate = LocalDate.parse("2014-12-25");
        dumbJob = new Job(null, "Rainier", null, futureDate, futureDate, 0, 1, 2, null);
        pastJob = new Job(null, "Rainier", null, pastDate, pastDate, 0, 0, 2, null);
        dumbJob2 = new Job(null, "Over the Rainbow", null, futureDate, futureDate, 1, 1, 2,
                null);
    }

    @Test
    public void testContainsVolunteerOnMissing() {
        assertFalse("Volunteer shouldn't be contained", dumbJob.containsVolunteer(v));
    }

    @Test
    //
    public void testContainsVolunteerOnPresent() throws ClassNotFoundException, IOException {
        try {
            dumbJob.addVolunteer(v, 'h');
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
        assertTrue("volunteer contained", dumbJob.containsVolunteer(v));
    }

    @Test
    public void testIsJobInParkListOnMissing() {
        final List<String> jobList = new ArrayList<String>();
        jobList.add("Yellowstone");
        assertFalse("List doesn't contain park", dumbJob.isJobinParkList(jobList));
    }

    @Test
    public void testIsJobInParkListOnPresent() {
        final List<String> jobList = new ArrayList<String>();
        jobList.add("Rainier");
        assertTrue("List does contain park", dumbJob.isJobinParkList(jobList));
    }

    @Test
    //
    public void testAddVolunteerOnNoRoom() throws IOException, ClassNotFoundException {
        try {
            dumbJob.addVolunteer(v, 'l');
            fail("Exception Expected!");
        } catch (final FullCategoryException theE) {
            // good, pass
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
    }

    @Test
    //
    public void testAddVolunteerOnRoom() throws IOException, ClassNotFoundException {
        try {
            dumbJob.addVolunteer(v, 'm');
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
    }

    @Test
    //
    public void testAddVolunteerOnFuture() throws IOException, ClassNotFoundException {
        try {
            dumbJob.addVolunteer(v, 'm');
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
    }

    @Test
    public void testAddVolunteerOnPast() throws IOException, ClassNotFoundException {
        try {
            pastJob.addVolunteer(v, 'm');
            fail("Exception expected!");
        } catch (final PastJobException theE) {
            // good, pass
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
    }

    @Test
    //
    public void testAddVolunteerOnTwoOnOneDay() throws IOException, ClassNotFoundException {
        try {
            dumbJob.addVolunteer(v, 'h');
            dumbJob2.addVolunteer(v, 'm');
            // TODO without interacting with Calendar, this will always fail.
            fail("Exception expected!");
        } catch (final OverbookedVolunteerException theE) {
            // good, pass
        } catch (final BusinessRuleException theE) {
            fail(theE.toString());
        }
    }

}
