/*
 * TCSS 360 Project - Group 3!
 */
package exception;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;

/**
 * Thrown when a Volunteer is signed up for a job already on the same day.
 *
 * @author Grant Toepfer
 * @version May 30, 2015
 */
public class OverbookedVolunteerException extends BusinessRuleException {
    private static final long serialVersionUID = 8737826581634351099L;

    /**
     * Creates an exception with an appropriate message.
     *
     * @param theJob the job they tried to sign up for
     * @param theV the volunteer who would be overbooked
     */
    public OverbookedVolunteerException(final Job theJob, final Volunteer theV)
            throws ClassNotFoundException, IOException {
        super(createErrorMessage(theJob, theV));
    }

    /**
     * Creates the appropriate message.
     *
     * @param theJob the job they tried to sign up for
     * @param theV the volunteer who would be overbooked
     * @return the message
     */
    private static String createErrorMessage(final Job theJob, final Volunteer theV)
            throws ClassNotFoundException, IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append("Can't sign up for ");
        sb.append(displayJobWithDate(theJob));
        sb.append(", conflicts with ");
        final List<Job> conflicts = getConflicts(theJob, theV.getJobs());
        for (final Job job : conflicts) {
            sb.append(displayJobWithDate(job));
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length() - 1, ". ");
        return sb.toString();
    }

    /**
     * Creates a default way to print jobs. Contains their title, start, and end
     * dates.
     * <p>
     * If the job is one day, it will just print the one day.
     *
     * @param theJob the job to print
     * @return a neatly formatted string for the job
     */
    private static Object displayJobWithDate(final Job theJob) {
        final StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(theJob.getTitle());
        sb.append("\" on ");
        final LocalDate start = theJob.getStartDate();
        sb.append(start);
        final LocalDate end = theJob.getEndDate();
        if (!start.equals(end)) {
            sb.append(" to ");
            sb.append(end);
        }
        return sb.toString();
    }

    /**
     * Finds the jobs that conflict with the given job
     *
     * @param targetJob the target job
     * @param theJobs the jobs the volunteer is signed up for
     * @return the jobs that conflict with the target job
     */
    private static List<Job> getConflicts(final Job targetJob, final List<Job> theJobs) {
        final List<Job> list = new ArrayList<Job>();
        for (final Job job : theJobs) {
            if (!targetJob.getStartDate().isAfter(job.getEndDate())
                    && !targetJob.getEndDate().isBefore(job.getStartDate())) {
                list.add(job);
            }
        }
        return list;
    }
}