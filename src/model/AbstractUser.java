/*
 * TCSS 360 Project - Group 3!
 */
package model;

public abstract class AbstractUser {
	protected String lastName;
	protected String firstName;
	protected String email;

    protected AbstractUser(final String lastName, final String firstName, final String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
