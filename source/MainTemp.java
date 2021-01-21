import java.io.IOException;
import java.util.ArrayList;

public class MainTemp{

    public static void main(String[] args) {

        String path = args[0];
        CsvReaderTemp csvReaderTemp = new CsvReaderTemp(path);

        try {
            csvReaderTemp.read();
        } catch (IOException e) {
            System.err.println("Error reading file");
        }

        ArrayList<TempController> sensorlist = (ArrayList<TempController>) csvReaderTemp.getData();

        sensorlist.forEach(System.out::println);
    }
}