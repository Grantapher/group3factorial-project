/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exception.BusinessRuleException;
import exception.InvalidTimeIntervalException;
import exception.JobTooLongException;
import exception.MaxJobsExceededException;
import exception.WeekFullException;

/**
 * Handles the association between jobs, dates, and scheduling.
 *
 * @version May 30, 2015
 * @author Wing-Sea Poon
 */
public class Calendar {
    private static Calendar instance = null;

    private static final int MAX_TOTAL_JOBS = 30;
    private static final int MAX_JOBS_PER_WEEK = 5;
    private static final int MAX_JOB_LENGTH = 2;
    private static final int MAX_DAYS = 90;
    private static final int HALF_WEEK = 3;

    private final Map<LocalDate, List<Job>> dateToListOfJobs;

    /**
     * @return the single instance to this class
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    public static Calendar getInstance() throws ClassNotFoundException, IOException {
        if (instance == null) {
            instance = new Calendar();
        }
        return instance;
    }

    /**
     * Private constructor. Reads in persistent data from a File, and stores it
     * back into the Calendar.
     *
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    private Calendar() throws ClassNotFoundException, IOException {
        dateToListOfJobs = SerializableIO.readJobs();
    }

    /**
     * @return a Map from dates to lists of jobs on that date.
     */
    public Map<LocalDate, List<Job>> getJobs() {
        return dateToListOfJobs;
    }

    /**
     * @param parks A List of parks that we want to see the jobs for.
     * @return A list of jobs associated with the parks passed in.
     */
    public List<Job> getJobs(final List<String> parks) {
        final List<Job> jobs = new ArrayList<Job>();

        for (final String park : parks) {
            for (final LocalDate date : dateToListOfJobs.keySet()) {
                for (final Job job : dateToListOfJobs.get(date)) {
                    if (job.getPark().equalsIgnoreCase(park)) {
                        jobs.add(job);
                    }
                }
            }
        }

        jobs.sort(null);
        return jobs;
    }

    /**
     * Adds a Job to the Calendar. Jobs must be added at least one day in
     * advance.
     *
     * @param job the Job to add.
     * @throws IOException if the Job file doesn't exist
     * @throws BusinessRuleException if a business rule is attempting to be
     *             violated
     */
    public void addJob(final Job job) throws IOException, BusinessRuleException {
        if (isFull()) {
            throw new MaxJobsExceededException();
        }
        if (isFull(job.getStartDate(), job.getEndDate())) {
            throw new WeekFullException();
        }
        if (!isValidLength(job)) {
            throw new JobTooLongException();
        }
        if (!isNotPast(job) || !isWithinMaxDays(job)) {
            throw new InvalidTimeIntervalException(job);
        }
        addJobToMap(job);
    }

    /**
     * Checks whether the whole Calendar is full of Jobs.
     *
     * @return true if the Calendar contains the max number of Jobs.
     */
    public boolean isFull() {
        int totalJobs = 0;
        final Set<LocalDate> allDaysInCalendar = dateToListOfJobs.keySet();
        List<Job> listOfJobsThisDay = null;

        for (final LocalDate date : allDaysInCalendar) {
            listOfJobsThisDay = dateToListOfJobs.get(date);
            totalJobs += listOfJobsThisDay.size();
        }

        return totalJobs >= MAX_TOTAL_JOBS;
    }

    /**
     * Checks whether a week is full of Jobs.
     *
     * @param start the startDate of the Job to add.
     * @param end the endDate of the Job to add.
     * @return true if this week contains the max number of Jobs it can handle
     *         for a week.
     */
    public boolean isFull(final LocalDate start, final LocalDate end) {
        int jobsThisWeek = countJobStartDatesInWeek(start, end);
        jobsThisWeek += countJobEndDatesAtBeginningOfWeek(start);

        return jobsThisWeek >= MAX_JOBS_PER_WEEK;
    }

