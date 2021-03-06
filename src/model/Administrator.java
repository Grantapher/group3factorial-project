/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Administrator.
 *
 * @author Maurice Shaw
 * @version May 2014
 */
public final class Administrator extends AbstractUser {

    /**
     * @version 1.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public Administrator(final String theLastName, final String theFirstName,
            final String theEmail) {
        super(theLastName, theFirstName, theEmail);
    }

    /**
     * Returns a list of volunteers with a certain last name.
     *
     * @param theName The last name of the user to be searched.
     * @return List of volunteers with the requested last name.
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    public List<Volunteer> findVolunteer(final String theName) throws ClassNotFoundException,
            IOException {
        final List<Volunteer> yourVolunteer = new ArrayList<>();
        final List<AbstractUser> userList = SerializableIO.queryUsers(theName,
                SerializableIO.VOLUNTEER_CHAR); // List of users

        if (!userList.isEmpty()) { // list will be empty if name is not found

            for (final AbstractUser theVolunteer : userList) {
                yourVolunteer.add((Volunteer) theVolunteer);
            }
        }
        return yourVolunteer;
    }

    /**
     * String representation of an Administrator.
     *
     * @return Adminstrator as a string.
     */
    @Override
    public String toString() {
        return "Administrator\n" + super.toString();
    }

}
