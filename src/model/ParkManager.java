/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is a Park Manager
 *
 * @author Maurice Shaw
 * @version May 2014
 */
public class ParkManager extends User {

    // Field

    /**
     * List of parks managed by this park manager.
     */
    private final ArrayList<String> myParks;

    /**
     * Constructs a ParkManager.
     */
    public ParkManager(final String theLastName, final String theFirstName,
            final String theEmail) {
        super(theLastName, theFirstName, theEmail);
        myParks = new ArrayList<String>();
    }

    /**
     * Returns a list of parks used to search jobs.
     *
     * @return list of parks.
     */
    public List<String> getParks() {
        final ArrayList<String> parksCopy = (ArrayList<String>) myParks.clone();
        return parksCopy;
    }

    /**
     * Returns a list of volunteers for a job.
     *
     * @param theJob The job for which a list of volunteers is requested.
     * @return List of volunteers.
     */
    public List<Volunteer> getVolunteers(final Job theJob) {
        final Job gig = theJob;

        return gig.getVolunteers();
    }

    /**
     * Submits a new job to the calendar.
     *
     * @param theCalendar The calendar to add job.
     * @param theTitle The title of the job
     * @param theParkName The name of the park where job is located.
     * @param theLocation The location of the job.
     * @param theStart The start date of the job.
     * @param theEnd The end date of the job
     * @param theLight The level of work.
     * @param theMed The level of work.
     * @param theHeavy The level of work.
     * @param theDescription Description of the job.
     * @throws FileNotFoundException
     */
    public void submit(final Calendar theCalendar, final String theTitle,
            final String theParkName, final String theLocation, final Date theStart,
            final Date theEnd, final int theLight, final int theMed, final int theHeavy,
            final String theDescription) throws FileNotFoundException {

        final Job gig = new Job(theTitle, theParkName, theLocation, theStart, theEnd,
                theLight, theMed, theHeavy, theDescription);
        final Calendar cal = theCalendar;
        cal.addJob(gig);
    }

    /**
     * Adds a park to this Park Managers list.
     *
     * @thePark the name of the park.
     */
    public void addPark(final String thePark) {
        final String park = thePark;
        if (park.length() > 0) {
            myParks.add(park);
        }
    }

    /**
     * String representation of a Park Manager.
     *
     * @return park manager as string.
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("Park Manager: ");
        str.append(getFirstName());
        str.append(" ");
        str.append(getLastName());
        str.append("\nEmail: ");
        str.append(getEmail());
        if (myParks.size() > 0) {
            moreToString(str);
        }
        return str.toString();
    }

    /**
     * Helps print park manager representation.
     *
     * @param str StringBuilder to make string
     */
    private void moreToString(final StringBuilder str) {
        str.append("\nParks Managed:\n");
        for (final String park : myParks) {
            str.append(park);
            str.append("\n");
        }
    }

}
