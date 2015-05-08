/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Job implements Comparable<Job>, Cloneable {
    private final String title;
    private final String parkName;
    private final String location;
    private final LocalDate start;
    private final LocalDate end;
    private int curLight;
    private int curMed;
    private int curHeavy;
    private final String description;
    private final int maxLight;
    private final int maxMed;
    private final int maxHeavy;
    private final List<Volunteer> volunteers;

    
    /**
     * Constructs a job from a string in this format
    	title + "/" + parkName "/" + location
    	+ "/" + start + "/" + end
    	+ "/" + maxLight + "/" + curLight
    	+ "/" + maxMed + "/" + curMed
    	+ "/" + maxHeavy + "/" + curHeavy
    	+ "/" + description
    	for(Volunteer v : volunteers) {
    		+ "/" + v.getEmail()   		
    	}
     */
    public Job(String jobString) {
    	String[] params = jobString.split("/");
    	title = params[0];
    	parkName = params[1];
    	location = params[2];
    	start = LocalDate.parse(params[3]);
    	end = LocalDate.parse(params[4]);
    	maxLight = Integer.parseInt(params[5]);
    	curLight = Integer.parseInt(params[6]);
    	maxMed = Integer.parseInt(params[7]);
    	curMed = Integer.parseInt(params[8]);
    	curHeavy = Integer.parseInt(params[9]);
    	maxHeavy = Integer.parseInt(params[10]);
    	description = params[11];
    	volunteers = new ArrayList<Volunteer>();
    	for(int i = 11; i < params.length; i++) {
    		volunteers.add(new Volunteer(params[i]));
    	}
    }
    /**
     * Constructs a new job
     * @param title
     * @param parkName
     * @param location
     * @param start
     * @param end
     * @param light
     * @param med
     * @param heavy
     * @param description
     */
    public Job(final String title, final String parkName, final String location,
            final LocalDate start, final LocalDate end, final int light, final int med,
            final int heavy, final String description) {
        this.title = title;
        this.parkName = parkName;
        this.location = location;
        this.start = LocalDate.from(start);
        this.end = LocalDate.from(end);
        maxLight = light;
        maxMed = med;
        maxHeavy = heavy;
        this.description = description;
        curLight = 0;
        curMed = 0;
        curHeavy = 0;
        volunteers = new ArrayList<Volunteer>();
    }

    // Getters
    public LocalDate getStartDate() {
        return start;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public LocalDate getEndDate() {
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

    /**
     * These 3 methods check if a job is full
     * @return
     */
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
     */
    public boolean containsVolunteer(final Volunteer volunteer) {
        for (final Volunteer v : volunteers) {
            if (v.getEmail().equals(volunteer.getEmail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Receives a list of parks as input and returns true if this job is in one
     * of the parks.
     */
    public boolean isJobinParkList(final List<String> parks) {
        for (final String p : parks) {
            if (parkName.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a volunteer to this job at the given job. 
     * Returns false if the add failed (because a grade was full)
     * @param v
     * @param grade
     * @return
     */
    public boolean addVolunteer(final Volunteer v, final char grade) {
    	if(this.containsVolunteer(v)) {
    		return false;
    	}
        if (grade == 'L' || grade == 'l') {
            if (maxLight == curLight) {
                return false;
            }
            curLight++;
        } else if (grade == 'M' || grade == 'm') {
            if (maxMed == curMed) {
                return false;
            }
            curMed++;
        } else if (grade == 'H' || grade == 'h') {
            if (maxHeavy == curHeavy) {
                return false;
            }
            curHeavy++;
        }
        volunteers.add(v);
        return true;
    }
    
    public String toString() {
    	String result = "";
    	result += title + "/" + parkName + "/" + location;
    	result += "/" + start + "/" + end;
    	result += "/" + maxLight + "/" + curLight;
    	result += "/" + maxMed + "/" + curMed;
    	result += "/" + maxHeavy + "/" + curHeavy;
    	result += "/" + description;
    	for(Volunteer v : volunteers) {
    		result += "/" + v;   		
    	}
    	result += "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    /**
     * Compares this job to another based on start date.
     */
	public int compareTo(Job other) {
		return this.start.compareTo(other.start);
	}
}
