/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Contains the information a Volunteer needs saved.
 *
 * @author Grant Toepfer
 * @version May 1, 2015
 */
public class Volunteer extends AbstractUser implements Cloneable {
    /** Contains the jobs currently signed up for. */
    private final List<Job> myJobs;

    /**
     * Creates a new volunteer object with the given identifiers and list of
     * jobs.
     *
     * @param lastName the user's last name
     * @param firstName the user's first name
     * @param email the user's email
     * @param jobs the jobs the user is signed up for
     */
    public Volunteer(final String lastName, final String firstName, final String email,
            final List<Job> jobs) {
        super(lastName, firstName, email);
        myJobs = cloneJobs(jobs);
    }

    /**
     * Creates a copy of the given volunteer.
     *
     * @param that the volunteer to create a copy of
     */
    public Volunteer(final Volunteer that) {
        super(that);
        myJobs = cloneJobs(that.myJobs);
    }

    private List<Job> cloneJobs(final List<Job> that) {
        final ArrayList<Job> clone = new ArrayList<>();
        for (final Job job : that) {
            try {
                clone.add((Job) job.clone());
            } catch (final CloneNotSupportedException e) {
                throw new AssertionError(e.getMessage());
            }
        }
        return clone;
    }

    /**
     * Adds the job to the this volunteer's list of jobs.
     *
     * @param job the job to sign up for
     */
    public void signUp(final Job job) throws SignupFailException {
        // BR 6: A Volunteer may not sign up for a job that has passed.
        final Date now = Date.from(Instant.now());
        if (job.getStart().before(now)) {
            throw new SignupFailException("Job is now in the past.");
        }

        // BR 7: A Volunteer may not sign up for two jobs on the same day.
        final GregorianCalendar start = new GregorianCalendar();
        start.setTime(job.getStart());
        final int startDay = start.get(GregorianCalendar.DAY_OF_YEAR);
        final GregorianCalendar end = new GregorianCalendar();
        end.setTime(job.getEnd());
        final int endDay = end.get(GregorianCalendar.DAY_OF_YEAR);
        for (final Job listJob : myJobs) {
            final GregorianCalendar listStart = new GregorianCalendar();
            listStart.setTime(listJob.getStart());
            final int listStartDay = listStart.get(GregorianCalendar.DAY_OF_YEAR);
            final GregorianCalendar listEnd = new GregorianCalendar();
            listEnd.setTime(listJob.getEnd());
            final int listEndDay = listEnd.get(GregorianCalendar.DAY_OF_YEAR);
            if (checkJobConflict(startDay, endDay, listStartDay, listEndDay)) {
                throw new SignupFailException(job + "conflicts with\n" + listJob);
            }
        }

        // Both the business rules have been satisfied.
        myJobs.add(job);
    }

    /**
     * Checks if these jobs' scheduled days of operation conflict with each
     * other.
     *
     * @param startDay1 day of the year of the first start day
     * @param endDay1 day of the year of the first end day
     * @param startDay2 day of the year of the second start day
     * @param endDay2 day of the year of the second end day
     * @return true if these two jobs conflict
     */
    private boolean checkJobConflict(final int startDay1, int endDay1, final int startDay2,
            int endDay2) {
        // check for leap year
        int numDays = 365;
        final int year = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
        if (year % 4 == 0) {
            // leap year
            numDays = 366;
        }

        // adjust for jobs spilling over new year
        if (startDay1 > endDay1) {
            endDay1 += numDays;
        }
        if (startDay2 > endDay2) {
            endDay2 += numDays;
        }

        return !(startDay2 < startDay1 && endDay2 < startDay1 || startDay1 < startDay2
                && endDay1 < startDay2);
    }

    /**
     * Prints all the jobs signed up for to the console.
     */
    public void viewSignedUp() {
        for (final Job job : myJobs) {
            System.out.println(job);
        }
    }

    /**
     * @return the list of jobs this volunteer is signed up for
     */
    public List<Job> getJobs() {
        return cloneJobs(myJobs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Volunteer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof Volunteer) {
            final Volunteer other = (Volunteer) that;
            return myJobs.equals(other.myJobs);
        } else {
            return super.equals(that);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int superHash = super.hashCode();
        return 31 * superHash + myJobs.hashCode();
        // the prime 31 for better variance
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Jobs: ");
        for (final Job job : myJobs) {
            sb.append('\t');
            sb.append(job);
        }
        return sb.toString();
    }

    /**
     * This exception is thrown when the signUp method fails to sign the
     * Volunteer up for a job due to a business rule violation.
     *
     * @author Grant Toepfer
     * @version May 4, 2015
     */
    static class SignupFailException extends Exception {
        /** Generated version ID. */
        private static final long serialVersionUID = -6216892480674809907L;
        /** Carries the exception message */
        private final String message;

        /**
         * Initializes message to a generic exception message.
         */
        public SignupFailException() {
            message = "Signup failed.";
        }

        /**
         * Initializes message to the given string.
         *
         * @param the exception message
         */
        public SignupFailException(final String message) {
            this.message = message;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getMessage() {
            return message;
        }

    }

}
