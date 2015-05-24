/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles the input and output from the ser files.
 *
 * @author Grant Toepfer
 * @version May 23, 2015
 */
public final class SerializableIO {
    /** The constant signifying an Administrator. */
    public static final char ADMIN_CHAR = 'A';
    /** The constant signifying a Park Manager. */
    public static final char PARK_MANAGER_CHAR = 'P';
    /** The constant signifying an Volunteer. */
    public static final char VOLUNTEER_CHAR = 'V';
    /** The constant signifying a user that does not exist. */
    public static final char USER_NOT_FOUND_CHAR = 'X';
    private static final File USER_FILE = new File(System.getProperty("user.dir")
            + "/serfiles/users2.info");
    private static final File JOB_FILE = new File(System.getProperty("user.dir")
            + "/serfiles/jobs2.info");

    private SerializableIO() {
        // empty constructor
    }

    /**
     * Gets a Map of all jobs.
     *
     * @return a map of all the jobs
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException If the ser file doesn't contain the jobs
     */
    public static Map<LocalDate, List<Job>> readJobs() throws IOException,
            ClassNotFoundException {
        final ObjectInput ois = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(JOB_FILE)));
        @SuppressWarnings("unchecked")
        final Map<LocalDate, List<Job>> map = (Map<LocalDate, List<Job>>) ois.readObject();
        ois.close();
        return map;
    }

    /**
     * Writes the map of jobs the the file, overwriting what is currently there.
     *
     * @param map the map to write from
     * @throws IOException if the file isn't found
     */
    public static void writeJobs(final Map<LocalDate, List<Job>> map) throws IOException {
        if (!JOB_FILE.exists()) {
            JOB_FILE.createNewFile();
        }
        final ObjectOutput oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(JOB_FILE, false)));
        oos.writeObject(map);
        oos.close();
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
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException if the ser file doesn't contain users
     */
    public static List<AbstractUser> queryUsers(final String lastName, final Character userType)
            throws ClassNotFoundException, IOException {
        return queryUsers(lastName, userType, null);
    }

    /**
     * Gets the user associated with the given email.
     *
     * @param email the email to query for
     * @return the user associated with the given email or null if the email
     *         doesn't exist
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException if the ser file doesn't contain users
     */
    public static AbstractUser getUser(final String email) throws ClassNotFoundException,
            IOException {
        final List<AbstractUser> list = queryUsers(null, null, email);
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
     * Appends the User file with the given user.
     *
     * @param user the user to add to the file
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException if the ser file doesn't contain users
     */
    public static void addUser(final AbstractUser user) throws IOException,
    ClassNotFoundException {
        if (!USER_FILE.exists()) {
            USER_FILE.createNewFile();
        }
        List<AbstractUser> userList;
        try {
            if (emailExists(user.getEmail())) {
                return;
            }
            userList = queryUsers(null, null);
        } catch (final EOFException e) {
            userList = new ArrayList<>();
        }
        userList.add(user);
        final ObjectOutput oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(USER_FILE, false)));
        oos.writeObject(userList);
        oos.close();
    }

    /**
     * @param email the email to look for a duplicate of
     * @return if the email exists in the system already
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException if the ser file doesn't contain users
     */
    private static boolean emailExists(final String email) throws ClassNotFoundException,
    IOException {
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
     * @param lastName the last name query
     * @param type the User type query
     * @param email the email query
     * @return a list of Users who match the query
     * @throws IOException if the file is not found
     * @throws ClassNotFoundException if the ser file doesn't contain users
     */
    private static List<AbstractUser> queryUsers(final String lastName, final Character type,
            final String email) throws FileNotFoundException, IOException,
            ClassNotFoundException {
        final ObjectInput ois = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream(USER_FILE)));
        @SuppressWarnings("unchecked")
        final List<AbstractUser> allList = (List<AbstractUser>) ois.readObject();
        ois.close();
        final List<AbstractUser> queryList = new ArrayList<>();
        for (final AbstractUser user : allList) {
            if ((null == lastName || lastName.equals(user.getLastName()))
                    && (null == email || email.equals(user.getEmail()))
                    && (null == type || checkType(user, type))) {
                queryList.add(user);
            }
        }

        return queryList;
    }

    /**
     * Checks to see if the user matches the given type.
     *
     * @param user the user to compare
     * @param type the type expected of the user
     * @return if the user matches the given type
     */
    private static boolean checkType(final AbstractUser user, final char type) {
        switch (type) {
            case ADMIN_CHAR:
                if (user instanceof Administrator) {
                    return true;
                }
                break;
            case PARK_MANAGER_CHAR:
                if (user instanceof ParkManager) {
                    return true;
                }
                break;
            case VOLUNTEER_CHAR:
                if (user instanceof Volunteer) {
                    return true;
                }
                break;
            default:
                throw new AssertionError(
                        "Type of AbstractUser other thatn Admin, PM, or Volunteer detected.");
        }
        return false;
    }
}
