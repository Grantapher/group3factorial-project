/*
 * TCSS 360 Project - Group 3!
 */
package utility;

import java.io.IOException;
import java.util.List;

import model.AbstractUser;
import model.Job;
import model.SerializableIO;

/**
 * Run this to display the contents of the ser files.
 *
 * @author Grant Toepfer
 * @version May 24, 2015
 */
public class ViewSer {

    public static void main(final String[] args) throws ClassNotFoundException, IOException {
        System.out.println("Serialized user file. Contents:\n\n");
        for (final AbstractUser user : SerializableIO.queryUsers(null, null)) {
            System.out.println(user);
        }
        System.out.println("End Users.");

        System.out
        .println("###########################################################################");

        System.out.println("Serialized job file. Contents:\n\n");
        for (final List<Job> list : SerializableIO.readJobs().values()) {
            for (final Job job : list) {
                System.out.println(job);
            }
        }
        System.out.println("End Jobs.");

    }

}
