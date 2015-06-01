/*
 * TCSS 360 Project - Group 3!
 */
package exception;

import java.time.LocalDate;

import model.Job;

/**
 * Thrown when the job is scheduled within an invalid time interval.
 *
 * @version May 28, 2015
 * @author Wing-Sea Poon
 */
public class InvalidTimeIntervalException extends BusinessRuleException {
    private static final long serialVersionUID = 3702469187863291822L;

    /**
     * Constructs an InvalidTimeIntervalException with an appropriate message.
     *
     * @param theJob the job to get the invalid interval from
     */
    public InvalidTimeIntervalException(final Job theJob) {
        super(makeMessage(theJob));
    }

    /**
     * Creates an appropriate exception message for this exception.
     *
     * @param theJob the job to get the date from
     * @return an appropriate exception message
     */
    private static String makeMessage(final Job theJob) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Trying to schedule a job within an invalid time interval!\nTried scheduling on ");
        final LocalDate start = theJob.getStartDate();
        sb.append(start);
        final LocalDate end = theJob.getEndDate();
        if (!start.equals(end)) {
            sb.append(" to ");
            sb.append(end);
        }
        sb.append(", when the valid interval is ");
        final LocalDate now = LocalDate.now();
        sb.append(now.plusDays(1));
        sb.append(" to ");
        sb.append(now.plusMonths(3));
        sb.append(".");
        return sb.toString();

    }
}
