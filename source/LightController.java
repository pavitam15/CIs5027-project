public class LightController {

    private double lightLevel;

    public LightController(double lightLevel) {
        this.lightLevel = lightLevel;
    }

    public double getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(double lightLevel) {
        this.lightLevel = lightLevel;
    }

    @Override
    public String toString() {
        return "LightController{" +
                "lightLevel=" + lightLevel +
                '}';
    }
}