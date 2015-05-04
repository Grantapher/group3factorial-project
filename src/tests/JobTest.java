package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

import org.junit.Test;

public class JobTest {

	@Test
	public void containsVolunteerTest() {
		Volunteer v = new Volunteer();
		Job j = new Job(null, null, null, null, null, 0, 0, 0, null);
		assertFalse("Volunteer shouldn't be contained",
				j.containsVolunteer(v));
		j.addVolunteer(v, 'l');
		assertTrue("volunteer contained", j.containsVolunteer(v));
	}
	
	@Test 
	public void isJobinParkListTest() {
		Job j = new Job(null, "Rainier", null, null, null, 0, 0, 0, null);
		List<String> jobList = new ArrayList<String>();
		assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
		jobList.add("Yellowstone");
		assertFalse("List doesn't contain park", j.isJobinParkList(jobList));
		jobList.add("Rainier");
		assertTrue("List does contain park", j.isJobinParkList(jobList));
	}
	
	@Test
	public void addVolunteerTest() {
		Job j = new Job(null, "Rainier", null, null, null, 1, 0, 0, null);
		Volunteer v = new Volunteer();
		assertFalse("Can't add to a full grade", j.addVolunteer(v, 'm'));
		assertTrue("Volunteer added", j.addVolunteer(v, 'l'));
		assertFalse("Full", j.addVolunteer(v, 'l'));
	}
}
