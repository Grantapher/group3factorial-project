/*
 * TCSS 360 Project - Group 3!
 */
package model;

/**
 * Every type of User in the system will inherit from this abstract class.
 *
 * @author Wing-Sea Poon
 * @version May 9, 2015
 */
public abstract class AbstractUser {
    protected String lastName;
    protected String firstName;
    protected String email;

    /**
     * Assigns the given parameters to this User.
     *
     * @param lastName the User's last name
     * @param firstName the User's first name
     * @param email the User's email address
     */
    protected AbstractUser(final String lastName, final String firstName, final String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    /**
     * @return The User's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return The User's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return The User's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof AbstractUser) {
<<<<<<< HEAD
            final AbstractUser user = (AbstractUser) other;
            return firstName.equalsIgnoreCase(user.getFirstName())
                    && lastName.equalsIgnoreCase(user.getLastName())
                    && email.equalsIgnoreCase(user.getEmail());
=======
            return this.firstName.equalsIgnoreCase(((AbstractUser) other).getFirstName())
            	&& this.lastName.equalsIgnoreCase(((AbstractUser) other).getLastName())
            	&& this.email.equalsIgnoreCase(((AbstractUser) other).getEmail());
>>>>>>> 97d7c38275a42df393d51e69c9e8c133f7cc5d08
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return lastName + "\t" + firstName + "\t" + email;
    }
}
