/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

import org.junit.Test;

public class JobTest {
    private static final Volunteer v = new Volunteer("Doe", "John", "john.doe@fake.com");
    private static final LocalDate date = LocalDate.parse("2015-12-25");

    @Test
    public void containsVolunteerTest() throws IOException, ClassNotFoundException {
        final Job j = new Job(null, "Rainier", null, date, date, 1, 0, 0, null);
        assertFalse("Volunteer shouldn't be contained", j.containsVolunteer(v));
        j.addVolunteer(v, 'l');
        assertTrue("volunteer contained", j.containsVolunteer(v));
    }

    @Test
    public void isJobinParkListTest() {
        final Job j = new Job(null, "Rainier", null, date, date, 1, 0, 0, null);
        final List<String> jobList = new ArrayList<String>();
        assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
        jobList.add("Yellowstone");
        assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
        jobList.add("Rainier");
        assertTrue("List does contain park", j.isJobinParkList(jobList));
    }

    @Test
    public void addVolunteerTest() throws IOException, ClassNotFoundException {
        final Job j = new Job(null, "Rainier", null, date, date, 1, 0, 0, null);
        assertFalse("Can't add to a full grade", j.addVolunteer(v, 'm'));
        assertTrue("Volunteer added", j.addVolunteer(v, 'l'));
        assertFalse("Full", j.addVolunteer(v, 'l'));
    }
}
