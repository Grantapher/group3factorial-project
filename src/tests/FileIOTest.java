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
import java.util.List;
import java.util.Map;

import model.AbstractUser;
import model.Administrator;
import model.FileIO;
import model.Job;
import model.ParkManager;
import model.Volunteer;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Grant Toepfer
 * @version May 9, 2015
 */
public class FileIOTest {
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
    private static final String[] parks = { "Kopachuck", "Blake Island", "Camp Seymour" };

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
        FileIO.addUser(admin);
        FileIO.addUser(pm);
        FileIO.addUser(volunteer);
    }

    /**
     * Test method for {@link model.FileIO#readJobs()} and
     * {@link model.FileIO#addJob(model.Job)}.
     *
     * @throws IOException if the file fails to create or open.
     */
    @Test
    public final void testAddAndReadJobs() throws IOException {
        FileIO.addJob(job);
        final Map<LocalDate, List<Job>> map = FileIO.readJobs();
        final List<Job> list = map.get(date);
        assertTrue("Job failed to add or failed to be read.", list.contains(job));
    }

    /**
     * Test method for
     * {@link model.FileIO#queryUsers(java.lang.String, java.lang.Character)}
     * and {@link model.FileIO#addUser(model.AbstractUser)}.
     *
     * @throws IOException if the file fails to create or open.
     */
    @Test
    public final void testAddAndQueryUsers() throws IOException {
        final List<AbstractUser> allList = FileIO.queryUsers(null, null);
        assertTrue("The query (null, null) didn't return the type Administrator.",
                allList.contains(admin));
        assertTrue("The query (null, null) didn't return the type ParkManager.",
                allList.contains(pm));
        assertTrue("The query (null, null) didn't return the type Volunteer.",
                allList.contains(volunteer));

        final List<AbstractUser> doeList = FileIO.queryUsers("Doe", null);
        assertTrue(
                "The query (\"Doe\", null) didn't return the Administrator with last name Doe.",
                doeList.contains(admin));
        assertTrue(
                "The query (\"Doe\", null) didn't return the ParkManager with last name Doe.",
                doeList.contains(pm));
        assertFalse("The query (\"Doe\", null) returned the Volunteer with last name Skins.",
                doeList.contains(volunteer));

        final List<AbstractUser> adminList = FileIO.queryUsers(null, FileIO.ADMIN_CHAR);
        assertTrue("The query (null, ADMIN) didn't return the Administrator",
                adminList.contains(admin));
        assertFalse("The query (null, ADMIN) returned the ParkManager.",
                adminList.contains(pm));
        assertFalse("The query (null, ADMIN) returned the Volunteer.",
                adminList.contains(volunteer));

        final List<AbstractUser> pMList = FileIO.queryUsers(null, FileIO.PARK_MANAGER_CHAR);
        assertFalse("The query (null, PM) returned the Administrator", pMList.contains(admin));
        assertTrue("The query (null, PM) didn't return the ParkManager", pMList.contains(pm));
        assertFalse("The query (null, PM) returned the Volunteer", pMList.contains(volunteer));

        final List<AbstractUser> volunteerList = FileIO
                .queryUsers(null, FileIO.VOLUNTEER_CHAR);
        assertFalse("The query (null, VOLUNTEER) returned the Administrator",
                volunteerList.contains(admin));
        assertFalse("The query (null, VOLUNTEER) returned the ParkManager",
                volunteerList.contains(pm));
        assertTrue("The query (null, VOLUNTEER) didn't return the Volunteer",
                volunteerList.contains(volunteer));
    }

    /**
     * Test method for {@link model.FileIO#getUserType(java.lang.String)}.
     *
     * @throws FileNotFoundException if the file isn't found.
     */
    @Test
    public final void testGetUserType() throws FileNotFoundException {
        assertEquals("Administrator type lookup failed", FileIO.ADMIN_CHAR,
                FileIO.getUserType(admin.getEmail()));
        assertEquals("ParkManager type lookup failed", FileIO.PARK_MANAGER_CHAR,
                FileIO.getUserType(pm.getEmail()));
        assertEquals("Volunteer type lookup failed", FileIO.VOLUNTEER_CHAR,
                FileIO.getUserType(volunteer.getEmail()));
        assertEquals("No type lookup failed", FileIO.USER_NOT_FOUND_CHAR,
                FileIO.getUserType("noEmail"));
    }

}
