import java.io.*;

public class PowerlifterWriter {
    // Handles writing powerlifters to a file
    private final String fileName = "../powerlifters.txt";
    private final Gym powerlifters;


    PowerlifterWriter(Gym powerlifters){
        this.powerlifters = powerlifters;
    }

    public String saveToFile() throws IOException {
        /*
        ----------------------------------------------------
        Saves information about powerlifters to the file
        ----------------------------------------------------
        Returns:
            (String) output message for the dialogue.
        ----------------------------------------------------
         */

        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        if (powerlifters.getPowerliftersAsArray()[0] == null){
            writer.close();
            return "The database is empty... There is nothing to write! \n";
        }

        for (Powerlifter p : powerlifters.getPowerliftersAsArray()){
            writer.write(p.toString());
            writer.write("\n");
        }

        writer.close();
        return "Information has been successfully saved! \n";
    }

}
