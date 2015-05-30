/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

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
        dumbJob = new Job(null, "Rainier", null, futureDate, futureDate, 1, 0, 2, null);
        pastJob = new Job(null, "Rainier", null, pastDate, pastDate, 1, 0, 2, null);
        dumbJob2 = new Job(null, "Over the Rainbow", null, futureDate, futureDate, 1, 0, 2,
                null);
    }

    @Test
    public void testContainsVolunteerOnMissing() {
        assertFalse("Volunteer shouldn't be contained", dumbJob.containsVolunteer(v));
    }

    @Test
    //
    public void testContainsVolunteerOnPresent() throws ClassNotFoundException {
        try {
            assertEquals(Job.SUCCESS, dumbJob.addVolunteer(v, 'l'));
        } catch (final FileNotFoundException e) {
            System.out.println("File not found");
        } catch (final IOException e) {
            System.out.println("IO exception");
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
        final int status = dumbJob.addVolunteer(v, 'm');
        assertEquals("" + status, Job.WORK_CATEGORY_FULL, status);
    }

    @Test
    //
    public void testAddVolunteerOnRoom() throws IOException, ClassNotFoundException {
        final int status = dumbJob.addVolunteer(v, 'l');
        assertEquals("" + status, Job.SUCCESS, status);
    }

    @Test
    //
    public void testAddVolunteerOnFuture() throws IOException, ClassNotFoundException {
        final int status = dumbJob.addVolunteer(v, 'l');
        assertEquals("" + status, Job.SUCCESS, status);
    }

    @Test
    public void testAddVolunteerOnPast() throws IOException, ClassNotFoundException {
        final int status = pastJob.addVolunteer(v, 'l');
        assertEquals("" + status, Job.JOB_IN_PAST, status);
    }

    @Test
    //
    public void testAddVolunteerOnTwoOnOneDay() throws IOException, ClassNotFoundException {
        dumbJob.addVolunteer(v, 'h');
        final int status = dumbJob2.addVolunteer(v, 'l');
        assertEquals("" + status, Job.TWO_IN_ONE_DAY, status);
    }

}
