package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AbstractUser;
import model.Administrator;
import model.Calendar;
import model.FileIO;
import model.Job;
import model.ParkManager;
import model.Volunteer;

/**
 * The console IO class for our project.
 *
 * @author Sam
 */
public class IO {
	private static char userType;
	private static AbstractUser user;
	private static Volunteer volunteer;
	private static Administrator admin;
	private static ParkManager manager;
	private static Scanner inputReader;
	private static Calendar calendar;

	public static void main(final String[] args) {
		try {
			calendar = Calendar.getInstance();
		} catch (final FileNotFoundException e) {
			System.out.println("Jobs File missing, find it!");
			System.exit(0);
		}
		inputReader = new Scanner(System.in);
		login();
		interact();
	}

	/**
	 * Called repeatedly once a user is logged in to display to the user their
	 * options
	 */
	private static void interact() {
		if (userType == FileIO.VOLUNTEER_CHAR) {
			volunteerInteract();
		} else if (userType == FileIO.ADMIN_CHAR) {
			administratorInteract();
		} else if (userType == FileIO.PARK_MANAGER_CHAR) {
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
		final char userInput = inputReader.next().charAt(0);
		if (userInput == 'p') {
			createJob();
		} else if (userInput == 'v') {
			displayManagersJobs();
		} else if (userInput == 'q') {
			System.exit(0);
		}
		managerInteract();
	}

	/**
	 * Displays all jobs happening in the managers parks. Then it prompts to see
	 * ask if they want a volunteer list from a job.
	 */
	private static void displayManagersJobs() {
		int jobIndex = 1;
		final List<Job> jobs = new ArrayList<Job>();
		for (final Job j : calendar.getJobs(manager.getParks())) {
			System.out.print(jobIndex + " " + j);
			jobs.add(j);
			jobIndex++;
		}

		// Queries to view the volunteers for a job
		System.out.println("Would you like to view the volunteers for a job?");
		System.out.print("Job Number (0 to not view volunteers): ");
		jobIndex = inputReader.nextInt();
		if (jobIndex >= 1 && jobIndex < jobs.size()) {
			for (final Volunteer v : manager.getVolunteers(jobs.get(jobIndex))) {
				System.out.println(v);
			}
		}
	}

	/**
	 * Promps to create a new job
	 */
	private static void createJob() {
		System.out.print("Title: ");
		final String title = inputReader.nextLine();
		System.out.print("Park Name: ");
		final String parkName = inputReader.nextLine();
		System.out.print("Location: ");
		final String location = inputReader.nextLine();
		System.out.print("Start Date (yyyy-mm-dd): ");
		final String startDate = inputReader.next();
		System.out.print("End Date (yyyy-mm-dd): ");
		final String endDate = inputReader.next();
		System.out.print("# of light volunteers needed: ");
		final int light = inputReader.nextInt();
		System.out.print("# of medium volunteers needed: ");
		final int med = inputReader.nextInt();
		System.out.print("# of heavy volunteers needed: ");
		final int heavy = inputReader.nextInt();
		System.out.print("Description: ");
		final String description = inputReader.nextLine();
		try {
			manager.submit(calendar, title, parkName, location, LocalDate.parse(startDate),
					LocalDate.parse(endDate), light, med, heavy, description);
		} catch (final IOException e) {
			System.out.println("Job File not found, Find it!");
			System.exit(0);
		}
	}

	/**
	 * The administrators options
	 */
	private static void administratorInteract() {
		System.out.print("Volunteer last name to search for (q to quit): ");
		final String lastName = inputReader.next();
		if (lastName.equals("q")) {
			System.exit(0);
		}
		List<AbstractUser> volunteers = new ArrayList<AbstractUser>();
		try {
			volunteers = FileIO.queryUsers(lastName, FileIO.VOLUNTEER_CHAR);
		} catch (final FileNotFoundException e) {
			System.out.println("User File missing, find it!");
			System.exit(0);
		}
		for (final AbstractUser v : volunteers) {
			System.out.println(v);
		}
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
		final char userInput = inputReader.next().charAt(0);
		if (userInput == 's') {
			displayJobs();
		} else if (userInput == 'v') {
			displaySignedUp();
		} else if (userInput == 'q') {
			System.exit(0);
		}
		volunteerInteract();
	}

	/**
	 * Displays all jobs the volunteer has signed up for
	 */
	private static void displaySignedUp() {
		List<Job> jobs = new ArrayList<Job>();
		try {
			jobs = volunteer.getJobs();
		} catch (final FileNotFoundException e) {
			System.out.println("Job File missing, find it!");
			System.exit(0);
		}
		for (final Job j : jobs) {
			System.out.println(j);
		}
	}

	/**
	 * Returns a string of a job ready to print to console
	 * 
	 * @param j
	 * @return
	 */
	private static String displayJob(final Job j) {
		final StringBuilder sb = new StringBuilder();
		sb.append(j.getTitle() + "|" + j.getPark() + "|" + j.getLocation());
		sb.append("|" + j.getStartDate() + "|" + j.getEndDate());
		sb.append("|" + j.getMaxLight() + "|" + j.getCurLight());
		sb.append("|" + j.getMaxMed() + "|" + j.getCurMed());
		sb.append("|" + j.getMaxHeavy() + "|" + j.getCurHeavy());
		sb.append("|" + j.getDescription());
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Displays all upcoming jobs Then it prompts the user for if they want to
	 * sign up for one.
	 */
	private static void displayJobs() {
		int jobIndex = 1;
		final List<Job> jobs = new ArrayList<Job>();
		for (final List<Job> jobList : calendar.getJobs().values()) {
			for (final Job j : jobList) {
				System.out.print(jobIndex + " " + displayJob(j));
				jobs.add(j);
				jobIndex++;
			}
		}

		// Queries to sign up for a job
		System.out.println("Would you like to sign up for a job?");
		System.out.print("Job Number (0 to not sign up for a job): ");
		jobIndex = inputReader.nextInt();
		char grade = 0;
		while (jobIndex >= 1 && jobIndex < jobs.size() && grade != 'l' && grade != 'm' && grade != 'h') {
			System.out.print("Which grade (l/m/h): ");
			grade = inputReader.next().charAt(0);
		}
		if(jobIndex >=1 && jobIndex <= 0) {
			final boolean check = jobs.get(jobIndex).addVolunteer(volunteer, grade);
			if (check) {
				System.out.println("Signup Successful");
			} else {
				System.out.println("Signup Failed");
			}
		}
	}

	/**
	 * Logs in the user, giving the option to create a new profile if the login
	 * email does't exist in the system.
	 */
	private static void login() {
		System.out.print("Login Email: ");
		final String email = inputReader.next();
		try {
			user = FileIO.getUser(email);
		} catch (final FileNotFoundException e) {
			System.out.println("User File missing, find it!");
			System.exit(0);
		}

		if (user == null) {
			System.out.println("Unrecognized email\n"
					+ "Would you like to create a new account? (y/n)");
			final String newAccount = inputReader.next();
			if (newAccount.charAt(0) == 'y') {
				createUser(email);
			} else {
				login();
			}
		} else if (user instanceof Volunteer) {
			volunteer = (Volunteer) user;
			userType = FileIO.VOLUNTEER_CHAR;
		} else if (user instanceof Administrator) {
			admin = (Administrator) user;
			userType = FileIO.ADMIN_CHAR;
		} else if (user instanceof ParkManager) {
			manager = (ParkManager) user;
			userType = FileIO.PARK_MANAGER_CHAR;
		}
	}

	/**
	 * Creates a new user profile
	 * 
	 * @param email
	 */
	private static void createUser(final String email) {
		System.out.print("First Name: ");
		final String first = inputReader.next();
		System.out.print("Last Name: ");
		final String last = inputReader.next();

		// Query for userType
		while (userType != FileIO.VOLUNTEER_CHAR && userType != FileIO.PARK_MANAGER_CHAR
				&& userType != FileIO.ADMIN_CHAR) {
			System.out.print("User Type(V for volunteer, P for park manager, A for admin): ");
			userType = inputReader.next().toUpperCase().charAt(0);
		}

		// Query for managers parks
		if (userType == FileIO.PARK_MANAGER_CHAR) {
			manager = new ParkManager(last, first, email);
			String park;
			do {
				System.out.print("Tell me a park you work in (q): ");
				park = inputReader.nextLine();
				if (!park.equals("q")) {
					manager.addPark(park);
				}
			} while (!park.equals("q"));
			try {
				FileIO.addUser(manager);
			} catch (final IOException e) {
				System.out.println("User File missing, find it!");
				System.exit(0);
			}

		} else if (userType == FileIO.VOLUNTEER_CHAR) {
			volunteer = new Volunteer(last, first, email);
			try {
				FileIO.addUser(volunteer);
			} catch (final IOException e) {
				System.out.println("User File missing, find it!");
				System.exit(0);
			}
		} else if (userType == FileIO.ADMIN_CHAR) {
			admin = new Administrator(last, first, email);
			try {
				FileIO.addUser(admin);
			} catch (final IOException e) {
				System.out.println("User File missing, find it!");
				System.exit(0);
			}
		}
	}
}
