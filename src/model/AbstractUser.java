
/*
 * TCSS 360 Project - Group 3!
 */
package model;

public class AbstractUser {
    
    /**
     * User Email.
     */
    private String myEmail;
    
    /**
     * The User's first name.
     */
    private String myFirstName;
    
    /**
     * The User's last name.
     */
    private String myLastName;

    public AbstractUser(final String lastName, final String firstName, final String email) {
        myEmail = email;
        myFirstName = firstName;
        myLastName = lastName;
    }

    public AbstractUser(final AbstractUser that) {
        // TODO Auto-generated constructor stub
    }

    public String getFirstName() {
        // TODO Auto-generated method stub
        return myFirstName;
    }

    public String getLastName() {
        // TODO Auto-generated method stub
        return myLastName;
    }

    public String getEmail() {
        // TODO Auto-generated method stub
        return myEmail;
    }

}