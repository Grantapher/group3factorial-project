/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.BusinessRuleException;
import exception.FullCategoryException;
import exception.OverbookedVolunteerException;
import exception.PastJobException;

public class Job implements Comparable<Job>, Serializable {
    /**
     * @version 1.0
     */
    public static final int SUCCESS = 0;
    public static final int WORK_CATEGORY_FULL = 1;
    public static final int JOB_IN_PAST = 2;
    public static final int TWO_IN_ONE_DAY = 3;

    private static final long serialVersionUID = 1L;

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
     * Constructs a Job from just a title, park name, start and end date. This
     * constructor was authored by Wing-Sea. / public Job(final String title,
     * final String park, final LocalDate start, final LocalDate end) {
     * this(title, park, "Location", start, end, 5, 5, 5, "Description"); } /**
     * Constructs a job from a jobs toString. The string should be in this
     * format: title + "|" + parkName "|" + location + "|" + start + "|" + end +
     * "|" + maxLight + "|" + curLight + "|" + maxMed + "|" + curMed + "|" +
     * maxHeavy + "|" + curHeavy + "|" + description for(Volunteer v :
     * volunteers) { + "|" + v.getEmail() }
     *
     * @deprecated use SerializableIO
     */
    @Deprecated
    public Job(final String jobString) {
        final String[] params = jobString.split("[|]");
        title = params[0];
        parkName = params[1];
        location = params[2];
        start = LocalDate.parse(params[3]);
        end = LocalDate.parse(params[4]);
        maxLight = Integer.parseInt(params[5]);
        curLight = Integer.parseInt(params[6]);
        maxMed = Integer.parseInt(params[7]);
        curMed = Integer.parseInt(params[8]);
        maxHeavy = Integer.parseInt(params[9]);
        curHeavy = Integer.parseInt(params[10]);
        description = params[11];
        volunteers = FileIO.getVolunteers(params[12]);
    }
    
    /**
     * A Job constructor that makes Calendar tests less cluttered with
     * unnecessary info.
     * 
     * @param start
     * @param end
     */
	public Job(final LocalDate start, final LocalDate end) {
		this("Job", "Park", "Location", start, end, 5, 5, 5, "Description");
	}

    /**
     * Constructs a new job
     *
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

    public int getCurLight() {
        return curLight;
    }

    public int getMaxMed() {
        return maxMed;
    }

    public int getMaxLight() {
        return maxLight;
    }

    public int getCurMed() {
        return curMed;
    }

    public int getMaxHeavy() {
        return maxHeavy;
    }

    public int getCurHeavy() {
        return curHeavy;
    }

    /**
     * These 3 methods check if a job is full
     *
     * @return
     */
    public boolean isLightFull() {
        return maxLight <= curLight;
    }

    public boolean isMedFull() {
        return maxMed <= curMed;
    }

    public boolean isHeavyFull() {
        return maxHeavy <= curHeavy;
    }

    /**
     * Checks if this job has the given volunteer signed up for it.
     */
    public boolean containsVolunteer(final Volunteer volunteer) {
        return volunteers.contains(volunteer);
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
     * Given a volunteer, returns true if adding that volunteer to this job
     * would make that volunteer be signed up for two jobs on the same day.
     *
     * @param v
     * @return
     */
    public boolean dayConflict(final Volunteer v) {
        try {
            for (final Job j : v.getJobs()) {
                if (!j.getStartDate().isAfter(end) && !j.getEndDate().isBefore(start)) {
                    return true;
                }
            }
        } catch (final ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (final IOException e) {
            System.out.println("IO Exception");
        }
        return false;
    }

    /**
     * Adds a volunteer to this job at the given grade(l/m/h). Returns int codes
     * for success/failure: SUCCESS = 0; WORK_CATEGORY_FULL = 1; JOB_IN_PAST =
     * 2; TWO_IN_ONE_DAY = 3;
     *
     * @param v
     * @param grade
     * @return
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     * @throws BusinessRuleException If a business rule is attempting to be
     *             violated
     */
    public void addVolunteer(final Volunteer v, final char grade) throws IOException,
            ClassNotFoundException, BusinessRuleException {
        if (start.isBefore(LocalDate.now())) {
            throw new PastJobException(start);
        }
        if (dayConflict(v)) {
            throw new OverbookedVolunteerException(this, v);
        }
        if (grade == 'l') {
            if (maxLight <= curLight) {
                throw new FullCategoryException("light");
            }
            curLight++;
        } else if (grade == 'm') {
            if (maxMed <= curMed) {
                throw new FullCategoryException("medium");
            }
            curMed++;
        } else if (grade == 'h') {
            if (maxHeavy <= curHeavy) {
                throw new FullCategoryException("heavy");
            }
            curHeavy++;
        }
        volunteers.add(v);
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
        if (that instanceof Job) {
            final Job other = (Job) that;
            if (volunteers.size() != other.volunteers.size()) {
                return false;
            }
            for (int i = 0; i < volunteers.size(); i++) {
                if (!volunteers.get(i).equals(other.volunteers.get(i))) {
                    return false;
                }
            }
            return title.equals(other.title) && parkName.equals(other.parkName)
                    && location.equals(other.location) && start.equals(other.start)
                    && end.equals(other.end) && curLight == other.curLight
                    && curMed == other.curMed && curHeavy == other.curHeavy
                    && description.equals(other.description) && maxLight == other.maxLight
                    && maxMed == other.maxMed && maxHeavy == other.maxHeavy;
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(title + "|" + parkName + "|" + location);
        sb.append("|" + start + "|" + end);
        sb.append("|" + maxLight + "|" + curLight);
        sb.append("|" + maxMed + "|" + curMed);
        sb.append("|" + maxHeavy + "|" + curHeavy);
        sb.append("|" + description);
        sb.append("|");
        sb.append("\n");
        for (final Volunteer v : volunteers) {
            sb.append(v);
        }
        return sb.toString();
    }

    /**
     * Compares this job to another based on start date.
     */
    @Override
    public int compareTo(final Job other) {
        return start.compareTo(other.start);
    }

}
