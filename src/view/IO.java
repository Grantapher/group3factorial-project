package view;

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
        calendar = new Calendar();
        Calendar.addJobs(fileReader.readJobs());
        inputReader = new Scanner(System.in);
        login();
        interact();
    }

    /**
     * Called repeatedly once a user is logged in to display to the user their
     * optios
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
        int jobIndex = 0;
        final List<Job> jobs = new ArrayList<Job>();
        for (final Job j : calendar.getJobs(manager)) {
            System.out.print(jobIndex + " " + j);
            jobs.add(j);
            jobIndex++;
        }
        System.out.println("Would you like to view the volunteers for a job?");
        System.out.print("Job Number (-1 to not view volunteers): ");
        jobIndex = inputReader.nextInt();
        if (jobIndex >= 0) {
            for (final Volunteer v : manager.getVolunteers(j)) {
                System.out.println(v);
            }
        }
    }

    /**
     * Creating a new job
     */
    private static void createJob() {
        System.out.print("Title: ");
        final String title = inputReader.next();
        System.out.print("Park Name: ");
        final String parkName = inputReader.next();
        System.out.print("Location: ");
        final String location = inputReader.next();
        System.out.print("Start Date (yyyy-mm-dd): ");
        final String startDate = inputReader.next();
        System.out.print("End Date (yyyy-mm-dd): ");
        final String endDate = inputReader.next();
        System.out.print("# of light volunteers needed: ");
        final int light = inputReader.nextInt();
        System.out.print("# of mediuim volunteers needed: ");
        final int med = inputReader.nextInt();
        System.out.print("# of heavy volunteers needed: ");
        final int heavy = inputReader.nextInt();
        System.out.print("Description: ");
        final String description = inputReader.next();
        manager.submit(calendar, title, parkName, location, LocalDate.parse(startDate),
                LocalDate.parse(endDate), light, med, heavy, description);
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
        List<AbstractUser> volunteers;
        volunteers = FileIO.queryUsers(lastName, FileIO.VOLUNTEER_CHAR);
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
        final List<Job> jobs = volunteer.getJobs();
        for (final Job j : jobs) {
            System.out.println(j);
        }
    }

    /**
     * Displays all upcoming jobs Then it prompts the user for if they want to
     * sign up for one.
     */
    private static void displayJobs() {
        int jobIndex = 0;
        final List<Job> jobs = new ArrayList<Job>();
        for (final Job j : FileIO.readJobs().keySet()) {
            System.out.print(jobIndex + " " + j.display());
            jobs.add(j);
            jobIndex++;
        }
        System.out.println("Would you like to sign up for a job?");
        System.out.print("Job Number (-1 to not sign up for a job): ");
        jobIndex = inputReader.nextInt();
        char grade = 0;
        while (jobIndex >= 0 && grade != 'l' && grade != 'm' && grade != 'h') {
            System.out.print("Which grade (l/m/h): ");
            grade = inputReader.next().charAt(0);
        }
        final boolean check = jobs.get(jobIndex).addVolunteer(volunteer, grade);
        if (check) {
            System.out.println("Signup Successful");
        } else {
            System.out.println("Signup Failed");
        }
    }

    /**
     * Logs in the user, giving the option to create a new profile if the login
     * email does't exist in the system.
     */
    private static void login() {
        System.out.print("Login Email: ");
        final String email = inputReader.next();
        userType = FileIO.getUserType(email);
        if (userType == 0) {
            System.out.println("Unrecognized email\n"
                    + "Would you like to create a new account? (y/n)");
            final String newAccount = inputReader.next();
            if (newAccount.charAt(0) == 'y') {
                createUser(email);
            } else {
                login();
            }
        } else if (userType == FileIO.VOLUNTEER_CHAR) {
            volunteer = FileIO.getUser(email);
        } else if (userType == FileIO.ADMIN_CHAR) {
            admin = FileIO.getUser(email);
        } else if (userType == FileIO.PARK_MANAGER_CHAR) {
            manager = FileIO.getUser(email);
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
        while (userType != FileIO.VOLUNTEER_CHAR || userType != FileIO.PARK_MANAGER_CHAR
                || userType != FileIO.ADMIN_CHAR) {
            System.out.print("User Type(V for volunteer, P for park manager, A for admin): ");
            userType = inputReader.next().charAt(0);
        }
        if (userType == FileIO.PARK_MANAGER_CHAR) {
            manager = new ParkManager(last, first, email);
            String park;
            do {
                System.out.print("Tell me a park you work in (leave empty when done): ");
                park = inputReader.next();
                if (!park.equals("")) {
                    manager.addPark(park);
                }
            } while (!park.equals(""));
        } else if (userType == FileIO.VOLUNTEER_CHAR) {
            volunteer = new Volunteer(last, first, email);
        } else if (userType == FileIO.ADMIN_CHAR) {
            admin = new Administrator(last, first, email);
        }
    }
}
