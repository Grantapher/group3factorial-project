/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.io.Serializable;

/**
 * Every type of User in the system will inherit from this abstract class.
 *
 * @author Wing-Sea Poon
 * @version May 9, 2015
 */
public abstract class AbstractUser implements Serializable {
    /**
     * @version 1.0
     */
    private static final long serialVersionUID = 1L;
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
            return firstName.equalsIgnoreCase(((AbstractUser) other).getFirstName())
                    && lastName.equalsIgnoreCase(((AbstractUser) other).getLastName())
                    && email.equalsIgnoreCase(((AbstractUser) other).getEmail());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return firstName + ' ' + lastName + ' ' + email + '\n';
    }
}
