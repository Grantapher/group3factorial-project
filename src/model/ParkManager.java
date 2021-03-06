/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.BusinessRuleException;
import exception.NotMyParkException;

/**
 * This class represents a Park Manager .
 *
 * @author Maurice Shaw
 * @version May 2014
 */
public final class ParkManager extends AbstractUser {

    // Field

    /**
     * @version 1.0
     */
    private static final long serialVersionUID = 1L;
    /**
     * List of parks managed by this park manager.
     */
    private final ArrayList<String> myParks;

    /**
     * Constructs a Park Manager.
     *
     * @param theLastName Park Manager's last name.
     * @param theFirstName Park Manager's first name.
     * @param theEmail Park Manager's email.
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
        return new ArrayList<String>(myParks);
    }

    /**
     * Returns a list of volunteers for a job.
     *
     * @param theJob The job for which a list of volunteers is requested.
     * @return List of volunteers.
     */
    public List<Volunteer> getVolunteers(final Job theJob) {
        return theJob.getVolunteers();
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
     * @throws IOException if the Job file doesn't exist.
     * @throws BusinessRuleException if a business rule is attempting to be
     *             violated
     * @return If Job has been successfully added.
     */
    public void submit(final Calendar theCalendar, final String theTitle,
            final String theParkName, final String theLocation, final LocalDate theStart,
            final LocalDate theEnd, final int theLight, final int theMed, final int theHeavy,
            final String theDescription) throws IOException, BusinessRuleException {

        if (isMyPark(theParkName)) {
            final Job gig = new Job(theTitle, theParkName, theLocation, theStart, theEnd,
                    theLight, theMed, theHeavy, theDescription);
            final Calendar cal = theCalendar;
            cal.addJob(gig);
        } else {
            throw new NotMyParkException(theParkName);
        }
    }

    /**
     * Checks to see if the park is managed by this park manager.
     *
     * @param theParkName Name of park.
     * @return If park is present are not.
     */
    public boolean isMyPark(final String theParkName) {
        boolean amIThere = false;

        for (final String parkName : myParks) {
            if (parkName.equalsIgnoreCase(theParkName)) {
                amIThere = true;
                break;
            }
        }
        return amIThere;
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
        if (that instanceof ParkManager) {
            final ParkManager other = (ParkManager) that;
            if (myParks.size() != other.myParks.size()) {
                return false;
            }
            for (int i = 0; i < myParks.size(); i++) {
                if (!myParks.get(i).equals(other.myParks.get(i))) {
                    return false;
                }
            }
            return super.equals(that);
        }
        return false;
    }

    /**
     * String representation of a Park Manager.
     *
     * @return park manager as string.
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("Park Manager\n");
        str.append(super.toString());
        str.append("Parks Managed: ");
        str.append(myParks.size());
        str.append('\n');
        for (final String park : myParks) {
            str.append(park);
            str.append("\n");
        }
        return str.toString();
    }

}
