/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the input and output from the files.
 *
 * @author Grant Toepfer
 * @version May 4, 2015
 */
public final class FileIO {
    /** The constant signifying an Administrator. */
    public static final char ADMIN_CHAR = 'A';
    /** The constant signifying a Park Manager. */
    public static final char PARK_MANAGER_CHAR = 'P';
    /** The constant signifying an Volunteer. */
    public static final char VOLUNTEER_CHAR = 'V';
    /** The constant signifying a user that does not exist. */
    public static final char USER_NOT_FOUND_CHAR = 'X';
    private static final File USER_FILE = new File(System.getProperty("user.dir")
            + "/files/users.info");
    private static final File JOB_FILE = new File(System.getProperty("user.dir")
            + "/files/jobs.info");

    private FileIO() {
        // empty constructor
    }

    /**
     * Gets a Map of all jobs.
     *
     * @return a map of all the jobs
     * @throws FileNotFoundException if the Job file doesn't exist
     */
    public static Map<LocalDate, List<Job>> readJobs() throws FileNotFoundException {
        final Map<LocalDate, List<Job>> map = new HashMap<>();
        final Scanner scan = new Scanner(JOB_FILE);
        while (scan.hasNextLine()) {
            final StringBuilder sb = new StringBuilder();
            String line;
            do {
                line = scan.nextLine();
                sb.append(line);
                sb.append('\n');
            } while (!"".equals(line));
            final Job job = new Job(sb.toString());
            final LocalDate date = job.getStartDate();
            if (!map.containsKey(date)) {
                map.put(date, new ArrayList<>());
            }
            map.get(date).add(job);
        } // end while
        scan.close();
        return map;
    }

    /**
     * Appends the Job file with the given job.
     *
     * @param job the job to add to the file
     * @throws IOException if the Job file doesn't exist
     */
    public static void addJob(final Job job) throws IOException {
        if (!JOB_FILE.exists()) {
            JOB_FILE.createNewFile();
        }
        if (jobExists(job)) {
            return;
        }
        final FileWriter fw = new FileWriter(JOB_FILE, true);
        fw.write(job.toString());
        fw.close();
    }

    /**
     * @param job the job to look for duplicates of
     * @return whether or not the job exists already
     * @throws FileNotFoundException if the file doesn't exist
     */
    private static boolean jobExists(final Job job) throws FileNotFoundException {
        final ArrayList<Job> list = new ArrayList<>();
        final Map<LocalDate, List<Job>> map = FileIO.readJobs();
        for (final LocalDate date : map.keySet()) {
            list.addAll(map.get(date));
        }
        return list.contains(job);
    }

    /**
     * Gets a list of users associated with the given lastName and/or userType.
     * <p>
     * If the last name, and/or user type parameters are left null then the
     * query will not factor that parameter into it's query.
     * <p>
     * Via the above, leaving all parameters null will return a list of all
     * users.
     *
     * @param lastName the last name query
     * @param userType the user type query
     * @return a list of Users matching the given query
     * @throws FileNotFoundException if the User file doesn't exist
     */
    public static List<AbstractUser> queryUsers(final String lastName, final Character userType)
            throws FileNotFoundException {
        final Scanner scan = new Scanner(USER_FILE);
        final List<AbstractUser> list = queryUsers(scan, lastName, userType, null);
        scan.close();
        return list;
    }

    /**
     * Gets the user associated with the given email.
     *
     * @param email the email to query for
     * @return the user associated with the given email or null if the email
     *         doesn't exist
     * @throws FileNotFoundException if the User file doesn't exist
     */
    public static AbstractUser getUser(final String email) throws FileNotFoundException {
        final Scanner scan = new Scanner(USER_FILE);
        final List<AbstractUser> list = queryUsers(scan, null, null, email);
        scan.close();
        final int size = list.size();
        if (size == 0) {
            return null;
        } else if (size > 1) {
            throw new AssertionError("Multiple users under same email.");
        } else {
            return list.get(0);
        }
    }

