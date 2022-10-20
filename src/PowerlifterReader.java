import java.io.*;
import java.util.ArrayList;

public class PowerlifterReader {
    // Handles reading powerlifters from a file
    private final String fileName = "powerlifters.txt";
    private Gym powerlifters;


    PowerlifterReader(Gym powerlifters){
        this.powerlifters = powerlifters;
    }


    private Powerlifter parsePowerlifter(String lineToParse){
        String[] data = lineToParse.split(", ");
        String fullName = data[0];
        String ageClass = data[1];
        String weightClass = data[2];

        float bestSquat = Float.parseFloat(data[3]);
        float bestBench = Float.parseFloat(data[4]);
        float bestDeadlift = Float.parseFloat(data[5]);

        Powerlifter powerlifter = new Powerlifter(fullName, ageClass, weightClass);
        powerlifter.setBestSquatKg(bestSquat);
        powerlifter.setBestSquatKg(bestBench);
        powerlifter.setBestDeadliftKg(bestDeadlift);

        return powerlifter;

    }

    public void readFromFile() throws IOException{
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

    public void importFromFile() throws IOException{
        /*
         ----------------------------------------------------
         Reads a file with information about powerlifters, and
         saves it to the database.
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

        } catch (Exception e){
            System.out.println("Something went wrong...");
        }


    }


}
