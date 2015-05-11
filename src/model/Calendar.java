/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Handles the association between jobs, dates, and scheduling.
 *
 * @version May 9, 2015
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
     * @throws FileNotFoundException if the job info file is not found.
     */
    public static Calendar getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new Calendar();
        }
        return instance;
    }

    /**
     * Private constructor. Reads in persistent data from a File, and stores it
     * back into the Calendar.
     *
     * @throws FileNotFoundException if the job info file is not found.
     */
    private Calendar() throws FileNotFoundException {
        dateToListOfJobs = FileIO.readJobs();
    }

    /**
     * @return a Map from dates to lists of jobs on that date.
     */
    public Map<LocalDate, List<Job>> getJobs() {
        final Map<LocalDate, List<Job>> copy = new TreeMap<LocalDate, List<Job>>(
                dateToListOfJobs);
        return copy;
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
     * Adds a Job to the Calendar.
     *
     * @param job the Job to add.
     * @throws IOException if the Job file doesn't exist
     * @return true if the Job has been added successfully
     */
    public boolean addJob(final Job job) throws IOException {
        if (!isFull() && !isFull(job.getStartDate()) && !isFull(job.getEndDate())
                && isValidLength(job) && isValidInterval(job)) {
            addJobToMap(job);
            addJobToFile(job);
            return true;
        }
        return false;
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
        return startDate.plusDays(MAX_JOB_LENGTH).isAfter(endDate);
    }

    /**
     * Checks whether the Job has been scheduled on a valid date.
     *
     * @param job The Job whose date we want to check
     * @return true if this Job is not in the past, and within the max number of
     *         days we want to keep track of.
     */
    public boolean isValidInterval(final Job job) {
        final LocalDate startDate = job.getStartDate();
        final LocalDate now = LocalDate.now();
        return startDate.isAfter(now) && now.plusDays(MAX_DAYS).isAfter(startDate);
    }

    /**
     * Checks whether a week is full of Jobs.
     *
     * @param date the Date when we want to add a Job.
     * @return true if this week contains the max number of Jobs it can handle
     *         for a week.
     */
    public boolean isFull(final LocalDate date) {
        int jobsThisWeek = 0;
        final LocalDate startDate = date.minusDays(HALF_WEEK);
        final LocalDate endDate = date.plusDays(HALF_WEEK);
        final LocalDate dayBeforeStart = startDate.minusDays(1);
        List<Job> listOfJobsThisDay = null;

        // Count all jobs that have startDate within this week,
        // based on date param
        for (LocalDate day = LocalDate.from(startDate); day.isBefore(endDate)
                || day.equals(endDate); day = day.plusDays(1)) {
            if (dateToListOfJobs.containsKey(day)) {
                listOfJobsThisDay = dateToListOfJobs.get(day);
                jobsThisWeek += listOfJobsThisDay.size();
            }
        }

        // Count all jobs that have endDate at the start of the week
        if (dateToListOfJobs.containsKey(dayBeforeStart)) {
            listOfJobsThisDay = dateToListOfJobs.get(dayBeforeStart);
            for (final Job job : listOfJobsThisDay) {
                if (job.getEndDate().equals(startDate)) {
                    jobsThisWeek++;
                }
            }
        }

        return jobsThisWeek >= MAX_JOBS_PER_WEEK;
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

    // private helper method for addJob(Job).
    // Adds the job to a persistent file so that the Calendar can restore
    // this information when the program starts up again.
    private void addJobToFile(final Job job) throws IOException {
        FileIO.addJob(job);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return dateToListOfJobs.toString();
    }
}
