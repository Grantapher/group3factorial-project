/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

import org.junit.Test;

public class JobTest {

    @Test
    public void containsVolunteerTest() {
        final Volunteer v = new Volunteer();
        final Job j = new Job(null, null, null, null, null, 0, 0, 0, null);
        assertFalse("Volunteer shouldn't be contained", j.containsVolunteer(v));
        j.addVolunteer(v, 'l');
        assertTrue("volunteer contained", j.containsVolunteer(v));
    }

    @Test
    public void isJobinParkListTest() {
        final Job j = new Job(null, "Rainier", null, null, null, 0, 0, 0, null);
        final List<String> jobList = new ArrayList<String>();
        assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
        jobList.add("Yellowstone");
        assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
        jobList.add("Rainier");
        assertTrue("List does contain park", j.isJobinParkList(jobList));
    }

    @Test
    public void addVolunteerTest() {
        final Job j = new Job(null, "Rainier", null, null, null, 1, 0, 0, null);
        final Volunteer v = new Volunteer();
        assertFalse("Can't add to a full grade", j.addVolunteer(v, 'm'));
        assertTrue("Volunteer added", j.addVolunteer(v, 'l'));
        assertFalse("Full", j.addVolunteer(v, 'l'));
    }
}