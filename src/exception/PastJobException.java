/*
 * TCSS 360 Project - Group 3!
 */
package exception;

import java.time.LocalDate;

/**
 * Thrown when a Volunteer tries to sign up for a job in the past.
 *
 * @author Grant Toepfer
 * @version May 30, 2015
 */
public class PastJobException extends BusinessRuleException {

    private static final long serialVersionUID = 1612687762716519007L;

    /**
     * Constructs an appropriate error message for this exception.
     *
     * @param theDate the date they tried to sign up for.
     */
    public PastJobException(final LocalDate theDate) {
        super("The job you tried to sign up for is on " + theDate.toString() + ", today is "
                + LocalDate.now().toString());
    }
}
