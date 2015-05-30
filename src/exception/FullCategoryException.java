/*
 * TCSS 360 Project - Group 3!
 */
package exception;

/**
 * Thrown when a Volunteer signs up for a category that's full.
 *
 * @author Grant Toepfer
 * @version May 30, 2015
 */
public class FullCategoryException extends BusinessRuleException {
    private static final long serialVersionUID = -7142802198927626243L;

    /**
     * Creates an appropriate message for the exception.
     *
     * @param theCategory the category to put in the exception
     */
    public FullCategoryException(final String theCategory) {
        super("The " + theCategory + " category is full!");
    }

}
