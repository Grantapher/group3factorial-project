/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.AbstractUser;
import model.Administrator;
import model.Job;
import model.ParkManager;
import model.SerializableIO;
import model.Volunteer;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Grant Toepfer
 * @version May 24, 2015
 */
public class SerializableIOTest {
    private static final LocalDate date = LocalDate.parse("2015-12-25");
    private static final Job job = new Job("Clearing", "Kopachuck State Park", "Trails", date,
            date, 10, 10, 10, "We're cleaning brush off the trails.");
    private static final Administrator admin = new Administrator("Doe", "John",
            "johndoe@fake.com");
    private static final ParkManager pm = new ParkManager("Doe", "Jane", "janedoe@fake.com");
    private static final Volunteer volunteer = new Volunteer("Skins", "Bob",
            "bobskins@fake.com");
    private static final Volunteer otherV = new Volunteer("Ross", "William",
            "ryansmith@fake.com");
    private static final String[] parks = { "Kopachuck State Park", "Blake Island",
        "Camp Seymour", "Titlow Beach" };

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        job.addVolunteer(volunteer, 'l');
        job.addVolunteer(otherV, 'h');

        for (final String park : parks) {
            pm.addPark(park);
        }
        SerializableIO.addUser(admin);
        SerializableIO.addUser(pm);
        SerializableIO.addUser(volunteer);
    }

    /**
     * Test method for {@link model.SerializableIO#writeJobs(java.util.Map)} and
     * {@link model.SerializableIO#readJobs()}.
     *
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    @Test
    public final void testReadAndWriteJobs() throws ClassNotFoundException, IOException {
        final Map<LocalDate, List<Job>> map = SerializableIO.readJobs();
        List<Job> list = map.get(job.getStartDate());
        if (list == null) {
            list = new ArrayList<>();
        }
        final boolean contained = list.contains(job);
        if (contained) {
            list.remove(job);
        } else {
            list.add(job);
        }

        map.put(job.getStartDate(), list);
        SerializableIO.writeJobs(map);

        final Map<LocalDate, List<Job>> newMap = SerializableIO.readJobs();

        assertTrue("Job not present after read, adding the job, writing, and reading again.",
                contained ? !containsJob(newMap, job) : containsJob(newMap, job));

    }

    /**
     * Checks to see if the map contains the job
     */
    private boolean containsJob(final Map<LocalDate, List<Job>> map, final Job job) {
        for (final List<Job> newList : map.values()) {
            for (final Job newJob : newList) {
                if (newJob.equals(job)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Test method for
     * {@link model.SerializableIO#getUserType(java.lang.String)}.
     *
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    @Test
    public final void testGetUserType() throws ClassNotFoundException, IOException {
        assertEquals("Administrator type lookup failed", admin,
                SerializableIO.getUser(admin.getEmail()));
        assertEquals("ParkManager type lookup failed", pm,
                SerializableIO.getUser(pm.getEmail()));
        assertEquals("Volunteer type lookup failed", volunteer,
                SerializableIO.getUser(volunteer.getEmail()));
        assertEquals("No type lookup failed", null, SerializableIO.getUser("noEmail"));
    }

    /**
     * Test method for
     * {@link model.SerializableIO#queryUsers(java.lang.String, java.lang.Character)}
     * and {@link model.SerializableIO#addUser(model.AbstractUser)}.
     *
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    @Test
    public final void testAddAndQueryUsers() throws IOException, ClassNotFoundException {
        final List<AbstractUser> allList = SerializableIO.queryUsers(null, null);
        assertTrue("The query (null, null) didn't return the type Administrator.",
                allList.contains(admin));
        assertTrue("The query (null, null) didn't return the type ParkManager.",
                allList.contains(pm));
        assertTrue("The query (null, null) didn't return the type Volunteer.",
                allList.contains(volunteer));

        final List<AbstractUser> doeList = SerializableIO.queryUsers("Doe", null);
        assertTrue(
                "The query (\"Doe\", null) didn't return the Administrator with last name Doe.",
                doeList.contains(admin));
        assertTrue(
                "The query (\"Doe\", null) didn't return the ParkManager with last name Doe.",
                doeList.contains(pm));
        assertFalse("The query (\"Doe\", null) returned the Volunteer with last name Skins.",
                doeList.contains(volunteer));

        final List<AbstractUser> adminList = SerializableIO.queryUsers(null,
                SerializableIO.ADMIN_CHAR);
        assertTrue("The query (null, ADMIN) didn't return the Administrator",
                adminList.contains(admin));
        assertFalse("The query (null, ADMIN) returned the ParkManager.",
                adminList.contains(pm));
        assertFalse("The query (null, ADMIN) returned the Volunteer.",
                adminList.contains(volunteer));

        final List<AbstractUser> pMList = SerializableIO.queryUsers(null,
                SerializableIO.PARK_MANAGER_CHAR);
        assertFalse("The query (null, PM) returned the Administrator", pMList.contains(admin));
        assertTrue("The query (null, PM) didn't return the ParkManager", pMList.contains(pm));
        assertFalse("The query (null, PM) returned the Volunteer", pMList.contains(volunteer));

        final List<AbstractUser> volunteerList = SerializableIO.queryUsers(null,
                SerializableIO.VOLUNTEER_CHAR);
        assertFalse("The query (null, VOLUNTEER) returned the Administrator",
                volunteerList.contains(admin));
        assertFalse("The query (null, VOLUNTEER) returned the ParkManager",
                volunteerList.contains(pm));
        assertTrue("The query (null, VOLUNTEER) didn't return the Volunteer",
                volunteerList.contains(volunteer));
    }

}
