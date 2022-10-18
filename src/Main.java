import java.util.Scanner;

public class Main {
    private static Gym powerlifters = new Gym();

    // Main method that runs the whole program
    public static void main(String[] args) {
        welcomeUser();
        do{
            handleOptions();

        }while (true);
    }

    public static void welcomeUser(){
        /*
         ----------------------------------------------------
         Welcomes the user to the database
         ----------------------------------------------------
        */
        System.out.println("Welcome to the powerlifting database! \n");
    }


    public static String getUserInput(String prompt){
        /*
        ----------------------------------------------------
        Gets user input.
        ----------------------------------------------------
        Arguments:
            prompt: (String) Message that will be print out,
            when getting the user input.
        ----------------------------------------------------
        Returns:
            (String) User input.
         */

        Scanner scn = new Scanner(System.in);

        System.out.print(prompt);
        String userInput = scn.nextLine();

        return userInput;
    }

    public static boolean isYes(String prompt){
        /*
        ----------------------------------------------------
        Gets user "yes" or "no" input.
        ----------------------------------------------------
        Arguments:
            prompt: (String) Message that will be print out,
            when getting the user input.
        ----------------------------------------------------
        Returns:
            (boolean) true if "Y" was the user input.
            false if "N" was the user input.
        ----------------------------------------------------
         */

        String userInput = getUserInput(prompt);
        return userInput.equalsIgnoreCase("y");
    }

    public static String getUserOption(){
        /*
        ----------------------------------------------------
        Prompts the user with 7 options to use the database.
        ----------------------------------------------------
        Returns:
            (String) User input.
         */
        String userInput = getUserInput(
                "Choose and option to proceed: " +
                        "\n1. Add powerlifter to the database " +
                        "\n2. Remove powerlifter from the database " +
                        "\n3. Print list of powerlifters (one line info)" +
                        "\n4. Print all powerlifters (short info)" +
                        "\n5. Print all powerlifters (full info)" +
                        "\n6. Sort all powerlifters (based on a lift or total)" +
                        "\n7. Search powerlifters by name" +
                        "\n8. Search powerlifters by percentile" +
                        "\n0. Exit the database" +
                        "\n-------------------------------" +
                        "\nType the corresponding number to choose an option: ");

        return userInput;
    }

    public static String executeUserOption(String userInput){
        /*
        ----------------------------------------------------
        Executes the method corresponding to the user's option
        ----------------------------------------------------
        Arguments:
           userInput: (String) User option to "communicate"
           with the database
        ----------------------------------------------------
        Returns:
            (String) Output message to be outputted after
            an option has been executed.
        ----------------------------------------------------
         */
        clearScreen();
        switch (userInput){
            case "0":
                // Ask if the user does indeed want to exit the program
                boolean wantsToExit = isYes("Are you sure you want to exit? (Y/N): ");
                if (wantsToExit){
                    System.out.println("Okay then! Goodbye!");
                    System.exit(0);
                }

                return "\n";

            case "1":
                // Add powerlifter
                String[] powerlifterInfo = getPowerlifter();
                Powerlifter powerlifter = new Powerlifter(powerlifterInfo);
                powerlifters.add(powerlifter);

                // Set best lifts
                setPowerlifterLifts(powerlifter);

                clearScreen();
                return "\n" + powerlifterInfo[0] + " has been successfully added to the database!\n";


            case "2":
                String fullName = getUserInput("Enter the full name of the powerlifter you want to remove: ");
                powerlifters.removePowerlifter(fullName);

                return "Powerlifter has been successfully removed! \n";

            case "3":
                // Print list of powerlifters with one line description of each lifter.
                powerlifters.printList();
                return "\n";

            case "4":
                // Print short information about all the lifters
                powerlifters.printAll(true);
                return "\n";

            case "5":
                // Print full information about the lifters
                powerlifters.printAll(false);
                return "\n";

            case "6":
                // Sor the lifters
                String sortBy = getUserInput(
                        "What do you want to sort the lifters by? \nSquat, bench, deadlift or total? "
                );
                boolean isAscending = isYes("Do you want to sort the lifters in ascending order? (Y/N): ");
                powerlifters.sortPowerlifters(sortBy, isAscending);

                clearScreen();
                return "\nPowerlifters have been sorted! \n";

            case "7":
                // Find lifters by name
                String searchKey = getUserInput("Enter a search key: ");
                powerlifters.find(searchKey);

                return "\n";

            case "8":
                // Find lifters by percentile
                String percentileSortBy = getUserInput(
                        "What lift do you want to find the percentile in? "
                );
                float userPercentile = Float.parseFloat(
                        getUserInput(
                                "Enter a percentile: " +
                                        "\nNOTE: if you enter 90, you will see all powerlifters that fall under the 90th percentile: "
                        ));

                powerlifters.findNthPercentile(userPercentile, percentileSortBy);
                return "\n";

            default:
                return "Option was invalid!";
        }
    }

    public static void handleOptions(){
        /*
        ----------------------------------------------------
        First gets user option, then executes it.
        ----------------------------------------------------
         */
        String userOption = getUserOption();
        String message = executeUserOption(userOption);

        // Print output message
        System.out.println(message);
    }

    public static String[] getPowerlifter(){
        /*
        ----------------------------------------------------
        Collects information about a powerlifter given by the user
        ----------------------------------------------------
        Returns:
            (String[]) Information about the given powerlifter.
            String[0] = full name
            String[1] = age class
            String[2] = weight class
        ----------------------------------------------------
         */
        String[] powerlifterInfo = new String[3];
        String[] prompts = {
                "Enter the full name of the powerlifter you want to add: ",
                "Enter the age class (e.g. Sub-Junior): ",
                "Enter the weight class (e.g. -120): "
        };

        for (int i = 0; i < powerlifterInfo.length; i++){
            String userInput = getUserInput(prompts[i]);
            powerlifterInfo[i] = userInput;
        }

        return powerlifterInfo;
    }


    public static void setPowerlifterLifts(Powerlifter powerlifter){
        /*
        ----------------------------------------------------
        Sets lifts for the given powerlifter.
        Used in executeUserOption.
        ----------------------------------------------------
        Arguments:
            powerlifter: (Powerlifter) A Powerlifter object
        ----------------------------------------------------
         */
        float[] bestLiftsKg = new float[3];
        String[] prompts = {
                "What is the best squat of this powerlifter? (in Kg): ",
                "What is the best bench press of this powerlifter? (in Kg): ",
                "What is the best deadlift of this powerlifter? (in Kg): "
        };

        // Get information
        for (int i = 0; i < bestLiftsKg.length; i++){
            float bestLift = Float.parseFloat(
                    getUserInput(
                            prompts[i]
                    )
            );

            bestLiftsKg[i] = bestLift;
        }

        // Set information
        powerlifter.setBestSquatKg(bestLiftsKg[0]);
        powerlifter.setBestBenchKg(bestLiftsKg[1]);
        powerlifter.setBestDeadliftKg(bestLiftsKg[2]);
        powerlifter.setBestTotalKg();

    }


    public static void clearScreen(){
        /*
        ----------------------------------------------------
        Clears the terminal screen
        ----------------------------------------------------
         */
        // Clear terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}









