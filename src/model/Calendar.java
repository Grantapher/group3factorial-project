/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Calendar
 * <p>
 * April 30, 2015
 *
 * @version 0.1
 * @author Wing-Sea Poon
 */

public class Calendar {
    public static final String JOB_FILE_NAME = "Jobs.txt";
    public static final int MAX_TOTAL_JOBS = 30;
    public static final int MAX_JOBS_PER_WEEK = 5;
    public static final int MAX_JOB_LENGTH = 2;
    public static final int MAX_DAYS = 90;
    public static final int HALF_WEEK = 3;

    private final Map<LocalDate, ArrayList<Job>> dateToListOfJobs;

    /**
     * Constructor. This will eventually read in the persistent data from a
     * File, and store it back into this Calendar.
     *
     * @throws FileNotFoundException if the Jobs.txt file is not found.
     */
    public Calendar() {
        dateToListOfJobs = new TreeMap<LocalDate, ArrayList<Job>>();
        // Scanner fileReader = new Scanner(new File(JOB_FILE_NAME));

        // TODO: Have to re-create job objects from File
        // If job is in the past, don't add it.
        // while (fileReader.hasNextLine()) {
        // Job job = new Job(fileReader.next());
        // LocalDateTime startDate = LocalDateTime.parse(fileReader.next());
        // LocalDateTime endDate = LocalDateTime.parse(fileReader.next());
        // addJobToMap(job);
        // }
        // fileReader.close();
    }

    /**
     * Adds a Job to the Calendar.
     *
     * @param job the Job to add.
     * @throws FileNotFoundException if the persistent Jobs.txt file is not
     *             found.
     */
    public void addJob(final Job job) {
        if (!isFull() && !isFull(job.getStartDate()) && !isFull(job.getEndDate())
                && isValidLength(job) && isValidInterval(job)) {
            addJobToMap(job);
            // addJobToFile(job);
        }
    }

    /**
     * Checks whether the Job spans a valid amount of time.
     *
     * @param job The Job whose length we want to check
     * @return true if this Job is within the max job length
     */
    boolean isValidLength(final Job job) {
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
    boolean isValidInterval(final Job job) {
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
    boolean isFull(final LocalDate date) {
        int jobsThisWeek = 0;
        final LocalDate startDate = date.minusDays(HALF_WEEK);
        final LocalDate endDate = date.plusDays(HALF_WEEK);
        final LocalDate dayBeforeStart = startDate.minusDays(1);
        ArrayList<Job> listOfJobsThisDay = null;

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
    boolean isFull() {
        int totalJobs = 0;
        final Set<LocalDate> allDaysInCalendar = dateToListOfJobs.keySet();
        ArrayList<Job> listOfJobsThisDay = null;

        for (final LocalDate date : allDaysInCalendar) {
            listOfJobsThisDay = dateToListOfJobs.get(date);
            totalJobs += listOfJobsThisDay.size();
        }

        return totalJobs >= MAX_TOTAL_JOBS;
    }

    // private helper method for addJob(Job).
    // Adds the job to this Calendar's temporary data structure
    // for keeping track of jobs.
    void addJobToMap(final Job job) {
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
    // void addJobToFile(Job job) throws FileNotFoundException {
    // PrintStream output = new PrintStream(new File(JOB_FILE_NAME));
    //
    // output.append(job.getTitle() + "\t" + job.getStartDate() + "\t"
    // + job.getEndDate());
    // output.close();
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return dateToListOfJobs.toString();
    }
}
