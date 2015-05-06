/*
 * TCSS 360 Project - Group 3!
 */ 
package model;




import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This class represents a Park Manager 
 * @author Maurice Shaw
 * @version May 2014
 */
public class ParkManager extends User {
	
	//Field
	
	/**
	 * List of parks managed by this park manager.
	 */
	private ArrayList<String> myParks;
	
	/**
	 * Constructs a Park Manager.
	 * @param theLastName  Park Manager's last name.
	 * @param theFirstName Park Manager's first name.
	 * @param theEmail     Park Manager's email.
	 */
	public ParkManager (final String theLastName, final String theFirstName, final String theEmail) {
        super(theLastName, theFirstName, theEmail);
        myParks = new ArrayList<String>();
	}
	
    /**
     * Returns a list of parks used to search jobs.
     * @return list of parks.
     */
    public List<String> getParks() {
        ArrayList<String> parksCopy =  (ArrayList<String>) myParks.clone(); 
        return parksCopy; 
    }
    
    
    /**
     * Returns a list of volunteers for a job.
     * @param theJob The job for which a list of volunteers is requested.
     * @return  List of volunteers.
     */
    public List<Volunteer> getVolunteers(final Job theJob) {
        Job gig = theJob;
        
        return gig.getVolunteers();
    }
	
	/**
	 * Submits a new job to the calendar.
	 * @param theCalendar The calendar to add job.
	 * @param theTitle The title of the job
	 * @param theParkName The name of the park where job is located.
	 * @param theLocation The location of the job.
	 * @param theStart	The start date of the job.
	 * @param theEnd	The end date of the job
	 * @param theLight	The level of work.
	 * @param theMed	The level of work.
	 * @param theHeavy	The level of work.
	 * @param theDescription Description of the job.
	 * @throws Exception 
	 */
	public void submit(final Calendar theCalendar, final String theTitle, final String theParkName, final String theLocation,
			final LocalDate theStart, final LocalDate theEnd, final int theLight, final int theMed,
			final int theHeavy, final String theDescription) throws Exception {
	    if (myParks.contains(theParkName.toLowerCase())) {
	        Job gig = new Job(theTitle, theParkName, theLocation,theStart, theEnd, theLight, theMed,
						theHeavy, theDescription);
	        Calendar cal = theCalendar;
	        cal.addJob(gig);
	    
	    } else {
	        throw new Exception("This not one of the parks you manage");
	    }
	}
	    
		
	
    /**
     * Adds a park to this Park Managers list.
     * @thePark the name of the park.
     */
    public void addPark(final String thePark) {
        String park = thePark;
        if(park.length() > 0)
            myParks.add(park.toLowerCase());
    }

	
	/**
	 * String representation of a Park Manager.
	 * @return park manager as string.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Park Manager: ");	
		str.append(super.toString());	
		if (myParks.size() > 0)
		    moreToString(str);
		return str.toString();
	}
	
	/**
	 * Helps print park manager representation.
	 * @param str StringBuilder to make string
	 */
	private void moreToString(final StringBuilder str) {
	    str.append("\nParks Managed:\n");
	    for(String park: myParks){
	        str.append(park.toUpperCase().charAt(0));
	        str.append("\n");
	    }
	}
	
	public static void main(String[] theArgs) throws Exception {
        ParkManager park = new ParkManager("Hampton", "Maurice", "email@youKnowWhere.com");
       
        Job job = new Job("Clean Stuff", "NW Park", "Elem Street", new Date(05/24/15), new Date(05/25/15), 1,2,3,"Clean up woods area and my office");
        Calendar cal = new Calendar();
        park.addPark("Nwp");
        
        park.submit(cal, "Clean Stuff", "NW Park", "Elem Street", new Date(05/24/15), new Date(05/25/15), 1,2,3,"Clean up woods area and my office");
        if (cal.isFull()){
        System.out.println(park.getParks());
        System.out.println(park.getFirstName());
        System.out.println(park.getLastName());
        System.out.println(park.getEmail());
        System.out.println(park);
        }
    }


}



