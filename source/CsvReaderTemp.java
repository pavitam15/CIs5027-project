import apps.FanApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReaderTemp implements IReader {

    private static final String splitBy = ","; // Separate values with comma
    private String filename;
    private ArrayList<TempController> sensorlist; // List with getters and setters from TempController class

    // Constructor
    public CsvReaderTemp(String filename) {
        this.filename = filename;
        this.sensorlist = new ArrayList<TempController>();
    }

    // Override read interface method
    @Override
    public void read() throws IOException {
        String line = "";

        try(BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            int row_count = 0;

            // Open fan app
            FanApp fan = new FanApp();
            fan.setVisible(true);

            while ((line = br.readLine()) != null) {
                if(row_count == 0) {
                    row_count++;
                    continue; // Skip first line of csv
                }

                String[] data = line.split(splitBy);
                this.sensorlist.add(new TempController(Float.parseFloat(data[9]))); // Get values from 9th row - temperature
                row_count++;
            }
        }
    }

    // Override getData interface method and return list
    @Override
    public Object getData() {
        return sensorlist;
    }

    /*public static void main(String[] args) {

        String path = args[0];
        CsvReaderTemp csvReaderTemp = new CsvReaderTemp(path);
    }*/
}