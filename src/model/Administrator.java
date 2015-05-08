/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class represents an Administrator.
 *
 * @author Maurice Shaw
 * @version May 2014
 */
public class Administrator extends AbstractUser {

    public Administrator(final String theLastName, final String theFirstName,
            final String theEmail) {
        super(theLastName, theFirstName, theEmail);
    }

    /**
     * Returns a volunteer found by last name.
     *
     * @param theFileReader Used to obtain list of users.
     * @param theName The last name of the user to be searched.
     * @return The volunteer or null if not found.
     * @throws FileNotFoundException if the file isn't found
     */
    public User findVolunteer(final String theName) throws FileNotFoundException {
        final FileIO fileReader = FileIO.getInstance();
        User freeWorker = null;
        // List of users
        final List<User> userList = fileReader.queryUsers(theName, 'v');
        if (userList != null) { // list does exist
            for (final User volunteer : userList) {
                // search by last name
                if (volunteer.getLastName().toLowerCase().equals(theName.toLowerCase())) {
                    freeWorker = volunteer;
                    break;
                }

            }
        }
        return freeWorker;
    }

    /**
     * String representation of an Administrator.
     *
     * @return park manager as string.
     */
    @Override
    public String toString() {
        return "Administrator\n" + super.toString();
    }

}
