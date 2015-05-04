/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Contains the information a Volunteer needs saved.
 *
 * @author Grant Toepfer
 * @version May 1, 2015
 */
public class Volunteer extends User implements Cloneable {

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
    }

    /**
     * Creates a copy of the given volunteer.
     *
     * @param that the volunteer to create a copy of
     */
    public Volunteer(final Volunteer that) {
        super(that);
    }

    /**
     * Prints all the jobs signed up for to the console.
     */
    public void viewSignedUp() {
        for (final Job job : getJobs()) {
            System.out.println(job);
        }
    }

    /**
     * @return the list of jobs this volunteer is signed up for
     */
    public List<Job> getJobs() {
        final ArrayList<Job> list = new ArrayList<>();
        final Map<LocalDate, List<Job>> map = Calendar.getJobs();
        for (final LocalDate date : map.keySet()) {
            for (final Job job : map.get(date)) {
                if (job.containsVolunteer(this)) {
                    list.add(job);
                }
            }
        }
        list.sort(null);
        return list;
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
        return super.equals(that);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Jobs: ");
        for (final Job job : getJobs()) {
            sb.append('\t');
            sb.append(job);
        }
        return sb.toString();
    }

}
