package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.AbstractUser;
import model.Administrator;
import model.Calendar;
import model.FileIO;
import model.ParkManager;
import model.SerializableIO;
import model.Volunteer;

@SuppressWarnings("deprecation")
public class MainUI {
    private static Scanner inputReader;
    private static UserUI userInterface;

    public static void main(final String[] args) {
        inputReader = new Scanner(System.in);
        login();
        interact();
    }

    /**
     * Called repeatedly once a user is logged in to display to the user their
     * options
     */
    private static void interact() {
        while (userInterface.userMenu(inputReader)) {
            ;
        }
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
			user = SerializableIO.getUser(email);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Exception");
			System.exit(0);
		}

        //Prompts if the user would like to create a new account
        if (user == null) {
            System.out.println("Unrecognized email\n"
                    + "Would you like to create a new account? (y/n)");
            final String newAccount = inputReader.next();
            if (newAccount.charAt(0) == 'y') {
                user = createUser(email);
            } else {
                login();
            }
            
            //Creates a UI of the user's type
        } else if (user instanceof Volunteer) {
            userInterface = new VolunteerUI((Volunteer) user);
        } else if (user instanceof Administrator) {
            userInterface = new AdminUI((Administrator) user);
        } else if (user instanceof ParkManager) {
            userInterface = new ParkManagerUI((ParkManager) user);
        }
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
        while (userType != SerializableIO.VOLUNTEER_CHAR && userType != SerializableIO.PARK_MANAGER_CHAR
                && userType != SerializableIO.ADMIN_CHAR) {
            System.out.print("User Type(V for volunteer, P for park manager, A for admin): ");
            userType = inputReader.next().toUpperCase().charAt(0);
        }

      //Creates a new ParkManager profile
        if (userType == SerializableIO.PARK_MANAGER_CHAR) {
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
					SerializableIO.addUser(newManager);
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found");
					System.exit(0);
				} catch (IOException e) {
					System.out.println("IO exception");
					System.exit(0);
				}
                return newManager;

                //Creates a new volunteer profile
        } else if (userType == SerializableIO.VOLUNTEER_CHAR) {
            final Volunteer newVolunteer = new Volunteer(last, first, email);
                try {
					SerializableIO.addUser(newVolunteer);
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found");
					System.exit(0);
				} catch (IOException e) {
					System.out.println("IO exception");
					System.exit(0);
				}
                return newVolunteer;
 
              //Creates a new admin profile
        } else if (userType == SerializableIO.ADMIN_CHAR) {
            final Administrator newAdmin = new Administrator(last, first, email);
                try {
					SerializableIO.addUser(newAdmin);
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found");
					System.exit(0);
				} catch (IOException e) {
					System.out.println("IO exception");
					System.exit(0);
				}
                return newAdmin;
        }
        return null;
    }

    /**
     * Saves any changes that have been made to jobs and exits.
     */
    private static void quit() {
    	try {
			SerializableIO.writeJobs(Calendar.getInstance().getJobs());
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println("Job File not found!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO exception!");
			System.exit(0);
		}

    }
}
