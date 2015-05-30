/*
 * TCSS 360 Project - Group 3!
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;

import model.Calendar;
import model.Job;
import model.SerializableIO;
import exception.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link model.Calendar}.
 *
 * @author Wing-Sea Poon
 * @version May 3, 2015
 */
public class CalendarTest {
	private static final int MAX_JOBS = 30;
    private static final int MAX_DAYS = 90;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalDate MAX_DAYS_FROM_NOW = TODAY.plusDays(MAX_DAYS);
    
    private Calendar cal;
    private Job oneDayJobTomorrow;
    private Job twoDayJobTomorrow;

    /**
     * Creates a test object.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cal = Calendar.getInstance();
        cal.getJobs().clear();
        
        oneDayJobTomorrow = new Job(TOMORROW, TOMORROW);
        twoDayJobTomorrow = new Job(TOMORROW, TOMORROW.plusDays(1));
    }

    /**
     * Test method for {@link model.Calendar#isFull()}.
     */
    @Test
    public void testIsFullNoJobs() {
        assertFalse(cal.isFull());
    }
    
    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws BRException
     * @throws IOException 
     */
    @Test
    public void testIsFullOneJob() throws IOException, BRException {
    	cal.addJob(oneDayJobTomorrow);
        assertFalse(cal.isFull());
    }
    
    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws BRException
     * @throws IOException 
     */
    @Test
    public void testIsFullMaxMinusOneJobs() throws IOException, BRException {
    	LocalDate start = LocalDate.from(TOMORROW);
    	for (int i = 0; i < (MAX_JOBS - 1) * 2; i += 2) {
    		LocalDate toAdd = start.plusDays(i);
    		Job job = new Job(toAdd, toAdd);
    		cal.addJob(job);
    	}
        assertFalse(cal.isFull());
    }
    
    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws BRException
     * @throws IOException 
     */
    @Test
    public void testIsFullMaxMinusOneJobsTwoDayJobs() throws IOException, BRException {
    	LocalDate start = LocalDate.from(TOMORROW);
    	for (int i = 0; i < (MAX_JOBS - 1) * 2; i += 2) {
    		LocalDate toAdd = start.plusDays(i);
    		Job job = new Job(toAdd, toAdd.plusDays(1));
    		cal.addJob(job);
    	}
        assertFalse(cal.isFull());
    }
    
    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws BRException 
     * @throws IOException 
     */
    @Test
    public void testIsFullMaxJobs() throws IOException, BRException {
    	LocalDate start = LocalDate.from(TOMORROW);
    	for (int i = 0; i < MAX_JOBS * 2; i += 2) {
    		LocalDate toAdd = start.plusDays(i);
    		Job job = new Job(toAdd, toAdd);
    		cal.addJob(job);
    	}
        assertTrue(cal.isFull());
    }
    
    /**
     * Test method for {@link model.Calendar#isFull()}.
     * @throws BRException 
     * @throws IOException 
     */
    @Test(expected = MaxJobsExceededException.class)
    public void testIsFullMaxPlusOneJobs() throws IOException, BRException {
    	LocalDate start = LocalDate.from(TOMORROW);
    	for (int i = 0; i < (MAX_JOBS + 1) * 2; i += 2) {
    		LocalDate toAdd = start.plusDays(i);
    		Job job = new Job(toAdd, toAdd);
    		cal.addJob(job);
    	}
        assertTrue(cal.isFull());
    }

    /**
     * Test method for {@link model.Calendar#isValidLength(model.Job)}.
     */
    @Test
    public void testIsValidLengthOneDayLong() {
        assertTrue(cal.isValidLength(oneDayJobTomorrow));
    }
    
    /**
     * Test method for {@link model.Calendar#isValidLength(model.Job)}.
     */
    @Test
    public void testIsValidLengthTwoDaysLong() {
        assertTrue(cal.isValidLength(twoDayJobTomorrow));
    }
    
    /**
     * Test method for {@link model.Calendar#isValidLength(model.Job)}.
     */
    @Test
    public void testIsValidLengthNegativeDaysLong() {
    	Job negLengthJob = new Job(TOMORROW, TOMORROW.minusDays(1));
        assertFalse(cal.isValidLength(negLengthJob));
    }
    
    /**
     * Test method for {@link model.Calendar#isValidLength(model.Job)}.
     */
    @Test
    public void testIsValidLengthThreeDaysLong() {
    	Job threeDayJob = new Job(TOMORROW, TOMORROW.plusDays(2));
        assertFalse(cal.isValidLength(threeDayJob));
    }
    
