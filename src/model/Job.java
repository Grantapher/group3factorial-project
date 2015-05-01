package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Job {
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
}
