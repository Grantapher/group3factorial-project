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

/**
 * Handles the input and output from the files.
 *
 * @author Grant Toepfer
 * @version May 4, 2015
 */
public final class FileIO {
    public static final char VOLUNTEER_CHAR = 'V';
    public static final char ADMIN_CHAR = 'A';
    public static final char PARK_MANAGER_CHAR = 'P';
    public static final String USER_FILE = "/files/users.info";
    public static final String JOB_FILE = "/files/jobs.info";
    public static FileIO instance = null;

    private FileIO() {
        // empty constructor
    }

    public static FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    public List<User> queryUsers(final String lastName, final Character userType)
            throws FileNotFoundException {
        final List<User> list = new ArrayList<>();
        final BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        // TODO
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        return list;
    }

    public Map<LocalDate, List<Job>> readJobs() throws FileNotFoundException {
        // TODO treemap?
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

    public void appendJobs(final Job job) throws IOException {
        final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(JOB_FILE,
                true)));
        pw.println(job.toString());
        pw.close();
    }

    public char getUserType(final String email) throws FileNotFoundException {
        final char type = 'X';
        final BufferedReader br = new BufferedReader(new FileReader(USER_FILE));
        // TODO
        try {
            br.close();
        } catch (final IOException theE) {
            // ignore closing issues
        }
        return type;
    }

    public void addUser(final User user) throws IOException {
        final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(USER_FILE,
                true)));
        pw.println(user.toString());
        pw.close();
    }

}
