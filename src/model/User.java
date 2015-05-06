/*
 * TCSS 360 Project - Group 3!
 */
package model;

import java.util.Objects;

/**
 * Super class for users that contain their name and email.
 *
 * @author Grant Toepfer
 * @version May 6, 2015
 */
public class User {
    private final String myLastName;
    private final String myFirstName;
    private final String myEmail;

    /**
     * Creates a user with the given name and email.
     *
     * @param lastName the user's last name
     * @param firstName the user's first name
     * @param email the user's email
     */
    protected User(final String lastName, final String firstName, final String email) {
        myLastName = lastName;
        myFirstName = firstName;
        myEmail = email;
    }

    /**
     * Copy Constructor.
     *
     * @param that the User to make a copy of
     */
    protected User(final User that) {
        this(that.myLastName, that.myFirstName, that.myEmail);
    }

    /**
     * @return the user's first name
     */
    public String getFirstName() {
        return myFirstName;
    }

    /**
     * @return the user's last name
     */
    public String getLastName() {
        return myLastName;
    }

    /**
     * @return the user's email
     */
    public String getEmail() {
        return myEmail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object theObj) {
        if (theObj == null) {
            return false;
        }
        if (this == theObj) {
            return true;
        }
        if (theObj instanceof User) {
            final User that = (User) theObj;
            return myFirstName.equals(that.myFirstName) && myLastName.equals(that.myLastName)
                    && myEmail.equals(that.myEmail);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new User(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(myLastName, myFirstName, myEmail);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(myFirstName);
        sb.append(' ');
        sb.append(myLastName);
        sb.append('\n');
        sb.append(myEmail);
        sb.append('\n');
        return sb.toString();
    }

}
