/*
 * TCSS 360 Project - Group 3!
 */
package exception;

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
     */
    public InvalidTimeIntervalException() {
        super("Trying to schedule a job within an invalid time interval!");
    }

}
