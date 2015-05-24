/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.io.IOException;
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
public class Volunteer extends AbstractUser {

    /**
     * @version 1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new volunteer object with the given identifiers and list of
     * jobs.
     *
     * @param lastName the user's last name
     * @param firstName the user's first name
     * @param email the user's email
     */
    public Volunteer(final String lastName, final String firstName, final String email) {
        super(lastName, firstName, email);
    }

    /**
     * @return the list of jobs this volunteer is signed up for
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    public List<Job> getJobs() throws ClassNotFoundException, IOException {
        final ArrayList<Job> list = new ArrayList<>();
        final Map<LocalDate, List<Job>> map = Calendar.getInstance().getJobs();
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
    public boolean equals(final Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (that instanceof Volunteer) {
            return super.equals(that);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Volunteer\n" + super.toString();
    }

}
