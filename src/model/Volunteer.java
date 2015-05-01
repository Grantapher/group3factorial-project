/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.util.ArrayList;
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

    public void viewPending() {
        // TODO print the jobs to the screen? Did we get rid of this?
    }

    /**
     * Adds the job to the this volunteer's list of jobs.
     *
     * @param job the job to sign up for
     */
    public void signUp(final Job job) {
        myJobs.add(job);
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

}
