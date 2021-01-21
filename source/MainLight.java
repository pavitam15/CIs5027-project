import java.io.IOException;
import java.util.ArrayList;

public class MainLight {

    public static void main(String[] args) {

        String path = args[0];
        CsvReaderLight csvReaderLight = new CsvReaderLight(path);

        try {
            csvReaderLight.read();
        } catch (IOException e) {
            System.err.println("Error reading file");
        }

        ArrayList<LightController> sensorlist = (ArrayList<LightController>) csvReaderLight.getData();

        sensorlist.forEach(System.out::println);
    }
}