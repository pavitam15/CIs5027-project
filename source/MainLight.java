/* import java.io.IOException;
import java.util.ArrayList;

public class MainLight {

    public static void main(String[] args) {

        String filename = args[0];
        CsvReaderLight csvReaderLight = new CsvReaderLight(filename);

        try {
            csvReaderLight.read();
        } catch (IOException e) {
            System.err.println("Error reading file");
        }

        ArrayList<LightController> sensorlist = (ArrayList<LightController>) csvReaderLight.getData();

        sensorlist.forEach(System.out::println);
    }
} */