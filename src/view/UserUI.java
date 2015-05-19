/*
 * TCSS 360 Project - Group 3!
 */
package view;

import java.util.Scanner;

/**
 * Defines how a UI for each user will be ran.
 * <p>
 * Classes that implement this interface should provide a constructor which
 * takes a parameter of the type of the model class that this UI is
 * representing.
 *
 * @author Grant Toepfer
 * @version May 18, 2015
 */
public interface UserUI {

    /**
     * Opens the prompt for the user's options.
     *
     * @param sc The scanner connected to the console
     * @return if the user is logging out
     */
    public boolean userMenu(Scanner sc);
}
