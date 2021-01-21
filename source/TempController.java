public class TempController {

    private int temperature;

    public TempController(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TempController{" +
                "temperature=" + temperature +
                '}';
    }
}
