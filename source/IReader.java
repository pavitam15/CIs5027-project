import java.io.IOException;

public interface IReader {
    void read() throws IOException;
    Object getData();
}