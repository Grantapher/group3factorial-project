
/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




/**
 * This class represents an Administrator.
 *  
 * @author Maurice Shaw
 * @version May 2014
 */
public class Administrator extends AbstractUser {
    
   /**
    * {@inheritDoc}
    */
    public Administrator(final String theLastName, final String theFirstName, final String theEmail) {
        super(theLastName, theFirstName, theEmail);
    }
    
   /**
    * Returns a volunteer found by last name.
    * 
    * @param theFileReader  Used to obtain list of users.
    * @param theName    The last name of the user to be searched.
    * @return           The volunteer or null if not found.
    */
    public User findVolunteer(final String theName) {
        FileIO fileReader = FileIO.getInstance();
        User freeWorker = null; 
        List<User> userList = fileReader.queryUsers(theName, 'v'); // List of users
        if (userList != null) { // list does exist
            for (User volunteer: userList) {
                if (volunteer.getLastName().toLowerCase().equals(theName.toLowerCase())){ // search by last name
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
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Administrator: ");   
        str.append(super.toString()); 
        return str.toString();
    }
    

}
