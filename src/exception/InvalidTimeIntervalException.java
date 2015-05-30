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
public class InvalidTimeIntervalException extends BRException {
	private static final long serialVersionUID = 3702469187863291822L;

	/**
     *  Constructs a InvalidTimeIntervalException with null as its error detail message.
     */
	public InvalidTimeIntervalException(){
        super();
    }

	/**
	 * Constructs an InvalidTimeIntervalException with the specified detail message.
	 * @param message The detail message
	 */
    public InvalidTimeIntervalException(String message){
        super(message);
    }
}
