/*
 * TCSS 360 Project - Group 3!
 */
package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.AbstractUser;
import model.FileIO;
import model.Job;
import model.SerializableIO;

/**
 * Reads the text files and writes them to the serialized files.
 *
 * @author Grant Toepfer
 * @version May 23, 2015
 */
@SuppressWarnings("deprecation")
public class TxtToSer {
    private static final File USER_FILE = new File(System.getProperty("user.dir")
            + "/serfiles/users2.info");

    /**
     * Reads the txt files and converts them to Ser files.
     */
    public static void main(final String[] args) throws ClassNotFoundException, IOException {
        final Map<LocalDate, List<Job>> allJobs = FileIO.readJobs();
        final List<AbstractUser> allUsers = FileIO.queryUsers(null, null);
        System.out.println("Text files loaded.");

        if (USER_FILE.exists()) {
            new FileWriter(USER_FILE, false).close();
            System.out.println("Serialized user file erased");
        } else {
            System.out.println("Creating new serialized user file");
        }

        for (final AbstractUser user : allUsers) {
            SerializableIO.addUser(user);
        }
        System.out.println("Serialized user file created. Contents:");
        for (final AbstractUser user : SerializableIO.queryUsers(null, null)) {
            System.out.println(user);
        }
        System.out.println("End Users.");

        System.out.println("Creating new serialized Jobs file");
        SerializableIO.writeJobs(allJobs);
        System.out.println("Serialized job file created. Contents:");
        for (final List<Job> list : SerializableIO.readJobs().values()) {
            for (final Job job : list) {
                System.out.println(job);
            }
        }
        System.out.println("End Jobs.");

    }

}
