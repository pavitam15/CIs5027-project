public class TempController {

    // temperature is a float
    private float temperature;

    // Constructor
    public TempController(float temperature) {
        this.temperature = temperature;
    }

    // Getters and Setters
    public float getTemperature() {

        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    // Convert float to string to be displayed to user
    @Override
    public String toString() {
        return "TempController{" +
                "temperature=" + temperature +
                '}';
    }
}