	 /**
	  * Test method for {@link model.Calendar#addJob(model.Job)}.
	 * @throws BRException 
	 * @throws IOException 
	  */
	 @Test(expected = JobTooLongException.class)
	 public void testAddJobJobTooLongException1() throws IOException, BRException {
		 Job threeDayJob = new Job(TOMORROW, TOMORROW.plusDays(2));
		 cal.addJob(threeDayJob);
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#addJob(model.Job)}.
	 * @throws BRException 
	 * @throws IOException 
	  */
	 @Test(expected = JobTooLongException.class)
	 public void testAddJobJobTooLongException2() throws IOException, BRException {
		 Job negLengthJob = new Job(TOMORROW, TOMORROW.minusDays(1));
		 cal.addJob(negLengthJob);
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#isNotPast(model.Job)}.
	  */
	 @Test
	 public void testIsNotPastYesterday() {
		 Job yesterday = new Job(YESTERDAY, YESTERDAY);
		 assertFalse(cal.isNotPast(yesterday));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#addJob(model.Job)}.
	  * @throws BRException 
	  * @throws IOException 
	  */
	  @Test(expected = InvalidTimeIntervalException.class)
	  public void testAddJobInvalidTimeIntervalException1() throws IOException, BRException {
		  Job yesterday = new Job(YESTERDAY, YESTERDAY);
		  cal.addJob(yesterday);
	  }
	 
	 /**
	  * Test method for {@link model.Calendar#isNotPast(model.Job)}.
	  */
	 @Test
	 public void testIsNotPastToday() {
		 Job today = new Job(TODAY, TODAY);
		 assertFalse(cal.isNotPast(today));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#addJob(model.Job)}.
	  * @throws BRException 
	  * @throws IOException 
	  */
	  @Test(expected = InvalidTimeIntervalException.class)
	  public void testAddJobInvalidTimeIntervalException2() throws IOException, BRException {
		  Job today = new Job(TODAY, TODAY);
		  cal.addJob(today);
	  }
	 
	 /**
	  * Test method for {@link model.Calendar#isNotPast(model.Job)}.
	  */
	 @Test
	 public void testIsNotPastTomorrow() {
		 assertTrue(cal.isNotPast(oneDayJobTomorrow));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#isWithinMaxDays(model.Job)}.
	  */
	 @Test
	 public void testIsWithinMaxDaysTomorrow() {
		 assertTrue(cal.isWithinMaxDays(oneDayJobTomorrow));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#isWithinMaxDays(model.Job)}.
	  */
	 @Test
	 public void testIsWithinMaxDaysMaxMinusOne() {
		 LocalDate dayBeforeMax = MAX_DAYS_FROM_NOW.minusDays(1);
		 Job job = new Job(dayBeforeMax, dayBeforeMax);
		 assertTrue(cal.isWithinMaxDays(job));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#isWithinMaxDays(model.Job)}.
	  */
	 @Test
	 public void testIsWithinMaxDaysMax() {
		 Job job = new Job(MAX_DAYS_FROM_NOW, MAX_DAYS_FROM_NOW);
		 assertTrue(cal.isWithinMaxDays(job));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#isWithinMaxDays(model.Job)}.
	  */
	 @Test
	 public void testIsWithinMaxDaysMaxPlusOne() {
		 LocalDate dayAfterMax = MAX_DAYS_FROM_NOW.plusDays(1);
		 Job job = new Job(dayAfterMax, dayAfterMax);
		 assertFalse(cal.isWithinMaxDays(job));
	 }
	 
	 /**
	  * Test method for {@link model.Calendar#addJob(model.Job)}.
	  * @throws BRException 
	  * @throws IOException 
	  */
	  @Test(expected = InvalidTimeIntervalException.class)
	  public void testAddJobInvalidTimeIntervalException3() throws IOException, BRException {
		  LocalDate dayAfterMax = MAX_DAYS_FROM_NOW.plusDays(1);
		  Job job = new Job(dayAfterMax, dayAfterMax);
		  cal.addJob(job);
	  }

    /**
     * Restores the Calendar to read from the persistent info files.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws ClassNotFoundException, IOException {
        cal.getJobs().clear();
        cal.getJobs().putAll(SerializableIO.readJobs());
    }
}
