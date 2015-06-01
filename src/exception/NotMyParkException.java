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

    public NotMyParkException(final String thePark) {
        super("\"" + thePark + "\" is not a park you manage!");
    }

}
