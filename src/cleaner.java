import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class cleaner {
    private static final String jobs = "/jobs.info";
    private static final String users = "/users.info";

    public static void main(final String[] args) throws IOException {
        print(jobs);
        print(users);
        new PrintWriter(jobs).close();
        new PrintWriter(users).close();
    }

    private static void print(final String s) throws FileNotFoundException {
        System.err.println("\nNEW FILE\n");
        final Scanner scan = new Scanner(new File(s));
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            if ("".equals(line)) {
                System.out.println("BLANK LINE--------------");
            } else {
                System.out.println(line);
            }
        }
        scan.close();
    }
}
