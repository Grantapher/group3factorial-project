package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Calendar;
import model.Job;
import model.ParkManager;
import model.Volunteer;
import exception.BusinessRuleException;

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
        final MenuOption[] options = { new ViewJobs(), new CreateJob(), new AddPark(),
                new ViewParks() };
        do {
            introduction();
            makeMenu();
            final Scanner scan = new Scanner(System.in);
            final String choice = theScan.nextLine();
            // ensure user enters valid choice
            if (validChoice(choice)) {
                System.out.println();
                final int menu = Integer.parseInt(choice) - 1;
                options[menu].option(scan);

            } else if ("q".equals(choice)) {
                iWantToQuit = true;
            } else {
                System.out.println(choice + " is not a valid choice.");
                System.out.println();
            }
        } while (!iWantToQuit);

        return iWantToQuit;

    }

    /**
     * Determines whether not not the users choice is a valid one.
     *
     * @param theChoice the users choice.
     * @return true if the users choice is the valid, false otherwise.
     */
    private boolean validChoice(final String theChoice) {
        return !"q".equals(theChoice) && theChoice.equals(VIEWJOBS.toString())
                || theChoice.equals(CREATEJOB.toString())
                || theChoice.equals(ADDPARK.toString())
                || theChoice.equals(VIEWPARKS.toString());
    }

    /**
     * Displays menu options.
     */
    private void makeMenu() {
        final String[] optionTitles = { "View your jobs", "Post new job", "Add new Park",
                "View your parks", "Your choice (q to Quit):" };
        int count = 1;
        for (final String title : optionTitles) {
            if (count < optionTitles.length) {
                System.out.println(count++ + ". " + title);
            } else {
                System.out.println(title);
            }
        }
    }

    /**
     * Prompts user for input.
     */
    private void introduction() {
        System.out
                .println("What would you like to do? Please enter the number of your choice. ");
    }

    /**
     * Returns string representation of a job.
     *
     * @param theJob the job to be printed.
     * @return String representation of a job.
     */
    private String jobString(final Job theJob) {
        final StringBuilder str = new StringBuilder();
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
        final StringBuilder str = new StringBuilder();
        str.append(theVol.getFirstName()).append(" ");
        str.append(theVol.getLastName()).append(" ");
        str.append(theVol.getEmail());

        return str.toString();
    }

    /**
     * Create a menu option.
     *
     * @author Maurice Shaw
     * @version 23May15
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
        @Override
        public void option(final Scanner theScan) {
            final List<Job> jobs = new ArrayList<Job>();
            int jobIndex = 1;
            try {
                for (final Job j : Calendar.getInstance().getJobs(myUser.getParks())) {
                    System.out.println(jobIndex + ". " + jobString(j));
                    jobs.add(j);
                    jobIndex++;
                }
            } catch (final IOException theE) {
                System.err.println("Job File is missing, can't add volunteer to job.");
                return;
            } catch (final ClassNotFoundException theE) {
                System.err.println("Job File is corrupted, can't add volunteer to job.");
                return;
            }
            if (jobs.size() > 0) { // park has job
                System.out.println("Would you like to view the volunteers for "
                        + (jobs.size() > 1 ? "a" : "this") + " job? Y/N ");
                final String choice = theScan.nextLine().toLowerCase();
                System.out.println();
                if ("y".equals(choice)) {
                    jobIndex = 1;
                    if (jobs.size() != 1) {
                        boolean goodInput = false;
                        jobIndex = -1;
                        do {
                            System.out.print("Please enter the number of the job: ");

                            try { // check numbers
                                goodInput = true;
                                jobIndex = theScan.nextInt();
                                if (jobIndex < 1 || jobs.size() < jobIndex) {
                                    throw new InputMismatchException();
                                }
                            } catch (final InputMismatchException e) {
                                System.out.println("You must enter a number between 1 and "
                                        + jobs.size() + ". Try Again.");
                                goodInput = false;
                                theScan.nextLine();
                            }
                        } while (!goodInput);
                        System.out.println();
                    }

                    // job has volunteers
                    if (!jobs.get(jobIndex - 1).getVolunteers().isEmpty()) {

                        final List<Volunteer> volList = myUser.getVolunteers(jobs
                                .get(jobIndex - 1));
                        System.out.println("Volunteers for \""
                                + jobs.get(jobIndex - 1).getTitle() + "\":");

                        int count = 1;
                        for (final Volunteer vol : volList) {
                            System.out.println(count++ + ". " + volString(vol));
                        }
                    } else {
                        System.out.println("No one has volunteered for this job.");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("You do not have any upcoming jobs.");
            }

        }
    }

    /**
     * Creates the menu option that creates a job.
     *
     * @author Maurice Shaw
     * @version 23May15
     */
    private final class CreateJob implements MenuOption {

        /**
         * Creates a new job and attempts to add a job. User will be informed if
         * attempt fails.
         *
         * @param theScan For user input.
         */
        @Override
        public void option(final Scanner theScan) {
            boolean goodInput;

            System.out.println("Please enter the following information: ");
            System.out.print("\nTitle: ");
            final String title = theScan.nextLine();
            System.out.print("Park Name: ");
            final String parkName = theScan.nextLine();
            System.out.print("Location: ");
            final String location = theScan.nextLine();

            LocalDate startDate = null;
            do {
                System.out.print("Start Date (yyyy-mm-dd): ");
                final String startDateString = theScan.next();

                try { // check date formatting
                    goodInput = true;
                    startDate = LocalDate.parse(startDateString);
                } catch (final DateTimeParseException dt) {
                    System.out
                    .println("You must enter the date using this format (yyyy-mm-dd). Try Again");
                    goodInput = false;
                }
            } while (!goodInput);

            LocalDate endDate = null;
            do {
                System.out.print("End Date (yyyy-mm-dd): ");
                final String endDateString = theScan.next();

                try { // check date formatting
                    goodInput = true;
                    endDate = LocalDate.parse(endDateString);
                } catch (final DateTimeParseException dt) {
                    System.out
                    .println("You must enter the date using this format (yyyy-mm-dd). Try Again");
                    goodInput = false;
                }

            } while (!goodInput);

            int light = -1;
            do {
                System.out.print("Number of light volunteers needed: ");

                try { // check numbers
                    goodInput = true;
                    light = theScan.nextInt();
                    if (light < 0) {
                        throw new InputMismatchException();
                    }
                } catch (final InputMismatchException e) {
                    System.out.println("You must enter an integer > 0. Try Again");
                    goodInput = false;
                    theScan.nextLine();
                }

            } while (!goodInput);

            int medium = -1;
            do {
                System.out.print("Number of medium volunteers needed: ");

                try { // check numbers
                    goodInput = true;
                    medium = theScan.nextInt();
                    if (medium < 0) {
                        throw new InputMismatchException();
                    }
                } catch (final InputMismatchException e) {
                    System.out.println("You must enter an integer > 0. Try Again");
                    goodInput = false;
                    theScan.nextLine();
                }

            } while (!goodInput);

            int heavy = -1;
            do {
                System.out.print("Number of heavy volunteers needed: ");

                try { // check numbers
                    goodInput = true;
                    heavy = theScan.nextInt();
                    if (heavy < 0) {
                        throw new InputMismatchException();
                    }
                } catch (final InputMismatchException e) {
                    System.out.println("You must enter an integer > 0. Try Again");
                    goodInput = false;
                    theScan.nextLine();
                }

            } while (!goodInput);

            theScan.nextLine();
            System.out.print("Description: ");
            final String description = theScan.nextLine();
            System.out.println();
            try {// try to add job
                myUser.submit(Calendar.getInstance(), title, parkName, location, startDate,
                        endDate, light, medium, heavy, description);
                System.out.println("Job successfully created!");
                System.out.println();
            } catch (final IOException theE) {
                System.err.println("Job File is missing, can't add volunteer to job.");
            } catch (final ClassNotFoundException theE) {
                System.err.println("Job File is corrupted, can't add volunteer to job.");
            } catch (final BusinessRuleException e) {
                System.out.println(e.getMessage());
                System.out.println();
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
        @Override
        public void option(final Scanner theScan) {
            String park = "";
            while (park.length() == 0) {
                System.out.println("Please enter the park name: ");
                park = theScan.nextLine();
                if (park.length() == 0) {
                    System.out.println("You must enter a park name, try again.");
                    System.out.println();
                } else if (myUser.isMyPark(park)) {
                    System.out.println("You can't have multiple parks under the same name.");
                    System.out.println();
                } else {
                    myUser.addPark(park);
                    System.out.println("Your park " + park + " was successfully added!");
                    System.out.println();
                    System.out.println("Do you want to view your parks? Y/N");
                    final String choice = theScan.nextLine();
                    if ("y".equals(choice.toLowerCase())) {
                        System.out.println();
                        new ViewParks().option(theScan);
                    } else {
                        System.out.println();
                    }
                }
            }
        }
    }

    /**
     * Creates the menu option to display the parks managed by this Park
     * Manager.
     *
     * @author Maurice Shaw
     * @version 23May15
     */
    private final class ViewParks implements MenuOption {

        /**
         * Displays list of volunteers.
         *
         * @param theScan Scanner for user input.
         */
        @Override
        public void option(final Scanner theScan) {
            int count = 1;
            final List<String> parks = myUser.getParks();
            for (final String park : parks) {
                System.out.println(count++ + ". " + park);
            }
            System.out.println();
        }
    }
}
