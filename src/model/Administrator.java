
/*
 * TCSS 360 Project - Group 3!
 */

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




/**
 * This class represents a an Administrator.
 *  
 * @author Maurice Shaw
 * @version May 2014
 */
public class Administrator extends User {
    
    
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
    public User findVolunteer(final FileIO theFileReader, final String theName) {
        FileIO fileReader = theFileReader;
        User freeWorker = null; 
        List<User> userList = fileReader.queryUsers(theName, 118); // List of users
        if (userList != null) { // list does exist
            for (User volunteer: userList) {
                if (volunteer.getLastName().equals(theName)){ // search by last name
                    freeWorker = volunteer;
                    break;
                }
  
            }
        }
        return freeWorker; 
    }
    
    /**
     * String representation of a Park Manager.
     * 
     * @return park manager as string.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Administrator: ");   
        str.append(getFirstName()); 
        str.append(" ");
        str.append(getLastName()); 
        str.append("\nEmail: ");
        str.append(getEmail());
        return str.toString();
    }
    

}
