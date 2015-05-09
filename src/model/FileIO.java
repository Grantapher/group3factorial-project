/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
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
    private static final String USER_FILE = "/files/users.info";
    private static final String JOB_FILE = "/files/jobs.info";
    private static FileIO instance = null;

    private FileIO() {
        // empty constructor
    }

    /**
     * @return the single instance to this class
     */
    public static FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    /**
     * Gets a Map of all jobs.
     *
     * @return a map of all the jobs
     * @throws FileNotFoundException if the Job file doesn't exist
     */
    public Map<LocalDate, List<Job>> readJobs() throws FileNotFoundException {
        final Map<LocalDate, List<Job>> map = new HashMap<>();
        final BufferedReader br = new BufferedReader(new FileReader(JOB_FILE));
        try {
            while (br.ready()) {
                // TODO will fail until job can be split easily
                final Job job = new Job(br.readLine());
                final LocalDate date = job.getStartDate();
                if (!map.containsKey(date)) {
                    map.put(date, new ArrayList<>());
                }
                map.get(date).add(job);
            } // end while
        } catch (final IOException theE) {
            System.err.println("ERROR: Job file corrupted.");
        }
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        return map;
    }

    /**
     * Appends the Job file with the given job.
     *
     * @param job the job to add to the file
     * @throws IOException if the Job file doesn't exist
     */
    public void appendJobs(final Job job) throws IOException {
        final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(JOB_FILE,
                true)));
        pw.println(job.toString());
        pw.close();
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
    public List<AbstractUser> queryUsers(final String lastName, final Character userType)
            throws FileNotFoundException {
        final BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        final List<AbstractUser> list = queryUsers(br, lastName, userType, null);
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        return list;
    }

    /**
     * Gets the user type of the user associated with the given email.
     * <p>
     * Check the "See Also" for the possible return values.
     *
     * @see ADMIN_CHAR
     * @see PARK_MANAGER_CHAR
     * @see USER_NOT_FOUND_CHAR
     * @see VOLUNTEER_CHAR
     * @param email the email to query for
     * @return the user type of the user associated with the given email
     * @throws FileNotFoundException if the User file doesn't exist
     */
    public char getUserType(final String email) throws FileNotFoundException {
        final BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        final List<AbstractUser> list = queryUsers(br, null, null, email);
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        final int size = list.size();
        if (size == 0) {
            return USER_NOT_FOUND_CHAR;
        } else if (size > 1) {
            throw new AssertionError("Multiple users under same email.");
        } else {
            final AbstractUser user = list.get(0);
            if (user instanceof Administrator) {
                return ADMIN_CHAR;
            } else if (user instanceof ParkManager) {
                return PARK_MANAGER_CHAR;
            } else if (user instanceof Volunteer) {
                return VOLUNTEER_CHAR;
            } else {
                throw new AssertionError("User not instance of Admin, PM, or Volunteer.");
            }
        }
    }

    /**
     * Creates a list of volunteers from a string of volunteers in Volunteer's
     * toString() method format.
     *
     * @param volunteers A string containing volunteers
     * @return A list of volunteers
     */
    public List<Volunteer> getVolunteers(final String volunteers) {
        final BufferedReader br = new BufferedReader(new StringReader(volunteers));
        final List<AbstractUser> userList = queryUsers(br, null, VOLUNTEER_CHAR, null);
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
    public void addUser(final AbstractUser user) throws IOException {
        final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(USER_FILE,
                true)));
        pw.println(user.toString());
        pw.println('\n');
        pw.close();
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
     * @param reader the reader attached to the User file
     * @param lastName the last name query
     * @param type the User type query
     * @param email the email query
     * @return a list of Users who match the query
     */
    private List<AbstractUser> queryUsers(final BufferedReader reader, final String lastName,
            final Character type, final String email) {
        final List<AbstractUser> list = new ArrayList<>();
        try {
            // gather User info
            while (reader.ready()) {
                final AbstractUser user = parseUser(reader, lastName, type, email);
                if (user != null) {
                    list.add(user);
                }
            } // end while
        } catch (final IOException theE) {
            System.err.println("ERROR: User file corrupted.");
        }
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
     * @param reader the reader attached to the User file
     * @param lastName the last name query
     * @param type the User type query
     * @param email the email query
     * @return the user if it matches the query, otherwise null
     * @throws IOException if an I/O error occurs
     */
    private AbstractUser parseUser(final BufferedReader reader, final String lastName,
            final Character type, final String email) throws IOException {
        final char queryChar = reader.readLine().charAt(0);
        final Scanner sc = new Scanner(reader.readLine());
        final String queryFirstName = sc.next();
        final String queryLastName = sc.next();
		final String queryEmail = sc.next();
        sc.close();

        // check if user matches query
        if ((lastName == null || lastName.equals(queryLastName))
                && (type == null || type == queryChar)
                && (email == null || email.equals(queryEmail))) {

            // user match found
            AbstractUser user;
            if (queryChar == PARK_MANAGER_CHAR) {
                final ParkManager pm = new ParkManager(queryLastName, queryFirstName,
                        queryEmail);
                String park;
                while (!"".equals(park = reader.readLine())) {
                    pm.addPark(park);
                }
                user = pm;
            } else if (queryChar == ADMIN_CHAR) {
                user = new Administrator(queryLastName, queryFirstName, queryEmail);
                reader.readLine();
            } else if (queryChar == VOLUNTEER_CHAR) {
                user = new Volunteer(queryLastName, queryFirstName, queryEmail);
                reader.readLine();
            } else {
                throw new AssertionError(
                        "User type other than Admin, PM, or Volunteer read from file");
            }
            return user;
        } else {
            return null;
        }
    }

}
