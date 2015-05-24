package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.AbstractUser;
import model.Administrator;
import model.Calendar;
import model.FileIO;
import model.ParkManager;
import model.Volunteer;

@SuppressWarnings("deprecation")
public class MainUI {
    private static Scanner inputReader;
    private static UserUI userInterface;

    public static void main(final String[] args) {
        inputReader = new Scanner(System.in);
        login();
    }

    /**
     * Called repeatedly once a user is logged in to display to the user their
     * options
     */
    private static void interact() {
        while (userInterface.userMenu(inputReader)) {
            ;
        }
        login();
    }

    /**
     * Logs in the user, giving the option to create a new profile if the login
     * email does't exist in the system.
     */
    private static void login() {
        AbstractUser user = null;
        System.out.print("Login Email (q to quit): ");
        final String email = inputReader.nextLine();
        if (email.equals("q")) {
            quit();
        }
        try {
            user = FileIO.getUser(email);
        } catch (final FileNotFoundException e) {
            System.out.println("User File missing, find it!");
            System.exit(0);
        }

        if (user == null) {
            System.out.println("Unrecognized email\n"
                    + "Would you like to create a new account? (y/n)");
            final String newAccount = inputReader.next();
            if (newAccount.charAt(0) == 'y') {
                user = createUser(email);
            } else {
                login();
            }
        } else if (user instanceof Volunteer) {
            userInterface = new VolunteerUI((Volunteer) user);
        } else if (user instanceof Administrator) {
            userInterface = new AdminUI((Administrator) user);
        } else if (user instanceof ParkManager) {
            userInterface = new ParkManagerUI((ParkManager) user);
        }
        interact();
    }

    /**
     * Creates a new user profile
     *
     * @param email
     */
    private static AbstractUser createUser(final String email) {
        System.out.print("\nFirst Name: ");
        final String first = inputReader.next();
        System.out.print("Last Name: ");
        final String last = inputReader.next();
        char userType = '0';
        // Query for userType
        while (userType != FileIO.VOLUNTEER_CHAR && userType != FileIO.PARK_MANAGER_CHAR
                && userType != FileIO.ADMIN_CHAR) {
            System.out.print("User Type(V for volunteer, P for park manager, A for admin): ");
            userType = inputReader.next().toUpperCase().charAt(0);
        }

        // Query for managers parks
        if (userType == FileIO.PARK_MANAGER_CHAR) {
            final ParkManager newManager = new ParkManager(last, first, email);
            String park;
            do {
                System.out.print("\nTell me a park you work in (q): ");
                park = inputReader.nextLine();
                if (!park.equals("q")) {
                    newManager.addPark(park);
                }
            } while (!park.equals("q"));
            try {
                FileIO.addUser(newManager);
                return newManager;
            } catch (final IOException e) {
                System.out.println("User File missing, find it!");
                System.exit(0);
            }

        } else if (userType == FileIO.VOLUNTEER_CHAR) {
            final Volunteer newVolunteer = new Volunteer(last, first, email);
            try {
                FileIO.addUser(newVolunteer);
                return newVolunteer;
            } catch (final IOException e) {
                System.out.println("User File missing, find it!");
                System.exit(0);
            }
        } else if (userType == FileIO.ADMIN_CHAR) {
            final Administrator newAdmin = new Administrator(last, first, email);
            try {
                FileIO.addUser(newAdmin);
                return newAdmin;
            } catch (final IOException e) {
                System.out.println("User File missing, find it!");
                System.exit(0);
            }
        }
        return null;
    }

    private static void quit() {
        try {
            FileIO.writeJobsAndExit(Calendar.getInstance().getJobs());
        } catch (final IOException e) {
            System.out.println("File missing, find it!");
            System.exit(0);
        }
    }
}
