package exception;

/**
 * This exception is to report that a park is not one of the the parks under
 * management
 *
 * @author Maurice Shaw
 * @version 27May15
 */
public final class NotMyParkException extends BusinessRuleException {

    private static final long serialVersionUID = -12411087227115972L;
    /**
     * The name of the park not under management.
     */
    private final String myPark;

    public NotMyParkException(final String thePark) {
        super(thePark + " is not a park you manage!");
        myPark = thePark;
    }

    /**
     * This method returns the name of the park that is not under management.
     * 
     * @return myPark the name of the park.
     */
    public String getPark() {
        return myPark;
    }
}
