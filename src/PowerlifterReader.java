import java.io.*;
import java.util.ArrayList;

public class PowerlifterReader {
    // Handles reading powerlifters from a file
    private final String fileName = "../powerlifters.txt";
    private final Gym powerlifters;


    PowerlifterReader(Gym powerlifters){
        this.powerlifters = powerlifters;
    }


    private Powerlifter parsePowerlifter(String lineToParse){
        /*
        ----------------------------------------------------
        Parses a string and creates an instance of Powerlifter.
        ----------------------------------------------------
        Arguments:
            lineToParse: (String) Line to parse.
       ----------------------------------------------------
       Returns:
            (Powerlifter) powerlifter object made of the parsed string.
       ----------------------------------------------------
         */

        String[] data = lineToParse.split(", ");
        String fullName = data[0];
        String ageClass = data[1];
        String weightClass = data[2];

        float bestSquat = Float.parseFloat(data[3]);
        float bestBench = Float.parseFloat(data[4]);
        float bestDeadlift = Float.parseFloat(data[5]);

        Powerlifter powerlifter = new Powerlifter(fullName, ageClass, weightClass);
        powerlifter.setBestSquatKg(bestSquat);
        powerlifter.setBestBenchKg(bestBench);
        powerlifter.setBestDeadliftKg(bestDeadlift);
        powerlifter.setBestTotalKg();

        return powerlifter;

    }

    public void readFromFile(){
        /*
        ----------------------------------------------------
        Reads information from a file and prints it.
        ----------------------------------------------------
         */

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String nextLine = reader.readLine();
            boolean isEmpty = nextLine == null;

            ArrayList<Powerlifter> powerliftersArray = new ArrayList<>();

            while(!isEmpty){
                Powerlifter powerlifter = parsePowerlifter(nextLine);
                powerliftersArray.add(powerlifter);

                nextLine = reader.readLine();
                isEmpty = nextLine == null;
            }

            for (Powerlifter p : powerliftersArray){
                p.printOneLiner();
            }

        } catch (Exception e){
            System.out.println("Something went wrong...");
        }

    }

    public String importFromFile(){
        /*
         ----------------------------------------------------
         Reads a file with information about powerlifters, and
         saves it to the database.
         ----------------------------------------------------
         Returns:
            (String) output message for the dialogue.
         ----------------------------------------------------
         */

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String nextLine = reader.readLine();
            boolean isEmpty = nextLine == null;

            while(!isEmpty){
                Powerlifter powerlifter = parsePowerlifter(nextLine);

                // If the powerlifter doesn't already exist, add it to the array
                if (!(powerlifters.powerlifterExists(powerlifter.fullName)))
                    powerlifters.add(powerlifter);

                nextLine = reader.readLine();
                isEmpty = nextLine == null;

            }
            return "Powerlifters have been successfully imported! \n";

        } catch (Exception e){
            return "Something went wrong...";
        }
    }
}
