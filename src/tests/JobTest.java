/*
 * TCSS 360 Project - Group 3!
 */
package tests;

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
    private static final Volunteer v = new Volunteer("Doe", "John", "john.doe@fake.com");
    private static final LocalDate date = LocalDate.parse("2015-06-25");
    private static Job dumbJob;

    @Before
    public void init() {
        dumbJob = new Job(null, "Rainier", null, date, date, 1, 0, 2, null);
    }

    @Test
    public void testContainsVolunteerOnMissing() {
        assertFalse("Volunteer shouldn't be contained", dumbJob.containsVolunteer(v));
    }

    @Test
    public void testContainsVolunteerOnPresent() throws ClassNotFoundException {
        try {
            dumbJob.addVolunteer(v, 'l');
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
    public void testAddVolunteerOnNoRoom() throws IOException, ClassNotFoundException {
        assertFalse("Can't add to a full grade", dumbJob.addVolunteer(v, 'm'));
    }

    @Test
    public void testAddVolunteerOnRoom() throws IOException, ClassNotFoundException {
        assertTrue("Volunteer added", dumbJob.addVolunteer(v, 'l'));
    }

    @Test
    public void testAddVolunteerTestOnDuplicate() throws IOException, ClassNotFoundException {
        dumbJob.addVolunteer(v, 'h');
        assertFalse("Duplicate detected", dumbJob.addVolunteer(v, 'l'));
    }
}
