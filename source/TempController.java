
public class TempController {

    private float temperature;

    public TempController(float temperature) {
        this.temperature = temperature;
    }

    public float getTemperature() {

        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TempController{" +
                "temperature=" + temperature +
                '}';
    }
}
