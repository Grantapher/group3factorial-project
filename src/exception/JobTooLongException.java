/*
 * TCSS 360 Project - Group 3!
 */
package exception;

/**
 * Thrown when the duration of the job exceeds the maximum duration allowed.
 *
 * @version May 28, 2015
 * @author Wing-Sea Poon
 */
public class JobTooLongException extends BusinessRuleException {
    private static final long serialVersionUID = 4637078049549883219L;

    /**
     * Constructs a JobTooLongException with an appropriate error detail
     * message.
     */
    public JobTooLongException() {
        super("The duration of the job exceeds the maximum duration allowed!");
    }

}
