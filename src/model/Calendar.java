/*
 * Calendar
 * 
 * Version 0.1
 * 
 * April 30, 2015
 * 
 * Author: Wing-Sea Poon
 */
 
 package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Calendar {
	public static final String JOB_FILE_NAME = "Jobs and Dates.txt";	
	public static final int MAX_TOTAL_JOBS = 30;
	public static final int MAX_JOBS_PER_WEEK = 5;
	public static final int HALF_WEEK = 3;
	
	private Map<LocalDateTime, ArrayList<Job>> listOfJobs;
	
	public Calendar() throws FileNotFoundException {
		listOfJobs = new TreeMap<LocalDateTime, ArrayList<Job>>();
		Scanner fileReader = new Scanner(new File(JOB_FILE_NAME));
		
		// TODO: Have to re-create job objects from File
		while (fileReader.hasNextLine()) {
			Job job = new Job(fileReader.next());
			LocalDateTime startDate = LocalDateTime.parse(fileReader.next());
			LocalDateTime endDate = LocalDateTime.parse(fileReader.next());
			addJobToMap(job);
		}
		fileReader.close();
	}
	
	public boolean isFull(LocalDateTime date) {
		int jobsThisWeek = 0;
		LocalDateTime startDate = date.minusDays(HALF_WEEK);
		LocalDateTime endDate = date.plusDays(HALF_WEEK);
		
		// TODO: What if the job spans 2 days?
		for (LocalDateTime dayIterator = startDate; 
				dayIterator.isBefore(endDate) || dayIterator.equals(endDate); 
				dayIterator = dayIterator.plusDays(1)) {
			if (listOfJobs.containsKey(dayIterator)) {
				jobsThisWeek += listOfJobs.get(dayIterator).size();
			}
		}
		
		return jobsThisWeek >= MAX_JOBS_PER_WEEK;
	}
	
	public boolean isFull() {
		return listOfJobs.size() >= MAX_TOTAL_JOBS;
	}
	
	public void addJob(Job job) throws FileNotFoundException {
		// TODO: BR 5
		addJobToMap(job);
		addJobToFile(job);
	}
	
	private void addJobToMap(Job job) {
		boolean dateIsInCalendar = listOfJobs.containsKey(job.getStartDate());
		
		if (!dateIsInCalendar) {
			
			// create a date to put into calendar
			listOfJobs.put(job.getStartDate(), new ArrayList<Job>());
		}
		
		// add job to list of jobs for that date
		listOfJobs.get(job.getStartDate()).add(job);
	}
	
	private void addJobToFile(Job job) throws FileNotFoundException {
		PrintStream output = new PrintStream(new File(JOB_FILE_NAME));
		
		output.append(job.getTitle() + "\t" + job.getStartDate() + "\t" 
						+ job.getEndDate());
		output.close();
	}
}
