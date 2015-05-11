/*
 * TCSS 360 Project - Group 3!
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import model.Calendar;
import model.Job;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link model.Calendar}.
 *
 * @author Wing-Sea Poon
 * @version May 3, 2015
 */
/*
 * final String title, final String parkName, final String location,
            final LocalDate start, final LocalDate end, final int light, final int med,
            final int heavy, final String description
 * 
 */
public class CalendarTest {
	private static final String TITLE = "Title";
	private static final String PARK = "Park";
	private static final String LOCATION = "Location";
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate ONE_MONTH_FROM_NOW = TODAY.plusDays(30);
    private static final int LIGHT = 5;
    private static final int MED = 5;
    private static final int HEAVY = 5;
    private static final String DESCRIPTION = "Description";
    private Calendar cal;
    private Job oneDayJobToday;
    private Job twoDayJobToday;
    private Job over3MonthsAway;
    private Job oneMonthAwayJob;

    /**
     * Creates a test object.
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cal = Calendar.getInstance();
        oneDayJobToday = new Job(TITLE, PARK, LOCATION, TODAY, TODAY, LIGHT, MED, HEAVY, DESCRIPTION);
        twoDayJobToday = new Job(TITLE, PARK, LOCATION, TODAY, TOMORROW, LIGHT, MED, HEAVY, DESCRIPTION);
        over3MonthsAway = new Job(TITLE, PARK, LOCATION, TODAY.plusDays(91), TODAY.plusDays(92), LIGHT, MED, HEAVY, DESCRIPTION);
        oneMonthAwayJob = new Job(TITLE, PARK, LOCATION, ONE_MONTH_FROM_NOW, ONE_MONTH_FROM_NOW, LIGHT, MED, HEAVY, DESCRIPTION);
    }

    /**
     * Test method for {@link model.Calendar#isValidLength(model.Job)}.
     */
    @Test
    public void testIsValidLength() {
        assertTrue(cal.isValidLength(oneDayJobToday));
        assertTrue(cal.isValidLength(twoDayJobToday));
        final Job job3 = new Job(TITLE, PARK, LOCATION, TODAY, TODAY.plusDays(4), LIGHT, MED, HEAVY, DESCRIPTION);
        assertFalse(cal.isValidLength(job3));
    }

    /**
     * Test method for {@link model.Calendar#isValidInterval(model.Job)}.
     */
    @Test
    public void testIsValidInterval() {
        final Job job1 = new Job(TITLE, PARK, LOCATION, TODAY, TODAY.minusDays(10), LIGHT, MED, HEAVY, DESCRIPTION);
        assertFalse(cal.isValidInterval(job1));
        assertFalse(cal.isValidInterval(oneDayJobToday));
        assertFalse(cal.isValidInterval(over3MonthsAway));
        assertTrue(cal.isValidInterval(oneMonthAwayJob));
    }

    /**
     * Test method for {@link model.Calendar#isFull(java.time.LocalDate)}.
     * @throws IOException if Job info file not found.
     */
    @Test
    public void testIsFullDate() throws IOException {
        assertFalse(cal.isFull(ONE_MONTH_FROM_NOW));
        cal.addJob(oneMonthAwayJob);
        assertFalse(cal.isFull(ONE_MONTH_FROM_NOW));
        for (int i = 0; i < 4; i++) {
            cal.addJob(oneMonthAwayJob);
        }
        assertTrue(cal.isFull(ONE_MONTH_FROM_NOW));
    }

    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws IOException if Job info file not found.
     */
    @Test
    public void testIsFull() throws IOException {
        assertFalse(cal.isFull());
        for (LocalDate ctr = LocalDate.now(); ctr.isBefore(ONE_MONTH_FROM_NOW)
                || ctr.isEqual(ONE_MONTH_FROM_NOW); ctr = ctr.plusDays(1)) {
            cal.addJob(new Job(TITLE, PARK, LOCATION, ctr, ctr, LIGHT, MED, HEAVY, DESCRIPTION));
        }
        assertTrue(cal.isFull());
    }

    /**
     * Test method for {@link model.Calendar#addJob(model.Job)}.
     * @throws IOException if Job info file not found.
     */
    @Test
    public void testAddJob() throws IOException {
        cal.addJob(oneMonthAwayJob);
        final Map<LocalDate, ArrayList<Job>> map = new TreeMap<LocalDate, ArrayList<Job>>();
        map.put(ONE_MONTH_FROM_NOW, new ArrayList<Job>());
        map.get(ONE_MONTH_FROM_NOW).add(oneMonthAwayJob);

        assertEquals(map.toString(), cal.toString());
    }
}
