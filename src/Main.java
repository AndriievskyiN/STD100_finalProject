import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Gym powerlifters = new Gym();
    private static final PowerlifterWriter powerlifterWriter = new PowerlifterWriter(powerlifters);
    private static final PowerlifterReader powerlifterReader = new PowerlifterReader(powerlifters);

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
        ----------------------------------------------------
         */

        Scanner scn = new Scanner(System.in);

        System.out.print(prompt);
        String userInput = scn.nextLine().trim();

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
        Prompts the user with 11 options to use the database.
        ----------------------------------------------------
        Returns:
            (String) User input.
        ----------------------------------------------------
         */

        String userInput = getUserInput(
                "Choose and option to proceed: " +
                        "\n1. Add powerlifter to the database " +
                        "\n2. Update powerlifter" +
                        "\n3. Remove powerlifter from the database " +
                        "\n4. Print list of powerlifters (one line info)" +
                        "\n5. Print all powerlifters (short info)" +
                        "\n6. Print all powerlifters (full info)" +
                        "\n7. Sort all powerlifters (based on a lift or total)" +
                        "\n8. Search powerlifters by name" +
                        "\n9. Search powerlifters by percentile" +
                        "\n10. Interact with files" +
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

        String outputMessage;

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

                // Check if the given powerlifter already exists
                if ((powerlifters.powerlifterExists(powerlifter.fullName)))
                    return "\n";

                // Otherwise add the powerlifter to the array
                powerlifters.add(powerlifter);

                // Set best lifts
                setPowerlifterLifts(powerlifter);

                clearScreen();
                return "\n" + powerlifterInfo[0] + " has been successfully added to the database!\n";


            case "2":
                // Update information about a lifter
                outputMessage = updatePowerlifter();

                clearScreen();
                return outputMessage + "\n";

            case "3":
                // Remove powerlifter
                String fullName = getUserInput("Enter the full name of the powerlifter you want to remove: ");
                boolean isFound = powerlifters.removePowerlifter(fullName);
                clearScreen();

                if (isFound)
                    return fullName + " was successfully removed from the database! \n";

                else
                    return fullName + " was not found in the database. Check your spelling! \n";

            case "4":
                // Print list of powerlifters with one line description of each lifter
                powerlifters.printList();
                return "\n";

            case "5":
                // Print short information about all the lifters
                powerlifters.printAll(true);
                return "\n";

            case "6":
                // Print full information about the lifters
                powerlifters.printAll(false);
                return "\n";

            case "7":
                // Sor the lifters
                String sortBy = getUserInput(
                        "What do you want to sort the lifters by? \nSquat, bench, deadlift or total? "
                );
                boolean isAscending = isYes("Do you want to sort the lifters in ascending order? (Y/N): ");
                powerlifters.sortPowerlifters(sortBy, isAscending);

                clearScreen();
                return "\nPowerlifters have been sorted! \n";

            case "8":
                // Find lifters by name
                String searchKey = getUserInput("Enter a search key: ");
                powerlifters.find(searchKey);

                return "\n";

            case "9":
                // Find lifters by percentile
                String percentileSortBy = getUserInput(
                        "What lift do you want to find the percentile in? "
                );
                float userPercentile = Float.parseFloat(
                        getUserInput(
                                "Enter a percentile: " +
                                        "\nNOTE: if you enter 90, you will see all powerlifters that fall under the 90th percentile: "
                        ));

                clearScreen();
                powerlifters.findNthPercentile(userPercentile, percentileSortBy);
                return "\n";


            case "10":
                // Interact with powerlifters.txt

                /*
                   To be honest, I don't feel like my implementation of working with files is good.
                   I would appreciate if you gave me an idea of how to do it more efficiently.
                 */

                String userOption = getUserInput(
                        "Do you want to: " +
                                "\n1. Write information to a file?" +
                                "\n2. Read and print information from a file?" +
                                "\n3. Import information from a file?" +
                                "\nEnter your option here: "
                );

                outputMessage = interactWithFiles(userOption);
                return outputMessage;

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

    public static String updatePowerlifter(){

        if (powerlifters.getPowerliftersAsArray()[0] == null)
            return "The database is empty!\n";

        powerlifters.printList();
        int userIndex = Integer.parseInt(
                getUserInput("\nEnter the number of the powerlifter you want to update: "));

        Powerlifter powerlifter = powerlifters.getOne(userIndex - 1);
        String powerlifterName = powerlifter.fullName;

        String userOption = getUserInput(
                "Do you want to: " +
                        "\n1. Update the name?" +
                        "\n2. Update the age class?" +
                        "\n3. Update the weight class?" +
                        "\n4. Update best squat?" +
                        "\n5. Update best bench press?" +
                        "\n6. Update best deadlift?" +
                        "\n------------------------" +
                        "\nEnter your option here: "
        );


        switch (userOption){
            case "1":
                String newName = getUserInput("How do you want to rename the lifter? ");
                powerlifter.fullName = newName;

                return powerlifterName + " is now " + newName + "! \n";

            case "2":
                String newAgeClass = getUserInput("What is the new age class of the lifter? ");
                powerlifter.ageClass = newAgeClass;

                return powerlifterName + "'s age class is now: " + newAgeClass;

            case "3":
                String newWeightClass = getUserInput("What is the new weight class of the lifter? ");
                powerlifter.weightClass = newWeightClass;

                return powerlifterName + "'s weight class is now: " + newWeightClass;

            case "4":
                float newSquat = Float.parseFloat(
                        getUserInput("What is the new best squat of the lifter? ")
                );

                // Set new best squat, and update the total
                powerlifter.setBestSquatKg(newSquat);
                powerlifter.setBestTotalKg();

                return powerlifterName + "'s best squat has been updated to " + newSquat;

            case "5":
                float newBench = Float.parseFloat(
                        getUserInput("What is the new best bench of the lifter? ")
                );

                // Set new best bench, and update the total
                powerlifter.setBestBenchKg(newBench);
                powerlifter.setBestTotalKg();

                return powerlifterName + "'s best bench has been updated to " + newBench;

            case "6":
                float newDeadlift = Float.parseFloat(
                        getUserInput("What is the new best deadlift of the lifter? ")
                );

                // Set new best deadlift, and update the total
                powerlifter.setBestDeadliftKg(newDeadlift);
                powerlifter.setBestTotalKg();

                return powerlifterName + "'s best deadlift has been updated to " + newDeadlift;

            default:
                return "Option was invalid! \n";

        }





    }

    public static String interactWithFiles(String userOption) {
        clearScreen();
        String outputMessage;

        switch (userOption){
            case "1":
                try {
                    outputMessage = powerlifterWriter.saveToFile();
                    return outputMessage;

                } catch (Exception e){
                    return "Something went wrong...\n";

                }

            case "2":
                try{
                    powerlifterReader.readFromFile();
                    return "\n";

                } catch (Exception e){
                    return "Something went wrong...\n";
                }

            case "3":
                try {
                    outputMessage = powerlifterReader.importFromFile();
                    return outputMessage;

                } catch (Exception e){
                    System.out.println("Something went wrong...");
                }

            default:
                return "Invalid option! \n";

        }
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









