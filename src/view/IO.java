package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.AbstractUser;
import model.Administrator;
import model.Calendar;
import model.FileIO;
import model.Job;
import model.ParkManager;
import model.Volunteer;

/**
 * The console IO class for our project.
 * @author Sam
 *
 */
public class IO {
	private static char userType;
	private static Volunteer volunteer;
	private static Administrator admin;
	private static ParkManager manager;
	private static Scanner inputReader;
	private static FileIO fileReader;
	private static Calendar calendar;
	
	public static void main(String[] args) {
		fileReader = new FileIO();
		calendar = new Calendar();
		Calendar.addJobs(fileReader.readJobs());
		inputReader = new Scanner(System.in);
		login();
		interact();
	}
	
	/**
	 * Called repeatedly once a user is logged in to display to the user their optios
	 */
	private static void interact() {
		if(userType == 'v') {
			volunteerInteract();
		} else if(userType == 'a') {
			administratorInteract();
		} else if(userType == 'p') {
			managerInteract();
		}
	}
	
	
	/**
	 * The managers options.
	 */
	private static void managerInteract() {
		System.out.println("What would you like to do?");
		System.out.println("Post a job (p)");
		System.out.println("View your jobs (v)");
		System.out.println("Quit (q)");
		char userInput = inputReader.next().charAt(0);
		if(userInput == 'p') {
			createJob();
		} else if(userInput == 'v') {
			displayManagersJobs();
		} else if(userInput == 'q') {
			System.exit(0);
		}
		volunteerInteract();
	}
	
	/**
	 * Displays all jobs happening in the managers parks.
	 * Then it prompts to see ask if they want a volunteer list from a job.
	 */
	private static void displayManagersJobs() {
		int jobIndex = 0;
		List<Job> jobs = new ArrayList<Job>();
		for (Job j : manager.getJobs()) {
			System.out.print(jobIndex + " " + j.display());
			jobs.add(j);
			jobIndex++;
		}
		System.out.println("Would you like to view the volunteers for a job?");
		System.out.print("Job Number (-1 to not view volunteers): ");
		jobIndex = inputReader.nextInt();
		if (jobIndex >= 0) {
			for(Volunteer v : manager.getVolunteers(j)) {
				System.out.println(v);
			}
		}
	}

	/**
	 * Creating a new job
	 */
	private static void createJob() {
		String title;
		String parkName;
		String location;
		String startDate;
		String endDate;
		int light;
		int med;
		int heavy;
		String description;
		System.out.print("Title: ");
		title = inputReader.next();
		System.out.print("Park Name: ");
		parkName = inputReader.next();
		System.out.print("Location: ");
		location = inputReader.next();
		System.out.print("Start Date (yyyy-mm-dd): ");
		startDate = inputReader.next();
		System.out.print("End Date (yyyy-mm-dd): ");
		endDate = inputReader.next();
		System.out.print("# of light volunteers needed: ");
		light = inputReader.nextInt();
		System.out.print("# of mediuim volunteers needed: ");
		med = inputReader.nextInt();
		System.out.print("# of heavy volunteers needed: ");
		heavy = inputReader.nextInt();
		System.out.print("Description: ");
		description = inputReader.next();
		manager.submit(calendar, title, parkName, location, LocalDate.parse(startDate),
				LocalDate.parse(endDate), light, med, heavy, description);
	}
	
	
	/**
	 * The administrators options
	 */
	private static void administratorInteract() {
		System.out.print("Volunteer last name to search for (q to quit): ");
		String lastName = inputReader.next();
		if(lastName.equals("q")) {
			System.exit(0);
		}
		System.out.println(fileIO.findVolunteer(lastName));
		administratorInteract();
	}
	
	/**
	 * The volunteers options
	 */
	private static void volunteerInteract() {
		System.out.println("What would you like to do?");
		System.out.println("Search for a job (s)");
		System.out.println("View your jobs (v)");
		System.out.println("Quit (q)");
		char userInput = inputReader.next().charAt(0);
		if(userInput == 's') {
			displayJobs();
		} else if(userInput == 'v') {
			displaySignedUp();
		} else if(userInput == 'q') {
			System.exit(0);
		}
		volunteerInteract();
	}
	
	/**
	 * Displays all jobs the volunteer has signed up for
	 */
	private static void displaySignedUp() {
		volunteer.viewSignedUp();
	}
	
	/**
	 * Displays all upcoming jobs
	 * Then it prompts the user for if they want to sign up for one.
	 */
	private static void displayJobs() {
		int jobIndex = 0;
		List<Job> jobs = new ArrayList<Job>();
		for (Job j : FileIO.readJobs()) {
			System.out.print(jobIndex + " " + j.display());
			jobs.add(j);
			jobIndex++;
		}
		System.out.println("Would you like to sign up for a job?");
		System.out.print("Job Number (-1 to not sign up for a job): ");
		jobIndex = inputReader.nextInt();
		char grade = 0;
		while (jobIndex >= 0 &&
			(grade == 'l' || grade == 'm' || grade == 'h')) {
			System.out.print("Which grade (l/m/h): ");
			grade = inputReader.next().charAt(0);
		}
		jobs.get(jobIndex).addVolunteer(volunteer, grade);
	}
	
	/**
	 * Logs in the user, giving the option to create a new profile if the login email
	 * does't exist in the system.
	 */
	private static void login() {
		System.out.print("Login Email: ");
		String email = inputReader.next();
		userType = FileIO.getUserType(email);
		if (userType == 0) {
			System.out.println("Unrecognized email\n"
					+ "Would you like to create a new account? (y/n)");
			String newAccount = inputReader.next();
			if(newAccount.charAt(0) == 'y') {
				createUser(email);
			} else {
				login();
			}
		} else if(userType == 'v') {
			volunteer = FileIO.getUser(email);
		} else if(userType == 'a') {
			admin = FileIO.getUser(email);
		} else if(userType == 'p') {
			manager = fileIO.getUser(email);
		}
	}
	
	/**
	 * Creates a new user profile
	 * @param email
	 */
	private void createUser(String email) {
		System.out.print("First Name: ");
		String first = inputReader.next();
		System.out.print("Last Name: ");
		String last = inputReader.next();
		while(userType != 'v' || userType != 'p' || userType != 'a') {
			System.out.print("User Type(v for volunteer, p for park manager, a for admin): ");
			userType = inputReader.next().charAt(0);
		}
		if(userType == 'p') {
			manager = new ParkManager(last, first, email);
			String park;
			do {
				System.out.print("Tell me a park you work in (leave empty when done): ");
				park = inputReader.next();
				if(!park.equals("")) {
					p.addPark(park);
				}
			} while (!park.equals(""));
		} else if(userType == 'v') {
			volunteer = new Volunteer(last, first, email);
		} else if(userType == 'a') {
			admin = new Administrator(last, first, email);
		}
	}
}
