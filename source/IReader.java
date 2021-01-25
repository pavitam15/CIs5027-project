import java.io.IOException;

// interface for csv abstract methods
public interface IReader {
    void read() throws IOException;
    Object getData();
}