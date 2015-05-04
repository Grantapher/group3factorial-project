/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Job implements Cloneable {
    private final String title;
    private final String parkName;
    private final String location;
    private final Date start;
    private final Date end;
    private int curLight;
    private int curMed;
    private int curHeavy;
    private final String description;
    private final int maxLight;
    private final int maxMed;
    private final int maxHeavy;
    private final List<Volunteer> volunteers;

    // Constructor
    public Job(final String title, final String parkName, final String location,
            final Date start, final Date end, final int light, final int med, final int heavy,
            final String description) {
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

    // Getters
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

    // Check if a grade is full
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
    // This method requires a user method getEmail() which returns a
    // String
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

    // adds a volunteer to this job at the given grade
    // returns false if the grade was full
    public boolean addVolunteer(final Volunteer v, final char grade) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

}
