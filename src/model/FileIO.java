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
    public static final char ADMIN_CHAR = 'A';
    public static final char PARK_MANAGER_CHAR = 'P';
    public static final char VOLUNTEER_CHAR = 'V';
    public static final char USER_NOT_FOUND_CHAR = 'X';
    private static final String USER_FILE = "/files/users.info";
    private static final String JOB_FILE = "/files/jobs.info";
    private static FileIO instance = null;

    /**
     * Empty constructor.
     */
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
        // TODO
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
     * @param userType the userType query
     * @return a list of Users matching the given query
     * @throws FileNotFoundException if the User file doesn't exist
     */
    public List<User> queryUsers(final String lastName, final Character userType)
            throws FileNotFoundException {
        final BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        final List<User> list = queryUsers(br, lastName, userType, null);
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
        final List<User> list = queryUsers(br, null, null, email);
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        final int size = list.size();
        if (size == 0) {
            return USER_NOT_FOUND_CHAR;
        } else if (size > 1) {
            throw new AssertionError("Multiple Users under same email.");
        } else {
            final User user = list.get(1);
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
    private List<User> queryUsers(final BufferedReader reader, final String lastName,
            final Character type, final String email) {
        final List<User> list = new ArrayList<>();
        try {
            // gather User info
            String queryType;
            while ((queryType = reader.readLine()) != null) {
                final char queryChar = queryType.charAt(0);
                final Scanner name = new Scanner(reader.readLine());
                final String queryFirstName = name.next();
                final String queryLastName = name.next();
                name.close();
                final String queryEmail = reader.readLine();

                // check if user matches query
                if ((lastName == null || lastName.equals(queryLastName))
                        && (type == null || type == queryChar)
                        && (email == null || email.equals(queryEmail))) {

                    // user match found
                    User user;
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
                    list.add(user);
                } // end while
            } // end try
        } catch (final IOException theE) {
            // TODO handle exception
        }
        return list;
    }

    /**
     * Appends the User file with the given user.
     *
     * @param user the user to add to the file
     * @throws IOException if the User file doesn't exist
     */
    public void addUser(final User user) throws IOException {
        final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(USER_FILE,
                true)));
        pw.println(user.toString());
        pw.println('\n');
        pw.close();
    }

}
