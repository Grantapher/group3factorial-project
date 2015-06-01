/*
 * TCSS 360 Project - Group 3!
 */
package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.SerializableIO;

/**
 * Simply prints out any 5 jobs in a week.
 *
 * @author Grant Toepfer
 * @version Jun 1, 2015
 */
public class FindFullWeek {

    public static void main(final String[] args) throws ClassNotFoundException, IOException {
        final List<List<Job>> mapList = new ArrayList<>(SerializableIO.readJobs().values());
        final List<Job> jobList = new ArrayList<>();
        for (final List<Job> list : mapList) {
            jobList.addAll(list);
        }
        boolean found = false;
        for (int i = 0; i < jobList.size(); i++) {
            try {
                final Job thisJob = jobList.get(i);
                final Job fiveAway = jobList.get(i + 5);
                if (!thisJob.getEndDate().plusDays(7).isBefore(fiveAway.getStartDate())) {
                    if (!found) {
                        found = true;
                        System.out.println("FULL WEEKS ON:");
                    }
                    System.out.println(thisJob.getEndDate().plusDays(3));
                }
            } catch (final IndexOutOfBoundsException e) {
                if (!found) {
                    System.out.println("NO FULL WEEKS :(");
                }
                return;
            }
        }
    }

}
