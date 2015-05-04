/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

/**
* Test cases for {@link model.Calendar}.
*
* @author Wing-Sea Poon
* @version May 3, 2015
*/
public class CalendarTest {
	private static final LocalDate today = LocalDate.now();
	private static final LocalDate tomorrow = today.plusDays(1);
	private static final LocalDate oneMonthFromNow = today.plusDays(30);
	private Calendar cal;
	private Job oneDayJobToday;
	private Job twoDayJobToday;
	private Job over3MonthsAway;
	private Job oneMonthAwayJob;
	
	/**
	 * Creates a test object.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cal = new Calendar();
		oneDayJobToday = new Job("One Day Job", today, today);
		twoDayJobToday = new Job("Two Day Job", today, tomorrow);
		over3MonthsAway = new Job("Over 3 Months", today.plusDays(91), today.plusDays(92));
		oneMonthAwayJob = new Job("Over 3 Months", oneMonthFromNow, oneMonthFromNow);
	}

	/**
	 * Test method for
	 * {@link model.Calendar#isValidLength(model.Job)}.
	 */
	@Test
	public void testIsValidLength() {
		assertTrue(cal.isValidLength(oneDayJobToday));
		assertTrue(cal.isValidLength(twoDayJobToday));
		Job job3 = new Job("Job 3", today, today.plusDays(4));
		assertFalse(cal.isValidLength(job3));
	}
	
	/**
	 * Test method for
	 * {@link model.Calendar#isValidInterval(model.Job)}.
	 */
	@Test
	public void testIsValidInterval() {
		Job job1 = new Job("Job 1", today, today.minusDays(10));
		assertFalse(cal.isValidInterval(job1));
		assertFalse(cal.isValidInterval(oneDayJobToday));
		assertFalse(cal.isValidInterval(over3MonthsAway));
		assertTrue(cal.isValidInterval(oneMonthAwayJob));
	}
	
	/**
	 * Test method for
	 * {@link model.Calendar#isFull(java.time.LocalDate)}.
	 */
	@Test
	public void testIsFullDate() {
		assertFalse(cal.isFull(oneMonthFromNow));
		cal.addJob(oneMonthAwayJob);
		assertFalse(cal.isFull(oneMonthFromNow));
		for(int i = 0; i < 4; i++) {
			cal.addJob(oneMonthAwayJob);
		}
		assertTrue(cal.isFull(oneMonthFromNow));
	}
	
	/**
	 * Test method for
	 * {@link model.Calendar#isFull()}.
	 */
	@Test
	public void testIsFull() {
		assertFalse(cal.isFull());
		for(LocalDate ctr = LocalDate.now(); 
			ctr.isBefore(oneMonthFromNow) || ctr.isEqual(oneMonthFromNow);
			ctr = ctr.plusDays(1)) {
				cal.addJob(new Job("Job", ctr, ctr));
		}
		assertTrue(cal.isFull());
	}
	
	/**
	 * Test method for
	 * {@link model.Calendar#addJob(model.Job)}.
	 */
	@Test
	public void testAddJob() {
		cal.addJob(oneMonthAwayJob);
		Map<LocalDate, ArrayList<Job>> map = new TreeMap<LocalDate, ArrayList<Job>>();
		map.put(oneMonthFromNow, new ArrayList<Job>());
		map.get(oneMonthFromNow).add(oneMonthAwayJob);
		
		assertEquals(map.toString(), cal.toString());
	}
}
