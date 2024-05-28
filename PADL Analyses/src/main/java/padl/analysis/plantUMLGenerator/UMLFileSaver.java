package padl.analysis.plantUMLGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UMLFileSaver {
    public static void saveToFile(String plantUML, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(plantUML);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing the PlantUML to file: " + e.getMessage());
        }
    }
}