    /**
     * Count all jobs that have startDate within this week, based on the start
     * and end date parameters
     *
     * @param start The startDate of the Job we want to add
     * @param end The endDate of the Job we want to add
     * @return The number of Jobs that are within 3 before "start," and 3 days
     *         after "end"
     */
    public int countJobStartDatesInWeek(final LocalDate start, final LocalDate end) {
        int jobsThisWeek = 0;
        final LocalDate weekStart = start.minusDays(HALF_WEEK);
        final LocalDate weekEnd = end.plusDays(HALF_WEEK);
        List<Job> listOfJobsThisDay = null;

        for (LocalDate day = LocalDate.from(weekStart); day.isBefore(weekEnd)
                || day.equals(weekEnd); day = day.plusDays(1)) {
            if (dateToListOfJobs.containsKey(day)) {
                listOfJobsThisDay = dateToListOfJobs.get(day);
                jobsThisWeek += listOfJobsThisDay.size();
            }
        }

        return jobsThisWeek;
    }

    /**
     * Count all jobs that have endDate at the start of the week
     *
     * @param start The startDate of the Job we want to add
     * @return The number of Jobs that have endDate on the start of the week,
     *         counting from "start."
     */
    public int countJobEndDatesAtBeginningOfWeek(final LocalDate start) {
        int jobs = 0;
        final LocalDate startDate = start.minusDays(HALF_WEEK);
        final LocalDate dayBeforeStart = startDate.minusDays(1);

        if (dateToListOfJobs.containsKey(dayBeforeStart)) {
            final List<Job> listOfJobsThisDay = dateToListOfJobs.get(dayBeforeStart);
            for (final Job job : listOfJobsThisDay) {
                if (job.getEndDate().equals(startDate)) {
                    jobs++;
                }
            }
        }

        return jobs;
    }

    /**
     * Checks whether the Job spans a valid amount of time.
     *
     * @param job The Job whose length we want to check
     * @return true if this Job is within the max job length
     */
    public boolean isValidLength(final Job job) {
        final LocalDate startDate = job.getStartDate();
        final LocalDate endDate = job.getEndDate();
        return (endDate.isAfter(startDate) || endDate.equals(startDate))
                && startDate.plusDays(MAX_JOB_LENGTH).isAfter(endDate);
    }

    /**
     * Checks whether the Job has been scheduled on a valid date.
     *
     * @param job The Job whose date we want to check
     * @return true if this Job is not in the past
     */
    public boolean isNotPast(final Job job) {
        final LocalDate startDate = job.getStartDate();
        final LocalDate now = LocalDate.now();
        return startDate.isAfter(now);
    }

    /**
     * Checks whether the Job has been scheduled on a valid date.
     *
     * @param job The Job whose date we want to check
     * @return true if this Job is within the max number of days we want to keep
     *         track of.
     */
    public boolean isWithinMaxDays(final Job job) {
        final LocalDate startDate = job.getStartDate();
        final LocalDate now = LocalDate.now();
        return now.plusDays(MAX_DAYS).equals(startDate)
                || now.plusDays(MAX_DAYS).isAfter(startDate);
    }

    // private helper method for addJob(Job).
    // Adds the job to this Calendar's temporary data structure
    // for keeping track of jobs.
    private void addJobToMap(final Job job) {
        final boolean dateIsInCalendar = dateToListOfJobs.containsKey(job.getStartDate());

        if (!dateIsInCalendar) {

            // create a date to put into calendar
            dateToListOfJobs.put(job.getStartDate(), new ArrayList<Job>());
        }

        // add job to list of jobs for that date
        dateToListOfJobs.get(job.getStartDate()).add(job);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return dateToListOfJobs.toString();
    }

    /**
     * Overwrites the Job file with the current map of jobs.
     *
     * @throws IOException if the file isn't found
     */
    public void writeJobs() throws IOException {
        SerializableIO.writeJobs(dateToListOfJobs);
    }
}
