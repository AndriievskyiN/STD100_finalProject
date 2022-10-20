import java.io.*;

public class PowerlifterWriter {
    // Handles writing powerlifters to a file
    private final String fileName = "powerlifters.txt";

    public void saveToFile(Powerlifter[] powerlifters) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        for (Powerlifter p : powerlifters){
            writer.write(p.toString());
            writer.write("\n");
        }

        writer.close();

    }

}
