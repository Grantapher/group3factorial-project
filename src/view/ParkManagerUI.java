
package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Park Manager User Interface.
 * 
 * @author Maurice Shaw
 */
public class ParkManagerUI implements UserUI {

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

    // Fields
    /**
     * A park Manger for this class.
     */
    private ParkManager myUser;

    private boolean iWantToQuit = false;

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
        Scanner scan = theScan;
        do {
            System.out.println("What would you like to do? Please enter the number of your choice. ");
            System.out.println("1. View your jobs.");
            System.out.println("2. Post a new job.");
            System.out.println("3. Add a new Park.");
            System.out.println("Enter: (q)Quit");
            scan = new Scanner(System.in);
            String choice = theScan.nextLine();
            if (!"q".equals(choice) && choice.equals(VIEWJOBS.toString())
                || choice.equals(CREATEJOB.toString()) || choice.equals(ADDPARK.toString())) { // ensure
                                                                                               // user
                                                                                               // enters
                                                                                               // 1
                                                                                               // or
                                                                                               // 2
                if (Integer.parseInt(choice) == VIEWJOBS)
                    option1(scan);
                if (Integer.parseInt(choice) == CREATEJOB)
                    option2(scan);
                if (Integer.parseInt(choice) == ADDPARK)
                    option3(scan);

            } else if ("q".equals(choice)) {
                iWantToQuit = true;
            } else {
                System.out.println(choice + " is not a valid choice.");

            }
        } while (!iWantToQuit);

        return iWantToQuit;

    }

    /**
     * Displays job and volunteers for a job.
     */
    private void option1(final Scanner theScan) {
        final List<Job> jobs = new ArrayList<Job>();
        int jobIndex = 1;
        try {
            for (final Job j : Calendar.getInstance().getJobs(myUser.getParks())) {
                System.out.print(jobIndex + ". " + jobString(j) + "\n");
                jobs.add(j);
                jobIndex++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("The jobs file is missing!");
            return;
        }
        if (jobs.size() > 0) {
            System.out.println("\nWould you like to view the volunteers for a job? Y/N ");
            String choice = theScan.nextLine().toLowerCase();
            System.out.print(choice);
            if ("y".equals(choice)) {
                System.out.print("Please enter the number of the job: ");
                jobIndex = theScan.nextInt();

                if (!jobs.get(jobIndex - 1).getVolunteers().isEmpty()) { // job
                                                                         // has
                                                                         // volunteers
                    List<Volunteer> volList = myUser.getVolunteers(jobs.get(jobIndex - 1));
                    System.out.println("Volunteers to "
                                       + jobs.get(jobIndex - 1).getDescription() + ":\n");

                    int count = 1;
                    for (final Volunteer vol : volList) {
                        System.out.println((count++) + ". " + volString(vol));
                    }
                }
            }
        } else
            System.out.println("You do not have any parks.");

    }

    /**
     * Creates a new job and attempts to add a job. User will be informed if
     * attempt fails.
     */
    private void option2(final Scanner theScan) {
        System.out.println("Please enter the following information: ");
        System.out.print("\nTitle: ");
        final String title = theScan.nextLine();
        System.out.print("Park Name: ");
        final String parkName = theScan.nextLine();
        System.out.print("Location: ");
        final String location = theScan.nextLine();
        System.out.print("Start Date (yyyy-mm-dd): ");
        final String startDate = theScan.next(); // job fields
        System.out.print("End Date (yyyy-mm-dd): ");
        final String endDate = theScan.next();
        System.out.print("# of light volunteers needed: ");
        final int light = theScan.nextInt();
        System.out.print("# of medium volunteers needed: ");
        final int medium = theScan.nextInt();
        System.out.print("# of heavy volunteers needed: ");
        final int heavy = theScan.nextInt();
        System.out.print("Description: ");
        final String description = theScan.nextLine();
        try {// try to add job
            final boolean submitCheck =
                            myUser.submit(Calendar.getInstance(), title, parkName, location,
                                          LocalDate.parse(startDate),
                                          LocalDate.parse(endDate), light, medium, heavy,
                                          description);

            if (!submitCheck) {
                System.out.println("Job not added");
            }
        } catch (final IOException e) {
            System.out.println("Job file not found!");

        }
    }

    /**
     * Adds a park to this park manager's parks managed.
     */
    private void option3(final Scanner theScan) {
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
                    int count = 1;
                    final List<String> parks = myUser.getParks();
                    for (String park1 : parks)
                        System.out.println((count++) + ". " + park1);
                }
            } else {
                System.out.println("You must enter a park name, try again");
            }
        } while (park.length() == 0);

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
}
