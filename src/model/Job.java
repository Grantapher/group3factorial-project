/*
 * TCSS 360 Project - Group 3!
 */
 
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Job implements Cloneable {
	private String title;
	private String parkName;
	private String location;
	private Date start;
	private Date end;
	private int curLight;
	private int curMed;
	private int curHeavy;
	private String description;
	private int maxLight;
	private int maxMed;
	private int maxHeavy;
	private List<Volunteer> volunteers;

	//Constructor
	public Job(String title, String parkName, String location,
			Date start, Date end, int light, int med,
			int heavy, String description) {
		this.title = title;
		this.parkName = parkName;
		this.location = location;
		this.start = start;
		this.end = end;
		maxLight = light;
		maxMed = med;
		maxHeavy = heavy;
		this.description = description;
		curLight = 0;
		curMed = 0;
		curHeavy = 0;
		volunteers = new ArrayList<Volunteer>();
	}


	//Getters
	public Date getStart() {
		return start;
	}

	public List<Volunteer> getVolunteers() {
		return volunteers;
	}

	public Date getEnd() {
		return end;
	}

	public String getTitle() {
		return title;
	}

	public String getPark() {
		return parkName;
	}

	public String getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}


	//Check if a grade is full
	public boolean isLightFull() {
		return maxLight == curLight;
	}

	public boolean isMedFull() {
		return maxMed == curMed;
	}

	public boolean isHeavyFull() {
		return maxHeavy == curHeavy;
	}


	/**
	 * Checks if this job has the given volunteer signed up for it.
	 * 
	 */
	//This method requires a user method getEmail() which returns a 
	//String
	public boolean containsVolunteer(Volunteer volunteer) {
		for(Volunteer v : volunteers) {
			if (v.getEmail().equals(volunteer.getEmail())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Receives a list of parks as input and returns true
	 * if this job is in one of the parks.
	 */
	public boolean isJobinParkList(List<String> parks) {
		for(String p : parks) {
			if (parkName.equals(p)) {
				return true;
			}
		}
		return false;
	}
	//adds a volunteer to this job at the given grade 
	//returns false if the grade was full
	public boolean addVolunteer(Volunteer v, char grade) {
		if(grade == 'L' || grade == 'l') {
			if(maxLight == curLight) {
				return false;			
			}
			curLight++;
		} else if(grade == 'M' || grade == 'm') {
			if(maxMed == curMed) {
				return false;
			}
			curMed++;
		} else if(grade == 'H' || grade == 'h') {
			if(maxHeavy == curHeavy) {
				return false;
			}
			curHeavy++;
		}
		volunteers.add(v);
		return true;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
	
}
