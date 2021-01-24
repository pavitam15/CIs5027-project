package components;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Bulb{

    //private final int triWidth = 20, triHeight = 40; //Wing triangle variables
    //private final int arcWidth = 20, arcHeight = 10; //Wing arc variables

    private int centerX, centerY; //The center of the bulb
    //private double angel; //Angel in radians

    private	int bulbBright;
    private BulbPanel bulb_panel;

    public void setBulbJPanel(BulbPanel bulbJPanel) {
        this.bulb_panel = bulbJPanel;
    }

    public int getBulbBright() {
        return bulbBright;
    }

    public void setBulbBright(int bright) {
        this.bulbBright = bright;
        //this.bulb_panel.setTimer(this.bulbBright);
    }

    public Bulb(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void update() {
        //angel = addRad(angel, 0.1);
    }

    private double addRad(double current, double addition) {
        double value = current + addition;

        if (value >= 2 * Math.PI)
            value -= 2 * Math.PI;
        return value;
    }

    // input the value for circle and square.
    Shape circle=new Ellipse2D.Float(100.0f,100.0f,100.0f,100.0f);

    public void draw(Graphics g) {

        Graphics2D gx = (Graphics2D) g;
        gx.draw(circle);
        gx.setPaint(Color.yellow);
        gx.fill(circle);

        //Make it look a little prettier
        //gx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Setting up triangle.
        //We only want to do the calculations for the top wing. We rotate the top wing to get the other four wings.
        //int[] x = { centerX + triWidth / 2, centerX - triWidth / 2, centerX };
        //int[] y = { centerY - triHeight, centerY - triHeight, centerY };
        //Polygon triangle = new Polygon(x, y, x.length);

        //Rotate 'angel' radians around the center
        //gx.rotate(angel, centerX, centerY);

        /*for (int i = 0; i < 4; i++) {
            //Draw the wing arc (top)
            //Start with an angel of 0 for starting horizontal left to right. Let the arc have an angel of 180
            gx.fillArc(centerX - triWidth / 2, centerY - triHeight - arcHeight / 2, arcWidth, arcHeight, 0, 180);

            //Draw the wing triangle
            gx.fillPolygon(triangle);

            //Rotate on top of the existing rotation. Rotate 45 degrees
            gx.rotate(Math.PI / 2, centerX, centerY);
        } */
    }
}
