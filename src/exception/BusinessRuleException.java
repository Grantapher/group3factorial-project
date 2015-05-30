/*
 * TCSS 360 Project - Group 3!
 */
package exception;

/**
 * Super Exception for business rules violations.
 *
 * @author Grant Toepfer
 * @version May 30, 2015
 */
public abstract class BusinessRuleException extends Exception {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -8966789500229952210L;

    /**
     * Constructs a BusinessRuleException with the given error message.
     *
     * @param theMessage the error message
     */
    protected BusinessRuleException(final String theMessage) {
        super(theMessage);
    }

}
