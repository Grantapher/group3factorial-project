/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for running all of our tests.
 *
 * @author Grant Toepfer
 * @version May 4, 2015
 */
@RunWith(Suite.class)
@SuiteClasses({ CalendarTest.class, JobTest.class, ParkManagerTest.class, VolunteerTest.class })
public class AllTests {

}
