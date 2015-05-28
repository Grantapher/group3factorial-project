
package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utility.NotMyParkException;
import model.Calendar;
import model.Job;
import model.ParkManager;
import model.Volunteer;

/**
 * Park Manager User Interface.
 * 
 * @author Maurice Shaw
 */
public final class ParkManagerUI implements UserUI {

    // Constants
    /**
     * View jobs option.
     */
    private static final Integer VIEWJOBS = 1;

    /**
     * Create new job option.
     */
    private static final Integer CREATEJOB = 2;

    /**
     * Add a new park option.
     */
    private static final Integer ADDPARK = 3;

    /**
     * View parks option.
     */
    private static final Integer VIEWPARKS = 4;

    // Fields
    /**
     * A park Manger for this class.
     */
    private final ParkManager myUser;

    /**
     * If user wants to quit.
     */
    private boolean iWantToQuit;

    /**
     * Constructs a Park Manager user interface.
     * 
     * @param theManager the logged-in park manager.
     */
    public ParkManagerUI(final ParkManager theManager) {
        myUser = theManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean userMenu(final Scanner theScan) {
        iWantToQuit = false;
        MenuOption[] options =
                        {new ViewJobs(), new CreateJob(), new AddPark(), new ViewParks()};
        do {
            introduction();
            makeMenu();
            Scanner scan = new Scanner(System.in);
            String choice = theScan.nextLine();
            // ensure user enters valid choice
            if (!"q".equals(choice) && choice.equals(VIEWJOBS.toString())
                || choice.equals(CREATEJOB.toString()) || choice.equals(ADDPARK.toString())
                || choice.equals(VIEWPARKS.toString())) {
                
                int menu = Integer.parseInt(choice) - 1;
                options[menu].option(scan);
                
            } else if ("q".equals(choice)) {
                iWantToQuit = true;
            } else {
                System.out.println(choice + " is not a valid choice.");
            }
        } while (!iWantToQuit);

        return iWantToQuit;

    }

    /**
     * Displays menu options.
     */
    private void makeMenu() {
        String[] optionTitles =
                        {"View your jobs", "Post new job", "Add new Park", "View your parks",
                                        "Enter: (q)Quit"};
        int count = 1;
        for (final String title : optionTitles) {
            if (count < optionTitles.length)
                System.out.println((count++) + ". " + title);
            else
                System.out.println(title);
        }
    }

    /**
     * Prompts user for input.
     */
    private void introduction() {
        System.out.println("What would you like to do? Please enter the number of your choice. ");
    }

    /**
     * Returns string representation of a job.
     * 
     * @param theJob the job to be printed.
     * @return String representation of a job.
     */
    private String jobString(final Job theJob) {
        StringBuilder str = new StringBuilder();
        str.append("Title:       ");
        str.append(theJob.getTitle() + "\n");
        str.append("   Park:        ");
        str.append(theJob.getPark() + "\n");
        str.append("   Location:    ");
        str.append(theJob.getLocation() + "\n");
        str.append("   Start Date:  ");
        str.append(theJob.getStartDate() + "\n");
        str.append("   End Date:    ");
        str.append(theJob.getEndDate() + "\n");
        str.append("   Labor:       Light  ");
        str.append(theJob.getCurLight() + "\n");
        str.append("                Medium ");
        str.append(theJob.getCurMed() + "\n");
        str.append("                Heavy  ");
        str.append(theJob.getCurHeavy() + "\n");
        str.append("   Description: ");
        str.append(theJob.getDescription() + "\n");

        return str.toString();
    }

    /**
     * Returns a string representation of a Volunteer.
     * 
     * @param theVol the Volunteer to be printed.
     * @return String representation of a Volunteer.
     */
    private String volString(final Volunteer theVol) {
        StringBuilder str = new StringBuilder();
        str.append(theVol.getFirstName() + " ");
        str.append(theVol.getLastName() + " ");
        str.append(theVol.getEmail() + "\n");

        return str.toString();
    }

    /**
     * Create a menu option.
     * 
     * @author Maurice Shaw
     */
    interface MenuOption {

        void option(final Scanner scan);
    }

    /**
     * Creates A display of jobs and their volunteers
     * 
     * @author Maurice Shaw
     */
    private final class ViewJobs implements MenuOption {

        /**
         * Displays job and volunteers for a job.
         * 
         * @param theScan For user input.
         */
        public void option(final Scanner theScan) {
            final List<Job> jobs = new ArrayList<Job>();
            int jobIndex = 1;
            try {
                for (final Job j : Calendar.getInstance().getJobs(myUser.getParks())) {
                    System.out.print(jobIndex + ". " + jobString(j) + "\n");
                    jobs.add(j);
                    jobIndex++;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("The jobs file is missing!");
                return;
            }
            if (jobs.size() > 0) { // park has job
                System.out.println("\nWould you like to view the volunteers for a job? Y/N ");
                String choice = theScan.nextLine().toLowerCase();
                if ("y".equals(choice)) {
                    System.out.print("Please enter the number of the job: ");
                    jobIndex = theScan.nextInt();
                    // job has volunteers
                    if (!jobs.get(jobIndex - 1).getVolunteers().isEmpty()
                        && jobIndex < jobs.size() && jobIndex > 0) {

                        List<Volunteer> volList = myUser.getVolunteers(jobs.get(jobIndex - 1));
                        System.out.println("Volunteers to "
                                           + jobs.get(jobIndex - 1).getDescription() + ":\n");

                        int count = 1;
                        for (final Volunteer vol : volList) {
                            System.out.println((count++) + ". " + volString(vol));
                        }
                        // user enters invalid number of job
                    } else if (jobIndex > jobs.size() || jobIndex < 1) { 
                        System.out.println(jobIndex + " is not a valid choice");
                    } else
                        System.out.println("No one has volunteered for this job."); 
                }
            } else
                System.out.println("You do not have any parks.");

        }

    }

    /**
     * Creates the menu option that creates a job.
     * 
     * @author Maurice Shaw
     */
    private final class CreateJob implements MenuOption {

        /**
         * Creates a new job and attempts to add a job. User will be informed if
         * attempt fails.
         * 
         * @param theScan For user input.
         */
        public void option(final Scanner theScan) {
            boolean goodInput;
            LocalDate startDate = null;
            LocalDate endDate = null;

            System.out.println("Please enter the following information: ");
            System.out.print("\nTitle: ");
            final String title = theScan.nextLine();
            System.out.print("Park Name: ");
            final String parkName = theScan.nextLine();
            System.out.print("Location: ");
            final String location = theScan.nextLine();
            do {
                System.out.print("Start Date (yyyy-mm-dd): ");
                final String startDateString = theScan.next();

                try { // check date formatting
                    goodInput = true;
                    startDate = LocalDate.parse(startDateString);
                } catch (DateTimeParseException dt) {
                    System.out.println("You must enter the date using this format (yyyy-mm-dd). Try Again");
                    goodInput = false;
                }
            } while (!goodInput);

            do {
                System.out.print("End Date (yyyy-mm-dd): ");
                final String endDateString = theScan.next();

                try { // check date formatting
                    goodInput = true;
                    endDate = LocalDate.parse(endDateString);
                } catch (DateTimeParseException dt) {
                    System.out.println("You must enter the date using this format (yyyy-mm-dd). Try Again");
                    goodInput = false;
                }

            } while (!goodInput);
            System.out.print("Number of light volunteers needed: ");
            final int light = theScan.nextInt();
            System.out.print("Number of medium volunteers needed: ");
            final int medium = theScan.nextInt();
            System.out.print("Number of heavy volunteers needed: ");
            final int heavy = theScan.nextInt();
            System.out.print("Description: ");
            final String description = theScan.nextLine();
            try {// try to add job
                myUser.submit(Calendar.getInstance(), title, parkName, location, startDate,
                              endDate, light, medium, heavy, description);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Job file not found!");

            } catch (NotMyParkException e) {
                System.out.println(e.getPark() + " is not one of the parks you manage");
            }
        }
    }

    /**
     * Creates the menu option that adds a Park to this Park Manager's list of
     * parks.
     * 
     * @author Maurice Shaw
     */
    private final class AddPark implements MenuOption {

        /**
         * Adds a park to this park manager's parks managed.
         * 
         * @param theScan For user input.
         */
        public void option(final Scanner theScan) {
            String park = "";
            do {
                System.out.println("Please enter the park name: ");
                park = theScan.nextLine();
                if (park.length() > 0) {
                    myUser.addPark(park);
                    System.out.println("Your park " + park + " was successfully added");
                    System.out.println("Do you want to view your parks? Y/N");
                    String choice = theScan.nextLine();
                    if ("y".equals(choice.toLowerCase())) {
                        new ViewParks().option(theScan);
                    }

                } else {
                    System.out.println("You must enter a park name, try again");
                }
            } while (park.length() == 0);
        }
    }

    /**
     * Creates the menu option to display the parks managed by this Park
     * Manager.
     * 
     * @author Maurice Shaw
     */
    private final class ViewParks implements MenuOption {

        /**
         * Displays list of volunteers.
         * 
         * @param theScan Scanner for user input.
         */
        public void option(final Scanner theScan) {
            int count = 1;
            final List<String> parks = myUser.getParks();
            for (String park1 : parks)
                System.out.println((count++) + ". " + park1);
        }
    }
}
