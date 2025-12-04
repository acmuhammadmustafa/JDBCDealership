// Grabbed ConsoleHelper from previous projects:
package com.pluralsight;
import java.time.*;
import java.util.*;

public class ConsoleHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static int promptForInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static LocalDate promptForDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String dateAsString = scanner.nextLine();
                return LocalDate.parse(dateAsString);
            } catch (Exception ex) {
                System.out.println("Invalid Entry, please enter a valid date (YYYY-MM-DD)");
            }
        }
    }

    public static float promptForFloat(String prompt) {
        System.out.print(prompt + ": ");
        float result = scanner.nextFloat();
        scanner.nextLine();
        return result;
    }

    public static long promptForLong(String prompt) {
        System.out.print(prompt + ": ");
        long result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }

    public static double promptForDouble(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static boolean promptForYesNo(String prompt) {
        while (true) {
            String input = promptForString(prompt + " (Y/N)").toLowerCase().trim();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    public static String promptForHelperMenu(String title, List<String> options, boolean allowCancel) { // Examples: title = "Choose your bread" | options = "List like [1,2,3] or [apple, banana, pears] | allowCancel = the cancel/return option.
        System.out.println("\n" + title); // Displays the title/question but creates a blank line before.
        for (int i = 0; i < options.size(); i++)
            System.out.printf("%d) %s\n", i + 1, options.get(i));// Prints each option (%d = menu number | %s = actual option | \n = new line) i+1 = index starts at 0 but +1 so string option 1 is ACTUALLY equal to 1 rather than 0.
        if (allowCancel){
            System.out.println("0) Cancel/Return");
        }

        while (true) { // While true = keeps asking till valid option is given.
            int choice = promptForInt("Enter your choice");
            if (allowCancel && choice == 0)
                return null; // Exits the method.
            if (choice >= 1 && choice <= options.size()) {
                return options.get(choice - 1); // Returns the actual chosen item (as a string) "choice - 1" so that choice and option list are matched. (choice -1 = 0 where options[0])
            }
            System.out.println("Invalid choice. Try again."); // Already exited the loop, therefore, no else statement needed.
        }
    }
}