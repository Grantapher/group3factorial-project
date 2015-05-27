/*
 * TCSS 360 Project - Group 3!
 */
package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AbstractUser;
import model.Administrator;
import model.Volunteer;

/**
 * This class contains the UI for an Administrator using the system.
 *
 * @author Wing-Sea Poon
 * @version May 20, 2015
 */
public class AdminUI implements UserUI {
    private final Administrator myAdmin;

    /**
     * Initializes the fields of AdminUI.
     *
     * @param admin the administrator that logged in
     */
    public AdminUI(final Administrator admin) {
        myAdmin = admin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean userMenu(final Scanner sc) {
        System.out.print("\nVolunteer last name to search for (q to quit): ");
        final String lastName = sc.next();
        if (lastName.equals("q")) {
            return true;
        }

        List<Volunteer> volunteers = new ArrayList<Volunteer>();
        try {
            volunteers = myAdmin.findVolunteer(lastName);
        } catch (final IOException e) {
            System.out.println("User File missing, find it!");
            System.exit(0);
        } catch (final ClassNotFoundException theE) {
            System.out.println("User File corrupted, fix it!");
            System.exit(0);
        }
        for (final AbstractUser v : volunteers) {
            System.out.println(v);
        }
        return false;
    }
}
