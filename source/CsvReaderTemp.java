import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReaderTemp implements IReader {

    private static final String splitBy = ",";
    private String filename;
    private ArrayList<TempController> sensorlist;

    public CsvReaderTemp(String filename) {
        this.filename = filename;
        this.sensorlist = new ArrayList<TempController>();
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
                this.sensorlist.add(new TempController(Integer.parseInt(data[0])));
                row_count++;
            }
        }
    }

    @Override
    public Object getData() {
        return sensorlist;
    }
}