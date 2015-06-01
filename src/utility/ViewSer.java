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

        System.out.println("Serialized user file. Contents:\n");
        int i = 1;
        for (final AbstractUser user : SerializableIO.queryUsers(null, null)) {
            System.out.println(i++);
            System.out.println(user);
        }
        System.out.println("End Users.");

        printDivider();

        System.out.println("Serialized job file. Contents:\n");
        int j = 1;
        for (final List<Job> list : SerializableIO.readJobs().values()) {
            for (final Job job : list) {
                System.out.println(j++);
                printJob(job);
            }
        }
        System.out.println("End Jobs.");

    }

    private static void printDivider() {
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.print('#');
        }
        System.out.println();
        System.out.println();
    }

    private static void printJob(final Job job) {
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
        sb.append("\nVolunteer Capacity:\n\tLight: ");
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
        System.out.println(sb.toString());
    }

}
