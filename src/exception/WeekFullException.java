/*
 * TCSS 360 Project - Group 3!
 */
package exception;

/**
 * Thrown when the total number of jobs for the week exceeds the maximum number 
 * of jobs allowed per week.
 *
 * @version May 28, 2015
 * @author Wing-Sea Poon
 */
public class WeekFullException extends BRException {
	private static final long serialVersionUID = -4858693123180616397L;

	/**
     *  Constructs a WeekFullException with null as its error detail message.
     */
	public WeekFullException(){
        super();
    }

	/**
	 * Constructs an WeekFullException with the specified detail message.
	 * @param message The detail message
	 */
    public WeekFullException(String message){
        super(message);
    }
}
