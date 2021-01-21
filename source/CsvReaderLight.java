import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReaderLight implements IReader {

    private static final String splitBy = ",";
    private String filename;
    private ArrayList<LightController> sensorlist;

    public CsvReaderLight(String filename) {
        this.filename = filename;
        this.sensorlist = new ArrayList<LightController>();
    }

    @Override
    public void read() throws IOException {
        String line = "";

        try(BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            int row_count = 0;

            while ((line = br.readLine()) != null) {
                if(row_count == 0) {
                    row_count++;
                    continue;
                }

                String[] data = line.split(splitBy);
                this.sensorlist.add(new LightController(Integer.parseInt(data[0])));
                row_count++;
            }
        }
    }

    @Override
    public Object getData() {
        return sensorlist;
    }
}