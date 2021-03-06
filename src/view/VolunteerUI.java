/*
 * TCSS 360 Project - Group 3!
 */
package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Calendar;
import model.Job;
import model.Volunteer;
import exception.BusinessRuleException;

/**
 * This class contains the UI for a Volunteer using the system.
 *
 * @author Grant Toepfer
 * @version May 18, 2015
 */
public class VolunteerUI implements UserUI {
    private final Volunteer myVolunteer;

    /**
     * Initializes the fields of VolunteerUI.
     *
     * @param volunteer the volunteer that logged in
     */
    public VolunteerUI(final Volunteer volunteer) {
        myVolunteer = volunteer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean userMenu(final Scanner sc) {
        System.out.println("What would you like to do?");
        System.out.println("1) Search for a job");
        System.out.println("2) View your jobs");
        System.out.println("3) Quit");
        System.out.print("Your choice: ");
        final String line = sc.nextLine();
        System.out.println();
        try {
            switch (parseInt(line, 1, 4)) {
                case 1:
                    displayJobs(sc);
                    return false;
                case 2:
                    displaySignedUp();
                    return false;
                case 3:
                    return true;
                default:
                    throw new AssertionError();
            }
        } catch (final NumberFormatException e) {
            System.err.println("Invalid Choice");
            return false;
        }
    }

    /**
     * Creates an easy to read String containing the information a volunteer
     * needs to know to sign up for this job.
     *
     * @see VolunteerUI#displayVolunteersJob(Job)
     * @param job the job to print the details of
     * @return a neatly formatted string for the user to read
     */
    private String displayJobForSignUp(final Job job) {
        final StringBuilder sb = new StringBuilder(displayVolunteersJob(job));
        sb.append("Volunteer Capacity:\n\tLight: ");
        sb.append(job.getCurLight());
        sb.append('/');
        sb.append(job.getMaxLight());
        sb.append("\n\tMedium: ");
        sb.append(job.getCurMed());
        sb.append('/');
        sb.append(job.getMaxMed());
        sb.append("\n\tHeavy: ");
        sb.append(job.getCurHeavy());
        sb.append('/');
        sb.append(job.getMaxHeavy());
        sb.append('\n');
        return sb.toString();
    }

    private void displayJobs(final Scanner sc) {
        // Get the calendar
        Calendar cal;
        try {
            cal = Calendar.getInstance();
        } catch (final ClassNotFoundException theE) {
            System.err.println("Job File is corrupted.");
            return;
        } catch (final IOException theE) {
            System.err.println("Job File is missing.");
            return;
        }

        // Display the jobs
        int jobIndex = 1;
        final List<Job> jobs = new ArrayList<Job>();
        for (final List<Job> jobList : cal.getJobs().values()) {
            for (final Job j : jobList) {
                System.out.print(jobIndex);
                System.out.print(' ');
                System.out.println(displayJobForSignUp(j));
                jobs.add(j);
                jobIndex++;
            }
        }

        // Queries to sign up for a job
        int choiceIndex = -1;
        do {
            System.out.println("Would you like to sign up for a job?");
            System.out.print("Job Number (0 to not sign up for a job): ");
            try {
                choiceIndex = parseInt(sc.nextLine(), 0, jobIndex);
            } catch (final NumberFormatException e) {
                System.err.println("Invalid Choice");
            }
        } while (choiceIndex < 0 || jobs.size() < choiceIndex);
        if (choiceIndex == 0) {
            System.out.println();
            return;
        }
        choiceIndex--;

        // get the chosen job
        final Job job = jobs.get(choiceIndex);
        System.out.println();
        System.out.println(displayJobForSignUp(job));

        // Pick a grade
        int gradeInt = -1;
        System.out.println("Which grade?");
        System.out.println("1) Light");
        System.out.println("2) Medium");
        System.out.println("3) Heavy");
        do {
            System.out.print("Grade Number (0 to not sign up for the job): ");
            try {
                gradeInt = parseInt(sc.nextLine(), 0, 4);
            } catch (final NumberFormatException e) {
                System.err.println("Invalid Choice");
            }
        } while (gradeInt < 0 || 4 <= gradeInt);
        System.out.println();
        if (gradeInt == 0) {
            return;
        }

        // turn gradeInt into it's corresponding character
        char grade;
        switch (gradeInt) {
            case 1:
                grade = 'l';
                break;
            case 2:
                grade = 'm';
                break;
            case 3:
                grade = 'h';
                break;
            default:
                throw new AssertionError();
        }

        // Sign up for job
        try {
            job.addVolunteer(myVolunteer, grade);
            System.out.println("Signup Success!");
        } catch (final IOException theE) {
            System.err.println("Job File is missing, can't add volunteer to job.");
            return;
        } catch (final ClassNotFoundException theE) {
            System.err.println("Job File is corrupted, can't add volunteer to job.");
            return;
        } catch (final BusinessRuleException theE) {
            System.out.println(theE.getMessage());
        }
        System.out.println();
    }

    /**
     * Displays the jobs that the user is currently signed up for.
     */
    private void displaySignedUp() {
        List<Job> jobs = new ArrayList<Job>();
        try {
            jobs = myVolunteer.getJobs();
        } catch (final IOException theE) {
            System.err.println("Job File is missing, can't view " + myVolunteer.getFirstName()
                    + "'s jobs.");
            return;
        } catch (final ClassNotFoundException theE) {
            System.err.println("Job File is corrupted, can't view "
                    + myVolunteer.getFirstName() + "'s jobs.");
            return;
        }
        if (jobs.size() == 0) {
            System.out.println("You are not signed up for any jobs at this moment.");
        } else {
            for (final Job job : jobs) {
                System.out.println(displayVolunteersJob(job));
            }
        }
    }

    /**
     * Creates an easy to read String containing the information a volunteer
     * needs to know about this job that they're signed up for.
     *
     * @param job the job to print the details of
     * @return a neatly formatted string for the user to read
     */
    private String displayVolunteersJob(final Job job) {
        final StringBuilder sb = new StringBuilder();
        sb.append(job.getTitle());
        sb.append("\nPark: ");
        sb.append(job.getPark());
        sb.append("\nLocation: ");
        sb.append(job.getLocation());
        sb.append("\nDate: ");
        sb.append(job.getStartDate());
        if (!job.getStartDate().equals(job.getEndDate())) {
            sb.append(" to ");
            sb.append(job.getEndDate());
        }
        sb.append("\nDescription: ");
        sb.append(job.getDescription());
        sb.append('\n');
        return sb.toString();
    }

    /**
     * Parses the int from the given string and ensures it is within the given
     * bounds.
     *
     * @param str the string that contains an integer
     * @param lowerBound the inclusive lower bound
     * @param upperBound the exclusive upper bound
     * @return the integer in the given string within the bounds
     * @throws NumberFormatException if the string does not contain an integer
     *             or the integer is not within the given bounds [lowerBound,
     *             upperBound)
     */
    private int parseInt(final String str, final int lowerBound, final int upperBound)
            throws NumberFormatException {
        final int number = Integer.parseInt(str);
        if (number < lowerBound || upperBound <= number) {
            throw new NumberFormatException();
        }
        return number;
    }
}
