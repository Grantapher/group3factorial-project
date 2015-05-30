/*
 * TCSS 360 Project - Group 3!
 */
package exception;

/**
 * Thrown when the total number of jobs exceeds the maximum number of total jobs
 * allowed.
 *
 * @version May 28, 2015
 * @author Wing-Sea Poon
 */
public class MaxJobsExceededException extends BusinessRuleException {
    private static final long serialVersionUID = -8453505605943487235L;

    /**
     * Constructs a MaxJobsExceededException with an appropriate error detail
     * message.
     */
    public MaxJobsExceededException() {
        super("Total number of jobs exceeds the maximum number allowed!");
    }

}
