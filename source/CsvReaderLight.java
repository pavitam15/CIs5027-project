import apps.BulbApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReaderLight implements IReader {

    private static final String splitBy = ","; // Separate values with comma
    private String filename;
    private ArrayList<LightController> sensorlist; //List with getters and setters from LightController class

    // Constructor
    public CsvReaderLight(String filename) {
        this.filename = filename;
        this.sensorlist = new ArrayList<LightController>();
    }

    // Override interface method
    @Override
    public void read() throws IOException {
        String line = "";

        // Open bulb app
        BulbApp bulb = new BulbApp();
        bulb.setVisible(true);

        try(BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            int row_count = 0;

            while ((line = br.readLine()) != null) {
                if(row_count == 0) {
                    row_count++;
                    continue; // Skip first line of csv
                }

                String[] data = line.split(splitBy);
                this.sensorlist.add(new LightController(Integer.parseInt(data[7]))); // Get values from 7th row - light levels
                row_count++;
            }
        }
    }

    // Override getData interface method and return list
    @Override
    public Object getData() {
        return sensorlist;
    }
}