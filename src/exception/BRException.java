package exception;

public class BRException extends Exception {
	protected static final long serialVersionUID = 498477131095050411L;
	
	/**
     *  Constructs a BRException with null as its error detail message.
     */
	public BRException(){
        super();
    }

	/**
	 * Constructs an BRException with the specified detail message.
	 * @param message The detail message
	 */
	public BRException(String message){
        super(message);
    }
}
