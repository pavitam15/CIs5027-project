public class LightController {

    // Light level is a double
    private double lightLevel;

    // Constructor
    public LightController(double lightLevel) {
        this.lightLevel = lightLevel;
    }

    // Getters and Setters
    public double getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(double lightLevel) {
        this.lightLevel = lightLevel;
    }

    // Convert double to string to be displayed to user
    @Override
    public String toString() {
        return "LightController{" +
                "lightLevel=" + lightLevel +
                '}';
    }
}