    /**
     * Creates a list of volunteers from a string of volunteers in Volunteer's
     * toString() method format.
     *
     * @param volunteers A string containing volunteers
     * @return A list of volunteers
     */
    public static List<Volunteer> getVolunteers(final String volunteers) {
        final Scanner scan = new Scanner(volunteers.substring(0, volunteers.length() - 1));
        final List<AbstractUser> userList = queryUsers(scan, null, VOLUNTEER_CHAR, null);
        final List<Volunteer> volunteerList = new ArrayList<>();
        for (final AbstractUser user : userList) {
            volunteerList.add((Volunteer) user);
        }
        return volunteerList;
    }

    /**
     * Appends the User file with the given user.
     *
     * @param user the user to add to the file
     * @throws IOException if the User file doesn't exist
     */
    public static void addUser(final AbstractUser user) throws IOException {
        if (!USER_FILE.exists()) {
            USER_FILE.createNewFile();
        }
        if (emailExists(user.getEmail())) {
            return;
        }
        final FileWriter fw = new FileWriter(USER_FILE, true);
        fw.write(user.toString());
        fw.close();
    }

    /**
     * @param email the email to look for a duplicate of
     * @return if the email exists in the system already
     * @throws FileNotFoundException if the file doesn't exist
     */
    private static boolean emailExists(final String email) throws FileNotFoundException {
        return null != getUser(email);
    }

    /**
     * Gathers a list of users that matches the query.
     * <p>
     * If the last name, user type, and/or email parameters are left null then
     * the query will not factor that parameter into it's query.
     * <p>
     * Via the above, leaving all but the first parameter null will return a
     * list of all users.
     *
     * @param scan the reader attached to the User file
     * @param lastName the last name query
     * @param type the User type query
     * @param email the email query
     * @return a list of Users who match the query
     */
    private static List<AbstractUser> queryUsers(final Scanner scan, final String lastName,
            final Character type, final String email) {
        final List<AbstractUser> list = new ArrayList<>();
        // gather User info
        while (scan.hasNextLine()) {
            final AbstractUser user = parseUser(scan, lastName, type, email);
            if (user != null) {
                list.add(user);
            }
        } // end while
        return list;
    }

    /**
     * Gets a single user.
     * <p>
     * If the last name, user type, and/or email parameters are left null then
     * the query will not factor that parameter into it's query.
     * <p>
     * Via the above, leaving all but the first parameter null will return a
     * list of all users.
     *
     * @param scan the Scanner attached to the users
     * @param lastName the last name query
     * @param type the User type query
     * @param email the email query
     * @return the user if it matches the query, otherwise null
     * @throws IOException if an I/O error occurs
     */
    private static AbstractUser parseUser(final Scanner scan, final String lastName,
            final Character type, final String email) {
        final char queryChar = scan.nextLine().charAt(0);
        final Scanner sc = new Scanner(scan.nextLine());
        final String queryFirstName = sc.next();
        final String queryLastName = sc.next();
        final String queryEmail = sc.next();
        sc.close();

        AbstractUser user;
        if (queryChar == PARK_MANAGER_CHAR) {
            final ParkManager pm = new ParkManager(queryLastName, queryFirstName, queryEmail);
            final Scanner numScan = new Scanner(scan.nextLine());
            while (!numScan.hasNextInt()) {
                numScan.next();
            }
            final int numParks = numScan.nextInt();
            numScan.close();
            for (int i = 0; i < numParks; i++) {
                pm.addPark(scan.nextLine());
            }
            user = pm;
        } else if (queryChar == ADMIN_CHAR) {
            user = new Administrator(queryLastName, queryFirstName, queryEmail);
        } else if (queryChar == VOLUNTEER_CHAR) {
            user = new Volunteer(queryLastName, queryFirstName, queryEmail);
        } else {
            throw new AssertionError(
                    "User type other than Admin, PM, or Volunteer read from file");
        }
        if ((lastName == null || lastName.equals(queryLastName))
                && (type == null || type == queryChar)
                && (email == null || email.equals(queryEmail))) {
            return user;
        } else {
            return null;
        }
    }

}
